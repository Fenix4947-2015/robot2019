package frc.robot.commands.gripper;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class GripperClose extends Command {
	
	public GripperClose() {
		requires(Robot.gripper);
	}

	// Called just before the command runs the first time.
	protected void initialize() {
		Robot.gripper.close();
		setTimeout(0.1);
	}

	// Called repeatedly when the command is scheduled to run.
	protected void execute() {
	}

	// Called when another command which requires one or more of the same subsystems is scheduled to run.
	protected void interrupted() {
		end();
	}

	// Make this return TRUE when the command no longer needs to run execute().
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns TRUE.
	protected void end() {
	}
}