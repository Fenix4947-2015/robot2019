package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.balloonbox.StopIntakeRoller;

public class LoadBalloonIntoBox extends CommandGroup {

    public LoadBalloonIntoBox() {
        addSequential(new MoveElevatorToTopOfBox2());
        addSequential(new StopIntakeRoller());
        addSequential(new MoveElevatorToGround());

        setInterruptible(true);
    }
}
