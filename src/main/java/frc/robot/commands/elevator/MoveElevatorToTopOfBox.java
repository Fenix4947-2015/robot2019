package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;

public class MoveElevatorToTopOfBox extends Command {

    public MoveElevatorToTopOfBox() {
        requires(Robot.elevator);

        setInterruptible(true);
        setTimeout(Elevator.COMMAND_TIMEOUT_IN_SECONDS);
    }

    @Override
    protected void initialize() {
        Robot.elevator.moveTo(Elevator.POSITION_TOP_OF_BOX);
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.isNear(Elevator.POSITION_TOP_OF_BOX) || isTimedOut();
    }

    @Override
    protected void interrupted() {
    }

    @Override
    protected void end() {
    }
}
