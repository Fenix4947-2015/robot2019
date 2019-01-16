package frc.robot.commands;

import frc.robot.commands.gripper.GripperClose;
import frc.robot.commands.gripper.GripperOpen;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PlatformReleaseSequence extends CommandGroup {

    public PlatformReleaseSequence() {
    	addSequential(new GripperOpen());
    	
        addSequential(new GripperClose());
        
        addSequential(new PlatformRelease());
        
    }
}
