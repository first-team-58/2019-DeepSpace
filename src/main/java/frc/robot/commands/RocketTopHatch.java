package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class RocketTopHatch extends CommandGroup {
	public RocketTopHatch() {
		SmartDashboard.putString("Current Setpoint Position", "Rocket Top Hatch");
		addParallel(new StartPID());
		addSequential(new UpdateElevatorSetpoint(RobotMap.rocketHatchTopElevatorHeight));
		addSequential(new UpdateWristSetpoint(RobotMap.rocketHatchTopWristAngle));
		addSequential(new UpdateShoulderSetpoint(RobotMap.rocketHatchTopShoulderAngle));
	}
}
