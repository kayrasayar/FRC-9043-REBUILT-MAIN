package frc.robot.constants;

import static edu.wpi.first.units.Units.Meters;
import edu.wpi.first.units.measure.Distance;

import org.ironmaple.simulation.IntakeSimulation;
import org.ironmaple.simulation.IntakeSimulation.IntakeSide;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;


// Over bumper Intake
public class IntakeConstants {

  public static final String GAME_PIECE = "Fuel";
  
  //sim

  public static final int FUEL_INTAKE_CAPATICY = 40; // Top kapasitesi (max100)
  public static final IntakeSide FUEL_INTAKE_SIDE = IntakeSimulation.IntakeSide.BACK; // Intake Tarafı 

  public static final Distance FUEL_INTAKE_WIDTH = Meters.of(0.3); // Intake genişlik
  public static final Distance FUEL_INTAKE_LENGTH = Meters.of(0.2); // Intake yukseklik

  // real

  public static final int fuelMotor1ID = 1;
  public static final int setIntakeMotor1ID = 2;

  public static final MotorType fuelMotor1type = MotorType.kBrushless;
  public static final MotorType setIntakeMotor1type = MotorType.kBrushless;

  public static final SparkBaseConfig motorDefaultConfigBrake = new SparkMaxConfig()
  .smartCurrentLimit(30)
  .idleMode(IdleMode.kBrake);

  public static final SparkBaseConfig motorDefaultConfigCoast = new SparkMaxConfig()
  .smartCurrentLimit(30)
  .idleMode(IdleMode.kCoast);

  public static final SparkBaseConfig fuelMotor1Config = new SparkMaxConfig()
  .apply(motorDefaultConfigCoast)
  .inverted(false);

  public static final SparkBaseConfig setIntakeMotor1Config = new SparkMaxConfig()
  .apply(motorDefaultConfigBrake)
  .inverted(false);

}
