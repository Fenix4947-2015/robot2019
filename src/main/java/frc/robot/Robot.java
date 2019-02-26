package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.HatchGrabber;
import frc.robot.subsystems.RobotUtilities;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    
    // Subsystems.
    public static HatchGrabber hatchGrabber;
    public static RobotUtilities utilities;
    public static DriveTrain driveTrain;

    // Operator interfaces.
    public static OI oi;

	  // Components / Sensors
	  //public static SensorMonitor sensorMonitor;

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        hatchGrabber = new HatchGrabber();
        utilities = new RobotUtilities();
        driveTrain = new DriveTrain();

        RobotMap.init();

        oi = new OI();

       // sensorMonitor = new SensorMonitor();
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for items like 
     * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
     *
     * This runs after the mode specific periodic functions, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        oi.log();
        
    }

    @Override
    public void autonomousInit() {
    	//	sensorMonitor.startMonitoring();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
    	//	sensorMonitor.startMonitoring();
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
    		//sensorMonitor.stopMonitoring();
    		System.out.println("Robot disabled");
  	}
}
