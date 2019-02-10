package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class UpdateWristSetpoint extends Command {
	private double setpoint;
	
	public UpdateWristSetpoint(double newSetpoint) {
		this.setpoint = newSetpoint;
	}
	
	protected void initialize() {
		Robot.m_Wrist.setSetpointAngle(setpoint);
	}

	protected void execute() {

	}

	@Override
	protected boolean isFinished() {
		if(Math.abs(setpoint-Robot.m_Wrist.getAngleDegrees()) < RobotMap.wristPositionDeadzone) {
			System.out.println("Wrist at position: " + (setpoint - Robot.m_Wrist.getAngleDegrees()));
			return true;

		} else {
			return false;
		}
	}

}
