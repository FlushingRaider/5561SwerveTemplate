// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Claw;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.util.Constants.ClawConstants.Wrist;
import frc.robot.util.Constants.ClawConstants.Wrist.WristPositions;

public class ClawPIDCommand extends Command {
  private final Claw mClawSubsystem;
  private double mSetpoint;
  private final PIDController mProfiledPIDController;
  private final ArmFeedforward mClawFeedforward;
  private boolean IS_TUNING = true;
  private double elevatorSetpoint;
  private double pivotPosition;

  public ClawPIDCommand(WristPositions pSetpoint, Claw pClawSubsystem) {
    this(false, pSetpoint.getPos(), pClawSubsystem);
  }

  public ClawPIDCommand(boolean isTuning, double pSetpoint, Claw pClawSubsystem) {
    this.mClawSubsystem = pClawSubsystem;
    this.IS_TUNING = isTuning;

    this.mProfiledPIDController = new PIDController(Wrist.kP, 0.0, Wrist.kD);
    // new ProfiledPIDController(
    //     Wrist.kP,
    //     0.0,
    //     Wrist.kD,
    //     new TrapezoidProfile.Constraints(Wrist.kMaxVelocity, Wrist.kMaxAcceleration));
    this.mProfiledPIDController.setTolerance(Wrist.kTolerance);
    this.mClawFeedforward = new ArmFeedforward(Wrist.kS, Wrist.kG, Wrist.kV, Wrist.kA);

    if (IS_TUNING) {
      this.mSetpoint = SmartDashboard.getNumber("TunableNumbers/Tuning/Wrist/Setpoint", 0);
      System.out.println(
          String.format(
              "<<< %s - %s is in TUNING mode. >>>\n",
              this.getClass().getSimpleName(), mProfiledPIDController.getClass().getSimpleName()));
    } else {
      this.mSetpoint = MathUtil.clamp(pSetpoint, Wrist.kReverseSoftLimit, Wrist.kForwardSoftLimit);
    }

    this.elevatorSetpoint = SmartDashboard.getNumber("Elevator/Setpoint", 0);
    this.pivotPosition = SmartDashboard.getNumber("Pivot/Position", 0.0);
    SmartDashboard.putNumber("Tuning/Wrist/Output Value", 0.0);
    SmartDashboard.putNumber("Wrist/Setpoint", pSetpoint);

    addRequirements(pClawSubsystem);
  }

  @Override
  public void initialize() {
    mProfiledPIDController.setPID(Wrist.kP, 0, Wrist.kD);
    // mProfiledPIDController.setConstraints(
    //     new TrapezoidProfile.Constraints(Wrist.kMaxVelocity, Wrist.kMaxAcceleration));
    // mProfiledPIDController.reset(getMeasurement());
    mProfiledPIDController.reset();
    System.out.println(
        String.format(
            "<<< %s - %s is STARTING :D >>>\n",
            this.getClass().getSimpleName(), mProfiledPIDController.getClass().getSimpleName()));
  }

  @Override
  public void execute() {
    elevatorSetpoint = SmartDashboard.getNumber("Elevator/Setpoint", 0);
    if (IS_TUNING) {
      mSetpoint = SmartDashboard.getNumber("TunableNumbers/Wrist/Tunable Setpoint", 0);
    }
    if ((elevatorSetpoint <= 5) && (mSetpoint < 10)) {
      mSetpoint = 10;
      System.out.println(
          String.format(
              "<<< %s - %s is going too far down! >>>\n",
              this.getClass().getSimpleName(), mProfiledPIDController.getClass().getSimpleName()));
    }
    System.out.println("Claw setpoint: " + mSetpoint);
    System.out.println(
        "Claw Tunable Setpoint: "
            + SmartDashboard.getNumber("TunableNumbers/Wrist/Tunable Setpoint", 0));
    SmartDashboard.putNumber("Wrist/Setpoint", mSetpoint);
    double calculatedFeedforward =
        mClawFeedforward.calculate(getMeasurement() + pivotPosition, 0.0);
    double calculatedProfilePID =
        mProfiledPIDController.calculate(getMeasurement() + pivotPosition, mSetpoint);
    double calculatedOutput = calculatedProfilePID + calculatedFeedforward;
    mClawSubsystem.setWrist(calculatedOutput);
    SmartDashboard.putNumber("Wrist/Output Value", calculatedOutput);
  }

  @Override
  public void end(boolean interrupted) {
    mClawSubsystem.setWrist(0);
    System.out.println(
        String.format(
            "<<< %s - %s is ENDING :C >>>\n",
            this.getClass().getSimpleName(), mProfiledPIDController.getClass().getSimpleName()));
  }

  @Override
  public boolean isFinished() {
    return false; // mProfiledPIDController.atSetpoint();
  }

  private double getMeasurement() {
    return mClawSubsystem.getEncoderMeasurement();
  }
}