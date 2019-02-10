package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class RocketTopHatch extends CommandGroup {
	public RocketTopHatch() {
		addParallel(new StartPID());
		if (Robot.m_Shoulder.getAngle() > 200) {
			System.out.println("going to safe point");
			addSequential(new UpdateShoulderSetpoint(200)); // clear it from collisions
		}
		
		addSequential(new UpdateElevatorSetpoint(RobotMap.rocketHatchTopElevatorHeight));
		
		addSequential(new UpdateWristSetpoint(RobotMap.rocketHatchTopWristAngle));
		addSequential(new UpdateShoulderSetpoint(RobotMap.rocketHatchTopShoulderAngle));
	}
}
