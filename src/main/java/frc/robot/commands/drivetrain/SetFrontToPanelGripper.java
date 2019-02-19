package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class SetFrontToPanelGripper extends CommandGroup {

    public SetFrontToPanelGripper() {
        addParallel(new InstantCommand(Robot.driveTrain, () -> {
            Robot.driveTrain.setFrontToPanelGripper();
        }));

        addParallel(new InstantCommand(() -> {
            // Set camera server to back camera.
            // See http://wpilib.screenstepslive.com/s/currentCS/m/vision/l/708159-using-multiple-cameras
        }));

        setInterruptible(false);
    }
}
