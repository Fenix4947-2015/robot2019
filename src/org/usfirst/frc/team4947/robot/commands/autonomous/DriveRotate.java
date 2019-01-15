package org.usfirst.frc.team4947.robot.commands.autonomous;

import org.usfirst.frc.team4947.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class DriveRotate extends Command {

	// Constants.
	private static final double DEFAULT_SPEED = 0.24;
	private static final double ROTATION_SPEED_PID_P = 0.13/90.0; // 20 % more at 90 deg. from 45 % at 90 deg to 15 at 0.
	private static final double Tolerance = 3.5; // degree	
	
	// Members.
	private double degrees;
	private double speed;

	public DriveRotate(double degreesClockWise) {
		this(degreesClockWise, DEFAULT_SPEED);
	}

	private DriveRotate(double degreesClockWise, double speed) {
		requires(Robot.driveTrain);
		this.degrees = degreesClockWise;
		this.speed = speed;
		setTimeout(2.5);
	}

	// Called just before the command runs the first time.
	protected void initialize() {
		Robot.driveTrain.resetGyroAngle();
	}

	// Called repeatedly when the command is scheduled to run.
	protected void execute() {
		double currentAngle = Robot.driveTrain.getGyroAngle();		
		
		double currentError = degrees - currentAngle; 
		double currentSpeed = speed + Math.abs(currentError) * ROTATION_SPEED_PID_P; 
		
		if (degrees < currentAngle) 
		{			
			Robot.driveTrain.rawDrive(currentSpeed, -currentSpeed);
		} 
		else if (currentAngle < degrees) 
		{			
			Robot.driveTrain.rawDrive(-currentSpeed, currentSpeed);
		}
		
	}

	// Called when another command which requires one or more of the same subsystems is scheduled to run.
	protected void interrupted() {
		end();
	}

	// Make this return TRUE when the command no longer needs to run execute().
	protected boolean isFinished() {
		boolean reachedTarget = Math.abs((Robot.driveTrain.getGyroAngle() - degrees)) < Tolerance;
		return reachedTarget;
	}

	// Called once after isFinished returns TRUE.
	protected void end() {
		Robot.driveTrain.driveStop();		
	}
}