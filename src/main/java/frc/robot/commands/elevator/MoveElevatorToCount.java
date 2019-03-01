package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Robot.HelperMode;
import frc.robot.subsystems.Elevator;

public class MoveElevatorToCount extends Command {

    private static final int DEFAULT_TOLERANCE = 512;

    private final int countToReachInCargoMode;
    private final int countToReachInHatchMode;
    private final int tolerance;
    
    private int countToReach;
    private int previousOffset;
    private int currentPos;
    private int currentOffset;

    public MoveElevatorToCount(int countToReach) {
        this(countToReach, countToReach);
    }

    public MoveElevatorToCount(int countToReachInCargoMode, int countToReachInHatchMode) {
        requires(Robot.elevator);

        setInterruptible(true);
        setTimeout(Elevator.COMMAND_TIMEOUT_IN_SECONDS);
        
        this.countToReachInCargoMode = countToReachInCargoMode;
        this.countToReachInHatchMode = countToReachInHatchMode;

        this.tolerance = DEFAULT_TOLERANCE;
    }

    @Override
    protected void initialize() {
        if (Robot.isHelperModeCargo()) {
            this.countToReach = countToReachInCargoMode;
        } else {
            this.countToReach = countToReachInHatchMode;
        }

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