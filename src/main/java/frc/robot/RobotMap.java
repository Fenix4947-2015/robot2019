package frc.robot;

public class RobotMap {

  // Joysticks.
  public static final int JOYSTICK_DRIVER_PORT = 0;
  public static final int JOYSTICK_HELPER_PORT = 1;

  // Drive train.
  public static final int LEFT_MOTOR1_ADDRESS = 7;
  public static final int LEFT_MOTOR2_ADDRESS = 1;

  public static final int RIGHT_MOTOR1_ADDRESS = 6;
  public static final int RIGHT_MOTOR2_ADDRESS = 2;

  // BalloonBox
  public static final int INTAKE_ROLLER_MOTOR_ADDRESS = 3;
  public static final int PIVOT_MOTOR_ADDRESS = 9;

  // Elevator
  public static final int ELEVATOR_MOTOR_ADDRESS = 5;

  // Color Sensors
  public static final int COLOR_SENSOR_MIDDLE_LEFT_ADDRESS = 0x10;
  public static final int COLOR_SENSOR_REAR_LEFT_ADDRESS = 0x14;
  public static final int COLOR_SENSOR_FRONT_LEFT_ADDRESS = 0x0;
  public static final int COLOR_SENSOR_MIDDLE_RIGHT_ADDRESS = 0x0;
  public static final int COLOR_SENSOR_REAR_RIGHT_ADDRESS = 0x0;
  public static final int COLOR_SENSOR_FRONT_RIGHT_ADDRESS = 0x0;

  public static void init() {
    // SmartDashboard.putData(Robot.elevator);
  }
}
