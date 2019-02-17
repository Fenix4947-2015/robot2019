package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.elevator.MoveElevatorManually;

public class Elevator extends Subsystem {

    public static final double COMMAND_TIMEOUT_IN_SECONDS = 2.0;

    private static final boolean LIMIT_SWITCH_PRESSED_STATE = false;

    private static final double MOVE_OUTPUT = 0.25;
    private static final int MOTOR_CONFIG_TIMEOUT_IN_MS = 30;
    private static final int PID_LOOP_ID = 0;

    private WPI_TalonSRX motor;
    private DigitalInput limitSwitchLow;
    private DigitalInput limitSwitchHigh;

    public Elevator() {
        super("Elevator");

        motor = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_ADDRESS);
        motor.configFactoryDefault();
        motor.setSafetyEnabled(false);

        motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PID_LOOP_ID, MOTOR_CONFIG_TIMEOUT_IN_MS);

        motor.setNeutralMode(NeutralMode.Brake);
        motor.setSensorPhase(true);
        motor.setInverted(false);

        motor.configNominalOutputForward(0.0, MOTOR_CONFIG_TIMEOUT_IN_MS);
        motor.configNominalOutputReverse(0.0, MOTOR_CONFIG_TIMEOUT_IN_MS);
        motor.configPeakOutputForward(1.0, MOTOR_CONFIG_TIMEOUT_IN_MS);
        motor.configPeakOutputReverse(-1.0, MOTOR_CONFIG_TIMEOUT_IN_MS);

        limitSwitchLow = new DigitalInput(RobotMap.ELEVATOR_LIMIT_SWITCH_LOW);
        limitSwitchHigh = new DigitalInput(RobotMap.ELEVATOR_LIMIT_SWITCH_HIGH);

        zero();
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new MoveElevatorManually());
    }

    public void move(double output) {
        double limitProtectedOutput = output;

        if (limitSwitchHigh.get() == LIMIT_SWITCH_PRESSED_STATE) {
            limitProtectedOutput = Math.min(output, 0.0);
        }

        if (limitSwitchLow.get() == LIMIT_SWITCH_PRESSED_STATE) {
            limitProtectedOutput = Math.max(output, 0.0);
        }

        motor.set(ControlMode.PercentOutput, limitProtectedOutput);
    }

    public void moveToLow() {
        motor.set(ControlMode.PercentOutput, -MOVE_OUTPUT);
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

    public double getMotorOutputPercent() {
        return motor.getMotorOutputPercent();
    }

    public int getSensorPosition() {
        return motor.getSelectedSensorPosition();
    }

    public int getSensorVelocity() {
        return motor.getSelectedSensorVelocity();
    }

    public boolean isLow() {
        return motor.getSensorCollection().isFwdLimitSwitchClosed();
    }

    public boolean isHigh() {
        return motor.getSensorCollection().isRevLimitSwitchClosed();
    }

    public void log() {
        SmartDashboard.putBoolean("Elevator low switch", limitSwitchLow.get());
        SmartDashboard.putBoolean("Elevator hight switch", limitSwitchHigh.get());

        SmartDashboard.putNumber("Elevator position", getSensorPosition());
    }
}