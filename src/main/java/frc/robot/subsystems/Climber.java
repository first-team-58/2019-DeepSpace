/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;


public class Climber extends Subsystem {
  private WPI_VictorSPX m_ClimberMotor;
  private WPI_VictorSPX m_ClimberFollower;

  // constructor
  public Climber() {
    m_ClimberMotor = new WPI_VictorSPX(RobotMap.climbMotor);
    m_ClimberFollower = new WPI_VictorSPX(RobotMap.ClimbAssist);

    public void runGripper(double riseValue){

      m_ClimberMotor.set (riseValue);
    }

    m_ClimberFollower.follow(m_ClimberMotor);


  }



private 

 @Override
  public void Climber initDefaultCommand()
  