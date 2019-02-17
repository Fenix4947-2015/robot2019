package frc.robot;

import frc.robot.commands.StopAll;
import frc.robot.commands.balloonbox.IntakeRollInside;
import frc.robot.joysticks.XBoxJoystick;

public class OI {

    public OI() {
        initJoystickOfDriver(XBoxJoystick.DRIVER);
        initJoystickOfHelper(XBoxJoystick.HELPER);
    }

    private void initJoystickOfDriver(XBoxJoystick joystick) {
    }

    private void initJoystickOfHelper(XBoxJoystick joystick) {
        joystick.A.whenPressed(new IntakeRollInside());

        joystick.start.whenPressed(new StopAll());
    }
}