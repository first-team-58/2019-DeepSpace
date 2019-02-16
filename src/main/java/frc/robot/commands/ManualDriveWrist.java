package frc.robot.commands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ManualDriveWrist extends Command{
	public double speed;

	public ManualDriveWrist(double value) {
	    speed = value;	
	}

	protected void execute() {
		if (Robot.manualMode) {
			Robot.m_Wrist.positionAchieved = true;
			Robot.m_Wrist.drive(speed);
		}
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
