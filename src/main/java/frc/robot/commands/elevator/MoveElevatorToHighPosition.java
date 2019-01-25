package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;

public class MoveElevatorToHighPosition extends Command {
    
    public MoveElevatorToHighPosition() {
        requires(Robot.elevator);

        setInterruptible(true);
    }

    @Override
    protected void initialize() {
        setTimeout(Elevator.COMMAND_TIMEOUT_IN_SECONDS);

        Robot.elevator.moveToHigh();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.isHigh() || isTimedOut();
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
