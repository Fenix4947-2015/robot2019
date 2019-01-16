package frc.robot.commands.gripper;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GripperShootToSwitch extends CommandGroup {

	public GripperShootToSwitch() {
		addSequential(new GripperOpen());
		addSequential(new GripperAtFullSpeed());
		addSequential(new GripperClose());
		addSequential(new GripperStop());
		addSequential(new GripperOpen());
	}
}