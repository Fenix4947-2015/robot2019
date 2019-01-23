package frc.robot;

import frc.robot.commands.elevator.MoveElevatorToHighPosition;
import frc.robot.commands.elevator.MoveElevatorToLowPosition;
import frc.robot.joysticks.XBoxJoystick;

public class OI {

    public OI() {
        initJoystickOfHelper();
    }

    private void initJoystickOfHelper() {
        XBoxJoystick joystick = XBoxJoystick.HELPER;

        joystick.A.whenPressed(new MoveElevatorToLowPosition());
        joystick.X.whenPressed(new MoveElevatorToHighPosition());
    }
}
