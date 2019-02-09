package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class UpdateShoulderSetpoint extends Command {
	private double setpoint;
	
	public UpdateShoulderSetpoint(double newSetpoint) {
		this.setpoint = newSetpoint;
	}
	
	protected void initialize() {
		Robot.m_Shoulder.setSetpoint(setpoint);
	}

	protected void execute() {

	}

	@Override
	protected boolean isFinished() {
		return setpoint == Robot.m_Shoulder.getSetpoint();
	}

}
