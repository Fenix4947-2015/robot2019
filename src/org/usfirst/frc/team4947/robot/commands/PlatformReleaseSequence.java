package org.usfirst.frc.team4947.robot.commands;

import org.usfirst.frc.team4947.robot.commands.gripper.GripperClose;
import org.usfirst.frc.team4947.robot.commands.gripper.GripperOpen;

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
