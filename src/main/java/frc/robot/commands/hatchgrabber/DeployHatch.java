package frc.robot.commands.hatchgrabber;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DeployHatch extends Command {

    public DeployHatch() {
        requires(Robot.hatchGrabber);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
      Robot.hatchGrabber.deploy();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    protected void end() {
    }
}
