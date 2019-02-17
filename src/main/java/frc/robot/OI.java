package frc.robot;

import frc.robot.commands.StopAll;
import frc.robot.commands.balloonbox.CloseLeft;
import frc.robot.commands.balloonbox.CloseRight;
import frc.robot.commands.balloonbox.IntakeRollInside;
import frc.robot.commands.balloonbox.OpenLeft;
import frc.robot.commands.balloonbox.OpenRight;
import frc.robot.joysticks.XBoxJoystick;

public class OI {

    public OI() {
        initJoystickOfDriver(XBoxJoystick.DRIVER);
    }

    private void initJoystickOfDriver(XBoxJoystick joystick) {
        joystick.A.whenPressed(new CloseRight());
        joystick.B.whenPressed(new OpenRight());
        joystick.X.whenPressed(new CloseLeft());
        joystick.Y.whenPressed(new OpenLeft());

        joystick.stickRight.whileHeld(new IntakeRollInside());

        joystick.start.whenPressed(new StopAll());
    }
}