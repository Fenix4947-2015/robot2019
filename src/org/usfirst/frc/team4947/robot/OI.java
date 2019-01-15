/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4947.robot;

import org.usfirst.frc.team4947.robot.commands.AbortAll;
import org.usfirst.frc.team4947.robot.commands.DriveArcade;
import org.usfirst.frc.team4947.robot.commands.GripperShootToSwitchLoop;
import org.usfirst.frc.team4947.robot.commands.PlatformLock;
import org.usfirst.frc.team4947.robot.commands.PlatformPull;
import org.usfirst.frc.team4947.robot.commands.PlatformRelease;
import org.usfirst.frc.team4947.robot.commands.PlatformReleaseSequence;
import org.usfirst.frc.team4947.robot.commands.ShiftDown;
import org.usfirst.frc.team4947.robot.commands.ShiftUp;
import org.usfirst.frc.team4947.robot.commands.autonomous.DriveDistance;
import org.usfirst.frc.team4947.robot.commands.autonomous.DriveFinalApproach;
import org.usfirst.frc.team4947.robot.commands.autonomous.DriveRotate;
import org.usfirst.frc.team4947.robot.commands.autonomous.swapmodeauto;
import org.usfirst.frc.team4947.robot.commands.gripper.GripperClose;
import org.usfirst.frc.team4947.robot.commands.gripper.GripperDefault;
import org.usfirst.frc.team4947.robot.commands.gripper.GripperOpen;
import org.usfirst.frc.team4947.robot.commands.gripper.GripperShootToSwitch;
import org.usfirst.frc.team4947.robot.commands.pivot.PivotCustomMotion;
import org.usfirst.frc.team4947.robot.commands.pivot.PivotToHighPosition;
import org.usfirst.frc.team4947.robot.commands.pivot.PivotToLowPosition;
import org.usfirst.frc.team4947.robot.commands.pivot.PivotToVerticalFromHighPosition;
import org.usfirst.frc.team4947.robot.commands.pivot.PivotToVerticalFromLowPosition;
import org.usfirst.frc.team4947.robot.commands.pivot.WaitForHighLimitSwitch;
import org.usfirst.frc.team4947.robot.commands.pivot.WaitForLowLimitSwitch;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator interface to the commands and command groups 
 * that allow control of the robot.
 */
public class OI {

	public static enum XBoxAxis {
		LEFT_STICK_X(0), 
		LEFT_STICK_Y(1), 
		LEFT_TRIGGER(2), 
		RIGHT_TRIGGER(3), 
		RIGHT_STICK_X(4), 
		RIGHT_STICK_Y(5);

		private int value;

