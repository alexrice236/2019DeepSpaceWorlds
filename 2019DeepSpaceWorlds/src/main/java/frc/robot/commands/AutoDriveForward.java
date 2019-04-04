/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.Robot;

public class AutoDriveForward extends PIDCommand {

  private double wheelRotations;
	private double travelDistance;
  private double Circumference = 6 * Math.PI;

  public AutoDriveForward(double distance) {
    super(10, 0, -5);
    requires(Robot.drivetrain);

    getPIDController().setAbsoluteTolerance(0.1);
    getPIDController().setSetpoint(distance);
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
    Robot.drivetrain.stopDrive();
    getPIDController().disable();
    super.end();
  }

  @Override
  protected double returnPIDInput() {
    wheelRotations = Robot.drivetrain.getRightEncoderPosition() / 4096;
    travelDistance = wheelRotations *Circumference;
    
    return travelDistance;
   }

   @Override
   protected void usePIDOutput(double output) {
     output = Math.min(output, 0.5);
     output = Math.max(output, -0.5);
     
     Robot.drivetrain.tankDrive(output, output);
   }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
