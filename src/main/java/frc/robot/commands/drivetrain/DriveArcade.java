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

  private boolean moveToLineBackward = false;
  private boolean moveToLineForward = false;
  private boolean stayOnLine = false;

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

    final boolean lineHitRearLeft = false;// Robot.colorSensorRearLeft.isOnReflectiveLine();
    final boolean lineHitRearRight = false;

    final boolean lineHitFrontLeft = false;
    final boolean lineHitFrontRight = false;

    final boolean lineHitMiddleLeft = false; // Robot.colorSensorMiddleLeft.isOnReflectiveLine();
    final boolean lineHitMiddleRight = false;

    double movePosValue = XBoxJoystick.DRIVER.getTriggerAxis(Hand.kLeft, 0.05);
    double moveNegValue = XBoxJoystick.DRIVER.getTriggerAxis(Hand.kRight, 0.05);
    double moveValue = movePosValue - moveNegValue;
    double rotateValue = XBoxJoystick.DRIVER.getX(Hand.kLeft, 0.05);

    // System.out.println("Move value: " + moveValue);
    // System.out.println("Rotate value: " + rotateValue);

    if (!lockOnLineMode) {
      moveToLineBackward = false;
      moveToLineForward = false;
      stayOnLine = false;
    } else {
      if (lineHitRearLeft || lineHitRearRight) {
        moveToLineBackward = true;
        moveToLineForward = false;
        stayOnLine = false;
        System.out.println("Line hit rear");
      } else if (lineHitFrontLeft || lineHitFrontRight) {
        moveToLineBackward = false;
        moveToLineForward = true;
        stayOnLine = false;
        System.out.println("Line hit front");
      } else if (lineHitMiddleLeft || lineHitMiddleRight) {
        moveToLineBackward = false;
        moveToLineForward = false;
        stayOnLine = true;
        System.out.println("Line hit middle");
      }
    }

    if (moveToLineBackward) {
      moveValue = 0.25;
      rotateValue = 0.0;
    } else if (moveToLineForward) {
      moveValue = -0.25;
      rotateValue = 0.0;
    } else if (stayOnLine) {
      moveValue = 0.0;
      rotateValue = 0.0;
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
