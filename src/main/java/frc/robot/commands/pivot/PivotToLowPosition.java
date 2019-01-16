package frc.robot.commands.pivot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PivotToLowPosition extends CommandGroup {

	public PivotToLowPosition() {
		addSequential(new PivotToVerticalFromHighPosition());
		addSequential(new BreakFromHighPosition());
		addSequential(new WaitForLowLimitSwitch());
	}
}