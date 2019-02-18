package frc.robot.commands.balloonbox;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.joysticks.XBoxJoystick;

public class ManualControl extends Command {

    private static final int POV_NONE = -1;
    private static final int POV_RIGHT = 90;
    private static final int POV_DOWN = 180;
    private static final int POV_LEFT = 270;

    private int lastPOV;

    public ManualControl() {
        requires(Robot.ballonBox);
    }

    @Override
    protected void initialize() {
        lastPOV = XBoxJoystick.HELPER.getPOV();
    }

    @Override
    protected void execute() {
        double triggers = XBoxJoystick.HELPER.getTriggerAxes(0.2);
        Robot.ballonBox.intakeRoll(triggers);

        double y = XBoxJoystick.HELPER.getY(Hand.kLeft);
        Robot.ballonBox.pivot(y);

        int pov = XBoxJoystick.HELPER.getPOV();
        if (lastPOV == POV_NONE) {
            if (pov == POV_RIGHT) {
                new DropBalloonRight().start();
            } else if (pov == POV_LEFT) {
                new DropBalloonLeft().start();
            } else if (pov == POV_DOWN) {
                new ResetFlippers().start();
            }
        }

        lastPOV = pov;
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
        Robot.ballonBox.pivotStop();
    }
}
