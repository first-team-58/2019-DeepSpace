/*----------------------------------------------------------------------------*/
/* subsystem to run wheels and hatch release                                  */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.commands.ReleaseHatch;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Gripper extends Subsystem {
    private WPI_VictorSPX m_GripperWheel;
    private Solenoid hatchReleaseSolenoid;
  
    // constructor
    public Gripper () {
        m_GripperWheel = new WPI_VictorSPX(RobotMap.gripperMotor);
        hatchReleaseSolenoid = new Solenoid(RobotMap.hatchSolenoid);
    }

    public void setHatch(boolean setting){
        hatchReleaseSolenoid.set(setting);
    }

    public void runGripper(double moveValue){
        m_GripperWheel.set (moveValue);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ReleaseHatch()); // just runs hatch release
    }
}
