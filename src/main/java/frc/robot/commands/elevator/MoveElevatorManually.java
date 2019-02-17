package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.joysticks.XBoxJoystick;

public class MoveElevatorManually extends Command {

    public MoveElevatorManually() {
        requires(Robot.elevator);

        setInterruptible(true);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        double y = XBoxJoystick.HELPER.getY(Hand.kLeft);

        double yAbs = Math.abs(y);
        if (yAbs >= 0.1) {
            Robot.elevator.move(y);
        } else {
            Robot.elevator.stop();
        }
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