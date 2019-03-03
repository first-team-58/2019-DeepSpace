package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class RocketMiddleHatch extends CommandGroup {
	public RocketMiddleHatch() {
		addParallel(new StartPID());
		if (Robot.m_Shoulder.getAngle() > RobotMap.shoulderSafePoint) {
			System.out.println("going to safe point");
			addSequential(new UpdateShoulderSetpoint(RobotMap.shoulderSafePoint)); // clear it from collisions
		}
		
		addSequential(new UpdateElevatorSetpoint(RobotMap.rocketHatchMidElevatorHeight));
		addSequential(new UpdateWristSetpoint(RobotMap.rocketHatchMidWristAngle));
		addSequential(new UpdateShoulderSetpoint(RobotMap.rocketHatchMidShoulderAngle));
	}


}
