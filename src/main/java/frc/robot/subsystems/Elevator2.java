package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

public class Elevator2 extends Subsystem {

    private static final double STOP_OUTPUT = 0.02;

    private static final int PID_LOOP = 0;
    private static final int TIMEOUT_MS = 30;

    private static final boolean LIMIT_SWITCH_PRESSED_STATE = false;

    private WPI_TalonSRX motor;
    private DigitalInput limitSwitchLow;
    private DigitalInput limitSwitchHigh;

    public Elevator2() {
        super("Elevator");

        motor = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_ADDRESS);
        motor.configFactoryDefault();

        motor.setNeutralMode(NeutralMode.Brake);
        motor.setSafetyEnabled(false);
        motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PID_LOOP, TIMEOUT_MS);

        // Ensure sensor is positive when output is positive.
        motor.setSensorPhase(true);

        // Set based on what direction you want forward/positive to be.
        // This does not affect sensor phase.
        motor.setInverted(false);

        // Config the peak and nominal outputs.
        // 1.0 means full.
        motor.configNominalOutputForward(0, TIMEOUT_MS);
        motor.configNominalOutputReverse(0, TIMEOUT_MS);
        motor.configPeakOutputForward(1.0, TIMEOUT_MS);
        motor.configPeakOutputReverse(-1.0, TIMEOUT_MS);

        limitSwitchLow = new DigitalInput(RobotMap.ELEVATOR_LIMIT_SWITCH_LOW);
        limitSwitchHigh = new DigitalInput(RobotMap.ELEVATOR_LIMIT_SWITCH_HIGH);

        zero();
    }

    @Override
    public void initDefaultCommand() {
    }

    public void move(double output) {
        double limitProtectedOutput = output;

        if (isHigh()) {
            limitProtectedOutput = Math.min(output, 0.0);
        }

        if (isLow()) {
            limitProtectedOutput = Math.max(output, 0.0);
            zero();
        }

        motor.set(ControlMode.PercentOutput, limitProtectedOutput);
    }

    public void stop() {
        if (isLow()) {
            motor.set(ControlMode.PercentOutput, 0.0);
        } else {
            motor.set(ControlMode.PercentOutput, STOP_OUTPUT);
        }
    }

    public void zero() {
        if (isLow()) {
            motor.setSelectedSensorPosition(0, PID_LOOP, TIMEOUT_MS);
        }
    }

    public boolean isLow() {
        return limitSwitchLow.get() == LIMIT_SWITCH_PRESSED_STATE;
    }

    public boolean isHigh() {
        return limitSwitchHigh.get() == LIMIT_SWITCH_PRESSED_STATE;
    }

    public void log() {
        SmartDashboard.putNumber("Elevator motor output", motor.getMotorOutputPercent());
    }
}
