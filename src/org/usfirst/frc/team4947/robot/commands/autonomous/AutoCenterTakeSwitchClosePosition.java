package org.usfirst.frc.team4947.robot.commands.autonomous;

import org.usfirst.frc.team4947.robot.Side;
import org.usfirst.frc.team4947.robot.commands.GripperShootToSwitchLoop;
import org.usfirst.frc.team4947.robot.commands.gripper.GripperClose;
import org.usfirst.frc.team4947.robot.commands.gripper.GripperShootToSwitch;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutoCenterTakeSwitchClosePosition extends CommandGroup {
	
	// Constants.
	public static final String NAME = "AutoCenterTakeSwitchClosePosition";
	
	public AutoCenterTakeSwitchClosePosition(Side side, double waitBeforeAutonomous) {
		super(NAME);
		addSequential(new WaitCommand(waitBeforeAutonomous));
		
		if (side == Side.LEFT) {
			addParallel(new GripperClose());
			addSequential(new DriveDistance(DistanceAutoConstants.DIST_CLEAR_EXCHANGE_Y));	
			addSequential(new DriveRotate(-90.0));
			addSequential(new DriveDistance((DistanceAutoConstants.SWITCH_CENTER_CENTER_X/2) + DistanceAutoConstants.ROBOT_LENGTH_LATERAL_DIRECTION /2.0)); // Turn right.
			addSequential(new DriveRotate(90.0)); 
			addSequential(new DriveFinalApproach(DistanceAutoConstants.FINAL_MOTION_BOOST + DistanceAutoConstants.DWALLTOSWITCH_CLOSESIDE_Y-DistanceAutoConstants.DIST_CLEAR_EXCHANGE_Y - DistanceAutoConstants.ROBOT_LENGTH_FORWARD_DIRECTION/2.0));
			addSequential(new GripperShootToSwitchLoop());
		} else if (side == Side.RIGHT) {
			addParallel(new GripperClose());
			addSequential(new DriveDistance(DistanceAutoConstants.DIST_CLEAR_EXCHANGE_Y));
			addSequential(new DriveRotate(90.0));	
			addSequential(new DriveDistance((DistanceAutoConstants.SWITCH_CENTER_CENTER_X/2) - DistanceAutoConstants.ROBOT_LENGTH_LATERAL_DIRECTION/2.0));
			addSequential(new DriveRotate(-90.0));
			addSequential(new DriveFinalApproach(DistanceAutoConstants.FINAL_MOTION_BOOST + DistanceAutoConstants.DWALLTOSWITCH_CLOSESIDE_Y-DistanceAutoConstants.DIST_CLEAR_EXCHANGE_Y - DistanceAutoConstants.ROBOT_LENGTH_FORWARD_DIRECTION/2.0));
			addSequential(new GripperShootToSwitchLoop());
		} else {
			addSequential(new AutoCenterFoward());
		}
	}
}