package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
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
        MID,
        HIGH,
    }

    private static final double PEAK_OUTPUT = 1.0;
    private static final double MOVE_OUTPUT = 0.25;

    private TalonSRX motor;

    public Elevator() {
        super("Elevator");

        motor = new TalonSRX(RobotMap.ELEVATOR_MOTOR_DEVICE_NUMBER);
        motor.configPeakOutputForward(PEAK_OUTPUT);
        motor.configPeakOutputReverse(PEAK_OUTPUT);
        motor.setInverted(false);
        motor.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new MoveElevatorManually());
    }

    public void move(double output) {
        motor.set(ControlMode.PercentOutput, output);
    }

    public void moveLow() {
        motor.set(ControlMode.PercentOutput, -MOVE_OUTPUT);
    }

    public void moveHigh() {
        motor.set(ControlMode.PercentOutput, MOVE_OUTPUT);
    }

    public void moveTo(SetPointTarget target, SetPointLevel level) {
        // Not implemented yet.
    }

    public void stop() {
        motor.set(ControlMode.PercentOutput, 0.0);
    }

    public boolean isLow() {
        return motor.getSensorCollection().isFwdLimitSwitchClosed();
    }

    public boolean isHigh() {
        return motor.getSensorCollection().isRevLimitSwitchClosed();
    }
}
