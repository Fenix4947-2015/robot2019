package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;

public class MoveElevatorToMiddlePosition extends TimedCommand {
    
    public MoveElevatorToMiddlePosition() {
        super(Elevator.COMMAND_TIMEOUT_IN_SECONDS);

        requires(Robot.elevator);
        
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
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