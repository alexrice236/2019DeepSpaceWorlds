/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.Robot;

public class MoveIntake extends PIDCommand {

  private double currentActuatorVoltage;
  
  public MoveIntake(double position) {
   super(5, 0, -5);
   requires(Robot.intakeExtender);
    	
    	getPIDController().setAbsoluteTolerance(.05);
    	getPIDController().setSetpoint(position);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    super.initialize();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    getPIDController().enable();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return getPIDController().onTarget();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.intakeExtender.stopExtension();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
    getPIDController().disable();
    super.end();
  }

  @Override
  protected double returnPIDInput() {
    currentActuatorVoltage = Robot.intakeExtender.getActuatorPosition();
    return currentActuatorVoltage;
  }

  @Override
  protected void usePIDOutput(double output) {
    Robot.intakeExtender.hatchIntakeExtensionMotor.set(output);
  }
}
