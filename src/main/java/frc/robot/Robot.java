package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.components.SensorMonitor;
import frc.robot.subsystems.BalloonBox;
import frc.robot.subsystems.DriveTrain;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  // Subsystems
  public static DriveTrain driveTrain;
  public static BalloonBox ballonBox;
  public static OI oi;

  // Components / Sensors
  //public static ColorSensor colorSensorMiddleLeft;
  //public static ColorSensor colorSensorRearLeft;
  public static SensorMonitor sensorMonitor;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    driveTrain = new DriveTrain();
    ballonBox = new BalloonBox();
    //colorSensorMiddleLeft = new ColorSensor(RobotMap.COLOR_SENSOR_MIDDLE_LEFT_ADDRESS);
    //colorSensorRearLeft = new ColorSensor(RobotMap.COLOR_SENSOR_REAR_LEFT_ADDRESS);

    RobotMap.init();

    oi = new OI();

    sensorMonitor = new SensorMonitor();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    sensorMonitor.startMonitoring();
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    sensorMonitor.startMonitoring();
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
    Scheduler.getInstance().run();
  }
  
  @Override
  public void disabledInit() {
    sensorMonitor.stopMonitoring();
    System.out.println("Robot disabled");
  }
}
