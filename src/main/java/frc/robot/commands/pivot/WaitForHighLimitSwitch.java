package frc.robot.commands.pivot;

import frc.robot.Robot;
import frc.robot.subsystems.Pivot;

import edu.wpi.first.wpilibj.command.Command;

public class WaitForHighLimitSwitch extends Command {

	public WaitForHighLimitSwitch() {
		requires(Robot.pivot);
	}

	// Called just before the command runs the first time.
	protected void initialize() {
//		Robot.pivot.moveToHighPos(Pivot.PERCENT_OUTPUT_MOTOR_TO_HIGH_2);
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
		return Robot.pivot.isAtHighPos();
	}

	// Called once after isFinished returns TRUE.
	protected void end() {
		Robot.pivot.stop();
	}
}