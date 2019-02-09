/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;

  // motor ids
  public static int gripperMotor = 7;
  public static int climbMotor = 8;
  public static int climbMotor2 = 9;
  public static int elevatorMotor = 6;
  public static int ShoulderMotor = 1;
  

  // solenoid ids
  public static int hatchSolenoid = 2;

  //Joysticks
  public static int driver = 0;
  public static int operator = 1;

  // Buttons on Driver Joystick -- needs to be fixed EBD
  public static int moveAxis = 1; //LY 
  public static int rotateAxis = 4; //RX
  public static int boostAxis = 3; //RTrigger
  public static int aButton = 1;
  public static int bButton = 2;
  public static int xButton = 3;
  public static int yButton = 4;

  
  //Sensors
  public static int shoulderPotentiometer = 0;
  
  //PID values
  public static double shoulderP = .02;
  public static double shoulderI = 0;
  public static double shoulderD = 0;
  
}
