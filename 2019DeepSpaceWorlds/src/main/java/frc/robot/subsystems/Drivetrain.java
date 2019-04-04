/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.DriveWithJoysticks;

/**
 * Add your docs here.
 */
public class Drivetrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private WPI_TalonSRX leftBackMotor = new WPI_TalonSRX(RobotMap.leftBackMotor);
	private WPI_TalonSRX leftFrontMotor = new WPI_TalonSRX(RobotMap.leftFrontMotor);
	private WPI_TalonSRX rightBackMotor = new WPI_TalonSRX(RobotMap.rightBackMotor);
  private WPI_TalonSRX rightFrontMotor = new WPI_TalonSRX(RobotMap.rightFrontMotor);
  
  private SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftBackMotor, leftFrontMotor);
  private SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightBackMotor, rightFrontMotor);
  
  private DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

  public boolean useReverseDrive = false;
  
  public Drivetrain(){
    drive = new DifferentialDrive(leftMotors, rightMotors);

    configureTalons();
  }
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveWithJoysticks());
  }

  private void configureTalons() {
		leftFrontMotor.configClosedloopRamp(.1, 0);
		leftBackMotor.configClosedloopRamp(.1, 0);
		rightFrontMotor.configClosedloopRamp(.1, 0);
    rightBackMotor.configClosedloopRamp(.1, 0);
  }

  public void stopDrive(){
    drive.arcadeDrive(0, 0);
  }

  public void resetEncoders() {
		leftFrontMotor.setSelectedSensorPosition(0, 0, 0);
		rightFrontMotor.setSelectedSensorPosition(0, 0, 0);
	}
  
  public double getLeftEncoderPosition() {
		return leftFrontMotor.getSelectedSensorPosition(0);
		
	}

	public double getRightEncoderPosition() {
		return rightFrontMotor.getSelectedSensorPosition(0);
  }

  public void arcadeDrive(double speed, double rotation){
    drive.arcadeDrive(speed, rotation);
  }
    
  public void tankDrive(double leftSpeed, double rightSpeed){
      drive.tankDrive(leftSpeed, rightSpeed);
  }

  public boolean shouldUseReverseDrive(){
    return useReverseDrive;
  }

  public void setUseReverseDrive(boolean notCurrentDriveDirection){
    useReverseDrive = notCurrentDriveDirection;
  }

  public double getGyroAngle(){
    return Robot.gyro.getAngle();
  }

}
