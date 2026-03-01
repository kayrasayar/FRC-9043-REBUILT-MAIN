package frc.robot.constants;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;


public class FeederConstants {

  public static final String GAME_PIECE = "Fuel";
  
  // real

  public static final int feederMotor1ID = 1;

  public static final MotorType feederMotor1type = MotorType.kBrushless;

  public static final SparkBaseConfig motorDefaultConfigCoast = new SparkMaxConfig()
  .smartCurrentLimit(30)
  .idleMode(IdleMode.kCoast);

  public static final SparkBaseConfig feederMotor1Config = new SparkMaxConfig()
  .apply(motorDefaultConfigCoast)
  .inverted(false);


}
