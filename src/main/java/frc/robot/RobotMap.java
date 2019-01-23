package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public interface RobotMap {

    // Joysticks.
    public static final int JOYSTICK_PORT_HELPER = 0;

    // Elevator.
    public static final int ELEVATOR_MOTOR_DEVICE_NUMBER = -1;

    public static void init() {
        SmartDashboard.putData(Robot.elevator);
    }
}
