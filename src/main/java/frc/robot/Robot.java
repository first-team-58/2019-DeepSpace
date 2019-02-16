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
  public static DriveTrain m_drivetrain = new DriveTrain();
  public static Gripper m_Gripper = new Gripper ();
  public static Shoulder m_Shoulder = new Shoulder();
  public static Climber m_Climber = new Climber ();
  public static Elevator m_Elevator = new Elevator ();
  public static Wrist m_Wrist = new Wrist ();
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
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_oi = new OI();
    
    //ahrs = new AHRS().Port.kMXP); /* Alternatives:  SPI.Port.kMXP, I2C.Port.kMXP or SerialPort.Port.kUSB */
    //m_chooser.setDefaultOption("Default Auto", new ExampleCommand());
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", m_chooser);
    ahrs = new AHRS(SPI.Port.kMXP);
    m_drivetrain.setGyro(ahrs);
    SmartDashboard.putNumber("P", Robot.m_drivetrain.P);
    SmartDashboard.putNumber("I", Robot.m_drivetrain.I);
    SmartDashboard.putNumber("D", Robot.m_drivetrain.D);
    SmartDashboard.putNumber("volts", test);
    SmartDashboard.putNumber("angle setpoint", testangle);
    //t = new AnalogInput(0);
    opcon = new XboxController(m_oi.operator.getPort());
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
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
   SmartDashboard.putNumber("Setpoint Shoulder", Robot.m_Shoulder.getSetpointAngle());
   SmartDashboard.putNumber("Wrist angle", Robot.m_Wrist.getAngleDegrees());
   SmartDashboard.putNumber("Wrist angle2", Robot.m_Wrist.getAngleRaw());

  // if(Math.abs(Robot.m_oi.operator.getRawAxis(5)) > .2) {
 //    double newangle = Robot.m_Shoulder.getSetpointAngle() + Robot.m_oi.operator.getRawAxis(5);
 //    double newvoltage = newangle / 72;
   // Scheduler.getInstance().add(new UpdateShoulderSetpoint(SmartDashboard.getNumber("ShoulderAngle", newvoltage)));
   //}
 //  if(Robot.m_oi.driver.getRawButton(RobotMap.aButton)) {
//	   //Scheduler.getInstance().add(new PIDDrive());
 //  }
 //  if(Robot.m_oi.driver.getRawButton(RobotMap.bButton)) {
     //Robot.m_drivetrain.positionAchieved = true;
  // }
  // if(Robot.m_oi.operator.getRawButton(RobotMap.yButton)) {
