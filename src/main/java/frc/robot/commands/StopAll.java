package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class StopAll extends InstantCommand {

    public StopAll() {
        super("Stop all", () -> {
            Robot.ballonBox.intakeStop();
            Robot.elevator.stop();
            Robot.ballonBox.pivotStop();
        });

        requires(Robot.ballonBox);
    }
}
