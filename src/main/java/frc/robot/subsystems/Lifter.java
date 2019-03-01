package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

public class Lifter extends Subsystem {

    public static final double TOGGLE_TIMEOUT_IN_S = 1.0;

    private static final boolean FRONT_LIFT_STATE_UP = true;
    private static final boolean FRONT_LIFT_STATE_DOWN = !FRONT_LIFT_STATE_UP;

    private static final boolean BACK_LIFT_STATE_UP = true;
    private static final boolean BACK_LIFT_STATE_DOWN = !BACK_LIFT_STATE_UP;

    private Solenoid frontSolenoid;
    private Solenoid backSolenoid;

    public Lifter() {
        if (RobotMap.LIFTER_FRONT_SOLENOID_ADDRESS != -1) {
            frontSolenoid = new Solenoid(RobotMap.LIFTER_FRONT_SOLENOID_ADDRESS);
        }

        if (RobotMap.LIFTER_BACK_SOLENOID_ADDRESS != -1) {
            backSolenoid = new Solenoid(RobotMap.LIFTER_BACK_SOLENOID_ADDRESS);
        }

        frontDown();
        backDown();
    }

    @Override
    public void initDefaultCommand() {
    }

    public void frontUp() {
        if (frontSolenoid == null) {
            return;
        }

        frontSolenoid.set(FRONT_LIFT_STATE_UP);
    }

    public void frontDown() {
        if (frontSolenoid == null) {
            return;
        }

        frontSolenoid.set(FRONT_LIFT_STATE_DOWN);
    }

    public void toggleFront() {
        if (frontSolenoid == null) {
            return;
        }

        boolean currentState = frontSolenoid.get();
        frontSolenoid.set(!currentState);
    }

    public void backUp() {
        if (backSolenoid == null) {
            return;
        }

        backSolenoid.set(BACK_LIFT_STATE_UP);
    }

    public void backDown() {
        if (backSolenoid == null) {
            return;
        }

        backSolenoid.set(BACK_LIFT_STATE_DOWN);
    }

    public void toggleBack() {
        if (backSolenoid == null) {
            return;
        }

        boolean currentState = backSolenoid.get();
        backSolenoid.set(!currentState);
    }

    public void log() {
        SmartDashboard.putBoolean("Front Lift", frontSolenoid.get());
        SmartDashboard.putBoolean("Back Lift", backSolenoid.get());
    }
}
