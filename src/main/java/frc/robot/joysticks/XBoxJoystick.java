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
        double x = getXRaw(hand);
        return applyDeadband(x, deadband);
    }

    public double getXSquared(Hand hand) {
        return getXSquared(hand, DEFAULT_DEADBAND);
    }

    public double getXSquared(Hand hand, double deadband) {
        double x = getXRaw(hand);
        double xSquared = Math.pow(Math.abs(x), 2.0) * Math.signum(x);
        return applyDeadband(xSquared, deadband);
    }

    public double getXRaw(Hand hand) {
        return joystick.getX(hand);
    }

    public double getY(Hand hand) {
        return getY(hand, DEFAULT_DEADBAND);
    }

    public double getY(Hand hand, double deadband) {
        double y = getYRaw(hand);
        return applyDeadband(y, deadband);
    }

    public double getYSquared(Hand hand) {
        return getYSquared(hand, DEFAULT_DEADBAND);
    }

    public double getYSquared(Hand hand, double deadband) {
        double y = getYRaw(hand);
        double ySquared = Math.pow(Math.abs(y), 2.0) * Math.signum(y);
        return applyDeadband(ySquared, deadband);
    }

    public double getYRaw(Hand hand) {
        return joystick.getY(hand) * -1.0;
    }

    public double getTriggerAxis(Hand hand) {
        return getTriggerAxis(hand, DEFAULT_DEADBAND);
    }

    public double getTriggerAxis(Hand hand, double deadband) {
        double triggerAxis = getTriggerAxisRaw(hand);
        return applyDeadband(triggerAxis, deadband);
    }

    public double getTriggerAxisSquared(Hand hand) {
        return getTriggerAxisSquared(hand, DEFAULT_DEADBAND);
    }

    public double getTriggerAxisSquared(Hand hand, double deadband) {
        double triggerAxis = getTriggerAxisRaw(hand);
        double triggerAxisSquared = Math.pow(Math.abs(triggerAxis), 2.0) * Math.signum(triggerAxis);
        return applyDeadband(triggerAxisSquared, deadband);
    }

    public double getTriggerAxisRaw(Hand hand) {
        return joystick.getTriggerAxis(hand);
    }

    private double applyDeadband(double axisValue, double deadband) {
        double absAxisValue = Math.abs(axisValue);
        if (absAxisValue < deadband) {
            return 0.0;
        }

        return axisValue;
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