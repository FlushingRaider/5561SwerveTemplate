// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.util.Constants.SwerveConstants;
import swervelib.SwerveDrive;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to each mode, as
 * described in the TimedRobot documentation. If you change the name of this class or the package after creating this
 * project, you must also update the build.gradle file in the project.
 */
public class Robot extends TimedRobot
{
  private boolean drivestationcolor;
  AddressableLED led;
  AddressableLEDBuffer ledBuffer;

  private static Robot   instance;
  private        Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  private Timer disabledTimer;

        // all hues at maximum saturation and half brightness
        private final LEDPattern m_rainbow = LEDPattern.rainbow(255, 128);

        // Our LED strip has a density of 120 LEDs per meter
        private static final Distance kLedSpacing = Units.Meters.of(1 / 120.0);
      
        // Create a new pattern that scrolls the rainbow pattern across the LED strip, moving at a speed
        // of 1 meter per second.
        private final LEDPattern m_scrollingRainbow =
            m_rainbow.scrollAtAbsoluteSpeed(Units.MetersPerSecond.of(1), kLedSpacing);


  public Robot()
  {


    instance = this;
  
  }

  public static Robot getInstance()
  {
    return instance;
  }

  /**
   * This function is run when the robot is first started up and should be used for any initialization code.
   */
  @Override
  public void robotInit()
  {
    led = new AddressableLED (0);
    ledBuffer = new AddressableLEDBuffer(1500);
    led.setLength(1500);
    led.setLength(ledBuffer.getLength());
    led.start();
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    // Create a timer to disable motor brake a few seconds after disable.  This will let the robot stop
    // immediately when disabled, but then also let it be pushed more 
    disabledTimer = new Timer();

    if (isSimulation())
    {
      DriverStation.silenceJoystickConnectionWarning(true);
    }
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics that you want ran
   * during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic()
  {
  
if (DriverStation.waitForDsConnection( 0.025) == false){
// GRB
LEDPattern base = LEDPattern.solid(new Color(7,255,0));
LEDPattern pattern = base.breathe(Units.Seconds.of(2.0));

// Apply the LED pattern to the data buffer
pattern.applyTo(ledBuffer);

// Write the data to the LED strip
led.setData(ledBuffer);

}

if
(DriverStation.getAlliance().get() == DriverStation.Alliance.Red) {
  for (var i = 0; i < ledBuffer.getLength(); i++) {
    // Sets the specified LED to the GRB values for red
    ledBuffer.setRGB(i, 0, 255, 0);
    led.setData(ledBuffer);
}
}
else if (DriverStation.waitForDsConnection( 0.025) == false){
  // GRB
  LEDPattern base = LEDPattern.solid(new Color(7,255,0));
  LEDPattern pattern = base.breathe(Units.Seconds.of(2.0));
  
  // Apply the LED pattern to the data buffer
  pattern.applyTo(ledBuffer);
  
  // Write the data to the LED strip
  led.setData(ledBuffer);
  
  }
  
else if(DriverStation.getAlliance().get() == DriverStation.Alliance.Blue){
  for (var i = 0; i < ledBuffer.getLength(); i++) {
    // Sets the specified LED to the GRB values for red
    ledBuffer.setRGB(i, 0, 15 ,255);
    led.setData(ledBuffer);
}
}
else{
  for (var i = 0; i < ledBuffer.getLength(); i++) {
    // Sets the specified LED to the GRB values for red
    ledBuffer.setRGB(i, 7, 255 ,255);
    led.setData(ledBuffer);
}
}



    CommandScheduler.getInstance().run();
}
  
  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit()
  {
    m_robotContainer.setMotorBrake(true);
    disabledTimer.reset();
    disabledTimer.start();
  }

  @Override
  public void disabledPeriodic()
  {
    if (disabledTimer.hasElapsed(SwerveConstants.DrivebaseConstants.WHEEL_LOCK_TIME))
    {
      m_robotContainer.setMotorBrake(false);
      disabledTimer.stop();
      disabledTimer.reset();
    }
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit()
  {
    m_robotContainer.setMotorBrake(true);
     m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)

    if (m_autonomousCommand != null)
    {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic()
  {
    
  }

  @Override
  public void teleopInit()
  {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.


    if (m_autonomousCommand != null)
    {
      m_autonomousCommand.cancel();
    } else
    {
      CommandScheduler.getInstance().cancelAll();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic()
  {

  
  }

  @Override
  public void testInit()
  {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic()
  {
  }

  /**
   * This function is called once when the robot is first started up.
   */
  @Override
  public void simulationInit()
  {
  }

  /**
   * This function is called periodically whilst in simulation.
   */
  @Override
  public void simulationPeriodic()
  {
  }
}
