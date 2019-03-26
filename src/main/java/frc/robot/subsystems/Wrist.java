package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Wrist extends Subsystem {
	public WPI_TalonSRX m_wristMotor;
	private double setpoint = 200; // default angle
	public double integral = 0, previous_error;
	private double pidOut = 0;
	private double P, I, D;
	public boolean positionAchieved = true;
	public AnalogInput i_pot;

	public Wrist() {
		m_wristMotor = new WPI_TalonSRX(RobotMap.wristMotor);
		i_pot = new AnalogInput(RobotMap.wristPotentiometer);
	}

	public void setSetpoint(double voltage) {
		//if (voltage > 3.0) {
			//this.setpoint = 3.0;
		//} else {
			this.setpoint = voltage;
		//}
		// SmartDashboard.putNumber("Wrist setpoint", this.setpoint);
	}

	public void setSetpointAngle(double angle) {
		setSetpoint(angle / 72.0);
	}

	public void setPID(double p, double i, double d) {
		P = p;
		I = i;
		D = d;
	}

	public double getSetpointAngle() {
		//return setpoint * 109.09;
		return setpoint * 72.0;
	}

	public void PID() {
		double error = getVoltage() - setpoint;
		this.integral += (error * .02);
		double derivative = (error - this.previous_error) / .02;
		pidOut = P * error + I * integral + D * derivative;
		previous_error = error;
	}

	public void drive(double value) {
		double v2;
		if (value > .75) {
			// m_wristMotor.set(.5);
			v2 = .75;
		} else if (value < -.5) {
			// m_wristMotor.set(-.5);
			v2 = -.5;
		} else {
			// m_wristMotor.set(value);
			v2 = value;
		}
		// positive v2 = counterclockwise (From right side)
		if (v2 > 0 && getAngleDegrees() > RobotMap.wristPositiveLimit) { // wrist rotating counterclocksise | will not
																			// turn ccw when angle > 300
		} else if (v2 < 0 && getAngleDegrees() < RobotMap.wristNegitiveLimit) { // wrist rotating clockwise | will not
																				// turn cw when angle < 50
		//} else if(Robot.pdp.getCurrent(11) > 3) {
			
		} else {
			m_wristMotor.set(v2);

		}
		//SmartDashboard.putNumber("Wrist speed", Double.valueOf(String.format("%.4f", v2)));
	}

	public void drivePID() {
		PID();
		//SmartDashboard.putNumber("Wrist pidOut", pidOut);
		drive(-pidOut);
	}

	@Override
	protected void initDefaultCommand() {
		// no command
	}

	public double getVoltage() {
		return i_pot.getVoltage();
		//return m_wristMotor.getSensorCollection().getAnalogInRaw() * .003222;
	}

	//public int getAngleRaw() {
		//return m_wristMotor.getSensorCollection().getAnalogInRaw();
	//}

	public double getAngleDegrees() {
		//return getVoltage() * 109.09; // conversion for wrist
		return getVoltage() * 72.0;
	}

	public double getSetpoint() {
		return setpoint;
	}

	public void setPotZero() {
		m_wristMotor.getSensorCollection().setAnalogPosition(RobotMap.wristZero, 0);
	}

}