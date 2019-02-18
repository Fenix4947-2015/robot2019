package frc.robot.commands.balloonbox;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.joysticks.XBoxJoystick;

public class ManualControl extends Command {

    public ManualControl() {
        requires(Robot.ballonBox);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        double triggers = XBoxJoystick.HELPER.getTriggerAxes(0.2);
        Robot.ballonBox.intakeRoll(triggers);

        double y = XBoxJoystick.HELPER.getY(Hand.kLeft);
        Robot.ballonBox.pivot(y);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    protected void end() {
        Robot.ballonBox.pivotStop();
    }
}
