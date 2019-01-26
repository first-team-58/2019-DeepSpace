package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Shoulder extends Subsystem {
    private WPI_TalonSRX m_armMotor;
	
	public Shoulder() {
        m_armMotor = new WPI_TalonSRX(RobotMap.ShoulderMotor);

        // add potentiometer as analog input
        m_armMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog);

        m_armMotor.selectProfileSlot(0, 0);
        m_armMotor.config_kP(0, 2);
        m_armMotor.config_kI(0, 0);
        m_armMotor.config_kD(0, 1);
        //m_armMotor.config_kF(0, 2);
        m_armMotor.configAllowableClosedloopError(0, 500, 10);

        // motion magic
        m_armMotor.configMotionCruiseVelocity(10000); // probably very wrong
        m_armMotor.configMotionAcceleration(3000); // probably super duper wrong
    }

    public void setSetpoint(double setpoint) {
		m_armMotor.set(ControlMode.MotionMagic, setpoint );
	}
	@Override
	protected void initDefaultCommand() {
		// no command
	}
   
}