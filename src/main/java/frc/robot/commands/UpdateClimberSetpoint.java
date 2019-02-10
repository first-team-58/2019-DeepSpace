package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class UpdateClimberSetpoint extends Command {
	private int setpoint;
	
	public UpdateClimberSetpoint(int newsetpoint) {
		this.setpoint = newsetpoint;
	}
	
	protected void initialize() {
		Robot.m_Climber.setSetpoint(setpoint);
	}
	
	protected void execute() {
		
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return setpoint == Robot.m_Climber.getSetpoint();
	}

}
