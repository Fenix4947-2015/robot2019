package frc.robot;

import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.autonomous.AutoNothing;
import frc.robot.commands.autonomous.StartGameMacro;
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

    public static enum HelperMode {
        CARGO,
        HATCH,
    }
        
    // Subsystems.
    public static BalloonBox ballonBox;
    public static Elevator elevator;
    public static HatchGrabber hatchGrabber;
    public static RobotUtilities utilities;
    public static DriveTrain driveTrain;
    public static Lifter lifter;

    // Operator interfaces.
    public static OI oi;

    private static HelperMode helperMode = HelperMode.HATCH;

    public static long startOfAutonomousMillis;
    String m_autonomousCommand;
	SendableChooser<String> m_chooser = new SendableChooser<>();
	Command m_currentCommand;

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

        setHelperMode(HelperMode.HATCH);

        SmartDashboard.putString("-------AUTONOMOUS--------","");
		m_chooser.addDefault("DO NOTHING", AutoNothing.NAME);
        m_chooser.addDefault("Descente Auto", StartGameMacro.NAME);
        SmartDashboard.putData("Auto mode", m_chooser);
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
        lifter.log();            
        oi.log();

        SmartDashboard.putString("Helper Mode", Robot.helperMode.name());

        long sinceStartOfMatchsMillis = System.currentTimeMillis() - Robot.startOfAutonomousMillis;
        SmartDashboard.putNumber("Since start (secs)", TimeUnit.MILLISECONDS.toSeconds(sinceStartOfMatchsMillis));
    }

    @Override
    public void autonomousInit() {
        elevator.zero();
        ballonBox.zeroPivot();
        lifter.frontDown();
        lifter.backDown(); 
        driveTrain.setFrontToPanelGripper();       
        setHelperMode(HelperMode.HATCH);

        Robot.startOfAutonomousMillis = System.currentTimeMillis();

        
		m_autonomousCommand = (String)m_chooser.getSelected();

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			switch (m_autonomousCommand) {
			
                case AutoNothing.NAME:
                    m_currentCommand = new AutoNothing();
                    m_currentCommand.start();
                    break;
                    
                case StartGameMacro.NAME:
                    m_currentCommand = new StartGameMacro();
                    m_currentCommand.start();
                    break;
			}
		}		
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
        System.out.println("Robot disabled");
    }

    public static void setHelperMode(HelperMode helperMode) {
        Robot.helperMode = helperMode;
    }

    public static boolean isHelperModeCargo() {
        return Robot.helperMode == HelperMode.CARGO;
    }

    public static boolean isHelperModeHatch() {
        return Robot.helperMode == HelperMode.HATCH;
    }    
}
