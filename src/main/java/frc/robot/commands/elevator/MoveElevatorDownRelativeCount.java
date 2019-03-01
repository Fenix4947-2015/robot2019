package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;

public class MoveElevatorDownRelativeCount extends Command {

    private static final int DEFAULT_TOLERANCE = 512;

    private int offsetToReach;
    private int tolerance;
    
    private int previousOffset;
    private int currentPos;
    private int currentOffset;

    private int targetAbsolute;

    public MoveElevatorDownRelativeCount(int offsetToReach) {
        this(offsetToReach, DEFAULT_TOLERANCE);
    }

    public MoveElevatorDownRelativeCount(int offsetToReach, int tolerance) {
        requires(Robot.elevator);

        setInterruptible(true);
        setTimeout(Elevator.COMMAND_TIMEOUT_IN_SECONDS);

        this.offsetToReach = offsetToReach;
        this.tolerance = tolerance;
    }

    @Override
    protected void initialize() {
        previousOffset = Integer.MAX_VALUE;

        currentPos = Robot.elevator.getSensorPosition();

        targetAbsolute = currentPos + offsetToReach;
        currentOffset = Math.abs(currentPos - targetAbsolute);

        if (isNear()) {
            cancel();
        } else {
            Robot.elevator.moveTo(targetAbsolute);
        }
    }

    @Override
    protected void execute() {
        previousOffset = currentOffset;

        currentPos = Robot.elevator.getSensorPosition();
        currentOffset = Math.abs(currentPos - targetAbsolute);
    }

    @Override
    protected boolean isFinished() {
        return isNear() || isOffsetIncreasing() || isTimedOut() || (Robot.elevator.isLow() && offsetToReach<0);
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