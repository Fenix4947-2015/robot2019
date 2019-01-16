package frc.robot.commands;

import frc.robot.OI.XBoxAxis;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveArcade extends Command {


	public DriveArcade() {
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		// motion forward is with left trigger, backwards with right trigger.
		double moveValue = Robot.oi.getJoystickDriverAxis(XBoxAxis.LEFT_TRIGGER) + -Robot.oi.getJoystickDriverAxis(XBoxAxis.RIGHT_TRIGGER);

		double rotateValue = Robot.oi.getJoystickDriverAxis(XBoxAxis.LEFT_STICK_X, 0.1);

		Robot.driveTrain.driveArcadeMethod(moveValue, rotateValue);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.driveStop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}