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

    public static final double COMMAND_TIMEOUT_IN_SECONDS = 10.0;

    private static final int POS_HIGH = 32000;
    private static final int POS_LOW = 0;
    
    public static final int POS_CARGO_LEVEL_3 = 29530;
    public static final int POS_CARGO_LEVEL_2 = 16250;
    public static final int POS_CARGO_LEVEL_1 = 3600;
    
    public static final int POS_HATCH_LEVEL_3 = 28250;
    public static final int POS_HATCH_LEVEL_2 = 15100;
    public static final int POS_HATCH_LEVEL_1 = 3900;
    public static final int POS_HATCH_LOADING_STATION = 2500;

    public static final int POS_HATCH_LEVEL_1_MINIMUM_THRESHOLD = 2500;

    public static final int POS_HATCH_DURING_SANDSTORM = 3500;

    public static final int POS_CARGO_AT_HUMAN_STATION = 15070;

    private static final double STOP_OUTPUT = 0.1;

    private static final int PID_LOOP = 0;
    private static final int TIMEOUT_MS = 30;

    private static final boolean LIMIT_SWITCH_PRESSED_STATE = false;

    private WPI_TalonSRX motor;
    private DigitalInput limitSwitchLow;
    private DigitalInput limitSwitchHigh;

    public Elevator() {
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
        setDefaultCommand(new MoveElevatorManually());
    }

    public void move(double output) {
        double limitProtectedOutput = output;

        if (isHigh()) {
            if (output >= 0.0) {
                limitProtectedOutput = STOP_OUTPUT;
            }
        }

        if (isLow()) {
            limitProtectedOutput = Math.max(output, 0.0);
        }

        motor.set(ControlMode.PercentOutput, limitProtectedOutput);
    }

    public void moveTo(int count) {
        if (count < POS_LOW) {
            count = POS_LOW;
        } else if (count > POS_HIGH) {
            count = POS_HIGH;
        }

        int currentCount = getSensorPosition();
        double output = (currentCount > count) ? -0.6 : 0.8;

        move(output);
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

    public int getSensorPosition() {
        return motor.getSelectedSensorPosition();
    }

    public boolean isLow() {
        return (limitSwitchLow.get() == LIMIT_SWITCH_PRESSED_STATE) || (getSensorPosition() <= POS_LOW);
    }

    public boolean isTooLowForHatch()
    {
        if(getSensorPosition()<POS_HATCH_LEVEL_1_MINIMUM_THRESHOLD)
        {
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isHigh() {
        return limitSwitchHigh.get() == LIMIT_SWITCH_PRESSED_STATE;
    }

    public void log() {
        SmartDashboard.putNumber("Elevator motor output", motor.getMotorOutputPercent());
        SmartDashboard.putNumber("Elevator position", motor.getSelectedSensorPosition());
    }
}
