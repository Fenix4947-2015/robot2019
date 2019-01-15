package org.usfirst.frc.team4947.robot.subsystems;
import org.usfirst.frc.team4947.robot.Robot;
import org.usfirst.frc.team4947.robot.RobotMap;
import org.usfirst.frc.team4947.robot.commands.DriveArcade;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {

	// Constants.
	private static final double WHEEL_DIAMETER_IN_INCHES = 6;
	private static final double WHEEL_RADIUS_IN_INCHES = (WHEEL_DIAMETER_IN_INCHES / 2.0);
	private static final double WHEEL_CIRCUMFERENCE_IN_INCHES = (2 * Math.PI * WHEEL_RADIUS_IN_INCHES);

	private static final double PULSES_PER_FOOT = 292;
	
	// Tuning parameters
	private static final double GO_STRAIGHT_COMPENSATION_STATIC = -0.08;
	private static final double GO_STRAIGHT_COMPENSATION_DYNAMIC = -0.03;
	private static final double MAX_SPEED_FOR_SHIFT_DOWN_FT_PER_SEC = 3.5;
	// Position PID
	private static final double MAX_CLOSED_LOOP_MODE_PERCENT_OUTPUT = 0.7;
	private static final double POSITION_PID_P = 0.75;
	private static final double POSITION_PID_I = 0.0;
	private static final double POSITION_PID_D = 0.0;
	private static final double POSITION_PID_F = 0.0;
	
	private static final double IS_MOVING_THRESHOLD_GYRORATE = 1.0 ; // deg/sec
	private static final double IS_MOVING_THRESHOLD_WHEELS = 0.1 ; // ft/sec
	
	/** Not used. */
	private static final double INCHES_PER_FOOT = 12.0;
	private static final double ENCODER_PULSES_PER_REVOLUTION = 1440;
	private static final double INCHES_PER_ENCODER_REVOLUTION = (3 * WHEEL_CIRCUMFERENCE_IN_INCHES);
	
	// Members.
	private WPI_TalonSRX leftMotor1 = new WPI_TalonSRX(RobotMap.LEFT_MOTOR1_ADDRESS); // encoder
	private WPI_TalonSRX leftMotor2 = new WPI_TalonSRX(RobotMap.LEFT_MOTOR2_ADDRESS);
	
	private WPI_TalonSRX rightMotor1 = new WPI_TalonSRX(RobotMap.RIGHT_MOTOR1_ADDRESS); // encoder
	private WPI_TalonSRX rightMotor2 = new WPI_TalonSRX(RobotMap.RIGHT_MOTOR2_ADDRESS);
	
	private DifferentialDrive robotDrive = new DifferentialDrive(leftMotor1, rightMotor1);
		
	private Solenoid gearboxSpeedSolenoid = new Solenoid(RobotMap.GEARBOX_SPEEDSOLENOID_ADDRESS);
	
	public boolean autonomousmode = false;

	//http://first.wpi.edu/FRC/roborio/release/docs/java/
	//https://ez.analog.com/blogs/engineeringmind/authors/ColmPrendergast
	ADXRS450_Gyro gyro;	
	
	// Define names for the shifter possibilities.
	public enum ShifterSpeed
	{
		Fast(true),
		Slow(false);
		
		private boolean value;
		ShifterSpeed(boolean value){
			this.value = value;
		}
		
		public boolean getValue() {
			return value;
		}
	}
	
	
	public DriveTrain()
	{			
		robotDrive.setSafetyEnabled(false);			 
		gyro = new ADXRS450_Gyro();
		resetGyroAngle();		 
	}
	
	public void initTeleop()
	{
		autonomousmode = false;
		gearboxShift(ShifterSpeed.Slow);
		
		boolean kMotorLeftInvert = false;
		initEncoder(leftMotor1,kMotorLeftInvert);
		leftMotor2.setInverted(kMotorLeftInvert);
		leftMotor2.set(ControlMode.Follower, leftMotor1.getDeviceID());
				
		boolean kMotorRightInvert = false; // to validate. tested on test robot. // to make work for closed loop also.
		initEncoder(rightMotor1,kMotorRightInvert);		
		rightMotor2.setInverted(kMotorRightInvert);	
		rightMotor2.set(ControlMode.Follower, rightMotor1.getDeviceID());		
		
		setAllMotorsAllowablePower(1.0);
		setDefaultCommand(new DriveArcade());
	}
	
	public void initAutonomous()
	{
		autonomousmode = true;
		gearboxShift(ShifterSpeed.Slow);
		
		boolean kMotorLeftInvert = true;
		initEncoder(leftMotor1,kMotorLeftInvert);
		leftMotor2.setInverted(kMotorLeftInvert);
		leftMotor2.set(ControlMode.Follower, leftMotor1.getDeviceID());
				
		boolean kMotorRightInvert = false;
		initEncoder(rightMotor1,kMotorRightInvert);		
		rightMotor2.setInverted(kMotorRightInvert);	
		rightMotor2.set(ControlMode.Follower, rightMotor1.getDeviceID());			
		
		setAllMotorsAllowablePower(MAX_CLOSED_LOOP_MODE_PERCENT_OUTPUT);
		setDefaultCommand(null);		
	}
	
    public void initDefaultCommand() {}
    
    public void calibrateGyro()
    {
    	gyro.calibrate();
    }
    
    private void setMotorAllowablePower(WPI_TalonSRX motor, double peakPercentOutput)
    {
		// Set the peak and nominal outputs, 12V means full.
    	motor.configNominalOutputForward(0, DriveTrainConstants.kTimeoutMs);
    	motor.configNominalOutputReverse(0, DriveTrainConstants.kTimeoutMs);
    	motor.configPeakOutputForward(peakPercentOutput, DriveTrainConstants.kTimeoutMs);
    	motor.configPeakOutputReverse(-peakPercentOutput, DriveTrainConstants.kTimeoutMs);
    }
    
    public void setAllMotorsAllowablePower(double peakPercentOutput)
    {    	
    	setMotorAllowablePower(leftMotor1,peakPercentOutput);
		setMotorAllowablePower(leftMotor2,peakPercentOutput);
		setMotorAllowablePower(rightMotor1,peakPercentOutput);
		setMotorAllowablePower(rightMotor2,peakPercentOutput);
    }
    
    public void driveToDistance(double distance_feet)
    {	
		positionPIDForward(leftMotor1,distance_feet);
		positionPIDForward(rightMotor1,distance_feet);
    }
    
    private void positionPIDForward(WPI_TalonSRX motor, double feet) 
    {	
    	// reinitialize counts for relative motion
		int absolutePosition = 0;
		motor.setSelectedSensorPosition(
				absolutePosition, 
				DriveTrainConstants.kPIDLoopIdx,
				DriveTrainConstants.kTimeoutMs);
			
		motor.set(ControlMode.Position, feetToEncoderCounts(feet));
	}
    
    public double getEncoderDistanceErrorFeet()
    {
    	// return error remaining on the closed loop, in foot. 
    	  int leftError = leftMotor1.getClosedLoopError(DriveTrainConstants.kPIDLoopIdx);
    	  int rightError = rightMotor1.getClosedLoopError(DriveTrainConstants.kPIDLoopIdx);
    	  int totalError = (leftError + rightError); // in counts
    	  double totalErrorFeet = encoderCountsToFeet(totalError);
    	  
    	  double averageErrorFeet = totalErrorFeet/2.0;
    	  
    	  return averageErrorFeet;
    }
    
    public double getAverageRobotSpeed_ft_s()
    {
    	//https://github.com/CrossTheRoadElec/Phoenix-Documentation/blob/master/README.md#what-are-the-units-of-my-sensor
    	int leftMotorSpd_UnitsPer100ms = leftMotor1.getSelectedSensorVelocity(DriveTrainConstants.kPIDLoopIdx);
    	int rightMotorSpd_UnitsPer100ms = rightMotor1.getSelectedSensorVelocity(DriveTrainConstants.kPIDLoopIdx);
    	
    	int sumspd = leftMotorSpd_UnitsPer100ms + rightMotorSpd_UnitsPer100ms;
    	double totalspd_ft_per_100ms = encoderCountsToFeet(sumspd);
    	double spd_ft_per_sec = totalspd_ft_per_100ms *10.0 ;
    	
    	return spd_ft_per_sec;
    }
    
    private double encoderCountsToFeet(int encoderCounts)
    {    	
    	return ((double)(encoderCounts) / PULSES_PER_FOOT);
    }
    
    private double feetToEncoderCounts(double feet)
    {    	
		return (feet * PULSES_PER_FOOT);
    }
    
	// GitHub: https://github.com/CrossTheRoadElec/Phoenix-Documentation
	// Closed-loop: https://github.com/CrossTheRoadElec/Phoenix-Documentation#closed-loop-using-sensor-control
	// Source: https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/tree/master/Java/PositionClosedLoop
	private static void initEncoder(WPI_TalonSRX motor, boolean kMotorInvert) {
		// Choose the sensor and sensor direction.
		motor.configSelectedFeedbackSensor(
				FeedbackDevice.QuadEncoder, 
				DriveTrainConstants.kPIDLoopIdx,
				DriveTrainConstants.kTimeoutMs);

		// Choose to ensure sensor is positive when output is positive.
		motor.setSensorPhase(DriveTrainConstants.kSensorPhase);

		// Choose based on what direction you want forward/positive to be.
		// This does not affect sensor phase.
		motor.setInverted(kMotorInvert);

		// Set the allowable closed-loop error, Closed-Loop output will be neutral within this range.
		// See Table in Section 17.2.1 for native units per rotation.
		motor.configAllowableClosedloopError(DriveTrainConstants.kPIDLoopIdx, DriveTrainConstants.kPIDLoopIdx, DriveTrainConstants.kTimeoutMs);

		// Set closed loop gains in slot0, typically kF stays zero.
		motor.config_kF(DriveTrainConstants.kPIDLoopIdx, POSITION_PID_F, DriveTrainConstants.kTimeoutMs);
		motor.config_kP(DriveTrainConstants.kPIDLoopIdx, POSITION_PID_P, DriveTrainConstants.kTimeoutMs);
		motor.config_kI(DriveTrainConstants.kPIDLoopIdx, POSITION_PID_I, DriveTrainConstants.kTimeoutMs);
		motor.config_kD(DriveTrainConstants.kPIDLoopIdx, POSITION_PID_D, DriveTrainConstants.kTimeoutMs);

		int absolutePosition = 0;
		motor.setSelectedSensorPosition(
				absolutePosition, 
				DriveTrainConstants.kPIDLoopIdx,
				DriveTrainConstants.kTimeoutMs);
	}
    
     public void driveArcadeMethod(double Speed, double Rotation) {
    	//ptoSolenoid.set(true);
    	SmartDashboard.putNumber("ForwardSpeed", Speed);
    	SmartDashboard.putNumber("RotationSpeed", Rotation);
    	
    	double rotationValueGain = 0.70; // for full rotation speed, use 1. Tune to have smoother rotation.
    	Rotation = Rotation * rotationValueGain;
    	
    	double GoStraightCompensation = 0;
    	if(Math.abs(Speed) > 0.1)
    	{
    		GoStraightCompensation = Speed * GO_STRAIGHT_COMPENSATION_DYNAMIC + GO_STRAIGHT_COMPENSATION_STATIC * Math.signum(Speed)  ; 
    		// TODO Tune this value. Has a speed proportional component (friction in mechanism()  and a fixed component
    	}
    	SmartDashboard.putNumber("Go Straight Compensation", GoStraightCompensation);
    	
    	robotDrive.arcadeDrive(Speed, Rotation + GoStraightCompensation);       	
    }
     
     public void rawDrive(double leftMot, double rightMot)
     {
    	 leftMotor1.set(ControlMode.PercentOutput, leftMot);
    	 rightMotor1.set(ControlMode.PercentOutput, rightMot);
     }
    
    public void driveStop()
    {
    	leftMotor1.stopMotor(); // motor 2 in follower mode.
    	rightMotor1.stopMotor(); // motor 2 in follower mode.    	
    }
    
    
    public void gearboxShift(ShifterSpeed desiredSpeed)
    {
    	if(gearboxSpeedSolenoid.get() == ShifterSpeed.Fast.value)
    	{
    		// When moving fast on fast gear, shifting down creates a loss of control
    		if(Math.abs(getAverageRobotSpeed_ft_s()) < MAX_SPEED_FOR_SHIFT_DOWN_FT_PER_SEC)
    		{
    			gearboxSpeedSolenoid.set(desiredSpeed.getValue());  
    		}
    	}
    	else
    	{
    		gearboxSpeedSolenoid.set(desiredSpeed.getValue());   
    	} 	
    }
    
    public double getGyroAngle()
    {
    	// positive angles are clockwise direction. 
    	return gyro.getAngle();
    }
    
    public void resetGyroAngle()
    {
    	gyro.reset();
    }
    
    public boolean isRobotMoving()
    {
    	boolean isRotating = gyro.getRate()<IS_MOVING_THRESHOLD_GYRORATE;
    	boolean wheelsRolling = getAverageRobotSpeed_ft_s() < IS_MOVING_THRESHOLD_WHEELS;
    	boolean isMoving = isRotating && wheelsRolling;
    	return isMoving;
    }
    
    public void log()
    {
    	SmartDashboard.putNumber("GyroAngleAbsolute", getGyroAngle());
    	int leftPosition = leftMotor1.getSelectedSensorPosition(0);
		int rightPosition = rightMotor1.getSelectedSensorPosition(0);

		SmartDashboard.putNumber("Left Sensor position", leftPosition);
		SmartDashboard.putNumber("Right Sensor position", rightPosition);


    }
}

