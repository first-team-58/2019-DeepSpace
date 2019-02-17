package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ToggleHatch extends Command {
  
    private static boolean toggle;

  public ToggleHatch() {
    //requires(Robot.m_Gripper);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
	toggle = !toggle;
    Robot.m_Gripper.setHatch(toggle);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
