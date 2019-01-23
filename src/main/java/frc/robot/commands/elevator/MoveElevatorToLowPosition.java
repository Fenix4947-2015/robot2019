package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MoveElevatorToLowPosition extends Command {
    
    public MoveElevatorToLowPosition() {
        requires(Robot.elevator);

        setInterruptible(true);
    }

    @Override
    protected void initialize() {
        Robot.elevator.moveLow();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.isLow();
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
