package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RetractFrontClimber extends Command {

  public RetractFrontClimber() {
    requires(Robot.m_Climber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.m_Climber.runClimberFront(-0.8);// not sure of speed
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_Climber.runClimberUp(0); 
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.m_Climber.runClimberUp(0); 
  }
}
