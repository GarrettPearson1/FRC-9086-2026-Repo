package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ShooterSubsystem;

public class Shoot extends SequentialCommandGroup {
    public Shoot(ShooterSubsystem shooter, XboxController controller) {
        addCommands(
            new ParallelRaceGroup(
                new SequentialCommandGroup(
                    new StartEndCommand(
                        () -> shooter.startShootingSystem(controller.getLeftTriggerAxis()),
                        shooter::stopShootingSystem
                    ).withTimeout(1),
                    new WaitCommand(1)
                )
            )
        );
    }
}