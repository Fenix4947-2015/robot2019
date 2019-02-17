package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.balloonbox.ManualPivot;

public class BalloonBox extends Subsystem {

    private static final int MOTOR_CONFIG_TIMEOUT_IN_MS = 30;

    private static final boolean SOLENOID_STATE_OPEN = false;
    private static final boolean SOLENOID_STATE_CLOSE = !SOLENOID_STATE_OPEN;

    private WPI_TalonSRX intakeRollerMotor;
    private WPI_TalonSRX pivotMotor;
    private Solenoid leftSolenoid;
    private Solenoid rightSolenoid;

    public BalloonBox() {
        createIntakeRollerMotor();
        createPivotMotor();
        createLeftSolenoid();
        createRightSolenoid();
    }

    private void createIntakeRollerMotor() {
        intakeRollerMotor = new WPI_TalonSRX(RobotMap.INTAKE_ROLLER_MOTOR_ADDRESS);
        intakeRollerMotor.configFactoryDefault();

        intakeRollerMotor.setNeutralMode(NeutralMode.Brake);
        intakeRollerMotor.setSafetyEnabled(false);

        intakeRollerMotor.configNominalOutputForward(0.0, MOTOR_CONFIG_TIMEOUT_IN_MS);
        intakeRollerMotor.configNominalOutputReverse(0.0, MOTOR_CONFIG_TIMEOUT_IN_MS);
        intakeRollerMotor.configPeakOutputForward(1.0, MOTOR_CONFIG_TIMEOUT_IN_MS);
        intakeRollerMotor.configPeakOutputReverse(-1.0, MOTOR_CONFIG_TIMEOUT_IN_MS);
    }

    private void createPivotMotor() {
        pivotMotor = new WPI_TalonSRX(RobotMap.PIVOT_MOTOR_ADDRESS);
        pivotMotor.configFactoryDefault();

        pivotMotor.setNeutralMode(NeutralMode.Brake);
        pivotMotor.setSafetyEnabled(false);

        pivotMotor.configNominalOutputForward(0.0, MOTOR_CONFIG_TIMEOUT_IN_MS);
        pivotMotor.configNominalOutputReverse(0.0, MOTOR_CONFIG_TIMEOUT_IN_MS);
        pivotMotor.configPeakOutputForward(1.0, MOTOR_CONFIG_TIMEOUT_IN_MS);
        pivotMotor.configPeakOutputReverse(-1.0, MOTOR_CONFIG_TIMEOUT_IN_MS);
    }    

    private void createLeftSolenoid() {
        leftSolenoid = new Solenoid(RobotMap.LEFT_SOLENOID_ADDRESS);
    }

    private void createRightSolenoid() {
        rightSolenoid = new Solenoid(RobotMap.RIGHT_SOLENOID_ADDRESS);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ManualPivot());
    }

    public void intakeRollInside() {
        intakeRollerMotor.set(ControlMode.PercentOutput, 0.4);
    }

    public void intakeStop() {
        intakeRollerMotor.set(ControlMode.PercentOutput, 0.0);
    }

    public void openLeft() {
        open(leftSolenoid);
    }

    public void openRight() {
        open(rightSolenoid);
    }

    private void open(Solenoid solenoid) {
        solenoid.set(SOLENOID_STATE_OPEN);
    }

    public void closeLeft() {
        close(leftSolenoid);
    }

    public void closeRight() {
        close(rightSolenoid);
    }

    private void close(Solenoid solenoid) {
        solenoid.set(SOLENOID_STATE_CLOSE);
    }

    public void pivot(double output) {
        pivotMotor.set(ControlMode.PercentOutput, output);
    }

    public void pivotStop() {
        pivotMotor.set(ControlMode.PercentOutput, 0.0);
    }

    public void robotPeriodic() {
        SmartDashboard.putNumber("Pivot motor %", pivotMotor.getMotorOutputPercent());
    }
}
