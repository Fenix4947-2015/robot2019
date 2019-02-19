package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;

public class MoveElevatorToGround extends Command {
    
    public MoveElevatorToGround() {
        requires(Robot.elevator);

        setInterruptible(true);
        setTimeout(Elevator.COMMAND_TIMEOUT_IN_SECONDS);
    }

    @Override
    protected void initialize() {
        Robot.elevator.moveTo(Elevator.POSITION_GROUND);
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.isNear(Elevator.POSITION_GROUND) || isTimedOut();
    }

    @Override
    protected void interrupted() {
    }

    @Override
    protected void end() {
    }
}