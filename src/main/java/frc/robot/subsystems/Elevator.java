package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

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

    private static final int POSITION_MIDDLE_IN_COUNTS = 1000;
   
    private static final int     PID_LOOP_ID = 0;
    private static final int     PID_SLOT_ID = 0;
    private static final double  PID_PEAK_OUTPUT = 0.5;
    private static final int     PID_TIMEOUT_IN_MS = 30;
    private static final boolean PID_SENSOR_PHASE = true;
    private static final boolean PID_MOTOR_INVERT = false;
    
    private static final int    PID_ALLOWABLE_CLOSED_LOOP_ERROR_IN_COUNTS = 0;
    private static final double PID_P = 0.15;
    private static final double PID_I = 0.0;
    private static final double PID_D = 0.0;
    private static final double PID_F = 0.0;
    
    private static final double MOVE_OUTPUT = 0.25;

    private TalonSRX motor;

    public Elevator() {
        super("Elevator");

        motor = new TalonSRX(RobotMap.ELEVATOR_MOTOR_DEVICE_NUMBER);
        motor.setNeutralMode(NeutralMode.Brake);

        // Config the sensor used for Primary PID and sensor direction.
        motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_LOOP_ID, PID_TIMEOUT_IN_MS);

        // Ensure sensor is positive when output is positive.
        motor.setSensorPhase(PID_SENSOR_PHASE);

        // Set based on what direction you want forward/positive to be.
        // This does not affect sensor phase.
        motor.setInverted(PID_MOTOR_INVERT);

        // Config the peak and nominal outputs.
        // 1.0 means full.
    	motor.configNominalOutputForward(0, PID_TIMEOUT_IN_MS);
    	motor.configNominalOutputReverse(0, PID_TIMEOUT_IN_MS);
        motor.configPeakOutputForward(PID_PEAK_OUTPUT, PID_TIMEOUT_IN_MS);
        motor.configPeakOutputReverse(-PID_PEAK_OUTPUT, PID_TIMEOUT_IN_MS);

        // Config the allowable closed-loop error.
        // Closed-Loop output will be neutral within this range.
        // See Table in Section 17.2.1 for native units per rotation.
        motor.configAllowableClosedloopError(PID_SLOT_ID, PID_ALLOWABLE_CLOSED_LOOP_ERROR_IN_COUNTS, PID_TIMEOUT_IN_MS);

        // Config position closed-loop gains in slot.
        // Typically kF stays zero.
        motor.config_kP(PID_SLOT_ID, PID_P, PID_TIMEOUT_IN_MS);
        motor.config_kI(PID_SLOT_ID, PID_I, PID_TIMEOUT_IN_MS);
        motor.config_kD(PID_SLOT_ID, PID_D, PID_TIMEOUT_IN_MS);
        motor.config_kF(PID_SLOT_ID, PID_F, PID_TIMEOUT_IN_MS);
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
        motor.setSelectedSensorPosition(0, PID_LOOP_ID, PID_TIMEOUT_IN_MS);
    }

    public boolean isLow() {
        return motor.getSensorCollection().isFwdLimitSwitchClosed();
    }

    public boolean isMiddle() {
        int currentPosition = motor.getSelectedSensorPosition();
        int error = Math.abs(POSITION_MIDDLE_IN_COUNTS - currentPosition);
        return (error <= PID_ALLOWABLE_CLOSED_LOOP_ERROR_IN_COUNTS);
    }

    public boolean isHigh() {
        return motor.getSensorCollection().isRevLimitSwitchClosed();
    }
}
