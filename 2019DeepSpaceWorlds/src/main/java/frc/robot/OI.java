/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.AutoMoveCargoToPosition;
import frc.robot.commands.MoveIntake;
import frc.robot.commands.SwapDriveDirection;
import frc.robot.commands.SwapIntake;
import frc.robot.commands.TurnRobot;


/**
 * Add your docs here.
 */
public class OI {
    
    private Joystick pilotController;
    private Joystick coPilotController;

    public OI() {
        pilotController = new Joystick(0);
        coPilotController = new Joystick(1);

        /*
        Button copilotButtonA = new JoystickButton(coPilotController, RobotMap.joystickButtonA);
        copilotButtonA.whileHeld(new HatchIntakeDown());
        
        Button copilotButtonY = new JoystickButton(coPilotController, RobotMap.joystickButtonY);
        copilotButtonY.whileHeld(new HatchIntakeUp());
        */

    
        Button copilotButtonY = new JoystickButton(coPilotController, RobotMap.joystickButtonY);
        copilotButtonY.whenPressed(new MoveIntake(1.75));

        Button copilotButtonB = new JoystickButton(coPilotController, RobotMap.joystickButtonB);
        copilotButtonB.whenPressed(new MoveIntake(1.6));
        
        Button copilotButtonA = new JoystickButton(coPilotController, RobotMap.joystickButtonA);
        copilotButtonA.whenPressed(new MoveIntake(0.94));

        Button copilotButtonX = new JoystickButton(coPilotController, RobotMap.joystickButtonX);
        copilotButtonX.whenPressed(new SwapIntake());

        Button copilotLeftBumper = new JoystickButton(coPilotController, RobotMap.joystickLeftBumper);
        //copilotLeftTrigger.whenPressed(new AutoMoveCargoToPosition(Robot.cargoIntake.getOffset()));
        copilotLeftBumper.whenPressed(new AutoMoveCargoToPosition(0));

        Button copilotStart = new JoystickButton(coPilotController, RobotMap.joystickButtonStart);
        copilotStart.whenPressed(new AutoMoveCargoToPosition(90)); // + Robot.cargoIntake.getOffset()));

        Button copilotBack = new JoystickButton(coPilotController, RobotMap.joystickButtonBack);
        copilotBack.whenPressed(new AutoMoveCargoToPosition(60 + Robot.cargoIntake.getOffset()));

        Button pilotButtonY = new JoystickButton(pilotController, RobotMap.joystickButtonY);
        pilotButtonY.whenPressed(new SwapDriveDirection());

        Button pilotButtonA = new JoystickButton(pilotController, RobotMap.joystickButtonA);
        pilotButtonA.whenPressed(new TurnRobot(Robot.drivetrain.getGyroAngle() + 180));

        Button pilotButtonX = new JoystickButton(pilotController, RobotMap.joystickButtonX);
        pilotButtonX.whenPressed(new TurnRobot(Robot.drivetrain.getGyroAngle() - 90));

        Button pilotButtonB = new JoystickButton(pilotController, RobotMap.joystickButtonB);
        pilotButtonB.whenPressed(new TurnRobot(Robot.drivetrain.getGyroAngle() + 90));

        
        
        
        copilotButtonA.close();
        copilotButtonY.close();
        copilotButtonX.close();
        copilotButtonB.close();
        copilotBack.close();
        copilotStart.close();
        copilotLeftBumper.close();
        pilotButtonY.close();
        pilotButtonA.close();

    }

    public Joystick getPilotController() {
        return pilotController;
    }
    public Joystick getcoPilotController() {
        return coPilotController;
    }
}
