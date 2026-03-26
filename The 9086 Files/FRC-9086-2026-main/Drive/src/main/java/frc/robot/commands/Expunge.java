package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ShooterSubsystem;

public class Expunge extends SequentialCommandGroup {
    public Expunge(ShooterSubsystem shooter) {
        addCommands(
            new ParallelRaceGroup(
                new SequentialCommandGroup(
                    new StartEndCommand(
                        () -> {
                            shooter.startShootingThemKidsUp(-1);
                            shooter.pullMotor(-1);
                        },
                        () -> {
                            shooter.stopAndShootYourselfSoThePoliceCantGetYou();
                            shooter.stopPull();
                        }
                    ).withTimeout(1),
                    new WaitCommand(1)
                )
            )
        );
    }
}
