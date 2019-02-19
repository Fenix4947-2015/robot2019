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
        

        // Pivot controls
        double y = XBoxJoystick.DRIVER.getY(Hand.kRight);        
        if(y > 0.0){
            // limit outwards pivot speed
            y = y * 0.25;
        }        
        Robot.ballonBox.pivot(y);

        // Balloon drop controls
        int pov = XBoxJoystick.HELPER.getPOV();
        if (lastPOV == POV_NONE) {
            if (pov == POV_RIGHT) {
                Robot.ballonBox.dropBallonLeft();
            } else if (pov == POV_LEFT) {
                Robot.ballonBox.dropBallonRight();
            } else if (pov == POV_DOWN) {
                Robot.ballonBox.resetFlippers();
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
