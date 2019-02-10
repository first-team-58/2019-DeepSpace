package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class DriveWrist extends Command {
	private double setpoint = 200;//default position to go to at start
	
	public DriveWrist() {
		requires(Robot.m_Wrist);
	}
	
	public DriveWrist(double angle) {
		Robot.m_Wrist.setSetpointAngle(angle);
		requires(Robot.m_Wrist);
	}
	
	@Override
	protected void initialize() {
		Robot.m_Wrist.positionAchieved = false;
		Robot.m_Wrist.setPID(RobotMap.wristP, RobotMap.wristI, RobotMap.wristD);
	}
	
	@Override
	protected void execute() {
		Robot.m_Wrist.drivePID();
		//Robot.m_Wrist.setSetpoint(setpoint); //might be wrong, I don't know if this will continue to feed the motor
	}
	
	@Override
	protected boolean isFinished() {
		return Robot.m_Wrist.positionAchieved;
	}

}
