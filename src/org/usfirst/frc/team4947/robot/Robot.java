/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team4947.robot;

import org.usfirst.frc.team4947.robot.commands.autonomous.AutoCenterFoward;
import org.usfirst.frc.team4947.robot.commands.autonomous.AutoCenterTakeSwitch;
import org.usfirst.frc.team4947.robot.commands.autonomous.AutoCenterTakeSwitchClosePosition;
import org.usfirst.frc.team4947.robot.commands.autonomous.AutoLeftRightFoward;
import org.usfirst.frc.team4947.robot.commands.autonomous.AutoLeftTakeSwitch;
import org.usfirst.frc.team4947.robot.commands.autonomous.AutoNothing;
import org.usfirst.frc.team4947.robot.commands.autonomous.AutoRightTakeSwitch;
import org.usfirst.frc.team4947.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4947.robot.subsystems.Gripper;
import org.usfirst.frc.team4947.robot.subsystems.Pivot;
import org.usfirst.frc.team4947.robot.subsystems.Platform;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to each mode, as 
 * described in the TimedRobot documentation. If you change the name of this class or the package after creating this 
 * project, you must also update the build.properties file in the project.
 */
public class Robot extends TimedRobot {

	public static OI oi;
	
	public static DriveTrain driveTrain;
	public static Gripper gripper;
	public static Pivot pivot;
	public static Platform platform;
	
	public double waitBeforeAutonomous = 0.0; // seconds
	
	String m_autonomousCommand;
	SendableChooser<String> m_chooser = new SendableChooser<>();
	Command m_currentCommand;

	/**
	 * This function is run when the robot is first started up and should be used for any initialization code.
	 */
	@Override
	public void robotInit() {
		driveTrain = new DriveTrain();
		gripper = new Gripper();
		pivot = new Pivot();
		platform = new Platform();
		
		oi = new OI();
		SmartDashboard.putString("-------AUTONOMOUS--------","");
		m_chooser.addDefault("DO NOTHING", AutoNothing.NAME);
		m_chooser.addDefault("Robot a gauche - switch", AutoLeftTakeSwitch.NAME);
		m_chooser.addDefault("Robot au centre - switch", AutoCenterTakeSwitch.NAME);
		m_chooser.addDefault("Robot a droite - switch", AutoRightTakeSwitch.NAME);
		m_chooser.addDefault("Robot a gauche ou droite - avance", AutoLeftRightFoward.NAME);
		m_chooser.addDefault("Robot au centre - avance", AutoCenterFoward.NAME);		
		m_chooser.addDefault("Robot au centre-switch-coteproche", AutoCenterTakeSwitchClosePosition.NAME);
		//SmartDashboard.putNumber("WaitDelayBeforeStart_sec", 0.0); // reset this line to make appear in smartdashboard.
		waitBeforeAutonomous= SmartDashboard.getNumber("WaitDelayBeforeStart_sec", 0.0);
		SmartDashboard.putData("Auto mode", m_chooser);
		
		// Camera sur le dashboard
		CameraServer.getInstance().startAutomaticCapture();		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You can use it to reset any subsystem 
	 * information you want to clear when the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		driveTrain.driveStop();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes using 
	 * the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the auto name from the text box below the 
	 * Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented 
	 * example) or additional comparisons to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() 
	{
		driveTrain.initAutonomous();
		
		m_autonomousCommand = (String)m_chooser.getSelected();

		
		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			String gameData = DriverStation.getInstance().getGameSpecificMessage();		
			SmartDashboard.putString("Switch Random Configuration", gameData);
			if (gameData != null) {
				planAutonomous(gameData);
			}
		}		
	}

	private void planAutonomous(String gameData) {
		Side sideOfSwitch = Side.ofSwitch(gameData);

		// TODO: do a delay with the smartDashBoard for all the autonomus
		// commands
		String commandName = m_autonomousCommand;
		switch (commandName) {
			
			case AutoNothing.NAME:
				m_currentCommand = new AutoNothing();
				m_currentCommand.start();
				break;
				
			case AutoLeftRightFoward.NAME:
				m_currentCommand = new AutoLeftRightFoward();
				m_currentCommand.start();
				break;
	
			case AutoCenterFoward.NAME:
				m_currentCommand = new AutoCenterFoward();
				m_currentCommand.start();
				break;
				
			case AutoCenterTakeSwitchClosePosition.NAME:
				m_currentCommand = new AutoCenterTakeSwitchClosePosition(sideOfSwitch, waitBeforeAutonomous);
				m_currentCommand.start();
				break;
				
			case AutoLeftTakeSwitch.NAME:
				m_currentCommand = new AutoLeftTakeSwitch(sideOfSwitch,waitBeforeAutonomous);
				m_currentCommand.start();
				break;
	
			case AutoCenterTakeSwitch.NAME:
				m_currentCommand =  new AutoCenterTakeSwitch(sideOfSwitch,waitBeforeAutonomous);
				m_currentCommand.start();
				break;
	
			case AutoRightTakeSwitch.NAME:
				m_currentCommand = new AutoRightTakeSwitch(sideOfSwitch,waitBeforeAutonomous);
				m_currentCommand.start();
				break;
	
			default:
				m_currentCommand = new AutoNothing();
				m_currentCommand.start();
				break;
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_currentCommand != null) {
			m_currentCommand.cancel();
		}
		driveTrain.initTeleop();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		log();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
		log();
	}

	private void log() {
		driveTrain.log();
		pivot.log();
		gripper.log();
	}
}