		private XBoxAxis(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	public static enum XBoxButton {
		A(1), 
		B(2), 
		X(3), 
		Y(4), 
		LB(5), 
		RB(6), 
		BACK(7), 
		START(8),
		LEFT_STICK(9), 
		RIGHT_STICK(10);

		private int value;

		private XBoxButton(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	// Members.
	private Joystick joystickDriver;
	private Joystick joystickHelper;

	public OI() {
		initJoystickDriver();
		initJoystickHelper();
		
		SmartDashboard.putString("-------Gripper--------","");
		SmartDashboard.putData("gripperClose", new GripperClose());
		SmartDashboard.putData("gripperOpen", new GripperOpen());
		SmartDashboard.putData("GripperShootToSwitch", new GripperShootToSwitch());
		SmartDashboard.putData("Manual Gripper", new GripperDefault());
		
		SmartDashboard.putString("-------Pivot--------","");
		SmartDashboard.putData("Manual pivot", new PivotCustomMotion());
		SmartDashboard.putData("PivotToHighPosition", new PivotToHighPosition());
		SmartDashboard.putData("PivotToLowPosition", new PivotToLowPosition());
		SmartDashboard.putData("PivotToVerticalFromHighPosition", new PivotToVerticalFromHighPosition());
		SmartDashboard.putData("PivotToVerticalFromLowPosition", new PivotToVerticalFromLowPosition());
		SmartDashboard.putData("WaitForHighLimitSwitch", new WaitForHighLimitSwitch());
		SmartDashboard.putData("WaitForLowLimitSwitch", new WaitForLowLimitSwitch());
		
		SmartDashboard.putString("-------DriveTrain--------","");
		SmartDashboard.putData("DriveArcade", new DriveArcade());
		SmartDashboard.putNumber("DriveDistanceInches", 12);
		double distanceInches= SmartDashboard.getNumber("DriveDistanceInches", 12);
		SmartDashboard.putData("DriveDistance", new DriveDistance(distanceInches));
		SmartDashboard.putData("DriveFinalApproach_Distance", new DriveFinalApproach(distanceInches));
		SmartDashboard.putNumber("rotateAngle", 90);
		double rotateAngle= SmartDashboard.getNumber("rotateAngle", 90);
		SmartDashboard.putData("DriveRotate", new DriveRotate(rotateAngle));
		SmartDashboard.putData("swapmodeauto", new swapmodeauto());
		SmartDashboard.putData("ShiftDown", new ShiftDown());
		SmartDashboard.putData("ShiftUp", new ShiftUp());
		
		SmartDashboard.putString("-------Platform--------","");			
		SmartDashboard.putData("PlatformPull", new PlatformPull());		
		SmartDashboard.putData("PlatformRelease", new PlatformRelease());
		SmartDashboard.putData("PlatformLock", new PlatformLock());
		SmartDashboard.putData("GripperShootToSwitchLoop", new GripperShootToSwitchLoop());
		
	}
	
	private void initJoystickDriver() {
		joystickDriver = new Joystick(RobotMap.JOYSTICK_DRIVER_PORT);
		
		// Create all buttons in case we need them.
		JoystickButton driverA = new JoystickButton(joystickDriver, XBoxButton.A.getValue());
		JoystickButton driverB = new JoystickButton(joystickDriver, XBoxButton.B.getValue());
		JoystickButton driverX = new JoystickButton(joystickDriver, XBoxButton.X.getValue());
		JoystickButton driverY = new JoystickButton(joystickDriver, XBoxButton.Y.getValue());
		JoystickButton driverLB = new JoystickButton(joystickDriver, XBoxButton.LB.getValue());
		JoystickButton driverRB = new JoystickButton(joystickDriver, XBoxButton.RB.getValue());
		JoystickButton driverBack = new JoystickButton(joystickDriver, XBoxButton.BACK.getValue());
		JoystickButton driverStart = new JoystickButton(joystickDriver, XBoxButton.START.getValue());
		JoystickButton driverLeftStick = new JoystickButton(joystickDriver, XBoxButton.LEFT_STICK.getValue());
		JoystickButton driverRightStick = new JoystickButton(joystickDriver, XBoxButton.RIGHT_STICK.getValue());		
		
		
		driverX.whenPressed(new ShiftUp());
		driverA.whenPressed(new ShiftDown());
		
		driverStart.whenPressed(new PlatformReleaseSequence());
		driverY.whenPressed(new PlatformPull());
		
		driverBack.whenPressed(new AbortAll());

	}
	
	private void initJoystickHelper() {
		joystickHelper = new Joystick(RobotMap.JOYSTICK_HELPER_PORT);

		// Create all buttons in case we need them.
		JoystickButton helperA = new JoystickButton(joystickHelper, XBoxButton.A.getValue());
		JoystickButton helperB = new JoystickButton(joystickHelper, XBoxButton.B.getValue());
		JoystickButton helperX = new JoystickButton(joystickHelper, XBoxButton.X.getValue());
		JoystickButton helperY = new JoystickButton(joystickHelper, XBoxButton.Y.getValue());
		JoystickButton helperLB = new JoystickButton(joystickHelper, XBoxButton.LB.getValue());
		JoystickButton helperRB = new JoystickButton(joystickHelper, XBoxButton.RB.getValue());
		JoystickButton helperBack = new JoystickButton(joystickHelper, XBoxButton.BACK.getValue());
		JoystickButton helperStart = new JoystickButton(joystickHelper, XBoxButton.START.getValue());
		JoystickButton helperLeftStick = new JoystickButton(joystickHelper, XBoxButton.LEFT_STICK.getValue());
		JoystickButton helperRightStick = new JoystickButton(joystickHelper, XBoxButton.RIGHT_STICK.getValue());
		
		helperX.whenPressed(new PivotToHighPosition());
		helperA.whenPressed(new PivotToLowPosition());
				
		helperLB.whenPressed(new GripperShootToSwitch());
		helperRB.whenPressed(new GripperShootToSwitch());
		
//		helperY.whenPressed(new GripperOpen());
//		helperA.whenPressed(new GripperClose());
		
		helperStart.whenPressed(new PivotCustomMotion());
	}

	public double getJoystickDriverAxis(XBoxAxis axis) {
		return joystickDriver.getRawAxis(axis.getValue());
	}

	public double getJoystickDriverAxis(XBoxAxis axis, double deadBand) {
		return getAxisWithDeadBand(joystickDriver, axis, deadBand);
	}

	public boolean getJoystickDriverButton(XBoxButton button) {
		return joystickDriver.getRawButton(button.getValue());
	}

	public double getJoystickHelperAxis(XBoxAxis axis) {
		return joystickHelper.getRawAxis(axis.getValue());
	}

	public double getJoystickHelperAxis(XBoxAxis axis, double deadBand) {
		return getAxisWithDeadBand(joystickHelper, axis, deadBand);
	}

	public boolean getJoystickHelperButton(XBoxButton button) {
		return joystickHelper.getRawButton(button.getValue());
	}	

	private static double getAxisWithDeadBand(Joystick joystick, XBoxAxis axis, double deadBand) {
		double axisValue = joystick.getRawAxis(axis.getValue());
		if (Math.abs(axisValue) <= deadBand) 
		{
			axisValue = 0.0;
		}
		return axisValue;
	}

	// RUMBLE Methods
	public void setJoystickDriverRumble(RumbleType rumbleType, float value) {
		joystickDriver.setRumble(rumbleType, value);
	}		

	public void stopJoystickDriverRumble() {
		joystickDriver.setRumble(RumbleType.kLeftRumble, 0.0f);
		joystickDriver.setRumble(RumbleType.kRightRumble, 0.0f);
	}

	public void setJoystickHelperRumble(RumbleType rumbleType, float value) {
		joystickHelper.setRumble(rumbleType, value);
	}	

	public void stopJoystickHelperRumble() {
		joystickHelper.setRumble(RumbleType.kLeftRumble, 0.0f);
		joystickHelper.setRumble(RumbleType.kRightRumble, 0.0f);
	}
	
}