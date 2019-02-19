package frc.robot.commands.balloonbox;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
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
        

        // Pivot controls
        double y = XBoxJoystick.HELPER.getY(Hand.kLeft);        
        if(y > 0.0){
            // limit outwards pivot speed
            y = y * 0.4;
        }        
        Robot.ballonBox.pivot(y);

        // Balloon drop controls
        int pov = XBoxJoystick.HELPER.getPOV();
        if (lastPOV == POV_NONE) {
            if (pov == POV_RIGHT) {
                Scheduler.getInstance().add(new DropBalloonRight());
            } else if (pov == POV_LEFT) {
                Scheduler.getInstance().add(new DropBalloonLeft());
            } else if (pov == POV_DOWN) {
                Scheduler.getInstance().add(new ResetFlippers());
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
