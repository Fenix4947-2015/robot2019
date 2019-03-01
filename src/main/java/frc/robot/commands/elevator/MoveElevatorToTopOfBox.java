package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Robot.HelperMode;

public class MoveElevatorToTopOfBox extends Command {

    public MoveElevatorToTopOfBox() {
        requires(Robot.elevator);

        setInterruptible(true);
        setTimeout(2.1);
    }

    @Override
    protected void initialize() {
        Robot.elevator.move(0.8);
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
