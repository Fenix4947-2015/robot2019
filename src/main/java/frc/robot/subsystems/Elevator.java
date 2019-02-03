package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.elevator.MoveElevatorManually;

public class Elevator extends Subsystem {

    public static enum SetPointTarget {
        CARGO,
        HATCH,
    }

    public static enum SetPointLevel {
        LOW, 
        MIDDLE, 
        HIGH,
    }

    public static final double COMMAND_TIMEOUT_IN_SECONDS = 2.0;

    private static final int POSITION_MIDDLE_IN_COUNTS = 1000;  // TODO: Must be determined.
    private static final int POSITION_HIGH_IN_COUNTS = 3000;    // TODO: Must be determined.

    private static final int    POSITION_SPEED_LIMIT_START_IN_COUNTS = (int) (POSITION_MIDDLE_IN_COUNTS * 0.5);                                                          // TODO: Must be determined.
    private static final int    POSITION_SPEED_LIMIT_END_IN_COUNTS = (int) (POSITION_MIDDLE_IN_COUNTS + ((POSITION_HIGH_IN_COUNTS - POSITION_MIDDLE_IN_COUNTS) * 0.5));  // TODO: Must be determined.
    private static final int    SPEED_LIMIT_RANGE_IN_COUNTS = (POSITION_SPEED_LIMIT_END_IN_COUNTS - POSITION_SPEED_LIMIT_START_IN_COUNTS);
    private static final double SPEED_LIMIT_FACTOR_WHEN_HIGH = 0.1;
    private static final double SPEED_LIMIT_FACTOR_RANGE = (1.0 - SPEED_LIMIT_FACTOR_WHEN_HIGH);

    private static final int MOTOR_CONFIG_TIMEOUT_IN_MS = 30;

    private static final double MOVE_OUTPUT = 0.25; // TODO: Must be increased.

    private static final int    PID_LOOP_ID = 0;
    private static final int    PID_SLOT_ID = 0;
    private static final int    PID_ALLOWABLE_CLOSED_LOOP_ERROR_IN_COUNTS = 0;
    private static final double PID_P = 0.15;
    private static final double PID_I = 0.0;
    private static final double PID_D = 0.0;
    private static final double PID_F = 0.0;

    private WPI_TalonSRX motor;

    public Elevator() {
        super("Elevator");

        motor = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_DEVICE_NUMBER);
        motor.setNeutralMode(NeutralMode.Brake);

        // Config the sensor used for Primary PID and sensor direction.
        motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_LOOP_ID, MOTOR_CONFIG_TIMEOUT_IN_MS);

        // Ensure sensor is positive when output is positive.
        motor.setSensorPhase(true);

        // Set based on what direction you want forward/positive to be.
        // This does not affect sensor phase.
        motor.setInverted(false);

        // Config the peak and nominal outputs.
        // 1.0 means full.
    	motor.configNominalOutputForward(0, MOTOR_CONFIG_TIMEOUT_IN_MS);
    	motor.configNominalOutputReverse(0, MOTOR_CONFIG_TIMEOUT_IN_MS);
        motor.configPeakOutputForward( 1.0, MOTOR_CONFIG_TIMEOUT_IN_MS);
        motor.configPeakOutputReverse(-1.0, MOTOR_CONFIG_TIMEOUT_IN_MS);

        // Config the allowable closed-loop error.
        // Closed-Loop output will be neutral within this range.
        // See Table in Section 17.2.1 for native units per rotation.
        motor.configAllowableClosedloopError(PID_SLOT_ID, PID_ALLOWABLE_CLOSED_LOOP_ERROR_IN_COUNTS, MOTOR_CONFIG_TIMEOUT_IN_MS);

        // Config position closed-loop gains in slot.
        // Typically kF stays zero.
        motor.config_kP(PID_SLOT_ID, PID_P, MOTOR_CONFIG_TIMEOUT_IN_MS);
        motor.config_kI(PID_SLOT_ID, PID_I, MOTOR_CONFIG_TIMEOUT_IN_MS);
        motor.config_kD(PID_SLOT_ID, PID_D, MOTOR_CONFIG_TIMEOUT_IN_MS);
        motor.config_kF(PID_SLOT_ID, PID_F, MOTOR_CONFIG_TIMEOUT_IN_MS);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new MoveElevatorManually());
    }

    public void move(double output) {
        motor.set(ControlMode.PercentOutput, output);
    }

    public void moveToLow() {
        motor.set(ControlMode.PercentOutput, -MOVE_OUTPUT);
    }

    public void moveToMiddle() {
        motor.set(ControlMode.Position, POSITION_MIDDLE_IN_COUNTS);
    }

    public void moveToHigh() {
        motor.set(ControlMode.PercentOutput, MOVE_OUTPUT);
    }

    public void stop() {
        motor.set(ControlMode.PercentOutput, 0.0);
    }

    public void zero() {
        motor.setSelectedSensorPosition(0, PID_LOOP_ID, MOTOR_CONFIG_TIMEOUT_IN_MS);
    }

    public int getEncoderPosition() {
        return motor.getSelectedSensorPosition();
    }

    public int getClosedLoopError() {
        return motor.getClosedLoopError(PID_LOOP_ID);
    }

    public double getMotorOutputPercent() {
        return motor.getMotorOutputPercent();
    }

    public double getSpeedFactor() {
        int encoderPosition = motor.getSelectedSensorPosition();
        if (encoderPosition <= POSITION_SPEED_LIMIT_START_IN_COUNTS) {
            return 1.0;
        } else if (encoderPosition >= POSITION_SPEED_LIMIT_END_IN_COUNTS) {
            return SPEED_LIMIT_FACTOR_WHEN_HIGH;
        }

        double delta = (encoderPosition - POSITION_SPEED_LIMIT_START_IN_COUNTS);
        double percent = (delta / SPEED_LIMIT_RANGE_IN_COUNTS);
        double correctionFactor = (percent * SPEED_LIMIT_FACTOR_RANGE);

        return (1.0 - correctionFactor);
    }

    public boolean isLow() {
        return motor.getSensorCollection().isFwdLimitSwitchClosed();
    }

    public boolean isMiddle() {
        int encoderPosition = motor.getSelectedSensorPosition();
        int error = Math.abs(POSITION_MIDDLE_IN_COUNTS - encoderPosition);
        return (error <= PID_ALLOWABLE_CLOSED_LOOP_ERROR_IN_COUNTS);
    }

    public boolean isHigh() {
        return motor.getSensorCollection().isRevLimitSwitchClosed();
    }
}
