// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Elevator;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.util.Constants.ElevatorConstants;
import frc.robot.util.Constants.ElevatorConstants.ElevatorConfigs.Positions;

public class ElevatorPIDCommand extends Command {
  private final Elevator mElevatorSubsystem;
  private double mSetpoint;
  private final ProfiledPIDController mProfiledPIDController;
  private final ElevatorFeedforward mElevatorFeedforward;

  private final boolean IS_TUNING = true;

  public ElevatorPIDCommand(Positions pSetpoint, Elevator pElevatorSubsystem) {
    this(pSetpoint.getPos(), pElevatorSubsystem);
  }

  public ElevatorPIDCommand(double pSetpoint, Elevator pElevatorSubsystem) {
    this.mElevatorSubsystem = pElevatorSubsystem;
    if (IS_TUNING) {
      this.mSetpoint = SmartDashboard.getNumber("TunableNumbers/Elevator/Setpoint", 0);
    } else {
      this.mSetpoint =
          MathUtil.clamp(
              pSetpoint, ElevatorConstants.kReverseSoftLimit, ElevatorConstants.kForwardSoftLimit);
    }

    this.mProfiledPIDController =
        new ProfiledPIDController(
            ElevatorConstants.kP,
            ElevatorConstants.kI,
            ElevatorConstants.kD,
            new TrapezoidProfile.Constraints(
                ElevatorConstants.kMaxVelocity, ElevatorConstants.kMaxAcceleration));
    this.mElevatorFeedforward =
        new ElevatorFeedforward(
            ElevatorConstants.kS, ElevatorConstants.kG, ElevatorConstants.kV, ElevatorConstants.kA);
    this.mProfiledPIDController.setTolerance(ElevatorConstants.kTolerance);

    SmartDashboard.putNumber("Elevator/PID Output", 0.0);
    addRequirements(pElevatorSubsystem);
  }

  @Override
  public void initialize() {
    mProfiledPIDController.reset(getMeasurement());
    System.out.println(
        String.format(
            "<<< %s - %s is STARTING! >>>\n",
            this.getClass().getSimpleName(), mProfiledPIDController.getClass().getSimpleName()));
  }

  @Override
  public void execute() {
    // double potentiometerReading = Potentiometer.getPotentiometer();
    // mProfiledPIDController.setP(potentiometerReading);

    if (IS_TUNING) {
      mSetpoint = SmartDashboard.getNumber("TunableNumbers/Elevator/Setpoint", 0);
    }

    double calculatedFeedforward = mElevatorFeedforward.calculate(0);
    double calculatedProfilePID = mProfiledPIDController.calculate(getMeasurement(), mSetpoint);
    double calculatedOutput = calculatedFeedforward + calculatedProfilePID;
    mElevatorSubsystem.setMotorVoltage(calculatedOutput);

    SmartDashboard.putNumber("Elevator ProfilePID Output", calculatedOutput);
    SmartDashboard.putNumber("Elevator/Target Pos", mSetpoint);
  }

  @Override
  public void end(boolean interrupted) {
    mElevatorSubsystem.setMotorVoltage(0);
    System.out.println(
        String.format(
            "<<< %s - %s is ENDING! >>>\n",
            this.getClass().getSimpleName(), mProfiledPIDController.getClass().getSimpleName()));
  }

  @Override
  public boolean isFinished() {
    // return mProfiledPIDController.atGoal();
    return false;
  }

  private double getMeasurement() {
    return mElevatorSubsystem.getEncoderMeasurement();
  }
}