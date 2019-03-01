package frc.robot.commands.balloonbox;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Robot;
import frc.robot.Robot.HelperMode;
import frc.robot.commands.hatchgrabber.DeployHatchMacro;
import frc.robot.commands.hatchgrabber.RetractHatchMacro;
import frc.robot.joysticks.XBoxJoystick;

public class ManualControl extends Command {

    private static final int POV_NONE = -1;
    private static final int POV_UP = 0;
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
            y = y*0.45;
        }
        else{
            y = y * 0.55;
        }                
        Robot.ballonBox.pivot(y);

        double rollInValue = XBoxJoystick.HELPER.getTriggerAxis(Hand.kRight, 0.05) * 0.5;
        double rollOutValue = XBoxJoystick.HELPER.getTriggerAxis(Hand.kLeft, 0.05);
        double rollValue = rollInValue - rollOutValue;

        Robot.ballonBox.intakeRoll(rollValue);

        int pov = XBoxJoystick.HELPER.getPOV();

        // Balloon drop controls
        // Controls are reversed as requested by the pilots! (compressor side = right side)
        if (Robot.isHelperModeCargo()) {
            if (lastPOV == POV_NONE) {
                if (pov == POV_RIGHT) {
                    Robot.ballonBox.dropBallonLeft();
                } else if (pov == POV_LEFT) {
                    Robot.ballonBox.dropBallonRight();
                } else if (pov == POV_DOWN) {
                    Robot.ballonBox.resetFlippers();
                }
            }
        } else if (Robot.isHelperModeHatch()) {
            if (lastPOV == POV_NONE) {
                if (pov == POV_UP) {
                    Scheduler.getInstance().add(new DeployHatchMacro());
                    //Robot.hatchGrabber.deploy();
                } else if (pov == POV_DOWN) {
                    Scheduler.getInstance().add(new RetractHatchMacro());
                    //Robot.hatchGrabber.retract();
                }
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
