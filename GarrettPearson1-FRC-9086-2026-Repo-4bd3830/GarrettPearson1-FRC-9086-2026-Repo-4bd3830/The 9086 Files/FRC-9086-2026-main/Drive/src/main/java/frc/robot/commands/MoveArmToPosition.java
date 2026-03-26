package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class MoveArmToPosition extends SequentialCommandGroup {
    //private final IntakeSubsystem arm;
    public static final double TOLERANCE = 0.1;
    //private final double target;

    public MoveArmToPosition(IntakeSubsystem arm, XboxController controller) {
        addCommands(
            new ParallelRaceGroup(
                new SequentialCommandGroup(
                    new StartEndCommand(
                        () -> {
                            arm.moveArm(.2 * (controller.getBButton() ? -1 : 1));
                        },
                        () -> {
                            arm.stopArm();
                        }
                    ).withTimeout(1),
                    new WaitCommand(1)
                )
            )
        );
    }

    //@Override public void initialize() { arm.moveArm(target); }
    //@Override public boolean isFinished() {
    //    System.out.println(arm.getAbsolutePosition());
    //    return Math.abs(arm.getAbsolutePosition() - target) <= TOLERANCE;
    //}
}