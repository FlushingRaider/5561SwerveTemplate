package frc.robot.SuperStructure;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SuperStructure.SuperstructureState.State;
// import frc.robot.subsystems.Claw.GripperSUB;
// import frc.robot.commands.generic.CMD_Superstructure;
// import frc.robot.subsystems.Elevator.ElevatorSUB;

import org.littletonrobotics.junction.Logger;

public class SUB_Superstructure extends SubsystemBase {
	private SuperstructureState.State currentSuperstructureState = SuperstructureState.IDLE;
	private SuperstructureState.State previousSuperstructureState = SuperstructureState.IDLE;

	public State currentDynamicEjectState =
			SuperstructureState.createState("EJECT_DYNAMIC", 0.5, 135, 18);

	// public GripperSUB intake;
	// public ElevatorSUB elevator;

	private boolean previousIntakeSensorState = false;

	// public SUB_Superstructure(GripperSUB intak){ //ElevatorSUB elevator) {
	// 	this.intake = intake;
	// }
		// this.elevator = elevator;	}

	public void updateSuperstructureState(SuperstructureState.State newSuperstructureState) {
		previousSuperstructureState = currentSuperstructureState;
		currentSuperstructureState = newSuperstructureState;

		// elevator.updateLocalState(currentSuperstructureState);
		// intake.updateLocalState(currentSuperstructureState);

		Logger.recordOutput("Superstructure/State", currentSuperstructureState.toString());
		Logger.recordOutput("Superstructure/Name", currentSuperstructureState.getName());
		Logger.recordOutput("Superstructure/HeightIN", currentSuperstructureState.getheightIN());
		Logger.recordOutput("Superstructure/Deg", currentSuperstructureState.getDeg());
		Logger.recordOutput("Superstructure/Speed", currentSuperstructureState.getSpeed());
	}

	public State setAndGetEjectState(double newWheelSpeed) {
		currentDynamicEjectState =
				SuperstructureState.createState(
						"EJECT_DYNAMIC",
						currentSuperstructureState.getheightIN(),
						currentSuperstructureState.getDeg(),
						newWheelSpeed);
		return currentDynamicEjectState;
	}

	public SuperstructureState.State getCurrentSuperstructureState() {
		return currentSuperstructureState;
	}

	@Override
	public void periodic() {

		// Check for sensor state change from false to true
		if (!previousIntakeSensorState
				// && intake.getSensorState()
				&& DriverStation.isEnabled()
				&& currentSuperstructureState == SuperstructureState.CORAL_STATION) {
			// CommandScheduler.getInstance()
			// 		.schedule(new CMD_Superstructure(this, SuperstructureState.IDLE));
		}

		/*

		if (previousIntakeSensorState
				&& !intake.getSensorState()
				&& DriverStation.isEnabled()
				&& (previousSuperstructureState == SuperstructureState.L1_SCORING
						|| previousSuperstructureState == SuperstructureState.L2_SCORING
						|| previousSuperstructureState == SuperstructureState.L3_SCORING
						|| previousSuperstructureState == SuperstructureState.L4_SCORING)
				&& currentSuperstructureState == currentDynamicEjectState) {
			CommandScheduler.getInstance()
					.schedule(new CMD_Superstructure(this, SuperstructureState.CORAL_STATION));
		}
					*/

		previousIntakeSensorState = intake.getSensorState();
	}
}