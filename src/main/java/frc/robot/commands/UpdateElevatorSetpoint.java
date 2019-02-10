package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class UpdateElevatorSetpoint extends Command {
	private int setpoint;
	
	public UpdateElevatorSetpoint(int newSetpoint) {
		this.setpoint = newSetpoint;
	}
	
	protected void initialize() {
		Robot.m_Elevator.setSetpoint(setpoint);
	}

	protected void execute() {

	}

	@Override
	protected boolean isFinished() {
		if(Math.abs(setpoint - Robot.m_Elevator.getEncoderPosition()) < RobotMap.elevatorPositionDeadzone) {
			System.out.println("Elevator at position: " + (setpoint - Robot.m_Elevator.getEncoderPosition()));
			return true;
		} else {
			return false;
		}

	}
}
