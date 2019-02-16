package frc.robot.commands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ManualDriveElevator extends Command{
	public double speed;

	public ManualDriveElevator(double value) {
	    speed = value;	
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
