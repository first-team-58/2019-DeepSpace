package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveWrist extends Command {
	private double setpoint = 20;//default position to go to at start
	
	public DriveWrist() {
		requires(Robot.m_Wrist);
	}
	
	public DriveWrist(double angle) {
		this.setpoint = angle / 72; //convert degrees into voltage
		requires(Robot.m_Wrist);
	}
	
	@Override
	protected void execute() {
		Robot.m_Wrist.setSetpoint(setpoint); //might be wrong, I don't know if this will continue to feed the motor
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return Robot.m_Wrist.getSetpoint() == setpoint;
	}

}
