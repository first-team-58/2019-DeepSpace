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
			//System.out.println("going to safe point");
			addSequential(new UpdateShoulderSetpoint(RobotMap.shoulderSafePoint)); // clear it from collisions
		}

		addParallel(new UpdateElevatorSetpoint(RobotMap.rocketBallMidElevatorHeight));
		addParallel(new UpdateWristSetpoint(RobotMap.rocketBallMidWristAngle));
		addParallel(new UpdateShoulderSetpoint(RobotMap.rocketBallMidShoulderAngle));
	}
}
