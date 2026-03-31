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
    public Auto(DriveSubsystem drive, ShooterSubsystem shooter, IntakeSubsystem intake) {
        addCommands(

            
            //System.out.println("Initiating Drive"),
            new RunCommand(() -> drive.drive(-0.5, 0.0, 0.0, true), drive).withTimeout(2.5),
            new RunCommand(() -> drive.drive(0.0, 0.0, 0.0, true), drive).withTimeout(0.5),
            

            //System.out.println("Initiating Shooting System");
            new RunCommand(() -> shooter.startShootingSystem(0.7), shooter).withTimeout(1.5),
            new RunCommand(() -> shooter.pullMotor(0.9), shooter).withTimeout(4.0),
            //System.out.println("Shutting Down Shooting Motors");
            new RunCommand(() -> drive.drive(0.0, 0.0, 0.1, true), drive),

            new InstantCommand(() -> {
                shooter.stopShootingSystem();
                shooter.stopPull();
                System.out.println("Auto Complete");
            }, shooter)

        );
    }
}
