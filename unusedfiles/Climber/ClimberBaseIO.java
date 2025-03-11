package frc.robot.subsystems.Climber;

import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public interface ClimberBaseIO{
    


@AutoLog
	public static class ClimberInputs implements LoggableInputs {

		public double ClimberPos = 0.0;
		public double MotorRPM = 0.0;

		@Override
		public void toLog(LogTable table) {
			table.put("ArmAngleDegrees", ClimberPos);
			table.put("MotorRPM", MotorRPM);
		}

		@Override
		public void fromLog(LogTable table) {
			ClimberPos = table.get("ArmAngleDegrees", ClimberPos);
			MotorRPM = table.get("MotorRPM", MotorRPM);
		}
	}

	/** Updates the set of loggable inputs. */
	public void updateInputs(ClimberInputs inputs);

	public void setClimberPos(double Pos);

    public void setClimberSpeed(double speed);

    public void stopClimber();
}