package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.balloonbox.ManualControl;

public class BalloonBox extends Subsystem {

    private static final int MOTOR_CONFIG_TIMEOUT_IN_MS = 30;

    private static final boolean SOLENOID_STATE_OPEN = true;
    private static final boolean SOLENOID_STATE_CLOSE = !SOLENOID_STATE_OPEN;

    private WPI_TalonSRX intakeRollerMotor;
    private WPI_TalonSRX pivotMotor;
    private Solenoid leftSolenoid;
    private Solenoid rightSolenoid;

    private DigitalInput pivotLimitSwitchLow;
    private DigitalInput pivotLimitSwitchHigh;

    private static final boolean LIMIT_SWITCH_PRESSED_STATE = false;
    private static final boolean LIMIT_SWITCH_RELEASED_STATE = !LIMIT_SWITCH_PRESSED_STATE;

    public BalloonBox() {
        createIntakeRollerMotor();
        createPivotMotor();

        leftSolenoid = new Solenoid(RobotMap.LEFT_SOLENOID_ADDRESS);
        rightSolenoid = new Solenoid(RobotMap.RIGHT_SOLENOID_ADDRESS);

        pivotLimitSwitchLow = new DigitalInput(RobotMap.PIVOT_LIMIT_SWITCH_LOW);
        pivotLimitSwitchHigh = new DigitalInput(RobotMap.PIVOT_LIMIT_SWITCH_HIGH);

        closeLeft();
        closeRight();
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

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ManualControl());
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
        // prohibit speed to be positive if we are at max position already.
        double limitProtectedOutput = output;
        if (pivotLimitSwitchHigh.get() == LIMIT_SWITCH_PRESSED_STATE) {
            limitProtectedOutput = Math.min(output, 0.0);
        }
        if (pivotLimitSwitchLow.get() == LIMIT_SWITCH_PRESSED_STATE) {
            limitProtectedOutput = Math.max(output, 0.0);
        }
        pivotMotor.set(ControlMode.PercentOutput, limitProtectedOutput);
    }

    public void pivotStop() {
        pivotMotor.set(ControlMode.PercentOutput, 0.0);
    }

    public void log() {
        SmartDashboard.putNumber("Pivot motor %", pivotMotor.getMotorOutputPercent());
    }
}
