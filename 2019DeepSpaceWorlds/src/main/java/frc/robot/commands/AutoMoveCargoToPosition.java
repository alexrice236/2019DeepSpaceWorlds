/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.commands.SwapIntake;

public class AutoMoveCargoToPosition extends Command {
  
  double position;
  SwapIntake swapIntake = new SwapIntake();

  public AutoMoveCargoToPosition(double position) {
    requires(Robot.cargoIntake);
    this.position = position;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    super.initialize();
    Robot.cargoIntake.setSetpoint(position);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.cargoIntake.cargoIntakeMotor.configContinuousCurrentLimit(8);
    Robot.cargoIntake.cargoIntakeMotor.set(1);
    Robot.cargoIntake.enable();
    if(!Robot.cargoIntake.shouldRunIntake() && position == Robot.cargoIntake.getOffset()){
      swapIntake.start();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.cargoIntake.onTarget();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.cargoIntake.disable();
    Robot.cargoIntake.free();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }

 
}
