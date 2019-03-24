/*----------------------------------------------------------------------------*/
/* Drive command runs the DriveTrain Subsystem                                */
/* Written by EBD on 1/12                                                     */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Drive runs robot Drivetrain with Arcade Drive type
 */
public class Drive extends Command {
  public Drive() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_drivetrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // collect values from driver controls
    double moveValue = Robot.m_oi.driver.getRawAxis(RobotMap.moveAxis);
    double rotateValue = Robot.m_oi.driver.getRawAxis(RobotMap.rotateAxis);

    // deadbands
    if ((moveValue <= 0.2) && (moveValue >= -0.2)) {
      moveValue = 0;
    }

    if ((rotateValue <= 0.2) && (rotateValue >= -0.2 )) {
      rotateValue = 0;
    }
    
    // round up values for full speed
    if (moveValue > 0.95) {
      moveValue = 1;
    }

    if(moveValue < -0.95) {
      moveValue = -1;
    }

    // send values to DriveTrain subsystem
    Robot.m_drivetrain.drive(moveValue, rotateValue);

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
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
