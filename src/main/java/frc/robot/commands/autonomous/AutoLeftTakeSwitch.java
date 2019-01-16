package frc.robot.commands.autonomous;

import frc.robot.Side;
import frc.robot.commands.GripperShootToSwitchLoop;
import frc.robot.commands.gripper.GripperClose;
import frc.robot.commands.gripper.GripperShootToSwitch;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutoLeftTakeSwitch extends CommandGroup {
	
	// Constants.
	public static final String NAME = "AutoLeftTakeSwitch";
	
	public AutoLeftTakeSwitch(Side side, double waitBeforeAutonomous) {
		super(NAME);		
		addSequential(new WaitCommand(waitBeforeAutonomous));
		
		if (side == Side.LEFT) {
			addSequential(new GripperClose());
			addSequential(new DriveDistance(DistanceAutoConstants.DWALLTOSWITCH_Y - DistanceAutoConstants.ROBOT_LENGTH_FORWARD_DIRECTION/2.0));
			addSequential(new DriveRotate(90.0));
			addSequential(new DriveFinalApproach(DistanceAutoConstants.D_EXCHANGE_TO_SWITCH_X));			
			addSequential(new GripperShootToSwitchLoop());
		} else if (side == Side.RIGHT) {
			addSequential(new AutoLeftRightFoward());
			/*
			addSequential(new GripperClose());
			addSequential(new DriveDistance(DistanceAutoConstants.DIST_CLEAR_EXCHANGE_Y));
			addSequential(new DriveRotate(90.0));	
			addSequential(new DriveDistance(DistanceAutoConstants.DPORTALTOPORTAL_X - DistanceAutoConstants.ROBOT_LENGTH_FORWARD_DIRECTION));
			addSequential(new DriveRotate(-90.0));
			addSequential(new DriveDistance(DistanceAutoConstants.DWALLTOSWITCH_Y -DistanceAutoConstants.DIST_CLEAR_EXCHANGE_Y - DistanceAutoConstants.ROBOT_LENGTH_FORWARD_DIRECTION/2.0));
			addSequential(new DriveRotate(-90.0));
			addSequential(new DriveFinalApproach(DistanceAutoConstants.D_EXCHANGE_TO_SWITCH_X));			
			addSequential(new GripperShootToSwitch());
			*/
		} 
		else {
			addSequential(new AutoLeftRightFoward());
		}
	}
}