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
  public static int climbMotorRear = 8;
  public static int climbMotorFront = 9;
  public static int elevatorMotor = 6;
  public static int ShoulderMotor = 11;
  public static int wristMotor = 10;
  
  //limit ids
  public static int elevatorTopSwitch = 9;
  public static int climberFSwitch = 0;
  public static int climberBSwitch = 1;
  
  // solenoid ids
  public static int hatchSolenoid = 2;

  //Joysticks
  public static int driver = 0;
  public static int operator = 1;

  // Buttons on Driver Joystick -- needs to be fixed EBD
  public static int moveAxis = 1; //LY 
  public static int rotateAxis = 4; //RX
  public static int boostAxis = 3; //RTrigger
  
  //Xbox button values
  public static int verticalLeft = 1; //vertical axis of left joystick
  public static int horizontalLeft = 2; //horizontal axis of left joystick
  public static int horizontalRight = 4; //horizontal axis of right joystick
  public static int aButton = 1;
  public static int bButton = 2;
  public static int xButton = 3;
  public static int yButton = 4;
  public static int lBumper = 5;
  public static int rBumper = 6;
  public static int selectButton = 7;
  public static int startButton = 8;

  //Constants
  public static int elevatorTopPosition = 13902;
  public static double elevatorCalSpeed = -.5; //1 is full speed down, -1 is full speed up
  public static double climberCalSpeed = -.5; //1 is full speed down, -1 is full speed up
  
  //Sensors
  public static int shoulderPotentiometer = 0;
  
  //Angle limits
  public static double wristPositiveLimit = 300; //max angle for wrist
  public static double wristNegitiveLimit = 30; //min angle for wrist
  public static double shoulderPositiveLimit = 20; //highest angle for shoulder
  public static double shoulderNegitiveLimit = 230; //lowest angle for shoulder
  
  //PID values
  public static double shoulderP = .12;
  public static double shoulderI = .01;
  public static double shoulderD = 0;
  public static double wristP = .3;
  public static double wristI = .02;
  public static double wristD = 0;
  public static double elevatorP = .000071;
  public static double elevatorI = .0001;
  public static double elevatorD = 0;
  public static double climberPf = .0007;
  public static double climberIf = 0.0001;
  public static double climberDf = 0;
  public static double climberPr = .0007;
  public static double climberIr = 0.0001;
  public static double climberDr = 0;
  
  
  //PID Deadzones
  public static int elevatorPositionDeadzone = 250; //double (math is absolute value, so abs(setpoint-pos) < this value)
  public static double shoulderPositionDeadzone = 7.5; //(math is absolute value, so abs(setpoint-pos) < this value)
  public static double wristPositionDeadzone = 7.5; //(math is absolute value, so abs(setpoint-pos) < this value)
  
  //Positions
  public static int rocketBallTopElevatorHeight = 14000; //top
  public static double rocketBallTopShoulderAngle = 60;
  public static double rocketBallTopWristAngle = 20; //should be 5, but thats too low for the pot
  
  public static int rocketBallMidElevatorHeight = 14000;
  public static double rocketBallMidShoulderAngle = 263;
  public static double rocketBallMidWristAngle = 55;

  public static int rocketBallLowElevatorHeight = 12677;
  public static double rocketBallLowShoulderAngle = 282;
  public static double rocketBallLowWristAngle = 90;

  public static int rocketHatchTopElevatorHeight = 14000;
  public static double rocketHatchTopShoulderAngle = 104;
  public static double rocketHatchTopWristAngle = 63;
  
  public static int rocketHatchMidElevatorHeight = 14000;
  public static double rocketHatchMidShoulderAngle = 234;
  public static double rocketHatchMidWristAngle = 140;
  
  public static int rocketHatchLowElevatorHeight = 7000;
  public static double rocketHatchLowShoulderAngle = 282;
  public static double rocketHatchLowWristAngle = 140;
  
  public static int ballFromFloorElevatorHeight = 2800;
  public static double ballFromFloorShoulderAngle = 351;
  public static double ballFromFloorWristAngle = 47;
  
  public static int hatchFromFloorElevatorHeight = 0;
  public static double hatchFromFloorShoulderAngle = 351;
  public static double hatchFromFloorWristAngle = 80;
  
  public static int climberTarget = 22000;
  
}
