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
import frc.robot.components.SensorMonitor.SensorPos;
import frc.robot.joysticks.XBoxButton;
import frc.robot.joysticks.XBoxJoystick;

public class DriveArcade extends Command {

  private MoveMode moveMode = MoveMode.MANUAL;

  public DriveArcade() {
    requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  private enum MoveMode {
    MANUAL, BACKWARD_TO_LINE, FORWARD_TO_LINE, OVERSHOOT_FORWARD, OVERSHOOT_BACKWARD, STAY_ON_LINE_FORWARD,
    STAY_ON_LINE_BACKWARD, STAY_ON_LINE_STOP
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    final boolean lockOnLineMode = XBoxJoystick.DRIVER.getButton(XBoxButton.B);

    final boolean lineHitRearLeft = Robot.sensorMonitor.isLineDetectedAndClear(SensorPos.REAR_LEFT);
    final boolean lineHitRearRight = Robot.sensorMonitor.isLineDetectedAndClear(SensorPos.REAR_RIGHT);

    final boolean lineHitFrontLeft = Robot.sensorMonitor.isLineDetectedAndClear(SensorPos.FRONT_LEFT);
    final boolean lineHitFrontRight = Robot.sensorMonitor.isLineDetectedAndClear(SensorPos.FRONT_RIGHT);

    final boolean lineHitMiddleLeft = Robot.sensorMonitor.isLineDetectedAndClear(SensorPos.MIDDLE_LEFT);
    final boolean lineHitMiddleRight = Robot.sensorMonitor.isLineDetectedAndClear(SensorPos.MIDDLE_RIGHT);

    double movePosValue = XBoxJoystick.DRIVER.getTriggerAxis(Hand.kRight, 0.05);
    double moveNegValue = XBoxJoystick.DRIVER.getTriggerAxis(Hand.kLeft, 0.05);
    double moveValue = movePosValue - moveNegValue;
    double rotateValue = XBoxJoystick.DRIVER.getX(Hand.kLeft, 0.05);

    // System.out.println("Move value: " + moveValue);
    // System.out.println("Rotate value: " + rotateValue);

    if (!lockOnLineMode) {
      moveMode = MoveMode.MANUAL;
    } else {
      switch (moveMode) {
      case MANUAL:
        if (lineHitRearLeft || lineHitRearRight) {
          moveMode = MoveMode.BACKWARD_TO_LINE;
          System.out.println("Line hit rear");
        } else if (lineHitFrontLeft || lineHitFrontRight) {
          moveMode = MoveMode.FORWARD_TO_LINE;
          System.out.println("Line hit front");
        }
        break;
      case BACKWARD_TO_LINE:
        if (lineHitMiddleLeft || lineHitMiddleRight) {
          moveMode = MoveMode.STAY_ON_LINE_BACKWARD;
          System.out.println("Line hit middle from backward");
        }
        break;
      case FORWARD_TO_LINE:
        if (lineHitMiddleLeft || lineHitMiddleRight) {
          moveMode = MoveMode.STAY_ON_LINE_FORWARD;
          System.out.println("Line hit middle from forward");
        }
        break;
      case STAY_ON_LINE_BACKWARD:
        if (!(lineHitMiddleLeft || lineHitMiddleRight)) {
          // overshoot
          moveMode = MoveMode.OVERSHOOT_BACKWARD;
          System.out.println("Overshoot from backward");
        }
        break;
      case STAY_ON_LINE_FORWARD:
        if (!(lineHitMiddleLeft || lineHitMiddleRight)) {
          // overshoot
          moveMode = MoveMode.OVERSHOOT_FORWARD;
          System.out.println("Overshoot from forward");
        }
        break;
      case OVERSHOOT_BACKWARD:
        if (lineHitMiddleLeft || lineHitMiddleRight) {
          // overshoot
          moveMode = MoveMode.STAY_ON_LINE_FORWARD;
          System.out.println("Back on line from overshoot backward");
        }
        break;
      case OVERSHOOT_FORWARD:
        if (lineHitMiddleLeft || lineHitMiddleRight) {
          // overshoot
          moveMode = MoveMode.STAY_ON_LINE_BACKWARD;
          System.out.println("Back on line from overshoot forward");
        }
        break;
      case STAY_ON_LINE_STOP:
        break;
      }
    }

    switch (moveMode) {
    case MANUAL:
      break;
    case BACKWARD_TO_LINE:
      moveValue = 0.30;
      rotateValue = 0.0;
      break;
    case FORWARD_TO_LINE:
      moveValue = -0.30;
      rotateValue = 0.0;
      break;
    case OVERSHOOT_FORWARD:
      moveValue = 0.35;
      rotateValue = 0.0;
      break;
    case OVERSHOOT_BACKWARD:
      moveValue = -0.35;
      rotateValue = 0.0;
      break;
    case STAY_ON_LINE_STOP:
    case STAY_ON_LINE_FORWARD:
    case STAY_ON_LINE_BACKWARD:
      moveValue = 0.0;
      rotateValue = 0.0;
      break;
    default:
      break;
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
