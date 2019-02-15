/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());

  // create joysticks for driver and operator
  public Joystick driver = new Joystick(RobotMap.driver);
  public Joystick operator = new Joystick(RobotMap.operator); 


  // add buttons to operator

  public JoystickButton wrist = new JoystickButton(operator, RobotMap.xButton); // Left Bumper
  public JoystickButton shoulder = new JoystickButton(operator, RobotMap.aButton); // right Bumper
  public JoystickButton elevator = new JoystickButton(operator, RobotMap.bButton); // y Button

  // add buttons to driver
  public JoystickButton spit = new JoystickButton(driver, RobotMap.yButton); // b button
  public JoystickButton pull = new JoystickButton(driver, RobotMap.xButton); // a button
  public JoystickButton retractF = new JoystickButton(driver, RobotMap.lBumper); // Left Bumper
  public JoystickButton retractB = new JoystickButton(driver, RobotMap.rBumper); // Right Bumper 
  public JoystickButton calibrateClimber = new JoystickButton(driver, RobotMap.selectButton);
  public JoystickButton climb = new JoystickButton(driver, RobotMap.startButton);
  //public JoystickButton liftButton = new JoystickButton(driver, 4); // y button

  public OI(){
	calibrateClimber.whenPressed(new CalibrateClimber());
	climb.whenPressed(new DriveClimber(RobotMap.climberTarget));
    spit.whileHeld(new Grab(1)); // not sure of speed
    pull.whileHeld(new Grab(-1));
    retractF.whileHeld(new RetractFrontClimber());
    retractB.whileHeld(new RetractBackClimber());
    wrist.whileHeld(new UpdateWristSetpoint(Robot.m_Wrist.getSetpointAngle() + 1*operator.getRawAxis(RobotMap.verticalLeft)));
    shoulder.whileHeld(new UpdateShoulderSetpoint(Robot.m_Shoulder.getSetpointAngle() + 1*operator.getRawAxis(RobotMap.verticalLeft)));
    elevator.whileHeld(new UpdateElevatorSetpoint((int) (Robot.m_Elevator.getSetpoint() + ((int) 10*operator.getRawAxis(RobotMap.verticalLeft)))));
  }
  
}
