package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.Robot;

public class MoveElevatorToTopOfBox2 extends TimedCommand {

    public MoveElevatorToTopOfBox2() {
        super(1.7);

        requires(Robot.elevator);

        setInterruptible(true);
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
