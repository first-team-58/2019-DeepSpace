package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Elevator extends Subsystem{
    private WPI_TalonSRX m_elevatorMotor;
    private DigitalInput i_topCalSwitch;
    private int setpoint = 6000;
    
    public double integral = 0, previous_error;
    private double pidOut = 0;
    private double P, I, D;
    public boolean positionAchieved = true;
    
    public Elevator(){
        m_elevatorMotor = new WPI_TalonSRX(RobotMap.elevatorMotor);
        i_topCalSwitch = new DigitalInput(RobotMap.elevatorTopSwitch);
    }

    public void setSetpoint(int setpoint) {
    	this.setpoint = setpoint;
	}
    
    public DigitalInput getTopSwitch() {
    	return i_topCalSwitch;
    }
    
    public int getEncoderPosition() {
    	return m_elevatorMotor.getSensorCollection().getQuadraturePosition();
    }
    
    public void setPID(double p, double i, double d) {
    	P = p;
    	I = i;
    	D = d;
    }
    
    public int getSetpoint() {
    	return setpoint;
    }
    
    public void PID() {
    	double error =  getEncoderPosition() - setpoint;
    	this.integral += (error * .02);
    	double derivative = (error - this.previous_error) / .02;
    	pidOut = P*error + I*integral +D*derivative;
    	previous_error = error;
    }
    
    public void drivePID() {
    	PID();
    	drive(pidOut);
    	SmartDashboard.putNumber("Elevator pidOut", pidOut);
    }
    
    public void drive(double speed) {
    	double speed2;
    	if(speed > .5) {
    		speed2 = .5;
    	} else if(speed < -.5) {
    		speed2 = -.5;
    	} else {
    		speed2 = speed;
    	}
    	if(speed2 < 0 && !i_topCalSwitch.get()) { //If going up and top cal switch is triggered
    		
    	} else {
    		m_elevatorMotor.set(speed2);
    	}
    	
    	SmartDashboard.putNumber("Elevator speed", speed2);
    }
    
    public void setEncoderZero() {
    	m_elevatorMotor.getSensorCollection().setQuadraturePosition(0, 0);
    }
    
    public void setEncoderPosition(int pos) {
    	m_elevatorMotor.getSensorCollection().setQuadraturePosition(pos, 0);
    }
    
    protected void initDefaultCommand(){

    }
}