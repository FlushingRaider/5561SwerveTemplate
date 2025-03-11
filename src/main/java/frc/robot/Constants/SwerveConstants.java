package frc.robot.Constants;

import com.ctre.phoenix6.CANBus;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import swervelib.math.Matter;

public class SwerveConstants {
  public static CANBus Drive = new CANBus("Drive");
  public static final double ROBOT_MASS = (45) * 0.453592; // 32lbs * kg per pound
  public static final Matter CHASSIS    = new Matter(new Translation3d(0, 0, Units.inchesToMeters(8)), ROBOT_MASS);
  public static final double LOOP_TIME  = 0.13; //s, 20ms + 110ms sprk max velocity lag
  public static final double MAX_SPEED = Units.feetToMeters(14.5);//14.5);
  public static final double kMaxSpeedScalar = 0.4;
  public static final double kUnboostScalar = 0.1;
  // Maximum speed of the robot in meters per second, used to limit acceleration.

  public static final class DrivebaseConstants{
      // Hold time on motor brakes when disabled
      public static final double WHEEL_LOCK_TIME = 35; // seconds
  }
}
