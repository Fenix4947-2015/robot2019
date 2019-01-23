package frc.robot.joysticks;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.RobotMap;

public enum XBoxJoystick {
    HELPER(RobotMap.JOYSTICK_OPERATOR_PORT);

    private static final double DEADBAND = 0.1;

    private XboxController joystick;
    private JoystickButton buttonA;
    private JoystickButton buttonB;
    private JoystickButton buttonX;
    private JoystickButton buttonY;
    private JoystickButton buttonLB;
    private JoystickButton buttonRB;
    private JoystickButton buttonBack;
    private JoystickButton buttonStart;
    private JoystickButton buttonLeftStick;
    private JoystickButton buttonRightStick;

    private XBoxJoystick(int port) {
        joystick = new XboxController(port);

        buttonA = new JoystickButton(joystick, XBoxButton.A.getValue());
        buttonB = new JoystickButton(joystick, XBoxButton.B.getValue());
        buttonX = new JoystickButton(joystick, XBoxButton.X.getValue());
        buttonY = new JoystickButton(joystick, XBoxButton.Y.getValue());
        buttonLB = new JoystickButton(joystick, XBoxButton.LB.getValue());
        buttonRB = new JoystickButton(joystick, XBoxButton.RB.getValue());
        buttonBack = new JoystickButton(joystick, XBoxButton.BACK.getValue());
        buttonStart = new JoystickButton(joystick, XBoxButton.START.getValue());
        buttonLeftStick = new JoystickButton(joystick, XBoxButton.LEFT_STICK.getValue());
        buttonRightStick = new JoystickButton(joystick, XBoxButton.RIGHT_STICK.getValue());
    }

    public JoystickButton getButtonA() {
        return buttonA;
    }

    public JoystickButton getButtonB() {
        return buttonB;
    }

    public JoystickButton getButtonX() {
        return buttonX;
    }

    public JoystickButton getButtonY() {
        return buttonY;
    }

    public JoystickButton getButtonLB() {
        return buttonLB;
    }

    public JoystickButton getButtonRB() {
        return buttonRB;
    }

    public JoystickButton getButtonBack() {
        return buttonBack;
    }

    public JoystickButton getButtonStart() {
        return buttonStart;
    }

    public JoystickButton getButtonLeftStick() {
        return buttonLeftStick;
    }

    public JoystickButton getButtonRightStick() {
        return buttonRightStick;
    }

    public double getX(Hand hand) {
        double x = joystick.getX(hand);
        return applyDeadband(x);
    }

    public double getY(Hand hand) {
        double y = joystick.getY(hand);
        return applyDeadband(y);
    }

    private double applyDeadband(double axisValue) {
        double absAxisValue = Math.abs(axisValue);
        if (absAxisValue < DEADBAND) {
            return 0.0;
        }

        return axisValue;
    }
}
