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
  public JoystickButton spit = new JoystickButton(operator, 2); // b button
  public JoystickButton pull = new JoystickButton(operator, 1); // a button
  public JoystickButton high = new JoystickButton(operator, 5); // Left Bumper
  public JoystickButton low = new JoystickButton(operator, 6); // right Bumper
  public JoystickButton start = new JoystickButton(operator, 4); // y Button

  // add buttons to driver
  public JoystickButton retractF = new JoystickButton(driver, 5); // Left Bumper
  public JoystickButton retractB = new JoystickButton(driver, 6); // Right Bumper 
  public JoystickButton liftButton = new JoystickButton(driver, 4); // y button

  public OI(){

    spit.whileHeld(new Grab(0.5)); // not sure of speed
    pull.whileHeld(new Grab(-0.5));
    retractF.whileHeld(new RetractFrontClimber());
    retractB.whileHeld(new RetractBackClimber());
    liftButton.whenPressed(new LiftRobot());
    high.whenPressed(new PositionElevator(10000)); // need to detremine
    low.whenPressed(new PositionElevator(0)); 
    start.whenPressed(new PositionElevator(5000)); // need to determine 

  }

}
