package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ManualDriveShoulder extends Command{
	public double speed;
	
	public ManualDriveShoulder(double value) {
	    speed = value;	
	}
	
	protected void execute() {
		if(Robot.manualMode) {
			Robot.m_Shoulder.positionAchieved = true;
			Robot.m_Shoulder.drive(speed);
		}
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}

}
