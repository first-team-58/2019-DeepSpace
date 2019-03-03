package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class FloorHatch extends CommandGroup {
	public FloorHatch() {
		addParallel(new StartPID());
		if (Robot.m_Shoulder.getAngle() > 150) {
			System.out.println("going to safe point");
			addSequential(new UpdateShoulderSetpoint(150)); // clear it from collisions
		}
		
		addSequential(new UpdateElevatorSetpoint(RobotMap.hatchFromFloorElevatorHeight));
		addSequential(new UpdateWristSetpoint(RobotMap.hatchFromFloorWristAngle));
		addSequential(new UpdateShoulderSetpoint(RobotMap.hatchFromFloorShoulderAngle));
	}

}
