package frc.robot;

import frc.robot.commands.StopAll;
import frc.robot.commands.balloonbox.IntakeInPosition;
import frc.robot.commands.balloonbox.IntakeOutPosition;
import frc.robot.commands.balloonbox.ToggleIntakeRoller;
import frc.robot.commands.drivetrain.SetFrontToIntake;
import frc.robot.commands.drivetrain.SetFrontToPanelGripper;
import frc.robot.commands.elevator.LoadBalloonIntoBox;
import frc.robot.commands.elevator.MoveElevatorToCount;
import frc.robot.commands.elevator.MoveElevatorToLow;
import frc.robot.commands.hatchgrabber.DeployHatch;
import frc.robot.commands.hatchgrabber.RetractHatch;
import frc.robot.commands.hatchgrabber.RetractHatchMacro;
import frc.robot.commands.lifter.ToggleBackLift;
import frc.robot.commands.lifter.ToggleFrontLift;
import frc.robot.joysticks.XBoxJoystick;
import frc.robot.subsystems.Elevator;

public class OI {

    public OI() {
        initJoystickOfDriver(XBoxJoystick.DRIVER);
        initJoystickOfHelper(XBoxJoystick.HELPER);
    }

    private void initJoystickOfDriver(XBoxJoystick joystick) {
        //joystick.X.whenPressed(new IntakeOutPosition());
        //joystick.A.whenPressed(new IntakeInPosition());

        joystick.Y.whenPressed(new ToggleFrontLift());
        joystick.B.whenPressed(new ToggleBackLift());

        joystick.bumperRight.whenPressed(new SetFrontToIntake());
        joystick.bumperLeft.whenPressed(new SetFrontToPanelGripper());

        joystick.A.whenPressed(new MoveElevatorToLow());
        joystick.X.whenPressed(new MoveElevatorToCount(Elevator.POS_HATCH_DURING_SANDSTORM));

        joystick.start.whenPressed(new StopAll());
    }

    private void initJoystickOfHelper(XBoxJoystick joystick) {
        joystick.A.whenPressed(new LoadBalloonIntoBox());

        joystick.bumperLeft.whenPressed(new RetractHatchMacro());
        joystick.bumperRight.whenPressed(new DeployHatch());

        joystick.start.whenPressed(new StopAll());
        joystick.stickLeft.whenPressed(new ToggleIntakeRoller());

        joystick.X.whenPressed(new MoveElevatorToCount(Elevator.POS_ROCKET_LEVEL_1));
        joystick.Y.whenPressed(new MoveElevatorToCount(Elevator.POS_ROCKET_LEVEL_2));
        joystick.B.whenPressed(new MoveElevatorToCount(Elevator.POS_ROCKET_LEVEL_3));
    }

    public void log() {
        /*
        SmartDashboard.putNumber("JoystickDriverLeftX", XBoxJoystick.DRIVER.getX(Hand.kLeft));
        SmartDashboard.putNumber("JoystickDriverLeftY", XBoxJoystick.DRIVER.getY(Hand.kLeft));
        SmartDashboard.putNumber("JoystickDriverRightX", XBoxJoystick.DRIVER.getX(Hand.kRight));
        SmartDashboard.putNumber("JoystickDriverRightY", XBoxJoystick.DRIVER.getY(Hand.kRight));

        SmartDashboard.putNumber("JoystickHelperLeftX", XBoxJoystick.HELPER.getX(Hand.kLeft));
        SmartDashboard.putNumber("JoystickHelperLeftY", XBoxJoystick.HELPER.getY(Hand.kLeft));
        SmartDashboard.putNumber("JoystickHelperRightX", XBoxJoystick.HELPER.getX(Hand.kRight));
        SmartDashboard.putNumber("JoystickHelperRightY", XBoxJoystick.HELPER.getY(Hand.kRight));
        */
    }
}