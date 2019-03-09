package frc.robot.commands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ManualDriveWrist extends Command{
	public double speed;

	public ManualDriveWrist(Joystick controller, int axis) {
	    speed = controller.getRawAxis(axis);
	}

	public ManualDriveWrist(double speed) {
		this.speed = speed;
	}
	
	protected void execute() {
		if (Robot.manualMode) {
			Robot.m_Wrist.positionAchieved = true;
			Robot.m_Wrist.drive(speed);
		}
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
