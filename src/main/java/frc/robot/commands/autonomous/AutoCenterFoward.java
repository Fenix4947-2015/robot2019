package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCenterFoward extends CommandGroup {

	// Constants.
	public static final String NAME = "AutoCenterFoward";
	
	public AutoCenterFoward() {
		super(NAME);
		addSequential(new DriveFinalApproach(DistanceAutoConstants.DWALLTOSWITCH_Y));
		

	}
	
}