package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MoveElevatorToHighPosition extends Command {
    
    public MoveElevatorToHighPosition() {
        requires(Robot.elevator);

        setInterruptible(true);
    }

    @Override
    protected void initialize() {
        Robot.elevator.moveHigh();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.isHigh();
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
