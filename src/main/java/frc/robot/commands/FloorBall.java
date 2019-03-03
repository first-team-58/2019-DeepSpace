package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class FloorBall extends CommandGroup {
	public FloorBall() {
		SmartDashboard.putString("Current Setpoint Position", "Floor Ball");
		addParallel(new StartPID());
		if (Robot.m_Shoulder.getAngle() > RobotMap.shoulderSafePoint) {
			System.out.println("going to safe point");
			addSequential(new UpdateShoulderSetpoint(RobotMap.shoulderSafePoint)); // clear it from collisions was 200 also above if statement
		}
		
		addSequential(new UpdateElevatorSetpoint(RobotMap.ballFromFloorElevatorHeight));
		addSequential(new UpdateWristSetpoint(RobotMap.ballFromFloorWristAngle));
		addSequential(new UpdateShoulderSetpoint(RobotMap.ballFromFloorShoulderAngle));
	}


}
