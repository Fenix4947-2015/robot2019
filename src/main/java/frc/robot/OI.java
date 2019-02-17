package frc.robot;

import frc.robot.commands.StopAll;
import frc.robot.commands.balloonbox.IntakeRollInside;
import frc.robot.joysticks.XBoxJoystick;

public class OI {

    public OI() {
        initJoystickOfHelper();
    }

    private void initJoystickOfHelper() {
        XBoxJoystick joystick = XBoxJoystick.DRIVER;

        joystick.A.whenPressed(new IntakeRollInside());
        joystick.start.whenPressed(new StopAll());
        
        //joystick.X.whenPressed(new ColorSensorCalibration(true));
        //joystick.Y.whenPressed(new ColorSensorCalibration(false));
        
    }
}
