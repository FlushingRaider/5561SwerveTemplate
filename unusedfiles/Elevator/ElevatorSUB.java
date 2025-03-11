package frc.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SuperStructure.SuperstructureState;

import org.littletonrobotics.junction.Logger;

public class ElevatorSUB extends SubsystemBase {
//TODO - Need to add the height in meters.
	public enum State {
		STOWED(0),
		CORAL_STATION(0),
		//NOTE - No Ground intake planned.
		// ALGAE_GROUND(0),
		ALGAE_PROCESSOR(0),
		//NOTE - Barge may also be affected by the elevator height.
		// ALGAE_BARGE(0),
		L1_SCORING(0),
		L2_SCORING(0),
		L3_SCORING(0),
		//NOTE - L4 might not be able to be scored for Algae
		L4_SCORING(0);

		private final double heightIN;

		State(double heightIN) {
			this.heightIN = heightIN;
		}

		public double getHeightIN() {
			return heightIN;
		}
	}

	private final ElevatorBaseIO io;

	private final ElevatorBaseIO.ElevatorInputs inputs = new ElevatorBaseIO.ElevatorInputs();
	private SuperstructureState.State localState = SuperstructureState.IDLE;
	
	public ElevatorSUB(ElevatorBaseIO io) {
		this.io = io;
	}

	@Override
	public void periodic() {

		// Update inputs
		io.updateInputs(inputs);

		

		// Process inputs
		Logger.processInputs("Elevator", inputs);
		// Gets the distance traveled
		
	}

	public Command setState(State state) {
		return new InstantCommand(
				() -> {
					io.setPositionIN(state.getHeightIN());
				});
	}

		public void updateLocalState(SuperstructureState.State newLocalState) {
		localState = newLocalState;
	}

	public SuperstructureState.State getCurrentLocalState() {
		return localState;
	}
}
