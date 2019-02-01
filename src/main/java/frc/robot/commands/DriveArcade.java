/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.joysticks.XBoxJoystick;

public class DriveArcade extends Command {
  public DriveArcade() {
    requires(Robot.driveTrain);
	}

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
   // double moveValue = Robot.oi.getJoystickDriverAxis(XBoxAxis.LEFT_STICK_Y, 0.1);
    double moveValue = XBoxJoystick.DRIVER.getY(Hand.kLeft);


		
    double rotateValue = XBoxJoystick.DRIVER.getX(Hand.kLeft);
		Robot.driveTrain.driveArcadeMethod(-moveValue, rotateValue);
	
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
