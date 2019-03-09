package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveElevator extends Command {

	public DriveElevator() {
		requires(Robot.m_Elevator);
	}
	
	public DriveElevator(int position) {
		Robot.m_Elevator.setSetpoint(position);
		requires(Robot.m_Elevator);
		//SmartDashboard.putNumber("ElevatorFloor",position);//futile attempt
	}
	
	protected void initialize() {
		Robot.m_Elevator.positionAchieved = false;
		Robot.m_Elevator.setPID(RobotMap.elevatorP, RobotMap.elevatorI, RobotMap.elevatorD);
	}
	
	protected void execute() {
		Robot.m_Elevator.drivePID();
	}
	
	@Override
	protected boolean isFinished() {
		return Robot.m_Elevator.positionAchieved;
	}

}
