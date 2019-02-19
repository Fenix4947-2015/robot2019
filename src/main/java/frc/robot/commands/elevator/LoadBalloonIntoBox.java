package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LoadBalloonIntoBox extends CommandGroup {

    public LoadBalloonIntoBox() {
        addSequential(new MoveElevatorToTopOfBox());
        addSequential(new MoveElevatorToLow());

        setInterruptible(true);
    }
}
