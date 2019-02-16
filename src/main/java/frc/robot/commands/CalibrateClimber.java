package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class CalibrateClimber extends Command {

	boolean done = false;

	public CalibrateClimber() {
		requires(Robot.m_Climber);
	}

	protected void initialize() {
	}

	protected void execute() {
		// System.out.println(sw.get());
		if (Robot.m_Climber.checkFrontSwitch()) {
			Robot.m_Climber.runClimberFront(0);
			Robot.m_Climber.setFrontEncoderZero();
		} else {
			Robot.m_Climber.runClimberFront(RobotMap.climberCalSpeed);
		}

		if (Robot.m_Climber.checkBackSwitch()) {
			Robot.m_Climber.runClimberBack(0);
			Robot.m_Climber.setBackEncoderZero();
		} else {
			Robot.m_Climber.runClimberBack(RobotMap.climberCalSpeed);
		}

		if (Robot.m_Climber.checkBackSwitch() && Robot.m_Climber.checkFrontSwitch()) {
			done = true;
		}
	}

	@Override
	protected boolean isFinished() {
		return done;
	}

}
