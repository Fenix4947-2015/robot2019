package frc.robot.commands.balloonbox;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.BalloonBox;

public class DropBalloonRight extends Command {

    public DropBalloonRight() {
        requires(Robot.ballonBox);

        setInterruptible(false);
        setTimeout(BalloonBox.FLIPPER_TIMEOUT_IN_S);
    }

    @Override
    protected void initialize() {
        if (Robot.ballonBox.isLeftFlipperOpened()) {
            cancel();
        } else {
            Robot.ballonBox.dropBallonRight();
        }
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
    }

    @Override
    protected void end() {
    }
}