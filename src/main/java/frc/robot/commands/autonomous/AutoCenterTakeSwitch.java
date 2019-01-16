package frc.robot.commands.autonomous;

import frc.robot.Side;
import frc.robot.commands.GripperShootToSwitchLoop;
import frc.robot.commands.gripper.GripperClose;
import frc.robot.commands.gripper.GripperShootToSwitch;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutoCenterTakeSwitch extends CommandGroup {
	
	// Constants.
	public static final String NAME = "AutoCenterTakeSwitch";
	
	public AutoCenterTakeSwitch(Side side, double waitBeforeAutonomous) {
		super(NAME);
		addSequential(new WaitCommand(waitBeforeAutonomous));
		
		if (side == Side.LEFT) {
			addParallel(new GripperClose());
			addSequential(new DriveDistance(DistanceAutoConstants.DIST_CLEAR_EXCHANGE_Y));	
			addSequential(new DriveRotate(-90.0));
			addSequential(new DriveDistance((DistanceAutoConstants.DPORTALTOPORTAL_X/2) - DistanceAutoConstants.ROBOT_LENGTH_FORWARD_DIRECTION /2.0 +DistanceAutoConstants.OFFSET_FROM_CENTER_X)); // Turn right.
			addSequential(new DriveRotate(90.0)); 
			addSequential(new DriveDistance(DistanceAutoConstants.DWALLTOSWITCH_Y-DistanceAutoConstants.DIST_CLEAR_EXCHANGE_Y - DistanceAutoConstants.ROBOT_LENGTH_FORWARD_DIRECTION/2.0));
			addSequential(new DriveRotate(90.0)); 
			addSequential(new DriveFinalApproach(DistanceAutoConstants.FINAL_MOTION_BOOST + DistanceAutoConstants.D_EXCHANGE_TO_SWITCH_X));			
			addSequential(new GripperShootToSwitchLoop());
		} else if (side == Side.RIGHT) {
			addParallel(new GripperClose());
			addSequential(new DriveDistance(DistanceAutoConstants.DIST_CLEAR_EXCHANGE_Y));
			addSequential(new DriveRotate(90.0));	
			addSequential(new DriveDistance((DistanceAutoConstants.DPORTALTOPORTAL_X/2) - DistanceAutoConstants.ROBOT_LENGTH_FORWARD_DIRECTION/2.0 -DistanceAutoConstants.OFFSET_FROM_CENTER_X));
			addSequential(new DriveRotate(-90.0));
			addSequential(new DriveDistance(DistanceAutoConstants.DWALLTOSWITCH_Y-DistanceAutoConstants.DIST_CLEAR_EXCHANGE_Y - DistanceAutoConstants.ROBOT_LENGTH_FORWARD_DIRECTION/2.0));
			addSequential(new DriveRotate(-90.0));
			addSequential(new DriveFinalApproach(DistanceAutoConstants.FINAL_MOTION_BOOST + DistanceAutoConstants.D_EXCHANGE_TO_SWITCH_X));
			addSequential(new GripperShootToSwitchLoop());
		} else {
			addSequential(new AutoCenterFoward());
		}
	}
}