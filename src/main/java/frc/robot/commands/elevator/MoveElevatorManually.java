package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.joysticks.XBoxJoystick;

public class MoveElevatorManually extends Command {

    private static final double DEADBAND = 0.5;

    private double lastYLeft;

    public MoveElevatorManually() {
        requires(Robot.elevator);

        setInterruptible(true);
    }

    @Override
    protected void initialize() {
        lastYLeft = XBoxJoystick.HELPER.getY(Hand.kLeft, DEADBAND);
    }

    @Override
    protected void execute() {
        double yRight = XBoxJoystick.HELPER.getY(Hand.kRight);
        Robot.elevator.move(yRight);

        double yLeft = XBoxJoystick.HELPER.getY(Hand.kLeft, DEADBAND);
        if (lastYLeft == 0.0) {
            if (yLeft >= DEADBAND) {
                Robot.elevator.increaseCount();
            } else if (yLeft <= -DEADBAND) {
                Robot.elevator.decreaseCount();
            }
        }

        lastYLeft = yLeft;
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    protected void end() {
        Robot.elevator.stop();
    }
}