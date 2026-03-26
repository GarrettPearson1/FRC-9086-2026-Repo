package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class Auto extends SequentialCommandGroup {
    public Auto(DriveSubsystem drive, ShooterSubsystem shooter) {
        addCommands(
            new RunCommand(() -> drive.drive(0.5, 0.0, 0.0, true), drive).withTimeout(2.0),

            new RunCommand(() -> shooter.startShootingSystem(1.0), shooter).withTimeout(18.0),

            new InstantCommand(() -> shooter.stopShootingSystem(), shooter)
        );
    }
}
