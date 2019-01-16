package frc.robot.commands.autonomous;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveDistance extends Command {

	// Constants.
	private static final double DISTANCE_THRESHOLD_FEET = 0.25; // three inches
	
	// Members.
	private double distanceFeet;
	
	public DriveDistance(double distanceFeet) {
		requires(Robot.driveTrain);
		this.distanceFeet = distanceFeet;
	}

	// Called just before the command runs the first time.
	protected void initialize() {
		setTimeout(0.25);
		Robot.driveTrain.driveToDistance(distanceFeet);			
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
		SmartDashboard.putNumber("PID ERROR", Robot.driveTrain.getEncoderDistanceErrorFeet());
		
		if(isTimedOut())
		{
			
			boolean reachedPosition = (Robot.driveTrain.getEncoderDistanceErrorFeet()<DISTANCE_THRESHOLD_FEET);
	        return reachedPosition; //(reachedPosition|| isTimedOut() || Robot.driveTrain.isRobotMoving());

		}
		else
		{
			return false;
		}
	}

	// Called once after isFinished returns TRUE.
	protected void end() {
		Robot.driveTrain.driveStop();
	}
}