package frc.robot.commands.utility;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.components.SensorMonitor.SensorPos;

public class ColorSensorCalibration extends Command {

  private boolean finished = false;
  private final SensorPos pos;
  private final boolean isWhiteCal;

  public ColorSensorCalibration(SensorPos sensorPos, boolean isWhite) {
    pos = sensorPos;
    isWhiteCal = isWhite;
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    if (isWhiteCal) {
      System.out.println("White cal");
      //Robot.sensorMonitor.getColorSensor(pos).whiteCalibration();
    } else {
      System.out.println("Black cal");
      //Robot.sensorMonitor.getColorSensor(pos).blackCalibration();
    }
    finished = true;
  }

  @Override
  protected boolean isFinished() {
    return finished;
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
