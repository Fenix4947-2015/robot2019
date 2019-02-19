package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.balloonbox.ManualControl;

public class BalloonBox extends Subsystem {

    public static final double FLIPPER_TIMEOUT_IN_S = 1.0;

    private static final int MOTOR_CONFIG_TIMEOUT_IN_MS = 30;
    private static final int PID_LOOP_ID = 0;
    private static final double TOP_VELOCITY = 2500.0;
    private static final int    PID_LOOP = 0;
    private static final int    PID_SLOT = 0;
    private static final int    PID_ALLOWABLE_CLOSED_LOOP_ERROR_IN_COUNTS = 0;
    private static final double PID_P = 0.05;
    private static final double PID_I = 0.0;
    private static final double PID_D = 0.0;
    private static final double PID_F = (0.75 * 1023) / TOP_VELOCITY;  

    private static final int ACCELERATION = (int) (TOP_VELOCITY / 2.0);
    private static final int CRUISE_VELOCITY = (int) (TOP_VELOCITY / 2.0);
    private static final double FEED_FORWARD = 0.0;

    private static final boolean LIMIT_SWITCH_PRESSED_STATE = false;
    private static final boolean LIMIT_SWITCH_RELEASED_STATE = !LIMIT_SWITCH_PRESSED_STATE;

    private static final boolean FLIPPER_STATE_OPEN = true;
    private static final boolean FLIPPER_STATE_CLOSE = !FLIPPER_STATE_OPEN;

    private WPI_TalonSRX intakeRollerMotor;

    private WPI_TalonSRX pivotMotor;
    private DigitalInput pivotLimitSwitchLow;
    private DigitalInput pivotLimitSwitchHigh;

    private Solenoid flipperLeftSolenoid;
    private Solenoid flipperRightSolenoid;

    public BalloonBox() {
        createIntakeRollerMotor();

        createPivotMotor();
        pivotLimitSwitchLow = new DigitalInput(RobotMap.PIVOT_LIMIT_SWITCH_LOW);
        pivotLimitSwitchHigh = new DigitalInput(RobotMap.PIVOT_LIMIT_SWITCH_HIGH);

        flipperLeftSolenoid = new Solenoid(RobotMap.FLIPPER_LEFT_SOLENOID_ADDRESS);
        flipperRightSolenoid = new Solenoid(RobotMap.FLIPPER_RIGHT_SOLENOID_ADDRESS);
    }

    private void createIntakeRollerMotor() {
        intakeRollerMotor = createMotor(RobotMap.INTAKE_ROLLER_MOTOR_ADDRESS);
    }

    private void createPivotMotor() {
        pivotMotor = createMotor(RobotMap.PIVOT_MOTOR_ADDRESS);
    }

