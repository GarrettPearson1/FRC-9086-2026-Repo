package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ClimbMovement extends SequentialCommandGroup {
    public ClimbMovement(ClimbSubsystem climber, Boolean up) {
        addCommands(
            new ParallelRaceGroup(
                new SequentialCommandGroup(
                    new StartEndCommand(
                        () -> {
                            climber.forwardMotor.set(up ? -1 : 1);
                            climber.backwardMotor.set(up ? -1 : 1);
                        },
                        () -> {
                            climber.forwardMotor.stopMotor();
                            climber.backwardMotor.stopMotor();
                        }
                    ).withTimeout(.1),
                    new WaitCommand(.1)
                )
            )
        );
    }
}
