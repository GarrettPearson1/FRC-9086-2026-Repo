package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkFlexConfig;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RuntimeType;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.handlers.PID;

public class IntakeSubsystem extends SubsystemBase{
    private final SparkMax intakeMotor;
    private final RelativeEncoder intakeEncoder;

    private final SparkMax armMotor;
    private final RelativeEncoder armEncoder;
    private final SparkClosedLoopController armClosedLoopController;
    private final SparkFlexConfig armConfig;

    private final PID armController;

    private double armSetpoint = 0;
    private Thread armThread;

    public IntakeSubsystem() {
        intakeMotor = new SparkMax(Constants.IntakeConstants.kSpinningMotor, MotorType.kBrushless);
        intakeEncoder = intakeMotor.getEncoder();
        
        armMotor = new SparkMax(Constants.IntakeConstants.kUpDownMotor, MotorType.kBrushless);
        armEncoder = armMotor.getEncoder();
        armClosedLoopController = armMotor.getClosedLoopController();
        
        SparkFlexConfig armMotorConfig = new SparkFlexConfig();
        armMotorConfig.inverted(true);
        armMotor.configure(armMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        armConfig = new SparkFlexConfig();
        armConfig.inverted(true);
        armConfig.encoder
            .positionConversionFactor(1)
            .velocityConversionFactor(1);
        armConfig.closedLoop
            .feedbackSensor(FeedbackSensor.kAbsoluteEncoder)
            .p(3)
            .i(0)
            .d(1)
            .outputRange(-1, 1)
            .p(0.0001, ClosedLoopSlot.kSlot1)
            .i(0, ClosedLoopSlot.kSlot1)
            .d(0, ClosedLoopSlot.kSlot1)
            .velocityFF(1.0 / 5767, ClosedLoopSlot.kSlot1)
            .outputRange(-1, 1, ClosedLoopSlot.kSlot1);

        armController = new PID(1, -1, .4, .025, 0);

        armConfig.closedLoop.maxMotion
            .maxVelocity(1000)
            .maxAcceleration(1000)
            .allowedClosedLoopError(0.001)
            .maxAcceleration(500, ClosedLoopSlot.kSlot1)
            .maxVelocity(6000, ClosedLoopSlot.kSlot1)
            .allowedClosedLoopError(0.001, ClosedLoopSlot.kSlot1);

        armMotor.configure(armConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters); 
        intakeEncoder.setPosition(0);
        armEncoder.setPosition(0);

        armThread = new Thread(() -> {
            while (true) {
                if (!Robot.teleop) {
                    armController.resetLt();
                    continue;
                }

                double throttle = armController.calculate(armSetpoint, armEncoder.getPosition());
                armMotor.set(throttle);

                //System.out.println(armEncoder.getPosition() + ", " + armSetpoint + ", " + throttle);
            }
        });
        //armThread.start();
    }

    public void moveArm(double targetPosition) {
        // armSetpoint = targetPosition;
        armMotor.set(targetPosition);
        //armClosedLoopController.setReference(targetPosition, ControlType.kPosition);
    }

    public void stopArm() {
        armMotor.stopMotor();
    }

    public void setSpeed(double speed) {
        intakeMotor.set(speed);
    }

    public void stop() {
        intakeMotor.set(0);
    }

    public double getAbsolutePosition() {
        return armEncoder.getPosition();
    }
}