    private static WPI_TalonSRX createMotor(int deviceNumber) {
        WPI_TalonSRX motor = new WPI_TalonSRX(deviceNumber);
        motor.configFactoryDefault();

        motor.setNeutralMode(NeutralMode.Brake);
        motor.setSafetyEnabled(false);

        motor.configNominalOutputForward(0.0, MOTOR_CONFIG_TIMEOUT_IN_MS);
        motor.configNominalOutputReverse(0.0, MOTOR_CONFIG_TIMEOUT_IN_MS);
        motor.configPeakOutputForward(1.0, MOTOR_CONFIG_TIMEOUT_IN_MS);
        motor.configPeakOutputReverse(-1.0, MOTOR_CONFIG_TIMEOUT_IN_MS);

                // Config the allowable closed-loop error.
        // Closed-Loop output will be neutral within this range.
        // See Table in Section 17.2.1 for native units per rotation.
        motor.configAllowableClosedloopError(PID_SLOT, PID_ALLOWABLE_CLOSED_LOOP_ERROR_IN_COUNTS, MOTOR_CONFIG_TIMEOUT_IN_MS);

        // Config position closed-loop gains in slot.
        // Typically kF stays zero.
        motor.config_kP(PID_SLOT, PID_P, MOTOR_CONFIG_TIMEOUT_IN_MS);
        motor.config_kI(PID_SLOT, PID_I, MOTOR_CONFIG_TIMEOUT_IN_MS);
        motor.config_kD(PID_SLOT, PID_D, MOTOR_CONFIG_TIMEOUT_IN_MS);
        motor.config_kF(PID_SLOT, PID_F, MOTOR_CONFIG_TIMEOUT_IN_MS);

        motor.configMotionAcceleration(ACCELERATION, MOTOR_CONFIG_TIMEOUT_IN_MS);
		motor.configMotionCruiseVelocity(CRUISE_VELOCITY, MOTOR_CONFIG_TIMEOUT_IN_MS);

        return motor;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ManualControl());
    }

    public void intakeRoll(double output) {
        intakeRollerMotor.set(ControlMode.PercentOutput, output);
    }

    public void intakeRollInside()
    {
      intakeRollerMotor.set(ControlMode.PercentOutput,0.4);
    }

    public void intakeStop() {
        intakeRollerMotor.set(ControlMode.PercentOutput, 0.0);
    }

    public void dropBallonLeft() {
        if (isRightFlipperClosed()) {
            open(flipperLeftSolenoid);
        }
    }

    public void dropBallonRight() {
        if (isLeftFlipperClosed()) {
            open(flipperRightSolenoid);
        }
    }

    private void open(Solenoid flipperSolenoid) {
        flipperSolenoid.set(FLIPPER_STATE_OPEN);
    }

    public void resetFlippers() {
        resetLeftFlipper();
        resetRightFlipper();
    }

    public void resetLeftFlipper() {
        if (isRightFlipperClosed()) {
            reset(flipperLeftSolenoid);
        }
    }

    public void resetRightFlipper() {
        if (isLeftFlipperClosed()) {
            reset(flipperRightSolenoid);
        }
    }

    private void reset(Solenoid flipperSolenoid) {
        flipperSolenoid.set(FLIPPER_STATE_CLOSE);
    }

    public void pivot(double output) {
        double limitProtectedOutput = output;

        if (pivotLimitSwitchHigh.get() == LIMIT_SWITCH_PRESSED_STATE) {
            limitProtectedOutput = Math.min(output, 0.0);
        }

        if (pivotLimitSwitchLow.get() == LIMIT_SWITCH_PRESSED_STATE) {
            limitProtectedOutput = Math.max(output, 0.0);
        }

        pivotMotor.set(ControlMode.PercentOutput, limitProtectedOutput);
    }

    public void pivotToPosition(double angleFromBottomDegrees)
    {
        double encoderCountsAtPosition = angleFromBottomDegrees * 4200.0 / 180.0; 
        pivotMotor.set(ControlMode.MotionMagic, encoderCountsAtPosition, DemandType.ArbitraryFeedForward, FEED_FORWARD);
    }

    public void pivotInPosition()
    {
        pivotMotor.set(ControlMode.MotionMagic, -126.0, DemandType.ArbitraryFeedForward, FEED_FORWARD);
    }

    public void pivotOutPosition()
    {
        pivotMotor.set(ControlMode.MotionMagic, 696.0, DemandType.ArbitraryFeedForward, FEED_FORWARD);
    }

    public void pivotStop() {
        pivotMotor.set(ControlMode.PercentOutput, 0.0);
    }

    public boolean isLeftFlipperClosed() {
        return flipperLeftSolenoid.get() == FLIPPER_STATE_CLOSE;
    }

    public boolean isLeftFlipperOpened() {
        return flipperLeftSolenoid.get() == FLIPPER_STATE_OPEN;
    }

    public boolean isRightFlipperClosed() {
        return flipperRightSolenoid.get() == FLIPPER_STATE_CLOSE;
    }

    public boolean isRightFlipperOpened() {
        return flipperRightSolenoid.get() == FLIPPER_STATE_OPEN;
    }

    public void periodicLogic()
    {
        // Protect min and max limit switches
        if(pivotLimitSwitchLow.get() == LIMIT_SWITCH_PRESSED_STATE)
        {
            pivotStop();            
        }
        if(pivotLimitSwitchHigh.get() == LIMIT_SWITCH_PRESSED_STATE)
        {
            pivotStop();
        }
    }

    public void log() {
        SmartDashboard.putNumber("Pivot motor count", pivotMotor.getSelectedSensorPosition());
        
        SmartDashboard.putBoolean("Left flipper", isLeftFlipperClosed());
        SmartDashboard.putBoolean("Right flipper", isRightFlipperClosed());
    }
}
