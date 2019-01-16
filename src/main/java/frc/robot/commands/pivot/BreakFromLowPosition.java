package  frc.robot.commands.pivot;

import  frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class BreakFromLowPosition extends Command {

	public BreakFromLowPosition() {
		requires(Robot.pivot);
	}

	// Called just before the command runs the first time.
	protected void initialize() {
		Robot.pivot.activeBrakeWhenGoingHigh();
		setTimeout(0.25);
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
		return (isTimedOut() || Robot.pivot.isAtHighPos());
	}

	// Called once after isFinished returns TRUE.
	protected void end() {
	}
}