package frc.robot.subsystems.Claw;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;

import frc.robot.util.IRBeam;

import com.revrobotics.spark.config.SparkMaxConfig;

public class GripperRealIO implements GripperBaseIO {

	private SparkMax AngleGripper;
	private SparkMax wheelMotor;

	private IRBeam toggleSensor;

	public GripperRealIO() {

		AngleGripper = new SparkMax(12, MotorType.kBrushless);
		wheelMotor = new SparkMax(13, MotorType.kBrushless);
		toggleSensor = new IRBeam(0);

		SparkMaxConfig wheelSparkMaxConfig = new SparkMaxConfig();
		wheelSparkMaxConfig.smartCurrentLimit(40);
		wheelMotor.configure(
				wheelSparkMaxConfig,
				SparkBase.ResetMode.kNoResetSafeParameters,
				SparkBase.PersistMode.kPersistParameters);

		SparkMaxConfig gripperAngleSparkMaxConfig = new SparkMaxConfig();
		gripperAngleSparkMaxConfig.closedLoop.feedbackSensor(FeedbackSensor.kAbsoluteEncoder);
		gripperAngleSparkMaxConfig.inverted(true);

		ClosedLoopConfig closedLoopConfig = new ClosedLoopConfig();

		closedLoopConfig.p(0.013);
		closedLoopConfig.i(0);
		closedLoopConfig.d(0);

		gripperAngleSparkMaxConfig.apply(closedLoopConfig);

		AngleGripper.configure(
			gripperAngleSparkMaxConfig,
			SparkBase.ResetMode.kNoResetSafeParameters,
			SparkBase.PersistMode.kPersistParameters);

	}

	@Override
	public void updateInputs(GripperInputs inputs) {

		inputs.armAngleDegrees = AngleGripper.getEncoder().getPosition();
		inputs.armMotorCurrent = AngleGripper.getOutputCurrent();
		inputs.wheelMotorCurrent = wheelMotor.getOutputCurrent();
		inputs.wheelRPM = wheelMotor.getEncoder().getVelocity();
		inputs.toggleSensor = toggleSensor.getState();
		inputs.distanceSensorCM = 0;
	}

	@Override
	public void setArmAngle(double angle) {
		AngleGripper.getClosedLoopController().setReference(angle, ControlType.kMAXMotionPositionControl);
	}

	@Override
	public void setIntakeSpeed(double speed) {
		wheelMotor.set(speed);
	}

	
}