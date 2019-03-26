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
  public static int hatchSolenoid = 0;
  
  //PWM Ports
  public static int hookServo = 0;

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
  public static int lBumper = 5;//JACK - These seem backward =. shouldn't they be left 4 and right 5?
  public static int rBumper = 6;
  public static int selectButton = 7;
  public static int startButton = 8;
  public static int lTrigger = 2;
  public static int rTrigger = 3;

  //Constants
  public static int elevatorTopPosition = 13902;
  public static double elevatorCalSpeed = -.5; //1 is full speed down, -1 is full speed up
  /*
  -.5 is where Jack left off
  -1; seems a little faster but not really
  0; Dunno
  1; Not much change, guessing that "Cal" is something Jack does at first button push?  Putting this back to -.5
  */
  public static double climberCalSpeed = -.5; //1 is full speed down, -1 is full speed up
  public static double maxDriveSpeed = .70;
  public static double hookOpen = 158;
  public static double hookClosed = 9;

  //Sensors
  public static int shoulderPotentiometer = 0;
  public static int wristPotentiometer = 1;
  
  //Angle limits
  public static double wristPositiveLimit = 340; //max angle for wrist
  public static double wristNegitiveLimit = 5; //min angle for wrist
  public static double shoulderPositiveLimit = 20; //highest angle for shoulder
  public static double shoulderNegitiveLimit = 230; //lowest angle for shoulder
  
  //PID values
  public static double shoulderP = 1.5;//Was .5
  public static double shoulderI = .05;//Was .05
  public static double shoulderD = 0;
  public static double wristP = 4;//Was 4
  public static double wristI = 0.1;//Was.1
  public static double wristD = 0;
  public static double elevatorP = 0.0003058;//0.0000775 
  /*
  .0007; This is where jack left off
  .001; is reasonable but a little slow (I)0 (D) 0
  .00158; starts to Oscilate
  .002; about the same
  .01; try to crank it up. Faster but oscllates wildly
  .1; Still slow, looking for a m x speed setting of some sort.
  Is the CIM limited to .5 or something?
  After unshackling the speed, .0005 worked reasonably, a little too fast.  .0003 didnt have enough to make it to setpoint.
  .0004 still a little too violent
  */
  public static double elevatorI = 0.00000106;//.0001 .0000011 trips breaker
  public static double elevatorD = 0;//.000009
  public static double climberPf = .0007;
  public static double climberIf = 0.0001;
  public static double climberDf = 0;
  public static double climberPr = .0007;
  public static double climberIr = 0.0001;
  public static double climberDr = 0;
  
  //Position safepoints when pid is moving
  public static double shoulderSafePoint = 150;
  public static double wristSafePoint = 50; //??? idk what is safe
  public static int elevatorSafePoint = 10000;
  
  //PID Deadzones
  public static int elevatorPositionDeadzone = 500; //was 250 chnaged to 500 to try to make it more consistant, double (math is absolute value, so abs(setpoint-pos) < this value)
  public static double shoulderPositionDeadzone = 7.5; //(math is absolute value, so abs(setpoint-pos) < this value)
  public static double wristPositionDeadzone = 7.5; //(math is absolute value, so abs(setpoint-pos) < this value)
  
  //Cargo Positions
  public static int rocketBallTopElevatorHeight = 14000; //top
  public static double rocketBallTopShoulderAngle = 74;
  public static double rocketBallTopWristAngle = 117; //was 88 should be 5, but thats too low for the pot
  
  public static int rocketBallMidElevatorHeight = 10500;
  public static double rocketBallMidShoulderAngle = 104;
  public static double rocketBallMidWristAngle = 119;//was 86

  public static int rocketBallLowElevatorHeight = 3000;
  public static double rocketBallLowShoulderAngle = 137;
  public static double rocketBallLowWristAngle = 162;//was 123

  //was 230 3/23/2019 was 255 (changed wrist to snow blower.  after that the angle for the human players station was too high)
  //3/24: was 238, 
  public static int ballFromFloorElevatorHeight = 3000;
  public static double ballFromFloorShoulderAngle = 157;
  public static double ballFromFloorWristAngle = 115;//was 73

  //Hatch Positions
  // 3/24: 14000, 80, 182
  public static int rocketHatchTopElevatorHeight = 13600;
  public static double rocketHatchTopShoulderAngle = 80;
  public static double rocketHatchTopWristAngle = 180;
  
  //Update 3/23: Elevator setpoint was at 2500 before we changed the wrist to a snowblower
  // 3/24: was 6500, 102, 204
  public static int rocketHatchMidElevatorHeight = 4400;
  public static double rocketHatchMidShoulderAngle = 102;
  public static double rocketHatchMidWristAngle = 204; //was 173
  
  //Update 3/23: Elevator setpoint was at 1500 before we changed the wrist to a snowblower
  // 3/24: 5500, 167, 238
  public static int rocketHatchLowElevatorHeight = 2630;
  public static double rocketHatchLowShoulderAngle = 167;
  public static double rocketHatchLowWristAngle = 238;
  
  // 3/24: was 1500, 177, 154 
  public static int hatchFromFloorElevatorHeight = 2630;
  public static double hatchFromFloorShoulderAngle = 167;
  public static double hatchFromFloorWristAngle = 238;


  public static int climberTarget = 23000;//How high we climb?
  
  public static long shoulderZero = 0;
  public static int wristZero = 0;
}