//	   Robot.m_Shoulder.positionAchieved = true;
  // }
  // if(Robot.m_oi.operator.getRawButton(RobotMap.xButton)) {
    // Scheduler.getInstance().add(new UpdateShoulderSetpoint(SmartDashboard.getNumber("ShoulderAngle", testangle)));
	   //Scheduler.getInstance().add(new DriveShoulder(SmartDashboard.getNumber("ShoulderAngle", testangle))); //arbitrary number for now
   //}
  // if(Robot.m_oi.operator.getRawButton(8)) {
     	  // Scheduler.getInstance().add(new DriveShoulder(SmartDashboard.getNumber("ShoulderAngle", testangle))); //arbitrary number for now
	   	//Robot.m_Climber.setEncodersZero();
	  // Scheduler.getInstance().add(new CalibrateElevator());
  // }
   //if(Robot.m_oi.driver.getRawButton(RobotMap.selectButton)) {
	  // Scheduler.getInstance().add(new CalibrateClimber());
  // }
  // if(Robot.m_oi.driver.getRawButton(RobotMap.startButton)) {
	//   Scheduler.getInstance().add(new DriveClimber(22000));
  // }
   
   //if(Robot.m_oi.operator.getRawButton(RobotMap.aButton)) {
	   //Scheduler.getInstance().add(new DriveWrist(200));
	   //Robot.m_Elevator.setEncoderPosition(RobotMap.elevatorTopPosition);
	   //Scheduler.getInstance().add(new DriveElevator(8000));
	   //Scheduler.getInstance().add(new DriveClimber(25000));
      //Robot.m_Elevator.drive(.25);
	  // Scheduler.getInstance().add(new RocketTopHatch());
   // }
  // if(Robot.m_oi.operator.getRawButton(RobotMap.bButton)) {
	   //Scheduler.getInstance().add(new UpdateWristSetpoint(100));
	   //Scheduler.getInstance().add(new UpdateElevatorSetpoint(5000));
	   //Scheduler.getInstance().add(new CalibrateElevator());
	  // Scheduler.getInstance().add(new UpdateClimberSetpoint(100));
    //Robot.m_Elevator.drive(-.25);
 // }
  // if(Robot.m_oi.operator.getRawButton(5)) {
	   //if(Math.abs(Robot.m_oi.operator.getRawAxis(1)) > .2) {
		   //Robot.m_Shoulder.drive(Robot.m_oi.operator.getRawAxis(1));
		//   SmartDashboard.putNumber("Controller output", Robot.m_oi.operator.getRawAxis(1));
		   //Robot.m_Wrist.drive(Robot.m_oi.operator.getRawAxis(1));
		//   Robot.m_Elevator.drive(Robot.m_oi.operator.getRawAxis(1));
		   //Robot.m_Climber.runClimberUp(Robot.m_oi.operator.getRawAxis(1));
	  // } else {
		   //Robot.m_Shoulder.drive(0);
		   //Robot.m_Wrist.drive(0);
		 //  Robot.m_Elevator.drive(0);
		   //Robot.m_Climber.runClimberUp(0);
	//   }
  // }
   
   //IDK how to do this in OI properly yet
   if(Robot.m_oi.driver.getRawAxis(2) > .1) {
	   Robot.m_Climber.runClimberFront(Robot.m_oi.driver.getRawAxis(2));
   }
   if(Robot.m_oi.driver.getRawAxis(3) > .1) {
	   Robot.m_Climber.runClimberBack(Robot.m_oi.driver.getRawAxis(3));
   }
   /*
   if(Robot.m_oi.driver.getRawButton(RobotMap.bButton)) {
	   Robot.m_Climber.runClimberUp(0);
   }
   */
   if(m_oi.operator.getPOV() == 0) {//up on pov hat
	   if(cmdTBLY == 3) {
		   System.out.println("At top of command table, can't go up any more");
	   } else {
		   cmdTBLY++;
	   }
	   Scheduler.getInstance().add(new PositionPicker(cmdTBLX, cmdTBLY));
   }
   
   if(m_oi.operator.getPOV() == 90) {//right on pov hat, change to ball
	   if(cmdTBLY == 1) {
		   System.out.println("At right of command table, can't go right any more");
	   } else {
		   cmdTBLX++;
	   }
	   Scheduler.getInstance().add(new PositionPicker(cmdTBLX, cmdTBLY));
   }
   
   if(m_oi.operator.getPOV() == 180) {//down on pov hat
	   if(cmdTBLY == 0) {
		   System.out.println("At bottom of command table, can't go down any more");
	   } else {
		   cmdTBLY--;
	   }
	   Scheduler.getInstance().add(new PositionPicker(cmdTBLX, cmdTBLY));
   }
   
   if(m_oi.operator.getPOV() == 270) {//left on pov hat, change to hatch
	   if(cmdTBLY == 0) {
		   System.out.println("At left of command table, can't go left any more");
	   } else {
		   cmdTBLX--;
	   }
	   Scheduler.getInstance().add(new PositionPicker(cmdTBLX, cmdTBLY));
   }
   
   SmartDashboard.putNumber("Climber front encoder", Robot.m_Climber.getFrontEncoderPosition());
   SmartDashboard.putNumber("Climber rear encoder", Robot.m_Climber.getBackEncoderPosition());
   SmartDashboard.putBoolean("Elevator limit", Robot.m_Elevator.getTopSwitch().get());
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
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
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
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
  
  public void pushTable() {
	  //PID Values
	  NetworkTableEntry sP = adjustables.getEntry("Shoulder P");
	  sP.setNumber(RobotMap.shoulderP);
	  NetworkTableEntry sI = adjustables.getEntry("Shoulder I");
	  sI.setNumber(RobotMap.shoulderI);
	  NetworkTableEntry sD = adjustables.getEntry("Shoulder D");
	  sD.setNumber(RobotMap.shoulderD);
	  
	  NetworkTableEntry wP = adjustables.getEntry("Wrist P");
	  wP.setNumber(RobotMap.wristP);
	  NetworkTableEntry wI = adjustables.getEntry("Wrist I");
	  wI.setNumber(RobotMap.wristI);
	  NetworkTableEntry wD = adjustables.getEntry("Wrist D");
	  wD.setNumber(RobotMap.wristD);
	  
	  NetworkTableEntry eP = adjustables.getEntry("Elevator P");
	  eP.setNumber(RobotMap.elevatorP);
	  NetworkTableEntry eI = adjustables.getEntry("Elevator I");
	  eI.setNumber(RobotMap.elevatorI);
	  NetworkTableEntry eD = adjustables.getEntry("Elevator D");
	  eD.setNumber(RobotMap.elevatorD);
	  
	  NetworkTableEntry cFP = adjustables.getEntry("Climber Front P");
	  cFP.setNumber(RobotMap.climberPf);
	  NetworkTableEntry cFI = adjustables.getEntry("Climber Front I");
	  cFI.setNumber(RobotMap.climberIf);
	  NetworkTableEntry cFD = adjustables.getEntry("Climber Front D");
	  cFD.setNumber(RobotMap.climberDf);
	  
	  NetworkTableEntry cRP = adjustables.getEntry("Climber Rear P");
	  cRP.setNumber(RobotMap.climberPr);
	  NetworkTableEntry cRI = adjustables.getEntry("Climber Rear I");
	  cRI.setNumber(RobotMap.climberIr);
	  NetworkTableEntry cRD = adjustables.getEntry("Climber Rear D");
	  cRD.setNumber(RobotMap.climberDr);
	  
	  //Angle limits
	  NetworkTableEntry wPL = adjustables.getEntry("Wrist positive limit");
	  wPL.setNumber(RobotMap.wristPositiveLimit);
	  NetworkTableEntry wNL = adjustables.getEntry("Wrist negitive limit");
	  wNL.setNumber(RobotMap.wristNegitiveLimit);
	  
	  NetworkTableEntry sPL = adjustables.getEntry("Shoulder positive limit");
	  sPL.setNumber(RobotMap.shoulderPositiveLimit);
	  NetworkTableEntry sNL = adjustables.getEntry("Shoulder negitive limit");
	  sNL.setNumber(RobotMap.shoulderNegitiveLimit);
	  
	  //PID Deadzones
	  NetworkTableEntry ePD = adjustables.getEntry("Elevator position deadzone");
	  ePD.setNumber(RobotMap.wristPositiveLimit);
	  NetworkTableEntry sPD = adjustables.getEntry("Shoulder position deadzone");
	  sPD.setNumber(RobotMap.shoulderPositiveLimit);
	  NetworkTableEntry wPD = adjustables.getEntry("Wrist position deadzone");
	  wPD.setNumber(RobotMap.shoulderPositiveLimit);
	  
	  //PID Positions
	  NetworkTableEntry rBTE = adjustables.getEntry("Rocket Top Ball Elevator Height");
	  rBTE.setNumber(RobotMap.rocketBallTopElevatorHeight);
	  NetworkTableEntry rBTS = adjustables.getEntry("Rocket Top Ball Shoulder Angle");
	  rBTS.setNumber(RobotMap.rocketBallTopShoulderAngle);
	  NetworkTableEntry rBTW = adjustables.getEntry("Rocket Top Ball Wrist Angle");
	  rBTW.setNumber(RobotMap.rocketBallTopWristAngle);
	  
	  NetworkTableEntry rBME = adjustables.getEntry("Rocket Mid Ball Elevator Height");
	  rBME.setNumber(RobotMap.rocketBallMidElevatorHeight);
	  NetworkTableEntry rBMS = adjustables.getEntry("Rocket Mid Ball Shoulder Angle");
	  rBMS.setNumber(RobotMap.rocketBallMidShoulderAngle);
	  NetworkTableEntry rBMW = adjustables.getEntry("Rocket Mid Ball Wrist Angle");
	  rBMW.setNumber(RobotMap.rocketBallMidWristAngle);
	  
	  NetworkTableEntry rBLE = adjustables.getEntry("Rocket Low Ball Elevator Height");
	  rBLE.setNumber(RobotMap.rocketBallLowElevatorHeight);
	  NetworkTableEntry rBLS = adjustables.getEntry("Rocket Low Ball Shoulder Angle");
	  rBLS.setNumber(RobotMap.rocketBallLowShoulderAngle);
	  NetworkTableEntry rBLW = adjustables.getEntry("Rocket Low Ball Wrist Angle");
	  rBLW.setNumber(RobotMap.rocketBallLowWristAngle);
	  
	  NetworkTableEntry rHTE = adjustables.getEntry("Rocket Top Hatch Elevator Height");
	  rHTE.setNumber(RobotMap.rocketHatchTopElevatorHeight);
	  NetworkTableEntry rHTS = adjustables.getEntry("Rocket Top Hatch Shoulder Angle");
	  rHTS.setNumber(RobotMap.rocketHatchTopShoulderAngle);
	  NetworkTableEntry rHTW = adjustables.getEntry("Rocket Top Hatch Wrist Angle");
	  rHTW.setNumber(RobotMap.rocketHatchTopWristAngle);
	  
	  NetworkTableEntry rHME = adjustables.getEntry("Rocket Mid Hatch Elevator Height");
	  rHME.setNumber(RobotMap.rocketHatchMidElevatorHeight);
	  NetworkTableEntry rHMS = adjustables.getEntry("Rocket Mid Hatch Shoulder Angle");
	  rHMS.setNumber(RobotMap.rocketHatchMidShoulderAngle);
	  NetworkTableEntry rHMW = adjustables.getEntry("Rocket Mid Hatch Wrist Angle");
	  rHMW.setNumber(RobotMap.rocketHatchMidWristAngle);
	  
	  NetworkTableEntry rHLE = adjustables.getEntry("Rocket Low Hatch Elevator Height");
	  rHLE.setNumber(RobotMap.rocketHatchLowElevatorHeight);
	  NetworkTableEntry rHLS = adjustables.getEntry("Rocket Low Hatch Shoulder Angle");
	  rHLS.setNumber(RobotMap.rocketHatchLowShoulderAngle);
	  NetworkTableEntry rHLW = adjustables.getEntry("Rocket Low Hatch Wrist Angle");
	  rHLW.setNumber(RobotMap.rocketHatchLowWristAngle);
	  
	  NetworkTableEntry fHE = adjustables.getEntry("Floor Hatch Elevator Height");
	  fHE.setNumber(RobotMap.hatchFromFloorElevatorHeight);
	  NetworkTableEntry fHS = adjustables.getEntry("Floor Hatch Shoulder Angle");
	  fHS.setNumber(RobotMap.hatchFromFloorShoulderAngle);
	  NetworkTableEntry fHW = adjustables.getEntry("Floor Hatch Wrist Angle");
	  fHW.setNumber(RobotMap.hatchFromFloorWristAngle);
	  
	  NetworkTableEntry fBE = adjustables.getEntry("Floor Ball Elevator Height");
	  fBE.setNumber(RobotMap.ballFromFloorElevatorHeight);
	  NetworkTableEntry fBS = adjustables.getEntry("Floor Ball Shoulder Angle");
	  fBS.setNumber(RobotMap.ballFromFloorShoulderAngle);
	  NetworkTableEntry fBW = adjustables.getEntry("Floor Ball Wrist Angle");
	  fBW.setNumber(RobotMap.ballFromFloorWristAngle);
	  
	  NetworkTableEntry cPT = adjustables.getEntry("Climber Height Target");
	  cPT.setNumber(RobotMap.climberTarget);
  }
  
  public void initReadTable() {
	  adjustables.addEntryListener(new TableEntryListener() {
		@Override
		public void valueChanged(NetworkTable table, String key, NetworkTableEntry entry, NetworkTableValue value,
				int flags) {
			switch(key) {
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
			
			  //Angle limits
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
			
			  //PID Deadzones
			case ("Elevator position deadzone"):
				RobotMap.elevatorPositionDeadzone = (int) value.getDouble();
			break;
			case ("Shoulder position deadzone"):
				RobotMap.shoulderPositionDeadzone = value.getDouble();
			break;
			case ("Wrist position deadzone"):
				RobotMap.wristPositionDeadzone = value.getDouble();
			break;
			
			  //PID Positions
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
			}
		}
	  }, EntryListenerFlags.kUpdate);
  }
}
