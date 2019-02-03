package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.commands.drivetrain.DriveArcade;

public class DriveTrain extends Subsystem {

  private static final double PEAK_OUTPUT = 0.5;

  private static final double FRONT_DIRECTION_INTAKE = 1.0;
  private static final double FRONT_DIRECTION_PANEL_GRIPPER = -1.0;

  private WPI_TalonSRX leftMotor1 = new WPI_TalonSRX(RobotMap.LEFT_MOTOR1_ADDRESS); // encoder
  private WPI_TalonSRX leftMotor2 = new WPI_TalonSRX(RobotMap.LEFT_MOTOR2_ADDRESS);

  private WPI_TalonSRX rightMotor1 = new WPI_TalonSRX(RobotMap.RIGHT_MOTOR1_ADDRESS); // encoder
  private WPI_TalonSRX rightMotor2 = new WPI_TalonSRX(RobotMap.RIGHT_MOTOR2_ADDRESS);

  private DifferentialDrive robotDrive = new DifferentialDrive(leftMotor1, rightMotor1);

  public ColorSensor sensor;
  private double direction;

  public DriveTrain() {
    sensor = new ColorSensor(Port.kOnboard);
    direction = FRONT_DIRECTION_INTAKE;

    // Initialize drivetrain motors
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
    motor.configNominalOutputForward(0.0, DriveTrainConstants.TIMEOUT_MS);
    motor.configNominalOutputReverse(0.0, DriveTrainConstants.TIMEOUT_MS);
    motor.configPeakOutputForward(PEAK_OUTPUT, DriveTrainConstants.TIMEOUT_MS);
    motor.configPeakOutputReverse(-PEAK_OUTPUT, DriveTrainConstants.TIMEOUT_MS);
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
      // TODO Tune the constant values. Has a speed proportional component (friction in mechanism()  and a fixed component
      GoStraightCompensation = Speed * DriveTrainConstants.GO_STRAIGHT_COMPENSATION_DYNAMIC + DriveTrainConstants.GO_STRAIGHT_COMPENSATION_STATIC * Math.signum(Speed)  ;             
    }

    Speed *= direction;
    Rotation = direction * (Rotation + GoStraightCompensation);

    robotDrive.arcadeDrive(Speed, Rotation);
}

    public void setFrontToIntake() {
        direction = FRONT_DIRECTION_INTAKE;
    }

    public void setFrontToPanelGripper() {
        direction = FRONT_DIRECTION_PANEL_GRIPPER;
    }
}
