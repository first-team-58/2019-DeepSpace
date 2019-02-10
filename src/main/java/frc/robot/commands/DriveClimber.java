package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class DriveClimber extends Command {

	public DriveClimber() {
		requires(Robot.m_Climber);
	}
	
	public DriveClimber(int position) {
		Robot.m_Climber.setSetpoint(position);
		requires(Robot.m_Climber);
	}
	
	protected void initialize() {
		Robot.m_Climber.positionAchieved = false;
		Robot.m_Climber.setPID(RobotMap.climberPf, RobotMap.climberIf, RobotMap.climberDf, RobotMap.climberPr, RobotMap.climberIr, RobotMap.climberDr);
	}
	
	protected void execute() {
		Robot.m_Climber.drivePID();
	}
	
	@Override
	protected boolean isFinished() {
		return Robot.m_Climber.positionAchieved;
	}
	
}
