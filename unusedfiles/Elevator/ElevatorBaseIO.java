// Copyright (c) 2024 - 2025 : FRC 2106 : The Junkyard Dogs
// https://www.team2106.org

// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package frc.robot.subsystems.Elevator;

import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

///
///
/// //
public interface ElevatorBaseIO {

	@AutoLog
	public static class ElevatorInputs implements LoggableInputs {

		public double heightIN = 0.0;
		public double velocityRPM = 0.0;
		public double ElevatormotorLeftCurrent = 0.0;
		public double ElevatormotorRightCurrent = 0.0;

		@Override
		public void toLog(LogTable table) {
			table.put("heightM", heightIN);
			table.put("velocityMPS", velocityRPM);
			table.put("leftMotorCurrent", ElevatormotorLeftCurrent);
			table.put("rightMotorCurrent", ElevatormotorRightCurrent);
		}

		@Override
		public void fromLog(LogTable table) {
			heightIN = table.get("heightM", heightIN);
			velocityRPM = table.get("velocityMPS", velocityRPM);
			ElevatormotorLeftCurrent = table.get("leftMotorCurrent", ElevatormotorLeftCurrent);
			ElevatormotorRightCurrent = table.get("rightMotorCurrent", ElevatormotorRightCurrent);
		}
	}

	/** Updates the set of loggable inputs. */
	public void updateInputs(ElevatorInputs inputs);

	public void setPositionIN(double positionIN);
}