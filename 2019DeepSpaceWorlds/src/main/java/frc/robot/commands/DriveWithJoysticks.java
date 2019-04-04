/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class DriveWithJoysticks extends Command {
  public DriveWithJoysticks() {
    requires(Robot.drivetrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    super.initialize();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    super.execute();

    double speed = -Robot.oi.getPilotController().getRawAxis(RobotMap.leftJoystickYAxis);
    double rotation = Robot.oi.getPilotController().getRawAxis(RobotMap.rightJoystickXAxis) * 0.8;
    
    if(Robot.drivetrain.shouldUseReverseDrive()){
      speed *= -1;
    }

    if(Robot.oi.getPilotController().getRawButton(RobotMap.joystickRightBumper) || Robot.oi.getPilotController().getRawButton(RobotMap.joystickLeftBumper)){
      Robot.drivetrain.arcadeDrive(speed * 0.5, rotation * 0.65);
    }
    else{
      Robot.drivetrain.arcadeDrive(speed, rotation);
    }
  }
  

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
