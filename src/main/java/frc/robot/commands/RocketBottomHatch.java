package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class RocketBottomHatch extends CommandGroup {
	public RocketBottomHatch() {
		addParallel(new StartPID());
		if (Robot.m_Shoulder.getAngle() > 150) {
			System.out.println("going to safe point");
			addSequential(new UpdateShoulderSetpoint(150)); // clear it from collisions
		}
		
		addSequential(new UpdateElevatorSetpoint(RobotMap.rocketHatchLowElevatorHeight));
		addSequential(new UpdateWristSetpoint(RobotMap.rocketHatchLowWristAngle));
		addSequential(new UpdateShoulderSetpoint(RobotMap.rocketHatchLowShoulderAngle));
	}


}
