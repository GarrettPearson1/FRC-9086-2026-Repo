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

            /*
            Drive forward code. Disabled to only shoot.

            new InstantCommand(() -> SmartDashboard.putString("Auto Status", "1.9 Driving Forward")),
            new RunCommand(() -> {
                System.out.println("<Drive:> Running Auto.");
                //changed true to false to bypass the gyro system in order to not auto-aim at target
                drive.drive(0.5, 0.0, 0.0, false);
            }, drive).withTimeout(2.0),
            */

            new InstantCommand(() -> SmartDashboard.putString("Auto Status", "2. Shooting System Initiated")),
            new RunCommand(() -> intake.moveArm(-0.2), intake),
            new RunCommand(() -> shooter.startShootingSystem(0.7), shooter).withTimeout(1.5),
            new RunCommand(() -> shooter.pullMotor(0.9), shooter).withTimeout(4.0),
            new InstantCommand(() -> SmartDashboard.putString("Auto Status", "3. Shutting Down Motors")),
            
            new InstantCommand(() -> {
                shooter.stopShootingSystem();
                shooter.stopPull();
                SmartDashboard.putString("Auto Status", "4. Auto Complete");
            }, shooter)

        );
    }
}
