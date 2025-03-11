package frc.robot.subsystems.Intake;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

public class IntakeConstants {
  public static class Funnel {
    public static int kMotorID = 15;
    public static MotorType kMotorType = MotorType.kBrushless;
    public static IdleMode kIdleMode = IdleMode.kCoast;
    public static int kCurrentLimit = 30;

    public static final SparkMaxConfig kFunnelConfig = new SparkMaxConfig();

    static {
      kFunnelConfig.idleMode(kIdleMode).smartCurrentLimit(kCurrentLimit);
    }
  }

  public static class OTBIntakeConstants {
    // public static int kLeftPivotID = 51;
    // public static int kRightPivotID = 53;
    // public static int kLeftRollerID = 52;
    // public static int kRightRollerID = 54;
    // public static int kEncoderDIOPort = 3; // TO DO: CONFIGURE ME!

    // public static MotorType kMotorType = MotorType.kBrushless;
    // public static IdleMode kPivotIdleMode = IdleMode.kBrake;
    // public static IdleMode kRollerIdleMode = IdleMode.kCoast;
    // public static int kRollerCurrentLimit = 80;
    // public static int kPivotCurrentLimit = 60;

    // public static double kPositionConversionFactor = 360;
    // public static double kVelocityConversionFactor = kPositionConversionFactor / 60;

    // public static double kLeftEncoderOffsetRev = 0;
    // public static double kRightEncoderOffsetRev = 0;

    // public static final SparkMaxConfig kLeftPivotConfig = new SparkMaxConfig();
    // public static final SparkMaxConfig kRightPivotConfig = new SparkMaxConfig();
    // public static final SparkMaxConfig kRollerConfig = new SparkMaxConfig();

    // public static double kTolerance = 1;
    // public static double kP = 0;
    // public static double kD = 0;
    // public static double kForwardSoftLimit = 180; // TO DO: CONFIGURE ME!
    // public static double kReverseSoftLimit = -180; // TO DO: CONFIGURE ME!

    enum Positions {
      STOWED(0),
      INTAKING(40);
      public final double position;

      private Positions(double position) {
        this.position = position;
      }

      public double getPos() {
        return this.position;
      }
    };

    static {
      // kLeftPivotConfig.idleMode(kPivotIdleMode).smartCurrentLimit(kCurrentLimit);
      // kRightPivotConfig.idleMode(kPivotIdleMode).smartCurrentLimit(kPivotCurrentLimit);
      // kRollerConfig.idleMode(kRollerIdleMode).smartCurrentLimit(kRollerCurrentLimit);
      // kLeftPivotConfig
      //     .absoluteEncoder
      //     .positionConversionFactor(kPositionConversionFactor)
      //     .velocityConversionFactor(kVelocityConversionFactor)
      //     .zeroOffset(kLeftEncoderOffsetRev);
    }
  }
}
