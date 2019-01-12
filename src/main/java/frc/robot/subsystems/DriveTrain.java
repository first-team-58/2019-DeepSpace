/*----------------------------------------------------------------------------*/
/* West Coast Drive                                                           */
/* written 1/12 by EBD                                                        */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.Drive;

//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * West Coast Drive for Pneumatic Wheels
 */
public class DriveTrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private WPI_TalonSRX m_FrontRightMotor;
  private WPI_TalonSRX m_FrontLeftMotor;
  private WPI_VictorSPX m_RightFollower;
  private WPI_VictorSPX m_LeftFollower;
  private DifferentialDrive m_drive;
  private Solenoid m_SpeedSolenoid; 

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new Drive());
  }

  public DriveTrain() {
    // create motor instances
    m_FrontLeftMotor = new WPI_TalonSRX(2);
    m_FrontRightMotor = new WPI_TalonSRX(4);
    m_RightFollower = new WPI_VictorSPX(3);
    m_LeftFollower = new WPI_VictorSPX(5);
    m_SpeedSolenoid = new Solenoid(1);

    // congifure victoes to follow talons
    m_RightFollower.follow(m_FrontRightMotor);
    m_LeftFollower.follow(m_FrontLeftMotor);

    //create drive object
    m_drive = new DifferentialDrive(m_FrontRightMotor, m_FrontLeftMotor);
  
  }

  public void drive (double moveValue, double rotateValue) { 

    m_drive.arcadeDrive(moveValue, rotateValue);
    
  }

}