package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.commands.DriveArcade;

public class DriveTrain extends Subsystem {

  private static final double PEAK_OUTPUT = 0.5;

  private WPI_TalonSRX leftMotor1 = new WPI_TalonSRX(RobotMap.LEFT_MOTOR1_ADDRESS); // encoder
  private WPI_TalonSRX leftMotor2 = new WPI_TalonSRX(RobotMap.LEFT_MOTOR2_ADDRESS);

  private WPI_TalonSRX rightMotor1 = new WPI_TalonSRX(RobotMap.RIGHT_MOTOR1_ADDRESS); // encoder
  private WPI_TalonSRX rightMotor2 = new WPI_TalonSRX(RobotMap.RIGHT_MOTOR2_ADDRESS);

  private DifferentialDrive robotDrive = new DifferentialDrive(leftMotor1, rightMotor1);

  public ColorSensor sensor;

  public DriveTrain() {
    sensor = new ColorSensor(Port.kOnboard);

    setMotorsAllowablePower(leftMotor1);
    setMotorsAllowablePower(leftMotor2);

    setMotorsAllowablePower(rightMotor1);
    setMotorsAllowablePower(rightMotor2);

    leftMotor2.setInverted(false);
    leftMotor2.set(ControlMode.Follower, leftMotor1.getDeviceID());

    rightMotor2.setInverted(false);
    rightMotor2.set(ControlMode.Follower, rightMotor1.getDeviceID());

    robotDrive.setSafetyEnabled(false);
  }

  private void setMotorsAllowablePower(WPI_TalonSRX motor) {
    motor.configNominalOutputForward(0.0, DriveTrainConstants.kTimeoutMs);
    motor.configNominalOutputReverse(0.0, DriveTrainConstants.kTimeoutMs);
    motor.configPeakOutputForward(PEAK_OUTPUT, DriveTrainConstants.kTimeoutMs);
    motor.configPeakOutputReverse(-PEAK_OUTPUT, DriveTrainConstants.kTimeoutMs);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveArcade());
  }
  public void driveArcadeMethod(double Speed, double Rotation) {
    
    
    double rotationValueGain = 0.70; // for full rotation speed, use 1. Tune to have smoother rotation.
    Rotation = Rotation * rotationValueGain;
    
    double GoStraightCompensation = 0;
    if(Math.abs(Speed) > 0.1)
    {
      GoStraightCompensation = Speed * DriveTrainConstants.GO_STRAIGHT_COMPENSATION_DYNAMIC + DriveTrainConstants.GO_STRAIGHT_COMPENSATION_STATIC * Math.signum(Speed)  ; 
      // TODO Tune this value. Has a speed proportional component (friction in mechanism()  and a fixed component
    }
    
    robotDrive.arcadeDrive(Speed, Rotation + GoStraightCompensation);       	
}

}
