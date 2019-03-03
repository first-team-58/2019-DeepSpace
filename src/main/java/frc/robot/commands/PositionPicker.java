package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PositionPicker extends CommandGroup {

	public PositionPicker(int x, int y) {
		int s = x*10 + y;
		switch(s) {
		case 0: //floor hatch panel
			addSequential(new FloorHatch());
			break;
		case 10://floor ball
			addSequential(new FloorBall());
			break;
		case 11://Rocket bottom ball
			addSequential(new RocketBottomBall());
			break;
		case 1: //Rocket bottom hatch
			addSequential(new RocketBottomHatch());
			break;
		case 2://rocket middle hatch
			addSequential(new RocketMiddleHatch());
			break;
		case 12: //rocket middle ball
			addSequential(new RocketMiddleBall());
			break;
		case 13: //rocket top ball
			addSequential(new RocketTopBall());
			break;
		case 3: //rocket top hatch
			addSequential(new RocketTopHatch());
			//SmartDashboard.putNumber("PositionPicker",PositionPicker);//attempt to print position to dashboard
			break;
		}
	}
}
