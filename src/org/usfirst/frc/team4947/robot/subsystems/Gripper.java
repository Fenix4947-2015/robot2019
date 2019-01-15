package org.usfirst.frc.team4947.robot.subsystems;

import java.util.concurrent.TimeUnit;

import org.usfirst.frc.team4947.robot.RobotMap;
import org.usfirst.frc.team4947.robot.commands.gripper.GripperDefault;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gripper extends Subsystem {
	
	// Constants.
	private static final double MOTOR_SHOOT_TO_SWITCH_PERCENT_OUTPUT = 0.70;
	
	private static final boolean OPENER_SOLENOID_CLOSE_STATE = true;
	private static final boolean OPENER_SOLENOID_OPEN_STATE = !OPENER_SOLENOID_CLOSE_STATE;
	
	private static final boolean STATE_CUBE_PRESENT = false;

	private static final long FLIP_FREQUENCY_MILLIS = TimeUnit.SECONDS.toMillis(1L);
	private static final double SPEED_RATIO = 0.9;

	// Members.
	private WPI_TalonSRX leftMotor;
	private WPI_TalonSRX rightMotor;
	private Solenoid openerSolenoid;
	private DigitalInput cubePresenceDigitalInput;
	// sonar maxbotix 
	//https://www.chiefdelphi.com/forums/showthread.php?t=134143
	//http://team358.org/files/programming/ControlSystem2009-/MB1010_Datasheet.pdf
	
	private boolean _ratioToggle;
	private long _lastFlipMillis;
	
	public Gripper() {
		leftMotor = createLeftMotor();
		rightMotor = createRightMotor();
		openerSolenoid = createOpenerSolenoid();
		cubePresenceDigitalInput = createCubePresenceDigitalInput();
		
		_ratioToggle = false;
		_lastFlipMillis = System.currentTimeMillis();
	}
	
	private static WPI_TalonSRX createLeftMotor() {
		WPI_TalonSRX motor = new WPI_TalonSRX(RobotMap.GRIPPER_LEFT_MOTOR_DEVICE_NUMBER);
		return motor;
	}
	
	private static WPI_TalonSRX createRightMotor() {
		WPI_TalonSRX motor = new WPI_TalonSRX(RobotMap.GRIPPER_RIGHT_MOTOR_DEVICE_NUMBER);
		return motor;
	}
	
	private static Solenoid createOpenerSolenoid() {
		Solenoid solenoid = new Solenoid(RobotMap.GRIPPER_OPENER_SOLENOID_CHANNEL);
		return solenoid;
	}
	
	private static DigitalInput createCubePresenceDigitalInput() {
		DigitalInput digitalInput = new DigitalInput(RobotMap.GRIPPER_CUBE_PRESENCE_DIGITAL_INPUT_CHANNEL);
		return digitalInput;
	}

	public void initDefaultCommand() {
		setDefaultCommand(new GripperDefault());
	}
	
	public boolean isCubePresent() {
		return (cubePresenceDigitalInput.get() == STATE_CUBE_PRESENT);
	}
	
	public void close() {
		openerSolenoid.set(OPENER_SOLENOID_CLOSE_STATE);
	}
	
	public void open() {
		openerSolenoid.set(OPENER_SOLENOID_OPEN_STATE);
	}
	
	public void fullSpeed() {
		leftMotor.set(ControlMode.PercentOutput, -MOTOR_SHOOT_TO_SWITCH_PERCENT_OUTPUT);
		rightMotor.set(ControlMode.PercentOutput, MOTOR_SHOOT_TO_SWITCH_PERCENT_OUTPUT);
	}	
	
	public void rotateFlip(double percentOutput) {
		flipIfRequired();
		
		if (_ratioToggle) {
			leftMotor.set(ControlMode.PercentOutput, -percentOutput * SPEED_RATIO);
			rightMotor.set(ControlMode.PercentOutput, percentOutput);
		} else {
			leftMotor.set(ControlMode.PercentOutput, -percentOutput);
			rightMotor.set(ControlMode.PercentOutput, percentOutput * SPEED_RATIO);
		}
	}
	
	private void flipIfRequired() {
		long nowMillis = System.currentTimeMillis();
		
		long sinceLastFlipMillis = (nowMillis - _lastFlipMillis);
		if (sinceLastFlipMillis >= FLIP_FREQUENCY_MILLIS) {
			_ratioToggle = !_ratioToggle;

			_lastFlipMillis = nowMillis;
		}
	}

	public void stop() {
		leftMotor.set(ControlMode.PercentOutput, 0.0);
		rightMotor.set(ControlMode.PercentOutput, 0.0);
	}
	
	public void log() {
		SmartDashboard.putBoolean("Cube Presence", isCubePresent());
	}
}