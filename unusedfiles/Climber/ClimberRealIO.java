package frc.robot.subsystems.Climber;

import com.revrobotics.spark.SparkLowLevel.MotorType;
// import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.LimitSwitchConfig;
// import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.robot.subsystems.Climber.ClimberBaseIO.ClimberInputs;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

// import edu.wpi.first.wpilibj.DigitalInput;
// import edu.wpi.first.wpilibj.DutyCycleEncoder;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import frc.robot.miscConstants;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
// import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLimitSwitch;


public class ClimberRealIO{ //implements ClimberBaseIO{

  public SparkMax ClimberMotor;
  public SparkMaxConfig ClimberConfig;
  public SparkClosedLoopController closedLoopController;
  public RelativeEncoder climberencoder;
  private final SparkLimitSwitch limitSwitch;
  
public ClimberRealIO(){


  ClimberMotor = new SparkMax(14, MotorType.kBrushless);
  climberencoder = ClimberMotor.getEncoder();

 //  SparkMaxConfig globalConfig = new  SparkMaxConfig();
  SparkMaxConfig config = new SparkMaxConfig();
  closedLoopController = ClimberMotor.getClosedLoopController();

  config.encoder
      .positionConversionFactor(1)
      .velocityConversionFactor(1);

  config
  .smartCurrentLimit(20)
  .idleMode(IdleMode.kBrake);

  config.limitSwitch
  .forwardLimitSwitchEnabled(true);
  limitSwitch = ClimberMotor.getForwardLimitSwitch();
    
  
   config.closedLoop
      .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
      // Set PID values for position control. We don't need to pass a closed loop
      // slot, as it will default to slot 0.
      .p(0.0001)
      .i(0)
      .d(0)
      .outputRange(-1, 1);
      // // Set PID values for velocity control in slot 1
      // .p(0.001, ClosedLoopSlot.kSlot1)
      // .i(0, ClosedLoopSlot.kSlot1)
      // .d(0, ClosedLoopSlot.kSlot1)
      // .velocityFF(1.0 / 5767, ClosedLoopSlot.kSlot1)
      // .outputRange(-1, 1, ClosedLoopSlot.kSlot1);

      ClimberMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

      
}

	public void GetEncoderPOS(double climberENCpos){
    
    climberencoder.getPosition();
		
	}
}