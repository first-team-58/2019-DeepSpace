package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class RocketTopBall extends CommandGroup {
	public RocketTopBall() {
		SmartDashboard.putString("Current Setpoint Position", "Rocket Top Ball");
		addParallel(new StartPID());
		addParallel(new UpdateElevatorSetpoint(RobotMap.rocketBallTopElevatorHeight));
		addParallel(new UpdateWristSetpoint(RobotMap.rocketBallTopWristAngle));
		addParallel(new UpdateShoulderSetpoint(RobotMap.rocketBallTopShoulderAngle));
	}
}
