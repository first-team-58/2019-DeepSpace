package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class RocketTopHatch extends CommandGroup {
	public RocketTopHatch() {
		SmartDashboard.putString("Current Setpoint Position", "Rocket Top Hatch");
		addParallel(new StartPID());
		addParallel(new UpdateElevatorSetpoint(RobotMap.rocketHatchTopElevatorHeight));
		addParallel(new UpdateShoulderSetpoint(RobotMap.rocketHatchTopShoulderAngle));
		addParallel(new UpdateWristSetpoint(RobotMap.rocketHatchTopWristAngle));
	}
}
