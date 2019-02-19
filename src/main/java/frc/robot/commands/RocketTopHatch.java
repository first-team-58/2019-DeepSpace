package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class RocketTopHatch extends CommandGroup {
	public RocketTopHatch() {
		addParallel(new StartPID());
		addSequential(new UpdateElevatorSetpoint(RobotMap.rocketHatchTopElevatorHeight));
		addSequential(new UpdateWristSetpoint(RobotMap.rocketHatchTopWristAngle));
		addSequential(new UpdateShoulderSetpoint(RobotMap.rocketHatchTopShoulderAngle));
	}
}
