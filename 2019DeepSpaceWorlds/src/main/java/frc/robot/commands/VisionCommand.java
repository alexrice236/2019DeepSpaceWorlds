/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class VisionCommand extends Command {
  NetworkTableEntry xEntry;
  public VisionCommand() {
    requires(Robot.drivetrain);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    super.initialize();

    NetworkTableInstance NTI = NetworkTableInstance.getDefault();
    NetworkTable table = NTI.getTable("X");
    xEntry = table.getEntry("X");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    super.execute();
    double speed = Robot.oi.getPilotController().getRawAxis(RobotMap.leftJoystickYAxis) * 0.8;
    double rotation = 0.0;
    if((Robot.distanceSensor.getVoltage()/5)*512 > Robot.MIN_DISTANCE ){
      rotation = xEntry.getDouble(0) * 64;
    }

    Robot.drivetrain.arcadeDrive(speed, rotation);
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
