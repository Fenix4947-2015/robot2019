package frc.robot;

import frc.robot.commands.drivetrain.SetFrontToIntake;
import frc.robot.commands.drivetrain.SetFrontToPanelGripper;
import frc.robot.joysticks.XBoxJoystick;

public class OI {

    public OI() {
        initJoystickOfDriver(XBoxJoystick.DRIVER);
        initJoystickOfHelper(XBoxJoystick.HELPER);
    }

    private void initJoystickOfDriver(XBoxJoystick joystick) {
        joystick.bumperLeft.whenPressed(new SetFrontToIntake());
        joystick.bumperRight.whenPressed(new SetFrontToPanelGripper());
    }

    private void initJoystickOfHelper(XBoxJoystick joystick) {
    }
}