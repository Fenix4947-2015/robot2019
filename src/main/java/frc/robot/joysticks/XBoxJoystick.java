package frc.robot.joysticks;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.RobotMap;

public enum XBoxJoystick {
    DRIVER(RobotMap.JOYSTICK_DRIVER_PORT),
    HELPER(RobotMap.JOYSTICK_HELPER_PORT);

    private static final double DEFAULT_DEADBAND = 0.1;

    public JoystickButton A;
    public JoystickButton B;
    public JoystickButton X;
    public JoystickButton Y;
    public JoystickButton bumperLeft;
    public JoystickButton bumperRight;
    public JoystickButton back;
    public JoystickButton start;
    public JoystickButton stickLeft;
    public JoystickButton stickRight;

    private XboxController joystick;

    private XBoxJoystick(int port) {
        joystick = new XboxController(port);

        A = new JoystickButton(joystick, XBoxButton.A.getValue());
        B = new JoystickButton(joystick, XBoxButton.B.getValue());
        X = new JoystickButton(joystick, XBoxButton.X.getValue());
        Y = new JoystickButton(joystick, XBoxButton.Y.getValue());
        bumperLeft = new JoystickButton(joystick, XBoxButton.BUMPER_LEFT.getValue());
        bumperRight = new JoystickButton(joystick, XBoxButton.BUMPER_RIGHT.getValue());
        back = new JoystickButton(joystick, XBoxButton.BACK.getValue());
        start = new JoystickButton(joystick, XBoxButton.START.getValue());
        stickLeft = new JoystickButton(joystick, XBoxButton.STICK_LEFT.getValue());
        stickRight = new JoystickButton(joystick, XBoxButton.STICK_RIGHT.getValue());
    }

    public double getX(Hand hand) {
        return getX(hand, DEFAULT_DEADBAND);
    }

    public double getX(Hand hand, double deadband) {
        double x = joystick.getX(hand);
        return applyDeadband(x, deadband);
    }

    public double getY(Hand hand) {
        double y = joystick.getY(hand);
        return applyDeadband(y, DEFAULT_DEADBAND);
    }

    public double getY(Hand hand, double deadband) {
        double y = joystick.getY(hand);
        return applyDeadband(y, deadband);
    }

    public double getTriggerAxis(Hand hand) {
        return getTriggerAxis(hand, DEFAULT_DEADBAND);
    }

    public double getTriggerAxis(Hand hand, double deadband) {
        double triggerAxis = joystick.getTriggerAxis(hand);
        return applyDeadband(triggerAxis, deadband);
    }

    private double applyDeadband(double axisValue, double deadband) {
        double absAxisValue = Math.abs(axisValue);
        if (absAxisValue < deadband) {
            return 0.0;
        }

        return axisValue;
    }

    public double getRawX(Hand hand) {
        return joystick.getX(hand);
    }

    public double getRawY(Hand hand) {
        return joystick.getY(hand);
    }

    public double getRawTriggerAxis(Hand hand) {
        return joystick.getTriggerAxis(hand);
    }

    public boolean getButton(XBoxButton button) {
        return joystick.getRawButton(button.getValue());
    }

    public boolean getButtonPressed(XBoxButton button) {
        return joystick.getRawButtonPressed(button.getValue());
    }

    public boolean getButtonReleased(XBoxButton button) {
        return joystick.getRawButtonReleased(button.getValue());
    }
}