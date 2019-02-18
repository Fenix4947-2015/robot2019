package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.StopAll;
import frc.robot.commands.balloonbox.DropBalloonLeft;
import frc.robot.commands.balloonbox.DropBalloonRight;
import frc.robot.commands.balloonbox.ResetFlippers;
import frc.robot.joysticks.XBoxJoystick;

public class OI {

    public OI() {
        initJoystickOfDriver(XBoxJoystick.DRIVER);
        initJoystickOfHelper(XBoxJoystick.HELPER);
    }

    private void initJoystickOfDriver(XBoxJoystick joystick) {
    }

    private void initJoystickOfHelper(XBoxJoystick joystick) {
        joystick.bumperLeft.whenPressed(new DropBalloonLeft());
        joystick.bumperRight.whenPressed(new DropBalloonRight());

        joystick.back.whenPressed(new ResetFlippers());
        joystick.start.whenPressed(new StopAll());
    }

    public void log() {
        SmartDashboard.putNumber("JoystickDriverLeftX", XBoxJoystick.DRIVER.getX(Hand.kLeft));
        SmartDashboard.putNumber("JoystickDriverLeftY", XBoxJoystick.DRIVER.getY(Hand.kLeft));
        SmartDashboard.putNumber("JoystickDriverRightX", XBoxJoystick.DRIVER.getX(Hand.kRight));
        SmartDashboard.putNumber("JoystickDriverRightY", XBoxJoystick.DRIVER.getY(Hand.kRight));

        SmartDashboard.putNumber("JoystickHelperLeftX", XBoxJoystick.HELPER.getX(Hand.kLeft));
        SmartDashboard.putNumber("JoystickHelperLeftY", XBoxJoystick.HELPER.getY(Hand.kLeft));
        SmartDashboard.putNumber("JoystickHelperRightX", XBoxJoystick.HELPER.getX(Hand.kRight));
        SmartDashboard.putNumber("JoystickHelperRightY", XBoxJoystick.HELPER.getY(Hand.kRight));
    }
}