package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.joysticks.XBoxJoystick;
import frc.robot.subsystems.Elevator;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    public static Elevator elevator;

    public static OI oi;

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        elevator = new Elevator();

        RobotMap.init();

        oi = new OI();
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
        SmartDashboard.putBoolean("Elevator low switch", Robot.elevator.isLow());
        SmartDashboard.putBoolean("Elevator high switch", Robot.elevator.isHigh());
        SmartDashboard.putNumber("Elevator encoder position (count)", Robot.elevator.getEncoderPosition());
        SmartDashboard.putNumber("Elevator closed-loop error (count)", Robot.elevator.getClosedLoopError());
        SmartDashboard.putNumber("Elevator motor output %", Robot.elevator.getMotorOutputPercent());

        SmartDashboard.putNumber("Joystick helper left stick X", XBoxJoystick.HELPER.getXRaw(Hand.kLeft));
        SmartDashboard.putNumber("Joystick helper left stick Y", XBoxJoystick.HELPER.getYRaw(Hand.kLeft));
        SmartDashboard.putNumber("Joystick helper right stick X", XBoxJoystick.HELPER.getXRaw(Hand.kRight));
        SmartDashboard.putNumber("Joystick helper right stick Y", XBoxJoystick.HELPER.getYRaw(Hand.kRight));        
    }

    @Override
    public void autonomousInit() {
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
}
