package frc.robot.subsystems.Claw;


import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.util.Constants.ClawConstants.Wrist;

public class ClawFFCommand extends Command {
  private final Claw mClawSubsystem;
  private final ArmFeedforward mClawFeedforward;
  private double pivotPosition;

  public ClawFFCommand(Claw pClawSubsystem) {
    this.mClawSubsystem = pClawSubsystem;
    this.mClawFeedforward = new ArmFeedforward(Wrist.kS, Wrist.kG, Wrist.kV, Wrist.kA);
    this.pivotPosition = SmartDashboard.getNumber("Pivot/Position", 0.0);
    addRequirements(pClawSubsystem);
  }

  @Override
  public void initialize() {
    System.out.println(
        String.format(
            "<<< %s - %s is STARTING :D >>>\n",
            this.getClass().getSimpleName(), mClawFeedforward.getClass().getSimpleName()));
  }

  @Override
  public void execute() {
    double calculatedOutput = mClawFeedforward.calculate(getMeasurement() + pivotPosition, 0);

    mClawSubsystem.setWrist(calculatedOutput);

    SmartDashboard.putNumber("Wrist/FF Calculation", calculatedOutput);
  }

  @Override
  public void end(boolean interrupted) {
    System.out.println(
        String.format(
            "<<< %s - %s is ENDING :C >>>\n",
            this.getClass().getSimpleName(), mClawFeedforward.getClass().getSimpleName()));
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  private double getMeasurement() {
    return mClawSubsystem.getEncoderMeasurement();
  }
}
