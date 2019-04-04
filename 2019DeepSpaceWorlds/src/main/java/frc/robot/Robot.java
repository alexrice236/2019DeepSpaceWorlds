/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.commands.DriveWithJoysticks;
import frc.robot.commands.MoveIntake;
import frc.robot.commands.SwapDriveDirection;
import frc.robot.commands.HatchInitial;
import frc.robot.commands.SwapIntake;
import frc.robot.commands.AutoDriveForward;
import frc.robot.subsystems.CargoIntake;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.HatchIntake;
import frc.robot.subsystems.IntakeExtender;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.cscore.VideoMode;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  public static HatchIntake hatchIntake;
  public static Drivetrain drivetrain;
  public static IntakeExtender intakeExtender;
  public static CargoIntake cargoIntake;

  public static DriveWithJoysticks drive;
  public static SwapDriveDirection swapDrive;
  public static HatchInitial hatch;
  public static SwapIntake swapIntake;
  public static AutoDriveForward driveForward;
  
  public static MoveIntake moveIntake;

  public static Trigger.ButtonScheduler upButton;

  public static OI oi;

  public static DriverStation ds;

  Command initialcCommand;
  SendableChooser<Command> chooser; 

  public static UsbCamera frontCamera;
	public static UsbCamera backCamera;
	public static VideoSink cameraServer;

  
  public static AnalogInput actuatorPosition;
  public static AnalogInput distanceSensor;
  public static DigitalInput lowerHatchLimitSwitch;
  public static DigitalInput upperHatchLimitSwitch;
  public static DigitalInput lowerCargoLimitSwitch;
  public static DigitalInput upperCargoLimitSwitch;
  public static final int IMG_WIDTH = 1;
  public static final int IMG_HEIGHT = 1;
  public static VideoMode videoMode;
  public boolean prevTrigger = false;
  public static final int   MIN_DISTANCE = 30;
  public static final int MAX_CURRENT_NEO = 40;
  public static AHRS gyro;
  public final Object imgLock = new Object();


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */

  @Override
  public void robotInit(){


    hatchIntake = new HatchIntake();
    drivetrain = new Drivetrain();
    intakeExtender = new IntakeExtender();
    cargoIntake = new CargoIntake();

    drive = new DriveWithJoysticks();
    swapDrive = new SwapDriveDirection();
    moveIntake = new MoveIntake(1.75);
    hatch = new HatchInitial();
    swapIntake = new SwapIntake();

    chooser = new SendableChooser<>();
    chooser.setDefaultOption("Hatch", hatch);
    chooser.addOption("Cargo", swapIntake);
    SmartDashboard.putData("Initial Chooser", chooser);


    oi = new OI();

    actuatorPosition = new AnalogInput(0);
    distanceSensor = new AnalogInput(1);
    upperHatchLimitSwitch = new DigitalInput(0);
    lowerHatchLimitSwitch = new DigitalInput(4);
    lowerCargoLimitSwitch = new DigitalInput(1);
    upperCargoLimitSwitch = new DigitalInput(2);
    gyro = new AHRS(SPI.Port.kMXP);

    SmartDashboard.putData(actuatorPosition);
    
    SmartDashboard.putData(distanceSensor);

    SmartDashboard.putData(upperHatchLimitSwitch);
    SmartDashboard.putData(upperCargoLimitSwitch);
    SmartDashboard.putData(lowerCargoLimitSwitch);
    
    SmartDashboard.putData(gyro);
    
    videoMode = new VideoMode(1, IMG_WIDTH, IMG_HEIGHT, 30);

    frontCamera = CameraServer.getInstance().startAutomaticCapture(RobotMap.frontCamera);
    frontCamera.setVideoMode(videoMode);
    frontCamera.setExposureAuto();

    backCamera = CameraServer.getInstance().startAutomaticCapture(RobotMap.backCamera);
		backCamera.setVideoMode(videoMode);
    frontCamera.setExposureAuto();

  
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  @Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
  
    drivetrain.resetEncoders();
     
    initialcCommand = (Command) chooser.getSelected();
    initialcCommand.start();
    drive.start();
    //swapDrive.start();
    //extend.start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }
  

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    
    Scheduler.getInstance().run();
    SmartDashboard.putBoolean("rightBumper", Robot.oi.getPilotController().getRawButton(RobotMap.joystickRightBumper));
    SmartDashboard.putBoolean("leftBumper", Robot.oi.getPilotController().getRawButton(RobotMap.joystickLeftBumper));
    SmartDashboard.putBoolean("runIntake", Robot.cargoIntake.shouldRunIntake());
    SmartDashboard.putBoolean("ReverseDriveActive", Robot.drivetrain.shouldUseReverseDrive());
    SmartDashboard.putNumber("CurrentLimiting", Robot.cargoIntake.cargoIntakeMotor.getOutputCurrent());
    SmartDashboard.putNumber("cargoEncoder", Robot.cargoIntake.getCargoArmEncoderPosition());
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  @Override
  public void disabledInit() {
    driveForward = new AutoDriveForward(1);
    driveForward.start();
  }
}
