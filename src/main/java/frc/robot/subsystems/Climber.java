/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class Climber extends Subsystem {
  private WPI_TalonSRX m_ClimberFront;
  private WPI_TalonSRX m_ClimberBack;
  public DigitalInput m_Limit;

  // constructor
  public Climber() {
    m_ClimberFront = new WPI_TalonSRX(RobotMap.climbMotor);
    m_ClimberBack = new WPI_TalonSRX(RobotMap.climbMotor2);
    m_Limit = new DigitalInput(0);
  }
  
  public void runClimberUp(double moveValue){
    m_ClimberFront.set (moveValue);
    m_ClimberBack.set(moveValue);
  }

  public void runClimberFront(double moveValue){
    m_ClimberFront.set (moveValue);

  }

  public void runClimberBack(double moveValue){
    m_ClimberBack.set (moveValue);
  }

  public boolean checkSwitch() {
    return !m_Limit.get(); //false if open, true if closed
  }

 @Override
  public void initDefaultCommand(){
    // does not need init default
  }

}
  