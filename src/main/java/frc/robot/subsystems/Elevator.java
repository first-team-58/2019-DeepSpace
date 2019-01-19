package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Elevator extends Subsystem{
    private WPI_TalonSRX m_elevatorMotor;
    public double P = 0.05, I = 0.01, D = 0;
    double integral, previous_error;
	public double setpoint = 0;
	double motorValue;


    public Elevator(){

        m_elevatorMotor = new WPI_TalonSRX(RobotMap.elevatorMotor);

    }

    public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
	}

	public void PID() {
		double error = setpoint;
		this.integral += (error * .02);
		double derivative = (error - this.previous_error) / .02;
		this.motorValue = P * error + I * this.integral + D * derivative;
		if(error < 2 && previous_error < 2);
    }
    
    protected void initDefaultCommand(){

    }
}