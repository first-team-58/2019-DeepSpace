package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class FloorHatch extends CommandGroup {
	public FloorHatch() {
		SmartDashboard.putString("Current Setpoint Position", "Floor Hatch");
		addParallel(new StartPID());
		if (Robot.m_Shoulder.getAngle() > RobotMap.shoulderSafePoint) {
			System.out.println("going to safe point");
			addSequential(new UpdateShoulderSetpoint(RobotMap.shoulderSafePoint)); // clear it from collisions
		}
		
		addSequential(new UpdateElevatorSetpoint(RobotMap.hatchFromFloorElevatorHeight));
		addSequential(new UpdateWristSetpoint(RobotMap.hatchFromFloorWristAngle));
		addSequential(new UpdateShoulderSetpoint(RobotMap.hatchFromFloorShoulderAngle));
	}

}
