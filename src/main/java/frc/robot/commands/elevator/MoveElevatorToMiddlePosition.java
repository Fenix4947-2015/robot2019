package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;

public class MoveElevatorToMiddlePosition extends Command {
    
    public MoveElevatorToMiddlePosition() {
        requires(Robot.elevator);

        setInterruptible(true);
    }

    @Override
    protected void initialize() {
        setTimeout(Elevator.COMMAND_TIMEOUT_IN_SECONDS);

        Robot.elevator.moveToMiddle();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.isMiddle() || isTimedOut();
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
