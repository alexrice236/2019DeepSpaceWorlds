/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.MoveCargo;

/**
 * Add your docs here.
 */
public class CargoIntake extends PIDSubsystem {
  
  public WPI_TalonSRX cargoIntakeMotor = new WPI_TalonSRX(RobotMap.cargoIntakeMotor);
  public CANSparkMax cargoExtensionMotor = new CANSparkMax(RobotMap.cargoExtensionMotor, MotorType.kBrushless);
  public CANEncoder cargoExtensionEncoder = cargoExtensionMotor.getEncoder(); 
  private boolean armDirection;
  //private boolean intakeDirection;
  public boolean runIntake = false;
  public double encoderOffset;

  public CargoIntake(){
    super(10,0,-5);
    
    setAbsoluteTolerance(1);

    setSetpoint(0);

    configureControllers();
    cargoExtensionMotor.setSmartCurrentLimit(Robot.MAX_CURRENT_NEO);
    cargoIntakeMotor.configPeakCurrentLimit(0);
    cargoIntakeMotor.configContinuousCurrentLimit(5);
    cargoIntakeMotor.enableCurrentLimit(true);


  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new MoveCargo());
  }


  public double getCargoArmEncoderPosition(){
    return cargoExtensionEncoder.getPosition();
  }

  

  public void cargoExtensionStop() {
    cargoExtensionMotor.set(0);
  }

  public void cargoIntakeStop(){
    cargoIntakeMotor.set(0);
  }


  private void configureControllers() {
    cargoIntakeMotor.configClosedloopRamp(.1, 0);
    cargoExtensionMotor.setRampRate(.1);
  }

  public void intakeCargo(){
   if(! shouldRunIntake()){
    cargoIntakeStop();
   }
   else if(Robot.oi.getcoPilotController().getRawButton(RobotMap.joystickRightBumper)){
    cargoIntakeMotor.configContinuousCurrentLimit(25); 
    cargoIntakeMotor.set(-0.8);
   }
   else if(cargoExtensionEncoder.getVelocity() != 0){
     cargoIntakeMotor.configContinuousCurrentLimit(8);
     cargoIntakeMotor.set(1);
   }
   else{
    cargoIntakeMotor.configContinuousCurrentLimit(5);  
    cargoIntakeMotor.set(1);
   }
  }

  public boolean getCargoLowerLimit(){
    if(!Robot.lowerCargoLimitSwitch.get()){
      encoderOffset = cargoExtensionEncoder.getPosition();
    }
    return !Robot.lowerCargoLimitSwitch.get();
  }

  public boolean getCargoUpperLimit(){
    return Robot.upperCargoLimitSwitch.get();
  }

  public double getOffset(){
    return encoderOffset;
  }

  public void checkCargoLimits(){
    armDirection = Robot.oi.getcoPilotController().getRawAxis(RobotMap.leftJoystickYAxis) < 0;
    if(armDirection && getCargoUpperLimit() || !armDirection && getCargoLowerLimit()){
      cargoExtensionStop();
      return;
    }
    moveCargoArm();
  }
  

  public void moveCargoArm(){
    if(armDirection){
      cargoExtensionMotor.set(Robot.oi.getcoPilotController().getRawAxis(RobotMap.leftJoystickYAxis) * -.4);
    }else{
      cargoExtensionMotor.set(Robot.oi.getcoPilotController().getRawAxis(RobotMap.leftJoystickYAxis) * -.2);
    }
  }

  public boolean shouldRunIntake(){
    return runIntake;
  }

  public void setRunIntake(boolean notCurrentRunIntake){
    runIntake = notCurrentRunIntake;
  }

  @Override
  protected void usePIDOutput(double output) {
    /*if(armDirection && getCargoUpperLimit() || !armDirection && getCargoLowerLimit()){
      return;
    }*/
    output = Math.max(output, -0.4);
    output = Math.min(output, 0.7);

    cargoExtensionMotor.set(output);
  }

  @Override
  protected double returnPIDInput() {
    return getCargoArmEncoderPosition();
  }
}
