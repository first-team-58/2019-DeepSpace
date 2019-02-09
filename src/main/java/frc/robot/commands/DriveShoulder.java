package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class DriveShoulder extends Command {
	
	public DriveShoulder() {
		requires(Robot.m_Shoulder);
	}
	
	public DriveShoulder(double setpoint) {
		Robot.m_Shoulder.setSetpoint(setpoint);
		requires(Robot.m_Shoulder);
	}
	
	protected void initialize() {
		Robot.m_Shoulder.setPID(RobotMap.shoulderP, RobotMap.shoulderI, RobotMap.shoulderD);
	}

	protected void execute() {
		Robot.m_Shoulder.drivePID();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}