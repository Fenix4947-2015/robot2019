package frc.robot.commands.balloonbox;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class OpenRight extends InstantCommand {

    public OpenRight() {
        super("Open right", () -> {
            Robot.ballonBox.openRight();
        });

        requires(Robot.ballonBox);
    }
}
