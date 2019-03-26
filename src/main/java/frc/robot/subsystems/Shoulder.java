package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.DriveShoulder;

public class Shoulder extends Subsystem {
    private CANSparkMax m_armMotor;
	private AnalogInput i_pot;
    private double setpoint = 72;
	public double integral = 0, previous_error;
    private double pidOut = 0;
	private double P, I, D;
	public boolean positionAchieved = true;
    
	public double maxamp = 0;
	
	public Shoulder() {
        m_armMotor = new CANSparkMax(RobotMap.ShoulderMotor, MotorType.kBrushless);
        i_pot = new AnalogInput(RobotMap.shoulderPotentiometer);
    }

	public void resetSetpoint() {
		
	}
	
	public long getAccumulator() {
		return i_pot.getAccumulatorValue();
	}
	
    public void setSetpoint(double voltage) {
        this.setpoint = voltage;
	}
    
    public void setSetpointAngle(double angle) {
        setSetpoint(angle / 72);
    }

    public void setPID(double p, double i, double d) {
    	P = p;
    	I = i;
    	D = d;
    }
    
    public double getAngleVoltage() {
    	return i_pot.getVoltage(); //* 72; //72 degrees per volt
    }
    
    public double getAngle() {
    	return i_pot.getVoltage() * 72;
    }
    
public double getSetpointAngle() {
    return setpoint * 72;
}

    public void PID() {
    	double error = setpoint - getAngleVoltage();
    	this.integral += (error * .02); //no idea why .02
    	double derivative = (error - this.previous_error) / .02;
    	pidOut = P * error + I*integral + D*derivative;
    	previous_error = error;
    }
    
    public void drive(double value) {
        double v2;
        if(value > .2) {
            //m_armMotor.set(.5);
            v2 = -.2;
        } else if(value < -.5) {
            //m_armMotor.set(-.5);
            v2 = .5;
        } else {
            //m_armMotor.set(-value);
            v2 = -value;
        }
    	//if positive v2 = up
        if(v2 > 0 && getAngle() < RobotMap.shoulderPositiveLimit)  {//Clockwise (up) and top (20?)
        
        } else if(v2 < 0 && getAngle() > RobotMap.shoulderNegitiveLimit) {//Counterclockwise (down) and bottom (300???)
        	
        } else {
        	m_armMotor.set(v2);
        }
        if(m_armMotor.getOutputCurrent() > maxamp) {
        	maxamp = m_armMotor.getOutputCurrent();
        }
        //SmartDashboard.putNumber("Shoulder amperage", maxamp);
    	//SmartDashboard.putNumber("Shoulder speed", Double.valueOf(String.format("%.5f", v2)));
    }
    
	@Override
	protected void initDefaultCommand() {
		//setDefaultCommand(new DriveShoulder()); //commented out while testing, dont want it to fly when we enable
	}

	public void drivePID() {
		PID();
		drive(pidOut);
		//SmartDashboard.putNumber("Shoulder pidOut", pidOut);
	}

	public double getSetpoint() {
		return setpoint;
	}
   
}