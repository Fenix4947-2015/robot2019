package org.usfirst.frc.team4947.robot.commands;

import org.usfirst.frc.team4947.robot.commands.gripper.GripperShootToSwitch;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GripperShootToSwitchLoop extends CommandGroup {

    public GripperShootToSwitchLoop() {
    	addSequential(new GripperShootToSwitch());
        addSequential(new Wait(0.75));
        addSequential(new GripperShootToSwitch());
        addSequential(new Wait(0.75));
        addSequential(new GripperShootToSwitch());      
    }
}
