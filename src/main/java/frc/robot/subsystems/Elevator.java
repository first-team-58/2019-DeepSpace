package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Elevator extends Subsystem{
    private WPI_TalonSRX m_elevatorMotor;

    public Elevator(){

        m_elevatorMotor = new WPI_TalonSRX(RobotMap.elevatorMotor);

        // add encoder
        m_elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        // m_elevatorMotor.setInverted(true);
        // m_elevatroMotor.setSensorPhase(true);
        m_elevatorMotor.setSelectedSensorPosition(5000); // set to same as what we think start position should be
        
        m_elevatorMotor.selectProfileSlot(0, 0);
        m_elevatorMotor.config_kP(0, 2);
        m_elevatorMotor.config_kI(0, 0);
        m_elevatorMotor.config_kD(0, 1);
        //m_elevatorMotor.config_kF(0, 2);
        m_elevatorMotor.configAllowableClosedloopError(0, 500, 10);

        // motion magic
        m_elevatorMotor.configMotionCruiseVelocity(10000);
        m_elevatorMotor.configMotionAcceleration(3000);
    }

    public void setSetpoint(double setpoint) {
		m_elevatorMotor.set(ControlMode.MotionMagic, setpoint );
	}
    
    protected void initDefaultCommand(){

    }
}