package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

/*
I'm writing this after getting broken up the worst way imaginable...
First time I actually ever lost sleep over a girl despite already having
a couple of girlfriends in the past

so don't mind the function names
 */
public class ShooterSubsystem extends SubsystemBase {
    private final SparkMax shooterSparkMax;
    private final RelativeEncoder shooterEncoder;

    private final SparkMax pullerSparkMax;
    private final RelativeEncoder pullerEncoder;

    public ShooterSubsystem() {
        shooterSparkMax = new SparkMax(Constants.StephConstants.kShootingMotorId, MotorType.kBrushless);
        shooterEncoder = shooterSparkMax.getEncoder();

        pullerSparkMax = new SparkMax(Constants.StephConstants.kPullerMotorId, MotorType.kBrushless);
        pullerEncoder = pullerSparkMax.getEncoder();

        shooterEncoder.setPosition(0);
        pullerEncoder.setPosition(0);
    }

    public void pullMotor(double speed) {
        pullerSparkMax.set(-speed);
    }

    public void stopPull() {
        pullerSparkMax.stopMotor();
    }

    public void startShootingThemKidsUp(double speed) {
        shooterSparkMax.set(-speed);
    }

    public void stopAndShootYourselfSoThePoliceCantGetYou() {
        shooterSparkMax.stopMotor();
    }
}
