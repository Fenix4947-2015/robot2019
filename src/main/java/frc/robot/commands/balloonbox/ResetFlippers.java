package frc.robot.commands.balloonbox;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ResetFlippers extends CommandGroup {

    public ResetFlippers() {
        addSequential(new ResetLeftFlipper());
        addSequential(new ResetRightFlipper());
    }
}
