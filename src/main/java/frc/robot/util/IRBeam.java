package frc.robot.util;

import edu.wpi.first.wpilibj.DigitalInput;

public class IRBeam {

	private final DigitalInput Sensor;

	public IRBeam(int channel) {
		Sensor = new DigitalInput(channel);
	}

	public boolean getState() {
		return !Sensor.get();
	}
}