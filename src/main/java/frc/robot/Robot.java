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
import frc.robot.commands.RocketTopHatch;
import frc.robot.commands.UpdateClimberSetpoint;
import frc.robot.commands.UpdateElevatorSetpoint;
import frc.robot.commands.UpdateShoulderSetpoint;
import frc.robot.commands.UpdateWristSetpoint;
import frc.robot.subsystems.*;
import edu.wpi.first.networktables.*;
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

   if(Math.abs(Robot.m_oi.operator.getRawAxis(5)) > .2) {
     double newangle = Robot.m_Shoulder.getSetpointAngle() + Robot.m_oi.operator.getRawAxis(5);
     double newvoltage = newangle / 72;
    Scheduler.getInstance().add(new UpdateShoulderSetpoint(SmartDashboard.getNumber("ShoulderAngle", newvoltage)));
   }
   if(Robot.m_oi.driver.getRawButton(RobotMap.aButton)) {
	   //Scheduler.getInstance().add(new PIDDrive());
   }
   if(Robot.m_oi.driver.getRawButton(RobotMap.bButton)) {
     //Robot.m_drivetrain.positionAchieved = true;
   }
   if(Robot.m_oi.operator.getRawButton(RobotMap.yButton)) {
	   Robot.m_Shoulder.positionAchieved = true;
   }
   if(Robot.m_oi.operator.getRawButton(RobotMap.xButton)) {
    // Scheduler.getInstance().add(new UpdateShoulderSetpoint(SmartDashboard.getNumber("ShoulderAngle", testangle)));
	   //Scheduler.getInstance().add(new DriveShoulder(SmartDashboard.getNumber("ShoulderAngle", testangle))); //arbitrary number for now
   }
   if(Robot.m_oi.operator.getRawButton(8)) {
     	  // Scheduler.getInstance().add(new DriveShoulder(SmartDashboard.getNumber("ShoulderAngle", testangle))); //arbitrary number for now
	   	//Robot.m_Climber.setEncodersZero();
	   Scheduler.getInstance().add(new CalibrateElevator());
   }
   if(Robot.m_oi.driver.getRawButton(7)) {
	   Scheduler.getInstance().add(new CalibrateClimber());
   }
   if(Robot.m_oi.driver.getRawButton(8)) {
	   Scheduler.getInstance().add(new DriveClimber(22000));
   }
   if(Robot.m_oi.operator.getRawButton(RobotMap.aButton)) {
	   //Scheduler.getInstance().add(new DriveWrist(200));
	   //Robot.m_Elevator.setEncoderPosition(RobotMap.elevatorTopPosition);
	   //Scheduler.getInstance().add(new DriveElevator(8000));
	   //Scheduler.getInstance().add(new DriveClimber(25000));
      //Robot.m_Elevator.drive(.25);
	   Scheduler.getInstance().add(new RocketTopHatch());
    }
   if(Robot.m_oi.operator.getRawButton(RobotMap.bButton)) {
	   //Scheduler.getInstance().add(new UpdateWristSetpoint(100));
	   //Scheduler.getInstance().add(new UpdateElevatorSetpoint(5000));
	   //Scheduler.getInstance().add(new CalibrateElevator());
	  // Scheduler.getInstance().add(new UpdateClimberSetpoint(100));
    //Robot.m_Elevator.drive(-.25);
  }
   if(Robot.m_oi.operator.getRawButton(5)) {
	   if(Math.abs(Robot.m_oi.operator.getRawAxis(1)) > .2) {
		   //Robot.m_Shoulder.drive(Robot.m_oi.operator.getRawAxis(1));
		   SmartDashboard.putNumber("Controller output", Robot.m_oi.operator.getRawAxis(1));
		   //Robot.m_Wrist.drive(Robot.m_oi.operator.getRawAxis(1));
		   Robot.m_Elevator.drive(Robot.m_oi.operator.getRawAxis(1));
		   //Robot.m_Climber.runClimberUp(Robot.m_oi.operator.getRawAxis(1));
	   } else {
		   //Robot.m_Shoulder.drive(0);
		   //Robot.m_Wrist.drive(0);
		   Robot.m_Elevator.drive(0);
		   //Robot.m_Climber.runClimberUp(0);
	   }
   }
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
}
