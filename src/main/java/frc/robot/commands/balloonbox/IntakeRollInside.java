package frc.robot.commands.balloonbox;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class IntakeRollInside extends Command {

    public IntakeRollInside() {
        requires(Robot.ballonBox);
    }

    @Override
    protected void initialize() {
        Robot.ballonBox.intakeRollInside();
    }

    @Override
    protected void execute() {
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
        Robot.ballonBox.intakeStop();
    }
}
