// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Elevator;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.util.Constants.ElevatorConstants;

public class ElevatorFFCommand extends Command {
  private final Elevator mElevatorSubsystem;
  private ElevatorFeedforward mElevatorFeedforward;

  public ElevatorFFCommand(Elevator pElevatorSubsystem) {
    this.mElevatorSubsystem = pElevatorSubsystem;
    this.mElevatorFeedforward =
        new ElevatorFeedforward(
            ElevatorConstants.kS, ElevatorConstants.kG, ElevatorConstants.kV, ElevatorConstants.kA);
    addRequirements(pElevatorSubsystem);
  }

  @Override
  public void initialize() {
    this.mElevatorFeedforward =
        new ElevatorFeedforward(
            ElevatorConstants.kS, ElevatorConstants.kG, ElevatorConstants.kV, ElevatorConstants.kA);
    System.out.println(
        String.format(
            "<<< %s - %s is STARTING! >>>\n",
            this.getClass().getSimpleName(), mElevatorFeedforward.getClass().getSimpleName()));
  }

  @Override
  public void execute() {
    double calculatedOutput = mElevatorFeedforward.calculate(0);

    mElevatorSubsystem.setMotorVoltage(calculatedOutput);

    SmartDashboard.putNumber("Elevator FEEDFORWARD", calculatedOutput);
  }

  @Override
  public void end(boolean interrupted) {
    System.out.println(
        String.format(
            "<<< %s - %s is ENDING! >>>\n",
            this.getClass().getSimpleName(), mElevatorFeedforward.getClass().getSimpleName()));
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}