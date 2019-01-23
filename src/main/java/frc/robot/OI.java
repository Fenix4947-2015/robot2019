package frc.robot;

import frc.robot.commands.elevator.MoveElevatorToHighPosition;
import frc.robot.commands.elevator.MoveElevatorToLowPosition;
import frc.robot.joysticks.XBoxJoystick;

public class OI {

    public OI() {
        initControllerOfHelper();
    }

    private void initControllerOfHelper() {
        XBoxJoystick joystick = XBoxJoystick.HELPER;

        joystick.getButtonA().whenPressed(new MoveElevatorToLowPosition());
        joystick.getButtonX().whenPressed(new MoveElevatorToHighPosition());
    }
}
