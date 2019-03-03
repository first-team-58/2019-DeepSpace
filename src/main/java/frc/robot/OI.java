/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.buttons.Button;

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

	public JoystickButton wrist = new JoystickButton(operator, RobotMap.xButton); // x
	public JoystickButton shoulder = new JoystickButton(operator, RobotMap.aButton); // a
	public JoystickButton elevator = new JoystickButton(operator, RobotMap.bButton); // b
	public JoystickButton manual = new JoystickButton(operator, RobotMap.yButton);
	public JoystickPOVButton setpointUp = new JoystickPOVButton(operator, POV.NORTH);
	public JoystickPOVButton setpointDown = new JoystickPOVButton(operator, POV.SOUTH);
	public JoystickPOVButton setpointLeft = new JoystickPOVButton(operator, POV.WEST);
	public JoystickPOVButton setpointRight = new JoystickPOVButton(operator, POV.EAST);
	public JoystickButton calElevator = new JoystickButton(operator, RobotMap.startButton);

	// add buttons to driver
	public JoystickButton spit = new JoystickButton(driver, RobotMap.bButton); // b button
	public JoystickButton pull = new JoystickButton(driver, RobotMap.aButton); // a button
	public JoystickButton retractF = new JoystickButton(driver, RobotMap.lBumper); // Left Bumper
	public JoystickButton retractB = new JoystickButton(driver, RobotMap.rBumper); // Right Bumper
	public JoystickButton calibrateClimber = new JoystickButton(driver, RobotMap.selectButton);
	public JoystickButton climb = new JoystickButton(driver, RobotMap.startButton);
	// public JoystickButton liftButton = new JoystickButton(driver, 4); // y button
	public JoystickButton hatch = new JoystickButton(driver, RobotMap.xButton); //x
	public JoystickButton autoAim = new JoystickButton(driver, 10);

	//limit switches autoset position
	public DigitalInputButton elevatorSwitch = new DigitalInputButton(Robot.m_Elevator.getTopSwitch(), true);
	
	public OI() {
		elevatorSwitch.whenActive(new SetElevatorPosition());
		autoAim.whenPressed(new PIDDrive());
		hatch.whenPressed(new ToggleHatch());
		calElevator.whenPressed(new CalibrateElevator());
		setpointUp.whenPressed(new ModifyCommandTable(0, 1));
		setpointDown.whenPressed(new ModifyCommandTable(0, -1));
		setpointLeft.whenPressed(new ModifyCommandTable(-1, 0));
		setpointRight.whenPressed(new ModifyCommandTable(1, 0));
		manual.whenPressed(new TogglePID());
		calibrateClimber.whenPressed(new CalibrateClimber());
		climb.whenPressed(new DriveClimber(RobotMap.climberTarget));
		spit.whileHeld(new Grab(.5)); // not sure of speed
		//spit.toggleWhenPressed(new Grab(1));
		//pull.whileHeld(new Grab(-1));
		pull.toggleWhenPressed(new Grab(-.5));
		retractF.whileHeld(new RetractFrontClimber());
		retractB.whileHeld(new RetractBackClimber());
		//wrist.whileHeld(new UpdateWristSetpoint(
			//	Robot.m_Wrist.getSetpointAngle() + 10 * operator.getRawAxis(RobotMap.verticalLeft)));
		//shoulder.whileHeld(new UpdateShoulderSetpoint(
			//	Robot.m_Shoulder.getSetpointAngle() + 10 * operator.getRawAxis(RobotMap.verticalLeft)));
		//elevator.whileHeld(new UpdateElevatorSetpoint(
			//	(int) (Robot.m_Elevator.getSetpoint() + ((int) 10 * operator.getRawAxis(RobotMap.verticalLeft)))));
	}

	public class joystickAnalogButton extends Button {

		private Joystick stick;
		private int axis;
		private direction dir;
		private double deadzone = 0.2;

		joystickAnalogButton(Joystick joy, int axis, direction dir) {
			this.stick = joy;
			this.axis = axis;
			this.dir = dir;

		}

		public boolean get() {
			switch (dir) {
			case UP:
				if (stick.getRawAxis(axis) > deadzone) {
					return true;
				} else {
					return false;
				}
			case DOWN:
				deadzone = deadzone * -1;
				if (stick.getRawAxis(axis) < deadzone) {
					return true;
				} else {
					return false;
				}
			default:
				return false;
			}
		}
	}

	class DigitalInputButton extends Trigger {
		private DigitalInput t;
		private boolean invert;
		public DigitalInputButton(DigitalInput t, boolean invert) {
			this.t = t;
			this.invert = !invert;
		}
		public boolean get() {
			return t.get() == invert;
		}
	}
	
	class JoystickPOVButton extends Button {
		Joystick stick;
		int dir;

		public JoystickPOVButton(Joystick stick, POV dir) {
			this.stick = stick;
			switch (dir) {
			case EAST:
				this.dir = 90;
				break;
			case NORTH:
				this.dir = 0;
				break;
			case NORTHEAST:
				this.dir = 45;
				break;
			case NORTHWEST:
				this.dir = 315;
				break;
			case SOUTH:
				this.dir = 180;
				break;
			case SOUTHEAST:
				this.dir = 135;
				break;
			case SOUTWEST:
				this.dir = 225;
				break;
			case WEST:
				this.dir = 270;
				break;
			default:
				break;

			}
		}

		@Override
		public boolean get() {
			if (stick.getPOV() == dir) {
				return true;
			} else {
				return false;
			}
		}
	}

	enum POV {
		NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTWEST, WEST, NORTHWEST
	}

	enum direction {
		UP, DOWN
	}
}
