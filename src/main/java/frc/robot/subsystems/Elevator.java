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
    public boolean calibrated = false;
    
    public Elevator(){
        m_elevatorMotor = new WPI_TalonSRX(RobotMap.elevatorMotor);
        i_topCalSwitch = new DigitalInput(RobotMap.elevatorTopSwitch);
        setEncoderPosition(10000);
    }

    public void setSetpoint(int setpoint) {
    	this.setpoint = setpoint;
	}
    
    public DigitalInput getTopSwitch() {
        return i_topCalSwitch;
        //SmartDashboard.putNumber("Elevator Limit Switch", i_topCalSwitch);
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
    	//SmartDashboard.putNumber("Elevator pidOut", pidOut);
    }
    
    public void drive(double speed) {
    	double speed2;
        
        if(speed > .75) {//change the values if different max speed is desired 
    		speed2 = .75;
    	} else if(speed < -.75) {
    		speed2 = -.75;
        } else {
    		speed2 = speed;
    	}
        
        //speed2 = speed;
        if(speed2 < 0 && !i_topCalSwitch.get()) { //If going up and top cal switch is triggered
    		
    	} else {
    		m_elevatorMotor.set(speed2);
    	}
    	
        //SmartDashboard.putNumber("Elevator speed", speed2);
        //SmartDashboard.putNumber("Elevator Limit Switch", i_topCalSwitch);
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