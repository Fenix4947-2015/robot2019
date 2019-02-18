package frc.robot.commands.balloonbox;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.BalloonBox;

public class ResetLeftFlipper extends Command {

    public ResetLeftFlipper() {
        requires(Robot.ballonBox);

        setInterruptible(false);
        setTimeout(BalloonBox.FLIPPER_TIMEOUT_IN_S);
    }

    @Override
    protected void initialize() {
        if (Robot.ballonBox.isRightFlipperOpened()) {
            cancel();
        } else {
            Robot.ballonBox.resetLeftFlipper();
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