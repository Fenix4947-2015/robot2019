package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.BalloonBox;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.HatchGrabber;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.RobotUtilities;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    
    // Subsystems.
    public static BalloonBox ballonBox;
    public static Elevator elevator;
    public static HatchGrabber hatchGrabber;
    public static RobotUtilities utilities;
    public static DriveTrain driveTrain;
    public static Lifter lifter;

    public static UsbCamera intakeCam;
    public static UsbCamera hatchCam;
    public static VideoSink camServer;

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
        ballonBox = new BalloonBox();
        elevator = new Elevator();
        hatchGrabber = new HatchGrabber();
        utilities = new RobotUtilities();
        driveTrain = new DriveTrain();
        lifter = new Lifter();

        RobotMap.init();

        oi = new OI();

       // sensorMonitor = new SensorMonitor();

        intakeCam = CameraServer.getInstance().startAutomaticCapture("intakeCam", RobotMap.INTAKE_CAMERA_DEV);
        hatchCam = CameraServer.getInstance().startAutomaticCapture("hatchCam", RobotMap.HATCH_CAMERA_DEV);

        /*
         * Keeping Streams Open
         * 
         * By default, the cscore library is pretty aggressive in
         * turning off cameras not in use. What this means is that when you switch
         * cameras, it may disconnect from the camera not in use, so switching back will
         * have some delay as it reconnects to the camera. To keep both camera
         * connections open, use the SetConnectionStrategy() method to tell the library
         * to keep the streams open, even if you aren't using them.
         */
        intakeCam.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
        hatchCam.setConnectionStrategy(ConnectionStrategy.kKeepOpen);

        camServer = CameraServer.getInstance().getServer();
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
        ballonBox.log();
        elevator.log();        
        oi.log();
        
    }

    @Override
    public void autonomousInit() {
        elevator.zero();
        
        lifter.frontDown();
        lifter.backDown();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        ballonBox.periodicLogic();
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
