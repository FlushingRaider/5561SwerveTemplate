package frc.robot.subsystems.Claw;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.SuperStructure.SuperstructureState;

import org.littletonrobotics.junction.Logger;

public class GripperSUB extends SubsystemBase {
//TODO - Need to add the arm degrees and the speed.
	public enum State {
		STOWED(0, 0),
		CORAL_STATION(0, 0),
		// ALGAE_GROUND(0, 0),
		ALGAE_REMOVAL(0, 0),
		ALGAE_PROCESSOR(0, 0),
		// ALGAE_BARGE(0, 0),
		L1_SCORING(0, 0),
		L2_L3_SCORING(0, 0),
		L4_SCORING(0, 0);

		private final int deg;
		private final double speed;

		State(int deg, double speed) {
			this.deg = deg;
			this.speed = speed;
		}

		public int getDeg() {
			return deg;
		}

		public double getSpeed() {
			return speed;
		}
	}

	private final GripperBaseIO io;

	private final GripperBaseIO.GripperInputs inputs = new GripperBaseIO.GripperInputs();
	// private SuperstructureState.State localState = SuperstructureState.IDLE;

	public GripperSUB(GripperBaseIO io) {
		this.io = io;
	}

	@Override
	public void periodic() {

		// Update inputs
		io.updateInputs(inputs);

		// Process inputs
		Logger.processInputs("Intake", inputs);
	}

	public Command setState(State state) {
		return new InstantCommand(
				() -> {
					io.setArmAngle(state.getDeg());
					io.setIntakeSpeed(state.getSpeed());
				});
	}

	// 		public void updateLocalState(SuperstructureState.State newLocalState) {
	// 	localState = newLocalState;
	// }

	// public SuperstructureState.State getCurrentLocalState() {
	// 	return localState;
	// }

	public boolean getSensorState() {
		return inputs.toggleSensor;
	}
}