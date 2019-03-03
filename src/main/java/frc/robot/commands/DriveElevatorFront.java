package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class DriveElevatorFront extends Command {
	double speed;
	
	public DriveElevatorFront(double speed) {
		this.speed = speed;
		requires(Robot.m_Climber);
	}
	
	protected void execute() {
		Robot.m_Climber.runClimberFront(speed);
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}
	
}

