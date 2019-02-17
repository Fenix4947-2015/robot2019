package frc.robot.commands.balloonbox;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class CloseRight extends InstantCommand {

    public CloseRight() {
        super("Close right", () -> {
            Robot.ballonBox.closeRight();
        });

        requires(Robot.ballonBox);
    }
}
