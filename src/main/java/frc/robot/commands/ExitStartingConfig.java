package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class ExitStartingConfig extends CommandGroup {
	public ExitStartingConfig() {
		SmartDashboard.putString("Current Setpoint Position", "Exit Starting Config");
		addParallel(new StartPID());
		
		addParallel(new UpdateShoulderSetpoint(Robot.m_Shoulder.getSetpointAngle() - 20));
		addParallel(new UpdateWristSetpoint(Robot.m_Wrist.getSetpointAngle() - 20));
	}
}
