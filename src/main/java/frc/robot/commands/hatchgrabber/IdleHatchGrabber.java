package frc.robot.commands.hatchgrabber;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class IdleHatchGrabber extends Command {

    public IdleHatchGrabber() {
        requires(Robot.hatchGrabber);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    protected void end() {
    }
}
