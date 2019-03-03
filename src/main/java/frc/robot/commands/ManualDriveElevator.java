package frc.robot.commands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ManualDriveElevator extends Command{
	public double speed;

	public ManualDriveElevator(Joystick controller, int axis) {
	    speed = controller.getRawAxis(axis);
	}

	protected void execute() {
		if (Robot.manualMode) {
			Robot.m_Elevator.positionAchieved = true;
			Robot.m_Elevator.drive(speed);
		}
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
