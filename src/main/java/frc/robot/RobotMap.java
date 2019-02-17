package frc.robot;

import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotMap {
	
	// Joysticks.
	public static final int JOYSTICK_DRIVER_PORT = 0;
	public static final int JOYSTICK_HELPER_PORT = 1;

	// Drive train.
	public static final int LEFT_MOTOR1_ADDRESS = 7;
	public static final int LEFT_MOTOR2_ADDRESS = 1;

	public static final int RIGHT_MOTOR1_ADDRESS = 6;
    public static final int RIGHT_MOTOR2_ADDRESS = 2;
	
	// Balloon box.
    public static final int INTAKE_ROLLER_MOTOR_ADDRESS = 3;
    public static final int PIVOT_MOTOR_ADDRESS = 9;
    public static final int LEFT_SOLENOID_ADDRESS = 1;
    public static final int RIGHT_SOLENOID_ADDRESS = 2;

	// Elevator.
	public static final int ELEVATOR_MOTOR_ADDRESS = 5;

	// Color sensors.
	public static final int COLOR_SENSOR_MIDDLE_LEFT_ADDRESS = 0x10;
	public static final int COLOR_SENSOR_REAR_LEFT_ADDRESS = 0x14;
	
    public static void init() {
        SmartDashboard.putData(Scheduler.getInstance());

        SmartDashboard.putData(Robot.ballonBox);
    }
}