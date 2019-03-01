package frc.robot.commands.mode;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.Robot.HelperMode;

public class ActivateCargoModeForHelper extends InstantCommand {

    public ActivateCargoModeForHelper() {
        super(() -> Robot.setHelperMode(HelperMode.CARGO));
    }
}
