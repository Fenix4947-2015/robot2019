package frc.robot.commands.balloonbox;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class CloseLeft extends InstantCommand {

    public CloseLeft() {
        super("Close left", () -> {
            Robot.ballonBox.closeLeft();
        });

        requires(Robot.ballonBox);
    }
}
