package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.RobotMap;

public class Shoulder extends PIDSubsystem {

	TalonSRX motor = RobotMap.armMotor;
	AnalogInput pot = RobotMap.armPot;
	
	
	public Shoulder() {
		super("Shoulder", 2.0, 0.0, 0.0);
		setAbsoluteTolerance(0.05);
        getPIDController().setContinuous(false);
        
	}

	@Override
	protected double returnPIDInput() {
		return pot.getAverageVoltage();
	}

	@Override
	protected void usePIDOutput(double output) {
		
	}

	@Override
	protected void initDefaultCommand() {
		
	}
   
}