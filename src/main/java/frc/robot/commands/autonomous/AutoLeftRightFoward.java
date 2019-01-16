package frc.robot.commands.autonomous;

import frc.robot.commands.gripper.GripperClose;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoLeftRightFoward extends CommandGroup {

	// Constants.
	public static final String NAME = "AutoLeftRightFoward";

	public AutoLeftRightFoward() {
		super(NAME);

		//addSequential(new Dr0iveDistance(16.0)); // Drive to nothing ;-)7
		addSequential(new GripperClose());
		addSequential(new DriveFinalApproach(DistanceAutoConstants.DWALLTOSWITCH_Y + DistanceAutoConstants.D_SWITCH_THICKNESS_Y));
	}
}