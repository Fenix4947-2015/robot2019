package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

class ZeroElevatorIfLow extends InstantCommand {

    public ZeroElevatorIfLow() {
        super(Robot.elevator, () -> {
            if (Robot.elevator.isLow()) {
                Robot.elevator.zero();
            }
        });

        setInterruptible(false);
    }
}
