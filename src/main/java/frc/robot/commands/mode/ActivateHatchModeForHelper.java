package frc.robot.commands.mode;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.Robot.HelperMode;

public class ActivateHatchModeForHelper extends InstantCommand {

    public ActivateHatchModeForHelper() {
        super(() -> Robot.setHelperMode(HelperMode.HATCH));
    }
}
