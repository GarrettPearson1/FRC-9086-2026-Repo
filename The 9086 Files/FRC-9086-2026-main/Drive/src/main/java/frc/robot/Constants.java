// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

// DRIVE SUBSYSTEM CONSTANTS
// ========================================================
// ========================================================
public final class Constants {
  public static final class DriveConstants {
    // Driving Parameters - Note that these are not the maximum capable speeds of
    // the robot, rather the allowed maximum speeds
    public static final double kMaxSpeedMetersPerSecond = 5;                                // meters per second
    public static final double kMaxAngularSpeed = Math.PI / 1.0;                            // 360 degrees in 8 seconds

    // Chassis configuration
    public static final double kTrackWidth = Units.inchesToMeters(20.0);
    // Distance between centers of right and left wheels on robot
    public static final double kWheelBase = Units.inchesToMeters(20.0);
    // Distance between front and back wheels on robot

    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
        new Translation2d(kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

    // Angular offsets of the modules relative to the chassis in radians
    public static final double kFrontLeftChassisAngularOffset   =  -Math.PI / 2;
    public static final double kFrontRightChassisAngularOffset  =  0;
    public static final double kBackLeftChassisAngularOffset    = -Math.PI;
    public static final double kBackRightChassisAngularOffset   =  Math.PI / 2; // front right

    // SPARK MAX CAN IDs
    public static final int kFrontLeftDrivingCanId  =  2;
    public static final int kRearLeftDrivingCanId   =  4;
    public static final int kFrontRightDrivingCanId =  8;
    public static final int kRearRightDrivingCanId  =  6;

    public static final int kFrontLeftTurningCanId =  3;
    public static final int kRearLeftTurningCanId =   5;
    public static final int kFrontRightTurningCanId = 9;
    public static final int kRearRightTurningCanId =  7;

    // Boolean for Gyro 
    public static final boolean kGyroReversed = true;
  }

  // ARM SUBSYSTEM CONSTANTS
  // ========================================================
  // ======================================================== 
  public static final class ArmConstants {
    public static final int kArmPivotCanId = 10;                                 // Spark Flex with Absolute encoder
    public static final int kArmExtensionCanId = 11;                            // Spark Flex with Absolute encoder     // Spark Max with Absolute encoder
    public static final int kWristGripperCanId = 16;                  // Spark Max with Relative encoder
    
    // TARGET reference position
    public static final double kArmExtensionReferencePosition = 0.125;
  
    // TARGET encoder positions for handoff
    public static double kArmExtensionIntakePosition = 0.9;

    // TARGET encoder positions for scoring
    public static final double kArmPivotScorePosition = 0.55;

    // TARGET encoder positions for stow
    public static final double kArmPivotStowPosition = 0.995;
    public static final double kArmExtensionStowPosition = 0.55;
  }

  // MAX SWERVE MODULE CONSTANTS
  // ========================================================
  // ========================================================
  public static final class ModuleConstants {
    // The MAXSwerve module can be configured with one of three pinion gears: 12T,
    // 13T, or 14T. This changes the drive speed of the module (a pinion gear with
    // more teeth will result in a robot that drives faster).
    public static final int kDrivingMotorPinionTeeth = 14;

    // Calculations required for driving motor conversion factors and feed forward
    public static final double kDrivingMotorFreeSpeedRps = NeoMotorConstants.kFreeSpeedRpm / 60;
    public static final double kWheelDiameterMeters = 0.0762;
    public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;

    // 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15
    // teeth on the bevel pinion
    public static final double kDrivingMotorReduction = (45.0 * 22) / (kDrivingMotorPinionTeeth * 15);
    public static final double kDriveWheelFreeSpeedRps = (kDrivingMotorFreeSpeedRps * kWheelCircumferenceMeters) / kDrivingMotorReduction;
  }

  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;                                     //TODO: Confirm this is not redundant with firmware enforced deadband limit
    public static final int kScoringControllerPort = 999; 
    public static final double kDriveDeadband = 0.05;
  }

  public static final class IntakeConstants {
    public static final int kSpinningMotor = 11;
    public static final int kUpDownMotor = 10;
  }

  public static final class ClimbConstants {
    public static final int kForward = 12;
    public static final int kBackward = 14;
  }

  public static final class StephConstants {
    public static final int kPullerMotorId = 13;
    public static final int kShootingMotorId = 15;
  }

  public static final class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond = 1.0;                              //TODO: safety
    public static final double kMaxAccelerationMetersPerSecondSquared = 1.0;              //TODO: safety
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI / 1.0;          //TODO: safety
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI / 1.0;   //TODO  safety

    public static final double kPXController = 0.4;                                       //TODO:  needs tuning
    public static final double kPYController = 0.4;                                       //TODO: needs tuning
    public static final double kPThetaController = 0.5;                                   //TODO: needs tuning

    // Constraint for the motion profiled robot angle controller                       
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
        kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
  }

  public static final class NeoMotorConstants {
    public static final double kFreeSpeedRpm = 5676;
  }
}