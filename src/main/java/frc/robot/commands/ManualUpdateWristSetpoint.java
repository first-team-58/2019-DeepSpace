package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Robot;

public class ManualUpdateWristSetpoint extends Command {
	double setpoint;
	
	public ManualUpdateWristSetpoint(Joystick controller, int axis) {
		double setpoint = Robot.m_Shoulder.getSetpoint();
		double mod = controller.getRawAxis(axis) * 2;
		setpoint = setpoint + mod;
		this.setpoint = setpoint;
	}
	
	protected void execute() {
		Scheduler.getInstance().add(new UpdateWristSetpoint(setpoint));
	}
	
	
	@Override
	protected boolean isFinished() {
		return true;
	}

	
}