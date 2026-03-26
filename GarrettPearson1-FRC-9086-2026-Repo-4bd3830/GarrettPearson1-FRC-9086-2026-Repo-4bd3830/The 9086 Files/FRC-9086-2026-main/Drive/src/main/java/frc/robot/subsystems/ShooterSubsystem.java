package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

/*
Here is the shooting system
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

    public void startShootingSystem(double speed) {
        shooterSparkMax.set(-speed);
    }

    public void stopShootingSystem() {
        shooterSparkMax.stopMotor();
    }
}
