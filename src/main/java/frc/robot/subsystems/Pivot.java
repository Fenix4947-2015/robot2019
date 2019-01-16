package frc.robot.subsystems;

import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Pivot extends Subsystem {
	
	// Negatif monte
	// Pos descend
	
	// Constants.
	public static final double PERCENT_OUTPUT_MOTOR_TO_LOW = 0.70;
	public static final double PERCENT_OUTPUT_MOTOR_TO_LOW_2 = 0.25;
	public static final double PERCENT_OUTPUT_MOTOR_TO_HIGH = -1.0;
	public static final double PERCENT_OUTPUT_MOTOR_TO_HIGH_2 = -0.00;
	private static final double ACTIVE_BRAKE_WHEN_GOING_HIGH = 0.01;
	private static final double ACTIVE_BRAKE_WHEN_GOING_LOW = -0.005;
	
	// Members.
	private final WPI_TalonSRX motor;
	
	private DigitalInput verticalLimitSwitch;
	
	public Pivot() {
		motor = createMotor();
		verticalLimitSwitch = createVerticalLimitSwitch();
	}
	
	private static WPI_TalonSRX	createMotor() {
		WPI_TalonSRX motor = new WPI_TalonSRX(RobotMap.PIVOT_MOTOR_DEVICE_NUMBER);
		motor.setInverted(false);
		
		motor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		motor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		motor.overrideLimitSwitchesEnable(true);
			
		return motor;
	}
	
	private DigitalInput createVerticalLimitSwitch() {
		return new DigitalInput(RobotMap.PIVOT_VERTICAL_LIMIT_SWITCH);
	}

	public void initDefaultCommand() {
	}
	
	public boolean isAtLowPos() {
		return motor.getSensorCollection().isFwdLimitSwitchClosed();
	}
	
	public boolean isAtHighPos() {
		return motor.getSensorCollection().isRevLimitSwitchClosed();
	}
	
	public boolean isAtVerticalPos() {
		return verticalLimitSwitch.get();
	}
	
	public void activeBrakeWhenGoingHigh() {
		motor.set(ControlMode.PercentOutput, ACTIVE_BRAKE_WHEN_GOING_HIGH);
	}
	
	public void activeBrakeWhenGoingLow() {
		motor.set(ControlMode.PercentOutput, ACTIVE_BRAKE_WHEN_GOING_LOW);
	}	
	
	public void moveToLowPos(double percentOutput) {
		motor.set(ControlMode.PercentOutput, percentOutput);
	}
	
	public void moveToHighPos(double percent) {
		motor.set(ControlMode.PercentOutput, PERCENT_OUTPUT_MOTOR_TO_HIGH);
	}
	
	public void moveCustomSpeed(double percent) {
		motor.set(ControlMode.PercentOutput, percent);
	}
	
	public void stop() {
		motor.set(ControlMode.PercentOutput, 0.0);
	}
	
	public void log() {
		
	}
}