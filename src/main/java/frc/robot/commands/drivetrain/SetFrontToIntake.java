package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class SetFrontToIntake extends CommandGroup {

    public SetFrontToIntake() {
        addParallel(new InstantCommand(Robot.driveTrain, () -> {
            Robot.driveTrain.setFrontToIntake();
        }));

        addParallel(new InstantCommand(() -> {
            // Set camera server to front camera.
            // See http://wpilib.screenstepslive.com/s/currentCS/m/vision/l/708159-using-multiple-cameras
        }));

        setInterruptible(false);
    }
}
