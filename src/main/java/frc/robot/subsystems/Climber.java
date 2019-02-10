/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Climber extends Subsystem {
	private WPI_TalonSRX m_ClimberFront;
	private WPI_TalonSRX m_ClimberBack;
	public DigitalInput m_FLimit, m_BLimit;

	public double integralf = 0, previous_errorf;
	public double integralr = 0, previous_errorr;
	private double pidOutf = 0, pidOutr = 0;
	private double Pf, If, Df, Pr, Ir, Dr;
	public boolean positionAchieved = true;
	private int setpoint;

	// constructor
	public Climber() {
		m_ClimberFront = new WPI_TalonSRX(RobotMap.climbMotorFront);
		m_ClimberBack = new WPI_TalonSRX(RobotMap.climbMotorRear);
		m_FLimit = new DigitalInput(RobotMap.climberFSwitch);
		m_BLimit = new DigitalInput(RobotMap.climberBSwitch);
	}

	public void setSetpoint(int setpoint) {
		this.setpoint = setpoint;
	}

	public int getFrontEncoderPosition() {
		return m_ClimberFront.getSensorCollection().getQuadraturePosition();
	}

	public int getBackEncoderPosition() {
		return m_ClimberBack.getSensorCollection().getQuadraturePosition();
	}

	public void setPID(double pf, double iF, double df, double pr, double ir, double dr) {
		Pf = pf;
		If = iF;
		Df = df;
		Pr = pr;
		Ir = ir;
		Dr = dr;
	}

	public int getSetpoint() {
		return setpoint;
	}

	public void PID() {
		double errorf = setpoint - getFrontEncoderPosition();
		this.integralf += (errorf * .02);
		double derivativef = (errorf - this.previous_errorf);
		pidOutf = Pf * errorf + If * integralf + Df * derivativef;
		previous_errorf = errorf;

		double errorr = getFrontEncoderPosition() - getBackEncoderPosition();
		this.integralr += (errorr * .02);
		double derivativer = (errorr - this.previous_errorr);
		pidOutr = Pr * errorr + Ir * integralr + Dr * derivativer;
		previous_errorr = errorr;
	}

	public void drivePID() {
		PID();
		runClimberFront(pidOutf);
		runClimberBack(pidOutr);
		SmartDashboard.putNumber("Climber pidoutf", pidOutf);
		SmartDashboard.putNumber("Climber pidoutr", pidOutr);
	}

	public void runClimberUp(double moveValue) {
		m_ClimberFront.set(moveValue);
		m_ClimberBack.set(moveValue);
		SmartDashboard.putNumber("Movevalue climber", moveValue);
	}

	public void runClimberFront(double moveValue) {
		if (checkFrontSwitch() && moveValue < 0) {

		} else {
			m_ClimberFront.set(moveValue);
		}
		// SmartDashboard.putNumber("Climber pidoutf", pidOutf);

	}

	public void runClimberBack(double moveValue) {
		if (checkBackSwitch() && moveValue < 0) {

		} else {
			m_ClimberBack.set(moveValue);
		}
		// SmartDashboard.putNumber("Climber pidoutr", pidOutr);
	}

	public boolean checkFrontSwitch() {
		return !m_FLimit.get(); // false if open, true if closed
	}

	public boolean checkBackSwitch() {
		return !m_BLimit.get();
	}

	public void setFrontEncoderZero() {
		m_ClimberFront.getSensorCollection().setQuadraturePosition(0, 0);
	}

	public void setBackEncoderZero() {
		m_ClimberBack.getSensorCollection().setQuadraturePosition(0, 0);
	}

	public void setEncodersZero() {
		setFrontEncoderZero();
		setBackEncoderZero();
	}

	public void setFrontEncoderPosition(int position) {
		m_ClimberFront.getSensorCollection().setQuadraturePosition(position, 0);
	}

	public void setBackEncoderPosition(int position) {
		m_ClimberBack.getSensorCollection().setQuadraturePosition(position, 0);
	}

	@Override
	public void initDefaultCommand() {
		// does not need init default
	}

}
