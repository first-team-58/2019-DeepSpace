package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class PIDDrive extends Command {

	protected void initialize() {
		Robot.m_drivetrain.positionAchieved = false;
	}

	protected void execute() {
		if (Robot.tx.getDouble(0.0)!=0.0) {
			System.out.println("running pid");
			double moveValue = Robot.m_oi.driver.getRawAxis(RobotMap.moveAxis);
			Robot.m_drivetrain.setSetpoint(Robot.tx.getDouble(Robot.m_drivetrain.setpoint));
			if ((moveValue <= 0.2) && (moveValue >= -0.2)) {
				moveValue = 0;
			}

			Robot.m_drivetrain.pidDrive(moveValue);
		}
	}

	@Override
	protected boolean isFinished() {
		return Robot.m_drivetrain.positionAchieved;
	}

}
