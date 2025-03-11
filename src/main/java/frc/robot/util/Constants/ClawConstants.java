package frc.robot.util.Constants;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

// import edu.wpi.first.wpilibj.motorcontrol.Spark;

import com.revrobotics.spark.config.SparkMaxConfig;

public class ClawConstants {

  public static class Roller {

    public static int kRollerID = 13;
    public static MotorType kMotorType = MotorType.kBrushless;
    public static IdleMode kIdleMode = IdleMode.kBrake;
    public static int kCurrentLimit = 40;
    public static double kP = 0.01; // TODO: Configure me!
    public static double kD = 0.0; // TODO: Configure me!
    public static double kVelocityFF = 0.0; // TODO: Configure me!

    public static double kMaxAcceleration = 250;
    public static double kMaxVelocity = 1000;
    public static double kTolerance = 1.75;

    public static double kForwardSoftLimit = 10014;
    public static double kReverseSoftLimit = 0;

    public static double kEncoderOffsetRev = 0; // In revolutions

    public static double kGearRatio = 1;

    public static double kPositionConversionFactor =
        kGearRatio;
            //* 360.0; // (Drum Circumference * Final Gear Ratio) / One Encoder Revolution (if its 1:1
    // with motor shaft)  // TODO: Configure me!
    public static double kVelocityConversionFactor = kPositionConversionFactor / 60.0; // RPM -> MPS

    public static final SparkMaxConfig kRollerConfig = new SparkMaxConfig();
    public static final SparkMaxConfig kAngleConfig = new SparkMaxConfig();

    static {
        kRollerConfig
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(kCurrentLimit);
        // .voltageCompensation(10)
        // .openLoopRampRate(0.5);

        kRollerConfig
          .softLimit
          .forwardSoftLimit(kForwardSoftLimit)
          .forwardSoftLimitEnabled(false)
          .reverseSoftLimit(kReverseSoftLimit)
          .reverseSoftLimitEnabled(false);

      kRollerConfig
        .limitSwitch
        .forwardLimitSwitchEnabled(false)
        .reverseLimitSwitchEnabled(false);

      kRollerConfig
          .absoluteEncoder
          .positionConversionFactor(kPositionConversionFactor)
          .velocityConversionFactor(kVelocityConversionFactor)
          .zeroOffset(kEncoderOffsetRev);

      // kWristConfig
      //     .closedLoop
      //     .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
      //     .pidf(kP, 0.0, kD, kVelocityFF)
      //     .outputRange(-1, 1);

      // kWristConfig
      //     .closedLoop
      //     .maxMotion
      //     .maxVelocity(kMaxVelocity)
      //     .maxAcceleration(kMaxAcceleration)
      //     .allowedClosedLoopError(kTolerance);
    }
  }

  public static class Wrist {
    public static int kMotorID = 12;
    public static MotorType kMotorType = MotorType.kBrushless;
    public static IdleMode kIdleMode = IdleMode.kBrake;
    public static int kCurrentLimit = 20;
    public static double kP = 0.1; // TODO: Configure me!
    public static double kD = 0.0; // TODO: Configure me!
    public static double kVelocityFF = 0.0; // TODO: Configure me!

    public static double kMaxAcceleration = 10;
    public static double kMaxVelocity = 10;
    public static double kTolerance = 1;

    public static double kForwardSoftLimit = 2.8;
    public static double kReverseSoftLimit = 7.2;
    public static double kGearRatio = 1 / 49.0;

    public static double kEncoderOffsetRev = 0.0623246; // In revolutions

    public static double kPositionConversionFactor = kGearRatio * 360.0;
    public static double kVelocityConversionFactor = kPositionConversionFactor / 60.0; // RPM -> MPS

    public static double kS = 0.0;
    public static double kG = 0.21;
    public static double kV = 2.05;
    public static double kA = 0.0;

    public static final SparkMaxConfig kWristConfig = new SparkMaxConfig();

    public static final double WristPosElevatorSafeyThreshTop = 6.19;
    public static final double WristPosElevatorSafeyThreshBottom = 4.83;

    public static final double WristElevatorPosClearHightTop = 16;
    public static final double WristElevatorPosClearHightBottom = 27;

    public static final double WristAngleLimit = 4.9;
    public static final double WristAngleLimitHight = 36.9; 

    public enum ClawRollerVolt {
      STOPPED(0),
      INTAKE_CORAL(0.5),
      INTAKE_ALGAE(0.5),
      OUTTAKE_REEF(-0.5),
      OUTTAKE_BARGE(-0.5);

      public final double voltage;

      private ClawRollerVolt(double voltage) {
        this.voltage = voltage;
      }

      public double get() {
        return this.voltage;
      }
    };

    public enum WristPositions {
      ZERO(6.79),
      Home(3.03),
      Floor(2.84),
      Intake(6.73),
      L1_L2_Coral(6.21),
      Coral_updown(5.18),
      Algae_Drive(3.06),
      Max(5.8),
      
      Elevator_Threh(WristPosElevatorSafeyThreshBottom);


      public final double position;

      private WristPositions(double position) {
        this.position = position;
      }

      public double getPos() {
        return this.position;
      }
    };

    static {
      kWristConfig
      .idleMode(kIdleMode)
      .smartCurrentLimit(kCurrentLimit);

      // kWristConfig
      //     .softLimit
      //     .forwardSoftLimit(kForwardSoftLimit)
      //     .reverseSoftLimit(kReverseSoftLimit);

      kWristConfig
          .absoluteEncoder
          .positionConversionFactor(kPositionConversionFactor)
          .velocityConversionFactor(kVelocityConversionFactor)
          .zeroOffset(kEncoderOffsetRev);

      kWristConfig
          .closedLoop
          .feedbackSensor(FeedbackSensor.kAbsoluteEncoder)
          .pidf(kP, 0.0, kD, kVelocityFF)
          .outputRange(-0.9, 0.9);

      // kWristConfig
      //     .closedLoop
      //     .maxMotion
      //     .maxVelocity(kMaxVelocity)
      //     .maxAcceleration(kMaxAcceleration)
      //     .allowedClosedLoopError(kTolerance);
    }
  }
}