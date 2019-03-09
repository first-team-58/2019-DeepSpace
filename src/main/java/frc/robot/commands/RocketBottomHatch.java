package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class RocketBottomHatch extends CommandGroup {
	public RocketBottomHatch() {
		SmartDashboard.putString("Current Setpoint Position", "Rocket Bottom Hatch");
		addParallel(new StartPID());
		if (Robot.m_Shoulder.getAngle() > RobotMap.shoulderSafePoint) {
			//System.out.println("going to safe point");
			addSequential(new UpdateShoulderSetpoint(RobotMap.shoulderSafePoint)); // clear it from collisions
		}
		
		addParallel(new UpdateElevatorSetpoint(RobotMap.rocketHatchLowElevatorHeight));
		addParallel(new UpdateShoulderSetpoint(RobotMap.rocketHatchLowShoulderAngle));
		addParallel(new UpdateWristSetpoint(RobotMap.rocketHatchLowWristAngle));
	}


}
