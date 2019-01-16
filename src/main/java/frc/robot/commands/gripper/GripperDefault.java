package frc.robot.commands.gripper;

import frc.robot.OI.XBoxAxis;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

public class GripperDefault extends Command {
	
	public static final float CUBE_OK_RUMBLE_INTENSITY = 0.78f; // between 0 and 1
	
	public GripperDefault() {
		requires(Robot.gripper);
	}

	// Called just before the command runs the first time.
	protected void initialize() {
	}

	// Called repeatedly when the command is scheduled to run.
	protected void execute() {
		updateOpening();
		updateSpeed();
		updatePresence();
	}
	
	private void updateOpening() {
		double leftStickAxisY = Robot.oi.getJoystickHelperAxis(XBoxAxis.LEFT_STICK_Y, 0.1);
		if (leftStickAxisY > 0.5) {
			Robot.gripper.open();
		} else if (leftStickAxisY < -0.5) {
			Robot.gripper.close();
		}
	}
	
	private void updateSpeed() {
		double leftTrigger = -Robot.oi.getJoystickHelperAxis(XBoxAxis.LEFT_TRIGGER);
		double rightTrigger = Robot.oi.getJoystickHelperAxis(XBoxAxis.RIGHT_TRIGGER);
		
		double percentOutput = (leftTrigger + rightTrigger);
		double direction = Math.signum(Double.compare(rightTrigger, leftTrigger));

		Robot.gripper.rotateFlip(direction * percentOutput);
	}
	
	private void updatePresence()
	{
		if(Robot.gripper.isCubePresent())
		{
			Robot.oi.setJoystickHelperRumble(RumbleType.kLeftRumble, CUBE_OK_RUMBLE_INTENSITY);
		}
		else
		{
			Robot.oi.stopJoystickHelperRumble();
		}
	}
	
	// Called when another command which requires one or more of the same subsystems is scheduled to run.
	protected void interrupted() {
		Robot.oi.stopJoystickHelperRumble();
	}

	// Make this return TRUE when the command no longer needs to run execute().
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns TRUE.
	protected void end() {
		Robot.oi.stopJoystickHelperRumble();
	}
}