/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

//import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.*;

/**
 * Add your docs here.
 */
public class HatchIntake extends Subsystem {
  
  public WPI_TalonSRX hatchIntakeMotor = new WPI_TalonSRX(RobotMap.hatchIntakeMotor);

  @Override
  public void initDefaultCommand() {
   
  }
  public boolean getUpperHatchLimit(){
    return Robot.upperHatchLimitSwitch.get();
  }

  public boolean getLowerHatchLimit(){
    return !Robot.lowerHatchLimitSwitch.get();
  }

  public void intakeUp() {
    Robot.hatchIntake.hatchIntakeMotor.set(0.8);
  }

  public void intakeDown() {
    Robot.hatchIntake.hatchIntakeMotor.set(-0.8);
  }

  public void hatchIntakeStop() {
  Robot.hatchIntake.hatchIntakeMotor.set(0);
  }

  /*public void limitActive(){
    if(!getLimitBoolean()){
      Robot.hatchIntake.hatchIntakeMotor.set(0);
    }else if(getLimitBoolean()){
     Robot.hatchIntake.intakeUp();
    }
  }
  */

}
