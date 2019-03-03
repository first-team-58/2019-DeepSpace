package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TogglePID extends Command{
	private static boolean toggle = false;
	
	public TogglePID() {
		toggle = !toggle;
	}
	
	protected void execute() {
		Robot.manualMode = toggle;
		Robot.m_Wrist.positionAchieved = toggle;
		Robot.m_Elevator.positionAchieved = toggle;
		Robot.m_Shoulder.positionAchieved = toggle;
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

}
