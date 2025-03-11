package frc.robot.subsystems.LEDS;


import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.units.Units;


public class LedSUB {
  AddressableLED led;
  AddressableLEDBuffer ledBuffer;
  
 
  public LedSUB(){
    led = new AddressableLED (0);
    ledBuffer = new AddressableLEDBuffer(500);
    led.setLength(500);
    led.setLength(ledBuffer.getLength());
    led.start();
    
  }
  private final LEDPattern m_rainbow = LEDPattern.rainbow(255, 128);

  private static final Distance kLedSpacing = Units.Meters.of(1 / 120.0);

  private final LEDPattern m_scrollingRainbow = m_rainbow.scrollAtAbsoluteSpeed(Units.MetersPerSecond.of(1), kLedSpacing);

  public void ledPeriodic() {
    m_scrollingRainbow.applyTo(ledBuffer);
    led.setData(ledBuffer);
  }

  }
  
 
 
