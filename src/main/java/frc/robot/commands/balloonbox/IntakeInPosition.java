/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.balloonbox;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class IntakeInPosition extends Command {
  public IntakeInPosition() {
    requires(Robot.ballonBox);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.ballonBox.pivotToPosition(-5.0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.ballonBox.intakeRollInside();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.ballonBox.intakeStop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
