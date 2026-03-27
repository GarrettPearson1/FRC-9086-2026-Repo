// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.ClimbMovement;
import frc.robot.commands.Expunge;
import frc.robot.commands.MoveArmToPosition;
import frc.robot.commands.Pull;
import frc.robot.commands.Shoot;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.AutoSubsystem;
import frc.robot.commands.Auto;

public class RobotContainer {
  // Subsystems
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();
  private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  private final ClimbSubsystem climbSubsystem = new ClimbSubsystem();

  // Controller
  private final XboxController driverController = new XboxController(OIConstants.kDriverControllerPort);
  private final XboxController operatorController = new XboxController(OIConstants.kOperatorControllerPort); // set to other port (1?)

  public RobotContainer() {
    configureBindings();
    configureDriveCommand();
  }

  private void configureDriveCommand() {
    driveSubsystem.setDefaultCommand(
      new RunCommand(
        () -> {
          double leftX = MathUtil.applyDeadband(driverController.getLeftY() * -1.0, OIConstants.kDriveDeadband);
          double leftY = MathUtil.applyDeadband(driverController.getLeftX() * -1.0, OIConstants.kDriveDeadband);
          double rightX = MathUtil.applyDeadband(driverController.getRightX() * -1.0, OIConstants.kDriveDeadband);
          
          driveSubsystem.drive(leftX, leftY, rightX, true);
        },
        driveSubsystem
        )
    );

    intakeSubsystem.setDefaultCommand(
      new RunCommand(() -> {
        double pullerSpool = operatorController.getRightTriggerAxis() * brev();

        intakeSubsystem.setSpeed(pullerSpool);
      }, intakeSubsystem));

    shooterSubsystem.setDefaultCommand(
      new RunCommand(() -> {
        double shooterSpool = operatorController.getLeftTriggerAxis() * brev();
        //double pullTrigger = driverController.getRightTriggerAxis() == 1 ? 1 : 0;

        shooterSubsystem.startShootingSystem(shooterSpool);
        //shooterSubsystem.pullMotor(pullTrigger);

      }, shooterSubsystem)
    );

    climbSubsystem.setDefaultCommand(
      new RunCommand(() -> {
        //System.out.println(driverController.getPOV());
        //if (driverController.getPOV() == 180)
        //  climbSubsystem.forwardMotor.set(1);
        //else if (driverController.getPOV() == 0)
        //  climbSubsystem.forwardMotor.set(-1);
        double out = 0;
        if (dpad_down())
          out++;
        if (dpad_up())
          out--;
        climbSubsystem.forwardMotor.set(out);
        climbSubsystem.backwardMotor.set(out);
      }, climbSubsystem)
    );
  }

  private boolean dpad_down() {
    return driverController.getPOV() == 180;
  }

  private boolean dpad_up() {
    return driverController.getPOV() == 0;
  }

  private int brev() {
    return operatorController.getBButton() ? -1 : 1;
  }

  private boolean isLeftDown() {
    return operatorController.getLeftTriggerAxis() != 0;
  }

  private void configureBindings() {
    //new Trigger(this::isSpooling)
    // .whileTrue(
    //    new Shoot(shooterSubsystem, driverController)
    //  );

    //new Trigger(driverController::getBButton)
    //.whileTrue(
    //  new Expunge(shooterSubsystem)
    //);

    new Trigger(this::dpad_up)
      .whileTrue(
        new ClimbMovement(climbSubsystem, true)
      );
    
    new Trigger(this::dpad_down)
      .whileTrue(
        new ClimbMovement(climbSubsystem, false)
      );

    new Trigger(operatorController::getLeftBumperButton)
      .whileTrue(
        new Pull(shooterSubsystem, operatorController)
      );

    new Trigger(operatorController::getRightBumperButton)
      .whileTrue(
        new MoveArmToPosition(intakeSubsystem, operatorController)
      );
  }

  public Command getAutonomousCommand() {
    new InstantCommand(() -> SmartDashboard.putString("Auto Status", "0.1 Auto Command Sent")),
    Command autoCommand = new Auto(driveSubsystem, shooterSubsystem);
    new InstantCommand(() -> SmartDashboard.putString("Auto Status", "1. Auto Command Sent")),
    return autoCommand;
    // return Commands.print("No autonomous command configured");
  }
}