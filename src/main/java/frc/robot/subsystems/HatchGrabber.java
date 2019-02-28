package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.hatchgrabber.IdleHatchGrabber;

public class HatchGrabber extends Subsystem {

  public static final double HATCHGRABBER_TIMEOUT_IN_S = 1.0;

  private static final boolean HATCH_STATE_DEPLOYED = true;
  private static final boolean HATCH_STATE_RETRACTED = !HATCH_STATE_DEPLOYED;

  private Solenoid hatchSolenoid;

  public HatchGrabber() {
    hatchSolenoid = new Solenoid(RobotMap.HATCH_GRABBER_SOLENOID_ADDRESS);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new IdleHatchGrabber());
  }

  public void deploy() {
    hatchSolenoid.set(HATCH_STATE_DEPLOYED);
    log();
  }

  public void retract() {
    hatchSolenoid.set(HATCH_STATE_RETRACTED);
    log();
  }

  public void log() {
    SmartDashboard.putBoolean("Hatch grabber deployed", hatchSolenoid.get());
  }
}
