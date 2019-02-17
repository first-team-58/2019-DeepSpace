/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;
//import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
//import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.CalibrateClimber;
import frc.robot.commands.CalibrateElevator;
import frc.robot.commands.DriveClimber;
import frc.robot.commands.DriveElevator;
import frc.robot.commands.DriveShoulder;
import frc.robot.commands.DriveWrist;
import frc.robot.commands.ManualDriveElevator;
import frc.robot.commands.ManualDriveShoulder;
import frc.robot.commands.ManualDriveWrist;
import frc.robot.commands.PIDDrive;
import frc.robot.commands.PositionPicker;
import frc.robot.commands.RocketTopHatch;
import frc.robot.commands.UpdateClimberSetpoint;
import frc.robot.commands.UpdateElevatorSetpoint;
import frc.robot.commands.UpdateShoulderSetpoint;
import frc.robot.commands.UpdateWristSetpoint;
import frc.robot.subsystems.*;
import edu.wpi.first.networktables.*;

import java.util.function.Consumer;

import com.kauailabs.navx.frc.AHRS;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static boolean manualMode = false;
	public static DriveTrain m_drivetrain = new DriveTrain();
	public static Gripper m_Gripper = new Gripper();
	public static Shoulder m_Shoulder = new Shoulder();
	public static Climber m_Climber = new Climber();
	public static Elevator m_Elevator = new Elevator();
	public static Wrist m_Wrist = new Wrist();
	public static OI m_oi;
	public static AHRS ahrs;
	public static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
	public static NetworkTableEntry tx = table.getEntry("tx");
	public static NetworkTableEntry ty = table.getEntry("ty");
	public static NetworkTableEntry ta = table.getEntry("ta");
	public static NetworkTableEntry tv = table.getEntry("tv");
	AnalogInput test2 = new AnalogInput(1);
	public static double test;
	public static AnalogInput t;
	public static double testangle = 20;

	public static NetworkTable adjustables = NetworkTableInstance.getDefault().getTable("adjustables");

	public static int cmdTBLX = 0, cmdTBLY = 0;
	public static XboxController opcon;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();

		// ahrs = new AHRS().Port.kMXP); /* Alternatives: SPI.Port.kMXP, I2C.Port.kMXP
		// or SerialPort.Port.kUSB */
		// m_chooser.setDefaultOption("Default Auto", new ExampleCommand());
		// chooser.addOption("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
		ahrs = new AHRS(SPI.Port.kMXP);
		m_drivetrain.setGyro(ahrs);
		SmartDashboard.putNumber("P", Robot.m_drivetrain.P);
		SmartDashboard.putNumber("I", Robot.m_drivetrain.I);
		SmartDashboard.putNumber("D", Robot.m_drivetrain.D);
		SmartDashboard.putNumber("volts", test);
		SmartDashboard.putNumber("angle setpoint", testangle);
		// t = new AnalogInput(0);
		//opcon = new XboxController(m_oi.operator.getPort());
		pushTable();
		initReadTable();
	}

	/**
	 * This function is called every robot packet, no matter the mode. Use this for
	 * items like diagnostics that you want ran during disabled, autonomous,
	 * teleoperated and test.
	 *
	 * <p>
	 * This runs after the mode specific periodic functions, but before LiveWindow
	 * and SmartDashboard integrated updating.
	 */
	@Override
	public void robotPeriodic() {
		SmartDashboard.putNumber("Limelight x", tx.getDouble(0.0));
		SmartDashboard.putNumber("Limelight y", ty.getDouble(0.0));
		SmartDashboard.putNumber("Limelight a", ta.getDouble(0.0));
		SmartDashboard.putString("Limelight targets", tv.getString("0"));
		SmartDashboard.putNumber("angle", ahrs.getAngle());
		Robot.m_drivetrain.P = SmartDashboard.getNumber("P", 1.0);
		Robot.m_drivetrain.I = SmartDashboard.getNumber("I", 1.0);
		Robot.m_drivetrain.D = SmartDashboard.getNumber("D", 1.0);

		SmartDashboard.putNumber("Shoulder angle", Robot.m_Shoulder.getAngle());
		SmartDashboard.putNumber("Shoulder setpoint", Robot.m_Shoulder.getSetpointAngle());

		SmartDashboard.putNumber("Wrist angle", Robot.m_Wrist.getAngleDegrees());
		SmartDashboard.putNumber("Wrist setpoint", Robot.m_Wrist.getSetpointAngle());

		SmartDashboard.putNumber("Elevator height", Robot.m_Elevator.getEncoderPosition());
		SmartDashboard.putNumber("Elevator setpoint", Robot.m_Elevator.getSetpoint());

		// IDK how to do this in OI properly yet
		if (Robot.m_oi.driver.getRawAxis(2) > .1) {
			Robot.m_Climber.runClimberFront(Robot.m_oi.driver.getRawAxis(2));
		}
		if (Robot.m_oi.driver.getRawAxis(3) > .1) {
			Robot.m_Climber.runClimberBack(Robot.m_oi.driver.getRawAxis(3));
		}

		if (Robot.m_oi.operator.getRawButton(RobotMap.aButton) && manualMode) {
			Scheduler.getInstance().add(new ManualDriveShoulder(Robot.m_oi.operator.getRawAxis(RobotMap.verticalLeft)));
		} else if (manualMode) {
			Scheduler.getInstance().add(new ManualDriveShoulder(0));
		}

		if (Robot.m_oi.operator.getRawButton(RobotMap.bButton) && manualMode) {
			Scheduler.getInstance().add(new ManualDriveElevator(Robot.m_oi.operator.getRawAxis(RobotMap.verticalLeft)));
		} else if (manualMode) {
			Scheduler.getInstance().add(new ManualDriveElevator(0));
		}

		if (Robot.m_oi.operator.getRawButton(RobotMap.xButton) && manualMode) {
			Scheduler.getInstance().add(new ManualDriveWrist(Robot.m_oi.operator.getRawAxis(RobotMap.verticalLeft)));
		} else if (manualMode) {
			Scheduler.getInstance().add(new ManualDriveWrist(0));
		}

		SmartDashboard.putNumber("Climber front encoder", Robot.m_Climber.getFrontEncoderPosition());
		SmartDashboard.putNumber("Climber rear encoder", Robot.m_Climber.getBackEncoderPosition());
		SmartDashboard.putBoolean("Elevator limit", Robot.m_Elevator.getTopSwitch().get());
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
		Robot.m_Shoulder.drive(0);
		Robot.m_Wrist.drive(0);
		Robot.m_Shoulder.integral = 0;
		Robot.m_Wrist.integral = 0;
		Robot.m_Shoulder.previous_error = 0;
		Robot.m_Wrist.previous_error = 0;
		Robot.m_Elevator.drive(0);
		Robot.m_Elevator.integral = 0;
		Robot.m_Elevator.previous_error = 0;
		Robot.m_Climber.runClimberUp(0);
		Robot.m_Climber.integralf = 0;
		Robot.m_Climber.integralr = 0;
		Robot.m_Climber.previous_errorf = 0;
		Robot.m_Climber.previous_errorr = 0;
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 * switch(autoSelected) { case "My Auto": autonomousCommand = new
		 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
		 * ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}

	// adds values to network tables
	public void pushTable() {
		// PID Values
		addTableEntry("Shoulder P", RobotMap.shoulderP);

		addTableEntry("Shoulder I", RobotMap.shoulderI);
		addTableEntry("Shoulder D", RobotMap.shoulderD);
		addTableEntry("Wrist P", RobotMap.wristP);
		addTableEntry("Wrist I", RobotMap.wristI);
		addTableEntry("Wrist D", RobotMap.wristD);
		addTableEntry("Elevator P", RobotMap.elevatorP);
		addTableEntry("Elevator I", RobotMap.elevatorI);
		addTableEntry("Elevator D", RobotMap.elevatorD);
		addTableEntry("Climber Front P", RobotMap.climberPf);
		addTableEntry("Climber Front I", RobotMap.climberIf);
		addTableEntry("Climber Front D", RobotMap.climberDf);
		addTableEntry("Climber Rear P", RobotMap.climberPr);
		addTableEntry("Climber Rear I",RobotMap.climberIr);
		addTableEntry("Climber Rear D", RobotMap.climberDr);
		addTableEntry("Wrist positive limit", RobotMap.wristPositiveLimit);
		addTableEntry("Wrist negitive limit", RobotMap.wristNegitiveLimit);

		addTableEntry("Shoulder positive limit", RobotMap.shoulderPositiveLimit);
		addTableEntry("Shoulder negitive limit", RobotMap.shoulderNegitiveLimit);

		// PID Deadzones
		addTableEntry("Elevator position deadzone", RobotMap.wristPositiveLimit);
		addTableEntry("Shoulder position deadzone", RobotMap.shoulderPositiveLimit);
		addTableEntry("Wrist position deadzone", RobotMap.shoulderPositiveLimit);

		// PID Positions
		addTableEntry("Rocket Top Ball Elevator Height", RobotMap.rocketBallTopElevatorHeight);
		addTableEntry("Rocket Top Ball Shoulder Angle", RobotMap.rocketBallTopShoulderAngle);
		addTableEntry("Rocket Top Ball Wrist Angle", RobotMap.rocketBallTopWristAngle);

		addTableEntry("Rocket Mid Ball Elevator Height", RobotMap.rocketBallMidElevatorHeight);
		addTableEntry("Rocket Mid Ball Shoulder Angle", RobotMap.rocketBallMidShoulderAngle);
		addTableEntry("Rocket Mid Ball Wrist Angle", RobotMap.rocketBallMidWristAngle);

		addTableEntry("Rocket Low Ball Elevator Height", RobotMap.rocketBallLowElevatorHeight);
		addTableEntry("Rocket Low Ball Shoulder Angle", RobotMap.rocketBallLowShoulderAngle);
		addTableEntry("Rocket Low Ball Wrist Angle", RobotMap.rocketBallLowWristAngle);

		addTableEntry("Rocket Top Hatch Elevator Height", RobotMap.rocketHatchTopElevatorHeight);
		addTableEntry("Rocket Top Hatch Shoulder Angle", RobotMap.rocketHatchTopShoulderAngle);
		addTableEntry("Rocket Top Hatch Wrist Angle", RobotMap.rocketHatchTopWristAngle);

		addTableEntry("Rocket Mid Hatch Elevator Height", RobotMap.rocketHatchMidElevatorHeight);
		addTableEntry("Rocket Mid Hatch Shoulder Angle", RobotMap.rocketHatchMidShoulderAngle);
		addTableEntry("Rocket Mid Hatch Wrist Angle", RobotMap.rocketHatchMidWristAngle);

		addTableEntry("Rocket Low Hatch Elevator Height", RobotMap.rocketHatchLowElevatorHeight);
		addTableEntry("Rocket Low Hatch Shoulder Angle", RobotMap.rocketHatchLowShoulderAngle);
		addTableEntry("Rocket Low Hatch Wrist Angle", RobotMap.rocketHatchLowWristAngle);

		addTableEntry("Floor Hatch Elevator Height", RobotMap.hatchFromFloorElevatorHeight);
		addTableEntry("Floor Hatch Shoulder Angle", RobotMap.hatchFromFloorShoulderAngle);
		addTableEntry("Floor Hatch Wrist Angle", RobotMap.hatchFromFloorWristAngle);

		addTableEntry("Floor Ball Elevator Height", RobotMap.ballFromFloorElevatorHeight);
		addTableEntry("Floor Ball Shoulder Angle", RobotMap.ballFromFloorShoulderAngle);
		addTableEntry("Floor Ball Wrist Angle", RobotMap.ballFromFloorWristAngle);

		addTableEntry("Climber Height Target", RobotMap.climberTarget);

		addTableEntry("Drive Max Speed", RobotMap.maxDriveSpeed);
	}

	public void addTableEntry(String key, Number value) {
		NetworkTableEntry tmp = adjustables.getEntry(key);
		if(!tmp.exists()) {
			tmp.setNumber(value);
		}
		tmp.setPersistent();
	}
	
	public void initReadTable() {
		adjustables.addEntryListener(new TableEntryListener() {
			@Override
			public void valueChanged(NetworkTable table, String key, NetworkTableEntry entry, NetworkTableValue value,
					int flags) {
				switch (key) {
				case ("Shoulder P"):
					RobotMap.shoulderP = value.getDouble();
					break;
				case ("Shoulder I"):
					RobotMap.shoulderI = value.getDouble();
					break;
				case ("Shoulder D"):
					RobotMap.shoulderD = value.getDouble();
					break;

				case ("Wrist P"):
					RobotMap.wristP = value.getDouble();
					break;
				case ("Wrist I"):
					RobotMap.wristI = value.getDouble();
					break;
				case ("Wrist D"):
					RobotMap.wristD = value.getDouble();
					break;

				case ("Elevator P"):
					RobotMap.elevatorP = value.getDouble();
					break;
				case ("Elevator I"):
					RobotMap.elevatorI = value.getDouble();
					break;
				case ("Elevator D"):
					RobotMap.elevatorD = value.getDouble();
					break;

				case ("Climber Front P"):
					RobotMap.climberPf = value.getDouble();
					break;
				case ("Climber Front I"):
					RobotMap.climberIf = value.getDouble();
					break;
				case ("Climber Front D"):
					RobotMap.climberDf = value.getDouble();
					break;

				case ("Climber Rear P"):
					RobotMap.climberPr = value.getDouble();
					break;
				case ("Climber Rear I"):
					RobotMap.climberIr = value.getDouble();
					break;
				case ("Climber Rear D"):
					RobotMap.climberDr = value.getDouble();
					break;

				// Angle limits
				case ("Wrist positive limit"):
					RobotMap.wristPositiveLimit = value.getDouble();
					break;
				case ("Wrist negitive limit"):
					RobotMap.wristNegitiveLimit = value.getDouble();
					break;

				case ("Shoulder positive limit"):
					RobotMap.shoulderPositiveLimit = value.getDouble();
					break;
				case ("Shoulder negitive limit"):
					RobotMap.shoulderNegitiveLimit = value.getDouble();
					break;

				// PID Deadzones
				case ("Elevator position deadzone"):
					RobotMap.elevatorPositionDeadzone = (int) value.getDouble();
					break;
				case ("Shoulder position deadzone"):
					RobotMap.shoulderPositionDeadzone = value.getDouble();
					break;
				case ("Wrist position deadzone"):
					RobotMap.wristPositionDeadzone = value.getDouble();
					break;

				// PID Positions
				case ("Rocket Top Ball Elevator Height"):
					RobotMap.rocketBallTopElevatorHeight = (int) value.getDouble();
					break;
				case ("Rocket Top Ball Shoulder Angle"):
					RobotMap.rocketBallTopShoulderAngle = value.getDouble();
					break;
				case ("Rocket Top Ball Wrist Angle"):
					RobotMap.rocketBallTopWristAngle = value.getDouble();
					break;

				case ("Rocket Mid Ball Elevator Height"):
					RobotMap.rocketBallMidElevatorHeight = (int) value.getDouble();
					break;
				case ("Rocket Mid Ball Shoulder Angle"):
					RobotMap.rocketBallMidShoulderAngle = value.getDouble();
					break;
				case ("Rocket Mid Ball Wrist Angle"):
					RobotMap.rocketBallMidWristAngle = value.getDouble();
					break;

				case ("Rocket Low Ball Elevator Height"):
					RobotMap.rocketBallLowElevatorHeight = (int) value.getDouble();
					break;
				case ("Rocket Low Ball Shoulder Angle"):
					RobotMap.rocketBallLowShoulderAngle = value.getDouble();
					break;
				case ("Rocket Low Ball Wrist Angle"):
					RobotMap.rocketBallLowWristAngle = value.getDouble();
					break;

				case ("Rocket Top Hatch Elevator Height"):
					RobotMap.rocketHatchTopElevatorHeight = (int) value.getDouble();
					break;
				case ("Rocket Top Hatch Shoulder Angle"):
					RobotMap.rocketHatchTopShoulderAngle = value.getDouble();
					break;
				case ("Rocket Top Hatch Wrist Angle"):
					RobotMap.rocketHatchTopWristAngle = value.getDouble();
					break;

				case ("Rocket Mid Hatch Elevator Height"):
					RobotMap.rocketHatchMidElevatorHeight = (int) value.getDouble();
					break;
				case ("Rocket Mid Hatch Shoulder Angle"):
					RobotMap.rocketHatchMidShoulderAngle = value.getDouble();
					break;
				case ("Rocket Mid Hatch Wrist Angle"):
					RobotMap.rocketHatchMidWristAngle = value.getDouble();
					break;

				case ("Rocket Low Hatch Elevator Height"):
					RobotMap.rocketHatchLowElevatorHeight = (int) value.getDouble();
					break;
				case ("Rocket Low Hatch Shoulder Angle"):
					RobotMap.rocketHatchLowShoulderAngle = value.getDouble();
					break;
				case ("Rocket Low Hatch Wrist Angle"):
					RobotMap.rocketHatchLowWristAngle = value.getDouble();
					break;

				case ("Floor Hatch Elevator Height"):
					RobotMap.hatchFromFloorElevatorHeight = (int) value.getDouble();
					break;
				case ("Floor Hatch Shoulder Angle"):
					RobotMap.hatchFromFloorShoulderAngle = value.getDouble();
					break;
				case ("Floor Hatch Wrist Angle"):
					RobotMap.hatchFromFloorWristAngle = value.getDouble();
					break;

				case ("Floor Ball Elevator Height"):
					RobotMap.ballFromFloorElevatorHeight = (int) value.getDouble();
					break;
				case ("Floor Ball Shoulder Angle"):
					RobotMap.ballFromFloorShoulderAngle = value.getDouble();
					break;
				case ("Floor Ball Wrist Angle"):
					RobotMap.ballFromFloorWristAngle = value.getDouble();
					break;
				case ("Climber Height Target"):
					RobotMap.climberTarget = (int) value.getDouble();
					break;
				case ("Drive Max Speed"):
					RobotMap.maxDriveSpeed = value.getDouble();
					break;
				}
			}
		}, EntryListenerFlags.kUpdate);
	}
}
