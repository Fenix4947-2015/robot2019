package org.usfirst.frc.team4947.robot.commands;

import org.usfirst.frc.team4947.robot.OI.XBoxAxis;
import org.usfirst.frc.team4947.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class PlatformPull extends Command {

	private static final double DEAD_BAND = 0.1;
	
	public PlatformPull() 
	{
		requires(Robot.platform);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double desiredSpeed = -Robot.oi.getJoystickDriverAxis(XBoxAxis.RIGHT_STICK_Y, DEAD_BAND); // joystick up to lift
		Robot.platform.liftSpeedSafe(desiredSpeed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {		
		Robot.platform.liftStop();
	}

	// Called when another command which requires one or more of the same subsystems is scheduled to run
	protected void interrupted() {
		Robot.platform.liftStop();
	}
}
