package frc.robot;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.CANcoder;

import java.time.Instant;

public class Robot extends TimedRobot {
  private DifferentialDrive m_myRobot;
  private Joystick m_leftStick;
  private Joystick m_rightStick;
  private CANcoder can12 = null;
  private static final int leftDeviceID = 11; 
  //private static final int rightDeviceID = 26;
  private SparkMax m_leftMotor;
  //private CANSparkMax m_rightMotor;

  @Override
  public void robotInit() {
  /**
   * SPARK MAX controllers are intialized over CAN by constructing a CANSparkMax object
   * 
   * The CAN ID, which can be configured using the SPARK MAX Client, is passed as the
   * first parameter
   * 
   * The motor type is passed as the second parameter. Motor type can either be:
   *  com.revrobotics.CANSparkLowLevel.MotorType.kBrushless
   *  com.revrobotics.CANSparkLowLevel.MotorType.kBrushed
   * 
   * The example below initializes four brushless motors with CAN IDs 1 and 2. Change
   * these parameters to match your setup
   */
    m_leftMotor = new SparkMax(leftDeviceID, MotorType.kBrushless);
    //m_rightMotor = new SparkMax(rightDeviceID, MotorType.kBrushless);

    /**
     * The RestoreFactoryDefaults method can be used to reset the configuration parameters
     * in the SPARK MAX to their factory default state. If no argument is passed, these
     * parameters will not persist between power cycles
     */
    //m_leftMotor. restoreFactoryDefaults();
    //m_rightMotor.restoreFactoryDefaults();

    //m_myRobot = new DifferentialDrive(m_leftMotor, m_rightMotor);

    m_leftStick = new Joystick(0);
    //m_rightStick = new Joystick(1);
    //m_rightStick.get

    can12 = new CANcoder(12);
  }

  @Override
  public void teleopPeriodic() {
      double y = m_leftStick.getY();
      m_leftMotor.set(Math.abs(y));
      StatusSignal<Angle> absPos = can12.getAbsolutePosition();
      double valueAsDouble = absPos.getValueAsDouble();
      Angle value = absPos.getValue();
      double baseUnitMagnitude = value.baseUnitMagnitude();

      Instant now = Instant.now(); // Get the current instant
      long epochSeconds = now.getEpochSecond(); // Get epoch seconds
      if ( (epochSeconds % 3) == 0) {
        System.out.println("HELLO" + y + " angle: " + valueAsDouble + " bm: " + baseUnitMagnitude );
      }
  }
}
