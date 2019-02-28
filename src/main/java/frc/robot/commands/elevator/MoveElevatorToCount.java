package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;

public class MoveElevatorToCount extends Command {

    private static final int DEFAULT_TOLERANCE = 512;

    private int countToReach;
    private int tolerance;
    
    private int previousOffset;
    private int currentPos;
    private int currentOffset;

    public MoveElevatorToCount(int countToReach) {
        this(countToReach, DEFAULT_TOLERANCE);
    }

    public MoveElevatorToCount(int countToReach, int tolerance) {
        requires(Robot.elevator);

        setInterruptible(true);
        setTimeout(Elevator.COMMAND_TIMEOUT_IN_SECONDS);

        this.countToReach = countToReach;
        this.tolerance = tolerance;
    }

    @Override
    protected void initialize() {
        previousOffset = Integer.MAX_VALUE;

        currentPos = Robot.elevator.getSensorPosition();
        currentOffset = Math.abs(currentPos - countToReach);

        if (isNear()) {
            cancel();
        } else {
            Robot.elevator.moveTo(countToReach);
        }
    }

    @Override
    protected void execute() {
        previousOffset = currentOffset;

        currentPos = Robot.elevator.getSensorPosition();
        currentOffset = Math.abs(currentPos - countToReach);
    }

    @Override
    protected boolean isFinished() {
        return isNear() || isOffsetIncreasing() || isTimedOut();
    }

    private boolean isNear() {
        return (currentOffset <= tolerance);
    }

    private boolean isOffsetIncreasing() {
        return currentOffset > previousOffset;
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