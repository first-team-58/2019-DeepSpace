package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Arm extends Subsystem {
	double setpointAngle;
	AnalogPotentiometer m_armPot;
	TalonSRX m_armMotor;
	
	double integral, previous_error;
	public double setpoint = 0;
	double motorDriveValue;
	
	public double P = 0.05, I = 0.01, D = 0;
	
	
	public Arm() {
		m_armMotor = new TalonSRX(RobotMap.armMotor);
		
		m_armPot = new AnalogPotentiometer(RobotMap.armPot);
	}

	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
	}

	public void PID() {
		double error = setpoint;
		this.integral += (error * .02);
		double derivative = (error - this.previous_error) / .02;
		this.motorDriveValue = P * error + I * this.integral + D * derivative;
		if(error < 2 && previous_error < 2);
	}
	
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}