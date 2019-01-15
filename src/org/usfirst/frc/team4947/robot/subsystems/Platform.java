package org.usfirst.frc.team4947.robot.subsystems;

import org.usfirst.frc.team4947.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Platform extends Subsystem {

	// Members.
	private WPI_TalonSRX liftMotor;
	private Solenoid unlockerSolenoid;
	private DigitalInput lifter_Limit_Switch;
	
	private static final boolean STATE_UNLOCKED = true;
	private static final boolean STATE_LOCKED = !STATE_UNLOCKED;
	private static final boolean STATE_LIFT_LIMIT_REACHED = false;

	
	public Platform() {
		liftMotor = new WPI_TalonSRX(RobotMap.LIFT_MOTOR_ADDRESS);
		liftMotor.setInverted(true);
		unlockerSolenoid = new Solenoid(RobotMap.UNLOCKER_SOLENOID_ADDRESS);
		lifter_Limit_Switch = new DigitalInput(RobotMap.LIFT_LIMIT_SWITCH_DIO_ADDRESS);
	}
	
	public void initDefaultCommand() {
		
	}

	public void liftSpeedSafe(double speedLift) {
		
		// forbid continue going up when limit is met
		if(isLiftLimitReached())
		{
			if(speedLift > 0.0)
			{
				speedLift = 0.0;
			}
		}
		
		liftMotor.set(ControlMode.PercentOutput, speedLift);
	}

	public void unlockPlatform(boolean unlock) {
		if(unlock)
		{
			unlockerSolenoid.set(STATE_UNLOCKED);
		}
		else
		{
			unlockerSolenoid.set(STATE_LOCKED);
		}		
	}
	
	public void liftStop()
	{
		liftMotor.stopMotor();
	}
	
	public boolean isLiftLimitReached()
	{
		return (lifter_Limit_Switch.get() == STATE_LIFT_LIMIT_REACHED);
	}
}