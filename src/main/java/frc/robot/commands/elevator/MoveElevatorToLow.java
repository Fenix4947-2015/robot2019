package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MoveElevatorToLow extends Command {
    
    public MoveElevatorToLow() {
        requires(Robot.elevator);

        setInterruptible(true);
        setTimeout(5.0);
    }

    @Override
    protected void initialize() {
        Robot.elevator.move(-0.3);
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.isLow() || isTimedOut();
    }

    @Override
    protected void interrupted() {
    }

    @Override
    protected void end() {
    }
}