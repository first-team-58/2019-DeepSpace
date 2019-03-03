package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Robot;

public class ManualUpdateElevatorSetpoint extends Command {
	int setpoint;
	
	public ManualUpdateElevatorSetpoint(Joystick controller, int axis) {
		int setpoint = Robot.m_Elevator.getSetpoint();
		int mod = (int) controller.getRawAxis(axis) * -100;
		setpoint = setpoint + mod;
		this.setpoint = setpoint;
	}
	
	protected void execute() {
		Scheduler.getInstance().add(new UpdateElevatorSetpoint(setpoint));
	}
	
	
	@Override
	protected boolean isFinished() {
		return true;
	}

	
}