package frc.robot.commands.balloonbox;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.joysticks.XBoxJoystick;

public class ManualPivot extends Command {

    public ManualPivot() {
        requires(Robot.ballonBox);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        double y = XBoxJoystick.DRIVER.getY(Hand.kLeft);
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
