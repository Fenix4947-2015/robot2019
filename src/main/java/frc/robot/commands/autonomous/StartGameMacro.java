/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.hatchgrabber.DeployHatch;
import frc.robot.commands.hatchgrabber.RetractHatch;
import frc.robot.commands.utility.SequenceDelay;

public class StartGameMacro extends CommandGroup {
  /**
   * Add your docs here.
   * 
   */
  public static final String NAME = "AutoDescente";
  public StartGameMacro() {
    super(NAME);

    addSequential(new TireToiEnBasCommeUnGrand(1.0, 1.25));
    addSequential(new TireToiEnBasCommeUnGrand(0.5, 0.25));
    addSequential(new TireToiEnBasCommeUnGrand(0.25, 0.25));
    addSequential(new SequenceDelay(0.25));
    addSequential(new DeployHatch());
    addSequential(new SequenceDelay(0.25));
    addSequential(new RetractHatch());
   
  }
}
