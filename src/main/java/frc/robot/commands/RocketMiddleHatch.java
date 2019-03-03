package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class RocketMiddleHatch extends CommandGroup {
	public RocketMiddleHatch() {
		SmartDashboard.putString("Current Setpoint Position", "Rocket Middle Hatch");
		addParallel(new StartPID());
		if (Robot.m_Shoulder.getAngle() > RobotMap.shoulderSafePoint) {
			System.out.println("going to safe point");
			addSequential(new UpdateShoulderSetpoint(RobotMap.shoulderSafePoint)); // clear it from collisions
		}
		
		addParallel(new UpdateElevatorSetpoint(RobotMap.rocketHatchMidElevatorHeight));
		addParallel(new UpdateShoulderSetpoint(RobotMap.rocketHatchMidShoulderAngle));
		addParallel(new UpdateWristSetpoint(RobotMap.rocketHatchMidWristAngle));
	}


}
