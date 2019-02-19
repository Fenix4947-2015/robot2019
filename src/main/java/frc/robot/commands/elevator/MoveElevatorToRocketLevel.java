package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;

public class MoveElevatorToRocketLevel extends Command {

    private int encoderCount;
    private int startEncoderCount;
    
    public MoveElevatorToRocketLevel(int encoderCount) {
        requires(Robot.elevator);

        setInterruptible(true);
        setTimeout(Elevator.COMMAND_TIMEOUT_IN_SECONDS);

        this.encoderCount = encoderCount;
    }

    @Override
    protected void initialize() {
        startEncoderCount = Robot.elevator.getSensorPosition();

        Robot.elevator.moveTo(encoderCount);
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void interrupted() {
    }

    @Override
    protected void end() {
    }
}