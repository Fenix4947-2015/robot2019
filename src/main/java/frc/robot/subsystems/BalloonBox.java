package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class BalloonBox extends Subsystem {

    private WPI_TalonSRX intakeRollerMotor;
    private WPI_TalonSRX pivotMotor;

    // Position PID.
    private static final double PULSES_PER_FOOT = 292;
    private static final double MAX_CLOSED_LOOP_MODE_PERCENT_OUTPUT = 0.7;
    private static final double POSITION_PID_P = 0.75;
    private static final double POSITION_PID_I = 0.0;
    private static final double POSITION_PID_D = 0.0;
    private static final double POSITION_PID_F = 0.0;

    public BalloonBox() {
        intakeRollerMotor = new WPI_TalonSRX(RobotMap.INTAKE_ROLLER_MOTOR_ADDRESS);
        intakeRollerMotor.setSafetyEnabled(false);
    }

    @Override
    public void initDefaultCommand() {
    }

    public void intakeRollInside() {
        intakeRollerMotor.set(ControlMode.PercentOutput, 0.4);
    }

    public void intakeStop() {
        intakeRollerMotor.set(ControlMode.PercentOutput, 0.0);
    }

    public void pivotLiftUp() {
        pivotMotor.set(ControlMode.PercentOutput, 0.7);
    }

    public void pivotGoDown() {
        pivotMotor.set(ControlMode.PercentOutput, -0.7);
    }

    public void pivotStop() {
        pivotMotor.set(ControlMode.PercentOutput, 0.0);
    }

    public void robotPeriodic() {
    }
}
