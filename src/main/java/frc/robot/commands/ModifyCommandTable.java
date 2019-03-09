package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class ModifyCommandTable extends Command {
	int xmod, ymod;
	
	public ModifyCommandTable(int xmod, int ymod) {
		this.xmod = xmod;
		this.ymod = ymod;
	}
	
	protected void initialize() {
		
	}
	
	protected void execute() {
		Robot.cmdTBLX += xmod;
		Robot.cmdTBLY += ymod;
		
		if(Robot.cmdTBLX > 1) {
			Robot.cmdTBLX = 1;
		} else if(Robot.cmdTBLX < 0) {
			Robot.cmdTBLX = 0;
		}
		if(Robot.cmdTBLY > 3) {
			Robot.cmdTBLY = 3;
		} else if(Robot.cmdTBLY < 0) {
			Robot.cmdTBLY = 0;
		}
		//System.out.println("cmdTBLX: " + Robot.cmdTBLX + " cmdTBLY: " + Robot.cmdTBLY);
		SmartDashboard.putNumber("ElevatorLevel",Robot.cmdTBLY);//displays on smart dashboard 
		SmartDashboard.putNumber("Hatch or Cargo",Robot.cmdTBLX);
	}
	
	protected void end() {
		Scheduler.getInstance().add(new PositionPicker(Robot.cmdTBLX, Robot.cmdTBLY));
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
