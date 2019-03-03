package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class RocketMiddleBall extends CommandGroup {
	public RocketMiddleBall() {
		SmartDashboard.putString("Current Setpoint Position", "Rocket Middle Ball");
		addParallel(new StartPID());
		if (Robot.m_Shoulder.getAngle() > RobotMap.shoulderSafePoint) {
			System.out.println("going to safe point");
			addSequential(new UpdateShoulderSetpoint(RobotMap.shoulderSafePoint)); // clear it from collisions
		}

		addSequential(new UpdateElevatorSetpoint(RobotMap.rocketBallMidElevatorHeight));
		addSequential(new UpdateWristSetpoint(RobotMap.rocketBallMidWristAngle));
		addSequential(new UpdateShoulderSetpoint(RobotMap.rocketBallMidShoulderAngle));
	}
}
