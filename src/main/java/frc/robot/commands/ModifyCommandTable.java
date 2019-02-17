package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Robot;

public class ModifyCommandTable extends Command {

	public ModifyCommandTable(int xmod, int ymod) {
		System.out.println("xmod: " + xmod + " ymod: " + ymod);
		if(xmod != 0) {
			if(xmod > 0) {
				   if(Robot.cmdTBLY == 1) {
					   System.out.println("At right of command table, can't go right any more");
				   } else {
					   Robot.cmdTBLX++;
				   }
				   Scheduler.getInstance().add(new PositionPicker(Robot.cmdTBLX, Robot.cmdTBLY));
			} else if(xmod < 0) {
				   if(Robot.cmdTBLY == 0) {
					   System.out.println("At left of command table, can't go left any more");
				   } else {
					   Robot.cmdTBLX--;
				   }
				   Scheduler.getInstance().add(new PositionPicker(Robot.cmdTBLX, Robot.cmdTBLY));
			}			
		}
		
		if(ymod != 0) {
			if(ymod > 0) {
				   if(Robot.cmdTBLY == 3) {
					   System.out.println("At top of command table, can't go up any more");
				   } else {
					   Robot.cmdTBLY++;
				   }
				   Scheduler.getInstance().add(new PositionPicker(Robot.cmdTBLX, Robot.cmdTBLY));
			} else if(ymod < 0) {
				   if(Robot.cmdTBLY == 0) {
					   System.out.println("At bottom of command table, can't go down any more");
				   } else {
					   Robot.cmdTBLY--;
				   }
				   Scheduler.getInstance().add(new PositionPicker(Robot.cmdTBLX, Robot.cmdTBLY));
			}
		}
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
