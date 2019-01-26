/*----------------------------------------------------------------------------*/
/* subsystem to run wheels and hatch release                                  */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Gripper extends Subsystem {
    private WPI_VictorSPX m_GripperWheel;
    private Solenoid hatchReleassSolenoid;
  
    // constructor
    public Gripper () {
        m_GripperWheel = new WPI_VictorSPX(RobotMap.gripperMotor);
        hatchReleassSolenoid = new Solenoid(RobotMap.hatchSolenoid);
    }

    public void releaseHatch(){

        hatchReleassSolenoid.set(true);
    }

    public void runGripper(double moveValue){

        m_GripperWheel.set (moveValue);
    }

  @Override
  public void initDefaultCommand() {
    // no init defult command
  }
}
