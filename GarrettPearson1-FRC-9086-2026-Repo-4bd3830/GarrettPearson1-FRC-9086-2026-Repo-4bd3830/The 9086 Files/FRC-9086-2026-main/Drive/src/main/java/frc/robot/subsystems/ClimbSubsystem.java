package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimbSubsystem extends SubsystemBase {
    public final SparkMax forwardMotor;
    public final AbsoluteEncoder forwardEncoder;

    public final SparkMax backwardMotor;
    public final AbsoluteEncoder backwardEncoder;

    public ClimbSubsystem() {
        forwardMotor = new SparkMax(Constants.ClimbConstants.kForward, MotorType.kBrushless);
        backwardMotor = new SparkMax(Constants.ClimbConstants.kBackward, MotorType.kBrushless);

        forwardEncoder = forwardMotor.getAbsoluteEncoder();
        backwardEncoder = backwardMotor.getAbsoluteEncoder();
    }
}
