package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.elevator.MoveElevatorManually;

public class Elevator extends Subsystem {

    public static final double COMMAND_TIMEOUT_IN_SECONDS = 2.0;

    private static final boolean LIMIT_SWITCH_PRESSED_STATE = false;

    private static final int POSITION_MIDDLE_IN_COUNTS = 15000;
    private static final int POSITION_TOLERANCE = 100;

    // TOP COUNT = 0;
    // BOTTOM COUNT: 32060;
    // -2.5CM À 0 -32060

    // top VELOCITY: 1000

    private static final double TOP_VELOCITY = 1000.0;

    private static final double MOVE_OUTPUT = 0.25;
    private static final int MOTOR_CONFIG_TIMEOUT_IN_MS = 30;
    private static final int PID_LOOP_ID = 0;

    private static final int    PID_LOOP = 0;
    private static final int    PID_SLOT = 0;
    private static final int    PID_ALLOWABLE_CLOSED_LOOP_ERROR_IN_COUNTS = 0;
    private static final double PID_P = 0.05;
    private static final double PID_I = 0.0;
    private static final double PID_D = 0.0;
    private static final double PID_F = (0.5 * 1023) / TOP_VELOCITY;  

    private static final int ACCELERATION = (int) (TOP_VELOCITY / 2.0);
    private static final int CRUISE_VELOCITY = (int) (TOP_VELOCITY / 2.0);
    private static final double FEED_FORWARD = 0.16;

    private WPI_TalonSRX motor;
    private DigitalInput limitSwitchLow;
    private DigitalInput limitSwitchHigh;
    private double pidP;
    private double pidI;
    private double pidD;
    private double pidF;    

    public Elevator() {
        super("Elevator");

        motor = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_ADDRESS);
        motor.configFactoryDefault();
        motor.setSafetyEnabled(false);

        pidP = Preferences.getInstance().getDouble("Elevator.PID.P", PID_P);
        pidI = Preferences.getInstance().getDouble("Elevator.PID.I", PID_I);
        pidD = Preferences.getInstance().getDouble("Elevator.PID.D", PID_D);
        pidF = Preferences.getInstance().getDouble("Elevator.PID.F", PID_F);

        motor.setNeutralMode(NeutralMode.Brake);

        // Config the sensor used for Primary PID and sensor direction.
        motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PID_LOOP, MOTOR_CONFIG_TIMEOUT_IN_MS);

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
        motor.configAllowableClosedloopError(PID_SLOT, PID_ALLOWABLE_CLOSED_LOOP_ERROR_IN_COUNTS, MOTOR_CONFIG_TIMEOUT_IN_MS);

        // Config position closed-loop gains in slot.
        // Typically kF stays zero.
        motor.config_kP(PID_SLOT, pidP, MOTOR_CONFIG_TIMEOUT_IN_MS);
        motor.config_kI(PID_SLOT, pidI, MOTOR_CONFIG_TIMEOUT_IN_MS);
        motor.config_kD(PID_SLOT, pidD, MOTOR_CONFIG_TIMEOUT_IN_MS);
        motor.config_kF(PID_SLOT, pidF, MOTOR_CONFIG_TIMEOUT_IN_MS);

        motor.configMotionAcceleration(ACCELERATION, MOTOR_CONFIG_TIMEOUT_IN_MS);
		motor.configMotionCruiseVelocity(CRUISE_VELOCITY, MOTOR_CONFIG_TIMEOUT_IN_MS);

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
            zero();
        }

        motor.set(ControlMode.PercentOutput, limitProtectedOutput);
    }

    public void moveToLow() {
        motor.set(ControlMode.PercentOutput, -MOVE_OUTPUT);
    }

    public void moveToMiddle() {
        motor.set(ControlMode.MotionMagic, POSITION_MIDDLE_IN_COUNTS, DemandType.ArbitraryFeedForward, FEED_FORWARD);
        // motor.set(ControlMode.Position, POSITION_MIDDLE_IN_COUNTS);
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

    public boolean isMiddle() {
        int encoderPosition = motor.getSelectedSensorPosition();
        int error = Math.abs(POSITION_MIDDLE_IN_COUNTS - encoderPosition);

        SmartDashboard.putNumber("Elevator error", error);

        return (error <= POSITION_TOLERANCE);
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