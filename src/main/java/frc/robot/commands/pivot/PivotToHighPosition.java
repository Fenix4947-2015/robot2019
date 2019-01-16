package frc.robot.commands.pivot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PivotToHighPosition extends CommandGroup {

	public PivotToHighPosition() {
		addSequential(new PivotToVerticalFromLowPosition());
		addSequential(new BreakFromLowPosition());
		addSequential(new WaitForHighLimitSwitch());
	}
}