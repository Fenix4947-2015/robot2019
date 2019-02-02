package frc.robot;

import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public interface RobotMap {

    // Joysticks.
    public static final int JOYSTICK_HELPER_PORT = 0;

    // Elevator.
    public static final int ELEVATOR_MOTOR_DEVICE_NUMBER = -1;

    public static void init() {
        SmartDashboard.putData(Scheduler.getInstance());

        SmartDashboard.putData(Robot.elevator);
    }
}
