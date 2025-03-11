package frc.robot.subsystems.Claw;

import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public interface GripperBaseIO{
@AutoLog
	public static class GripperInputs implements LoggableInputs {

		public double armAngleDegrees = 0.0;
		public double armMotorCurrent = 0.0;
		public double wheelMotorCurrent = 0.0;
		public double wheelRPM = 0.0;
		public boolean toggleSensor = false;
		public double distanceSensorCM = 0.0;

		@Override
		public void toLog(LogTable table) {
			table.put("ArmAngleDegrees", armAngleDegrees);
			table.put("ArmMotorCurrent", armMotorCurrent);
			table.put("wheelMotorCurrent", wheelMotorCurrent);
			table.put("wheelRPM", wheelRPM);
			table.put("toggleSensor", toggleSensor);
			table.put("distanceSensorCM", distanceSensorCM);
		}

		@Override
		public void fromLog(LogTable table) {
			armAngleDegrees = table.get("ArmAngleDegrees", armAngleDegrees);
			armMotorCurrent = table.get("ArmMotorCurrent", armMotorCurrent);
			wheelMotorCurrent = table.get("wheelMotorCurrent", wheelMotorCurrent);
			wheelRPM = table.get("wheelRPM", wheelRPM);
			toggleSensor = table.get("toggleSensor", toggleSensor);
			distanceSensorCM = table.get("distanceSensorCM", distanceSensorCM);
		}
	}

	/** Updates the set of loggable inputs. */
	public void updateInputs(GripperInputs inputs);

	public void setArmAngle(double angle);

	public void setIntakeSpeed(double speed);
}