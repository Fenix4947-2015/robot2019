package frc.robot;

public class RobotMap {
	
	// Joysticks.
	public static final int JOYSTICK_DRIVER_PORT = 0;
	public static final int JOYSTICK_HELPER_PORT = 1;

	// Drive train.
	public static final int LEFT_MOTOR1_ADDRESS = 5;
	public static final int LEFT_MOTOR2_ADDRESS = 1;

	public static final int RIGHT_MOTOR1_ADDRESS = 6;
    public static final int RIGHT_MOTOR2_ADDRESS = 2;
	
	// BalloonBox
	public static final int INTAKE_ROLLER_MOTOR_ADDRESS = 3;
	public static final int PIVOT_MOTOR_ADDRESS = 4;

    public static void init() {
        // SmartDashboard.putData(Robot.elevator);
    }
}