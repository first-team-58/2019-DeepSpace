package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Wrist extends Subsystem {
    private WPI_TalonSRX m_wristMotor;
	private double setpoint;
    
	public Wrist() {
        m_wristMotor = new WPI_TalonSRX(RobotMap.ShoulderMotor);

        // add potentiometer as analog input
        m_wristMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog);

        m_wristMotor.selectProfileSlot(0, 0);
        m_wristMotor.config_kP(0, 2);
        m_wristMotor.config_kI(0, 0);
        m_wristMotor.config_kD(0, 1);
        //m_wristMotor.config_kF(0, 2);
        m_wristMotor.configAllowableClosedloopError(0, 500, 10);

        // motion magic
        m_wristMotor.configMotionCruiseVelocity(10000); // probably very wrong
        m_wristMotor.configMotionAcceleration(3000); // probably super duper wrong
    }

    public void setSetpoint(double setpoint) {
    	this.setpoint = setpoint;
		m_wristMotor.set(ControlMode.MotionMagic, setpoint );
	}
	@Override
	protected void initDefaultCommand() {
		// no command
	}

	public double getSetpoint() {
		return setpoint;
	}
   
}