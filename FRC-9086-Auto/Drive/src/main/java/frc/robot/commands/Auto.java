package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Auto extends SequentialCommandGroup {
    public Auto(DriveSubsystem drive, ShooterSubsystem shooter, IntakeSubsystem intake, ClimbSubsystem climb) {
        addCommands(

            
            //Shooting Systems
            new RunCommand(() -> drive.drive(-0.5, 0.0, 0.0, true), drive).withTimeout(2.5),
            new RunCommand(() -> drive.drive(0.0, 0.0, 0.0, true), drive).withTimeout(0.5),
            
            new RunCommand(() -> shooter.startShootingSystem(0.7), shooter).withTimeout(1.5),
            new RunCommand(() -> shooter.pullMotor(0.9), shooter).withTimeout(4.0),
            

            /*
            new InstantCommand(() -> {
                shooter.stopShootingSystem();
                shooter.stopPull();
                System.out.println("Auto Complete");
            }, shooter)
            */
           new runCommand(() -> {
                shooter.stopShootingSystem();
                shooter.stopPull();
                System.out.println("Shooting Complete");
            }, shooter).withTimeout(0.3),

            new ClimbMovement(climb, true).withTimeout(1.5),

            //Find exactly how long turning 90 degrees takes
            new RunCommand(() -> drive.drive(0.0, 0.0, -0.5, true), drive).withTimeout(1.5),
            new RunCommand(() -> drive.drive(0.0, 0.0, 0.0, true), drive).withTimeout(0.1),


            //Sets Up climber
            new ClimbMovement(climb, true).withTimeout(1.0),

            //Drives to Post
            new RunCommand(() -> drive.drive(0.0, -0.5, 0.0, true), drive).withTimeout(1.0),
            new RunCommand(() -> drive.drive(0.0, 0.0, 0.0, true), drive).withTimeout(0.1),

            //Climbing the Ladder
            new ClimbMovement(climb, false).withTimeout(3.0),
            
            

        );
    }
}
