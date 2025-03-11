package frc.robot.subsystems.Elevator;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SoftLimitConfig;
// import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.robot.miscConstants;

import edu.wpi.first.wpilibj.DriverStation;
// import frc.robot.constants.RobotConstants;

// import edu.wpi.first.wpilibj.Encoder;
// import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkMax;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

//REVIEW - ADD THROUGH BORE ENCODER

public class ElevatorRealIO implements ElevatorBaseIO {

	private SparkMax ElevatormotorLeft;
	private SparkMax ElevatormotorRight;
	private DigitalInput ThroughBoreEncoderDIO;
	private DutyCycleEncoder ThroughBoreEncoder;
	public double ElevatorENCPOS;


	public ElevatorRealIO() {

		ElevatormotorLeft = new SparkMax(30, MotorType.kBrushless);
		ElevatormotorRight = new SparkMax(29, MotorType.kBrushless);

		ThroughBoreEncoderDIO = new DigitalInput(miscConstants.ThroughBoreEncoderDIOPort);
		ThroughBoreEncoder = new DutyCycleEncoder(ThroughBoreEncoderDIO);

		SparkMaxConfig globalConfig = new  SparkMaxConfig();
		SparkMaxConfig ElevatorLeftConfig = new SparkMaxConfig();
		SparkMaxConfig ElevatorRightConfig = new SparkMaxConfig();

		
		globalConfig
		.smartCurrentLimit(40)
		.idleMode(IdleMode.kBrake);

		ElevatorLeftConfig
		.apply(globalConfig);
		ElevatorLeftConfig.closedLoop
      .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
      // Set PID values for position control. We don't need to pass a closed loop
      // slot, as it will default to slot 0.
      .p(0.0001)
      .i(0)
      .d(0)
      .outputRange(-1, 1);

		ElevatorRightConfig
		.apply(globalConfig)
		.follow(ElevatormotorLeft)
		.inverted(true);
	
		ElevatorRightConfig.closedLoop
      .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
      // Set PID values for position control. We don't need to pass a closed loop
      // slot, as it will default to slot 0.
      .p(0.0001)
      .i(0)
      .d(0)
      .outputRange(-1, 1);

		ElevatormotorLeft.configure(globalConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
		ElevatormotorRight.configure(ElevatorRightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

	}
	
	
	public void GetEncoderPOS(double ElevatorENCPOS){
		// final DutyCycleEncoder ElevatorENC = new DutyCycleEncoder(0);
		DutyCycleEncoder ElevatorENC = new DutyCycleEncoder(0, 4.0, 2.0);
		// Gets the rotation
		ElevatorENC.get();
		ElevatorENC.isConnected();
		ElevatorENC.setDutyCycleRange(ElevatorENCPOS, ElevatorENCPOS);
		ElevatorENC.close();
		
	}


	@Override
	public void updateInputs(ElevatorInputs inputs) {
		inputs.heightIN = ElevatormotorLeft.getEncoder().getPosition();
		inputs.velocityRPM = ElevatormotorLeft.getEncoder().getVelocity();
		inputs.ElevatormotorLeftCurrent = ElevatormotorLeft.getOutputCurrent();
		inputs.ElevatormotorRightCurrent = ElevatormotorRight.getOutputCurrent();

		
	}

	@Override
	public void setPositionIN(double positionIN) {
		
		positionIN = ElevatormotorLeft.getAbsoluteEncoder().getPosition();

		ElevatormotorLeft.set(positionIN);
		
	}
}