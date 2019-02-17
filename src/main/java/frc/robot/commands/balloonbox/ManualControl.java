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
        double y = XBoxJoystick.HELPER.getY(Hand.kRight);

        double yAbs = Math.abs(y);
        if (yAbs >= 0.1) {
            Robot.ballonBox.pivot(y);
        } else {
            Robot.ballonBox.pivotStop();
        }

        double triggerRight = XBoxJoystick.HELPER.getTriggerAxis(Hand.kRight);
        if (triggerRight > 0.2) {
            Robot.ballonBox.intakeRollInside();
        } else {
            Robot.ballonBox.intakeStop();
        }
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
