package frc.robot.commands;

import frc.robot.commands.gripper.GripperShootToSwitch;

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
