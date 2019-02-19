package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LoadBalloonIntoBox extends CommandGroup {

    public LoadBalloonIntoBox() {
        // Stop roller.
        addSequential(new MoveElevatorToTopOfBox());
        addSequential(new MoveElevatorToGround());

        setInterruptible(true);
    }
}
