package frc.robot.commands.balloonbox;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class OpenLeft extends InstantCommand {

    public OpenLeft() {
        super("Open left", () -> {
            Robot.ballonBox.openLeft();
        });

        requires(Robot.ballonBox);
    }
}
