package frc.robot;

import frc.robot.joysticks.XBoxJoystick;

public class OI {

    public OI() {
        initJoystickOfHelper();
    }

    private void initJoystickOfHelper() {
        XBoxJoystick joystick = XBoxJoystick.HELPER;

        //joystick.A.whenPressed(new MoveElevatorToLowPosition());
    }
}