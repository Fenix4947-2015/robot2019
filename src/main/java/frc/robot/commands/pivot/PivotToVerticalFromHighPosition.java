package frc.robot.commands.pivot;

import frc.robot.Robot;
import frc.robot.subsystems.Pivot;

import edu.wpi.first.wpilibj.command.Command;

public class PivotToVerticalFromHighPosition extends Command {

	private boolean lastVerticalPosRead;
	private boolean verticalPosReached;
	
	public PivotToVerticalFromHighPosition() {
		requires(Robot.pivot);
	}

	// Called just before the command runs the first time.
	protected void initialize() {
		verticalPosReached = false;
		
		Robot.pivot.moveToLowPos(Pivot.PERCENT_OUTPUT_MOTOR_TO_LOW);
	}

	// Called repeatedly when the command is scheduled to run.
	protected void execute() {
		lastVerticalPosRead = Robot.pivot.isAtVerticalPos();
		verticalPosReached |= lastVerticalPosRead;
	}

	// Called when another command which requires one or more of the same subsystems is scheduled to run.
	protected void interrupted() {
		end();
	}

	// Make this return TRUE when the command no longer needs to run execute().
	protected boolean isFinished() {
		boolean isFinished = (verticalPosReached && !lastVerticalPosRead);
		if (isFinished) {
			System.out.println("finished");
		}
		return isFinished;
	}

	// Called once after isFinished returns TRUE.
	protected void end() {
	}
}