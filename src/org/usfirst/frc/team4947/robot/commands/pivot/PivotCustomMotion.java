package org.usfirst.frc.team4947.robot.commands.pivot;

import org.usfirst.frc.team4947.robot.OI.XBoxAxis;
import org.usfirst.frc.team4947.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class PivotCustomMotion extends Command {

	public PivotCustomMotion() {
		requires(Robot.pivot);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.pivot.log();
		
		double rotateValue = Robot.oi.getJoystickHelperAxis(XBoxAxis.RIGHT_STICK_Y, 0.1);
		Robot.pivot.moveCustomSpeed(rotateValue);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}
	
	// Called when another command which requires one or more of the same subsystems is scheduled to run.
	protected void interrupted() {
	}	

	// Called once after isFinished returns true
	protected void end() {
		Robot.pivot.stop();
	}
}
