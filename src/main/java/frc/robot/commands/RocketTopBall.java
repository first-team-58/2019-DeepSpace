package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class RocketTopBall extends CommandGroup {
	public RocketTopBall() {
		addParallel(new StartPID());
		addSequential(new UpdateElevatorSetpoint(RobotMap.rocketBallTopElevatorHeight));
		addSequential(new UpdateWristSetpoint(RobotMap.rocketBallTopWristAngle));
		addSequential(new UpdateShoulderSetpoint(RobotMap.rocketBallTopShoulderAngle));
	}
}
