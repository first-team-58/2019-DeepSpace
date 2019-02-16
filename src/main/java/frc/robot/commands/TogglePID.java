package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TogglePID extends Command{
	private static boolean toggle = true;
	
	public TogglePID() {
		toggle = !toggle;
	}
	
	protected void execute() {
		Robot.manualMode = true;
		Robot.m_Wrist.positionAchieved = true;
		Robot.m_Elevator.positionAchieved = true;
		Robot.m_Shoulder.positionAchieved = true;
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return toggle;
	}

}
