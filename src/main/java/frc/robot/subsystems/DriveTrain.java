/*----------------------------------------------------------------------------*/
/* West Coast Drive                                                           */
/* written 1/12 by EBD                                                        */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

//import edu.wpi.first.wpilibj.Solenoid;
//import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.Drive;

//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * West Coast Drive for Pneumatic Wheels
 */
public class DriveTrain extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private WPI_TalonSRX m_FrontRightMotor;
	private WPI_TalonSRX m_FrontLeftMotor;
	private WPI_VictorSPX m_RightFollower;
	private WPI_VictorSPX m_LeftFollower;
	private DifferentialDrive m_drive;
	//private Solenoid m_SpeedSolenoid;
	private AHRS gyro;

	public double P = 0.05, I = 0.01, D = 0;

	double integral, previous_error;
	public double setpoint = 0;
	double rcw;

	public boolean positionAchieved = false;

	public DriveTrain() {

		// create motor instances
		m_FrontLeftMotor = new WPI_TalonSRX(4);
		m_FrontRightMotor = new WPI_TalonSRX(2);
		m_RightFollower = new WPI_VictorSPX(3);
		m_LeftFollower = new WPI_VictorSPX(5);
		//m_SpeedSolenoid = new Solenoid(1);

		// congifure victoes to follow talons
		m_RightFollower.follow(m_FrontRightMotor);
		m_LeftFollower.follow(m_FrontLeftMotor);

		// create drive object
		m_drive = new DifferentialDrive( m_FrontRightMotor, m_FrontLeftMotor);
		// setGyro()
	}

	public void setGyro(AHRS gyro) {
		this.gyro = gyro;
	}

	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint + gyro.getAngle();
	}

	public void PID() {
		double error = setpoint - gyro.getAngle();
		this.integral += (error * .02);
		double derivative = (error - this.previous_error) / .02;
		this.rcw = P * error + I * this.integral + D * derivative;
		if(error < 2 && previous_error < 2);
	}

	public void pidDrive(double speed) {
		PID();
		drive(speed, rcw);
	}

	// manual drive the robot
	public void drive(double moveValue, double rotateValue) {
		if(moveValue > RobotMap.maxDriveSpeed) {
			moveValue = RobotMap.maxDriveSpeed;
		} else if(moveValue < -RobotMap.maxDriveSpeed) {
			moveValue = -RobotMap.maxDriveSpeed;
		}
		
		if(rotateValue > RobotMap.maxDriveSpeed) {
			rotateValue = RobotMap.maxDriveSpeed;
		} else if(rotateValue < -RobotMap.maxDriveSpeed) {
			rotateValue = -RobotMap.maxDriveSpeed;
		}
		m_drive.arcadeDrive(moveValue, rotateValue);

	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new Drive());
	}

}