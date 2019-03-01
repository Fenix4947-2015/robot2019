package frc.robot.commands.lifter;

import java.util.concurrent.TimeUnit;

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
        long sinceStartOfMatchsMillis = System.currentTimeMillis() - Robot.startOfAutonomousMillis;
        if (sinceStartOfMatchsMillis < TimeUnit.MINUTES.toMillis(2L)) {
            cancel();
        } else {
            Robot.lifter.toggleBack();
        }
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
