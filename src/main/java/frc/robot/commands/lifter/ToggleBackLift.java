package frc.robot.commands.lifter;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Lifter;

public class ToggleBackLift extends Command {

    public ToggleBackLift() {
        requires(Robot.lifter);

        setInterruptible(false);
        setTimeout(Lifter.TOGGLE_TIMEOUT_IN_S);
    }

    @Override
    protected void initialize() {
        Robot.lifter.toggleBack();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    protected void end() {
    }
}