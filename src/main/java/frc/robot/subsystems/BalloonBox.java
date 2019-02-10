/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class BalloonBox extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  // Members
  private WPI_TalonSRX intakeRollerMotor; 
  private WPI_TalonSRX pivotMotor;


  
  // Position PID
  private static final double PULSES_PER_FOOT = 292;
	private static final double MAX_CLOSED_LOOP_MODE_PERCENT_OUTPUT = 0.7;
	private static final double POSITION_PID_P = 0.75;
	private static final double POSITION_PID_I = 0.0;
	private static final double POSITION_PID_D = 0.0;
	private static final double POSITION_PID_F = 0.0;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }


  public BalloonBox()
  {
      intakeRollerMotor = new WPI_TalonSRX(RobotMap.INTAKE_ROLLER_MOTOR_ADDRESS);
      pivotMotor = new WPI_TalonSRX(RobotMap.PIVOT_MOTOR_ADDRESS);
  }

  public void IntakeRollInside()
  {
    intakeRollerMotor.set(ControlMode.PercentOutput,0.4);
  }

  public void IntakeStop()
  {
    intakeRollerMotor.set(ControlMode.PercentOutput,0.0);
  }

  public void PivotLiftUp()
  {
    pivotMotor.set(ControlMode.PercentOutput,0.7);
  }

  public void PivotGoDown()
  {
    pivotMotor.set(ControlMode.PercentOutput,-0.7);
  }

  public void PivotStop()
  {
    pivotMotor.set(ControlMode.PercentOutput,0.0);
  }

   
}
