package org.usfirst.frc.team4947.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */



public class Wait extends Command {
	private double waitTim;
    public Wait(double waittime) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	waitTim = waittime;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(waitTim);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
