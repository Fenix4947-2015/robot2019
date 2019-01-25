package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ZeroTheElevator extends CommandGroup {

    public ZeroTheElevator() {
        addSequential(new MoveElevatorToLowPosition());
        addSequential(new ZeroElevatorIfLow());

        setInterruptible(false);
    }
}
