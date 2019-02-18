package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class SetElevatorPosition extends Command{

	protected void execute() {
		Robot.m_Elevator.setEncoderPosition(RobotMap.elevatorTopPosition);
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

}
