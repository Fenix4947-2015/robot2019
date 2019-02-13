/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.joysticks.XBoxButton;
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
    // double moveValue = Robot.oi.getJoystickDriverAxis(XBoxAxis.LEFT_STICK_Y,
    // 0.1);

    final boolean lockOnLineMode = XBoxJoystick.DRIVER.getButton(XBoxButton.BUMPER_LEFT);
    final boolean colorSensorOnLine = Robot.colorSensorRearCentre.isOnReflectiveLine();

    double moveValue = XBoxJoystick.DRIVER.getY(Hand.kLeft);
    double rotateValue = XBoxJoystick.DRIVER.getX(Hand.kLeft);

    if (lockOnLineMode && colorSensorOnLine) {
      moveValue = 0.0;
    }

    Robot.driveTrain.driveArcadeMethod(-moveValue, rotateValue);

    // Example of how to use the Pigeon IMU

    // 1. read the values from the sensor
    // Robot.driveTrain.pigeon.refresh();
    // 2. use the retrieved values
    // System.out.println("Yaw: " + Robot.driveTrain.pigeon.yaw);
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
