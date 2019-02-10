package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class CalibrateElevator extends Command {
	DigitalInput sw;
	boolean done = false;
	
	public CalibrateElevator() {
		requires(Robot.m_Elevator);
	}
	
	protected void initialize() {
		sw = Robot.m_Elevator.getTopSwitch();
	}
	
	protected void execute() {
		//System.out.println(sw.get());
		if(!sw.get()) { //switch is triggered
			Robot.m_Elevator.drive(0);
			Robot.m_Elevator.setEncoderPosition(RobotMap.elevatorTopPosition);
			done = true;
		} else {
			//System.out.print("Now im here");
			Robot.m_Elevator.drive(RobotMap.elevatorCalSpeed);
		}
	}
	
	@Override
	protected boolean isFinished() {
		return done;
	}

}
