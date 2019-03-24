/*----------------------------------------------------------------------------*/
/* subsystem to run wheels and hatch release                                  */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
//import frc.robot.commands.ToggleHatch;
import edu.wpi.first.wpilibj.Servo;
//import edu.wpi.first.wpilibj.PWM;
/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Gripper extends Subsystem {
    private WPI_VictorSPX m_GripperWheel;
    private Servo Hook;
    private Solenoid hatchReleaseSolenoid;
  
    // constructor
    public Gripper () {
        m_GripperWheel = new WPI_VictorSPX(RobotMap.gripperMotor);
        //Hook = new Servo(5);
        //hatchReleaseSolenoid = new Solenoid(RobotMap.hatchSolenoid);
        //hatchReleaseSolenoid.set(false);
        
    }

    //public double getHookAngle(){
      //  return Hook.getAngle();
        
   // }
    public void setHookAngle(double angle)
    {
        Hook.setAngle(angle);
    } 
    public void setHatch(boolean setting){
        hatchReleaseSolenoid.set(setting);
        //    Hook.setAngle(90);
        }
    

    public void runGripper(double moveValue){
        m_GripperWheel.set (moveValue);
    }

    //public void runHook(double moveValue){
      //  Hook.set (moveValue);

   // }

    @Override
    public void initDefaultCommand() {

    }
}
