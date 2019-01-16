package frc.robot.commands.autonomous;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveFinalApproach extends Command {

	private double distance;
	private static final double NOMINAL_SPEED = 0.35;
	private static final double FT_PER_SEC_AT_NOMINAL_SPEED = 3.0 ; // to validate
			
	public DriveFinalApproach(double distanceDesired) 
	{        
    	requires(Robot.driveTrain);
    	requires(Robot.gripper);
    	distance = distanceDesired; 
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(distance / FT_PER_SEC_AT_NOMINAL_SPEED);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.rawDrive(NOMINAL_SPEED, NOMINAL_SPEED);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //return (isTimedOut() || Robot.gripper.isCubePresent()) ;
    	return isTimedOut();
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
