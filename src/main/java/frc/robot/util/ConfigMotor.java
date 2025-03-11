package frc.robot.util;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

public class ConfigMotor {
  public static void configSparkMax(
      SparkMax motor,
      boolean motorInverted,
      int currentLimit,
      IdleMode idleMode,
      RelativeEncoder encoder,
      double conversionFactor,
      int CPR) {
    var motorConfig = new SparkMaxConfig();

    motorConfig
        .smartCurrentLimit(currentLimit)
        .inverted(motorInverted)
        .idleMode(idleMode)
        .encoder
        .positionConversionFactor(conversionFactor)
        .countsPerRevolution(CPR);

    motor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    encoder.setPosition(0);
  }
}