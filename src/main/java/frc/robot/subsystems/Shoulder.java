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
	private double integral = 0, previous_error;
    private double pidOut = 0;
	private double P, I, D;
    
	public Shoulder() {
        m_armMotor = new CANSparkMax(RobotMap.ShoulderMotor, MotorType.kBrushless);
        i_pot = new AnalogInput(RobotMap.shoulderPotentiometer);
        
    }

    public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
	}
    
    public void setPID(double p, double i, double d) {
    	P = p;
    	I = i;
    	D = d;
    }
    
    private double getAngle() {
    	return i_pot.getVoltage() * 72; //72 degrees per volt
    }
    
    public void PID() {
    	double error = setpoint - getAngle();
    	this.integral += (error * .02); //no idea why .02
    	double derivative = (error - this.previous_error) / .02;
    	pidOut = P*error + I*integral + D*derivative;
    	previous_error = error;
    }
    
    public void drive(double value) {
    	m_armMotor.set(value);
    	SmartDashboard.putNumber("test", value);
    }
    
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveShoulder());
	}

	public void drivePID() {
		PID();
		drive(pidOut);
	}

	public double getSetpoint() {
		return setpoint;
	}
   
}