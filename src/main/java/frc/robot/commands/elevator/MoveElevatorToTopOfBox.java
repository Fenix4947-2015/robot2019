package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MoveElevatorToTopOfBox extends Command {

    public MoveElevatorToTopOfBox() {
        requires(Robot.elevator);

        setInterruptible(true);
        setTimeout(1.7);
    }

    @Override
    protected void initialize() {
        Robot.elevator.move(0.9);
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
        Robot.elevator.stop();
    }
}
