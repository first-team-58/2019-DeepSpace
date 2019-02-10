package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class UpdateShoulderSetpoint extends Command {
	private double setpoint;
	
	public UpdateShoulderSetpoint(double newSetpoint) {
		this.setpoint = newSetpoint;
	}
	
	protected void initialize() {
		Robot.m_Shoulder.setSetpointAngle(setpoint);
	}

	protected void execute() {

	}

	@Override
	protected boolean isFinished() {
		if(Math.abs(setpoint-Robot.m_Shoulder.getAngle()) < RobotMap.shoulderPositionDeadzone) {
			System.out.println("Shoulder at position: " + (setpoint - Robot.m_Shoulder.getAngle()));
			return true;
		} else {
			return false;
		}
	}

}
