package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

public class StartPID extends CommandGroup {
	public StartPID() {
		//Start each PID, setting current position to wherever it is.
		Robot.manualMode = false;
		addParallel(new DriveElevator(Robot.m_Elevator.getEncoderPosition()));
		addParallel(new DriveShoulder(Robot.m_Shoulder.getAngle()));
		addParallel(new DriveWrist(Robot.m_Wrist.getAngleDegrees()));
	}
}
