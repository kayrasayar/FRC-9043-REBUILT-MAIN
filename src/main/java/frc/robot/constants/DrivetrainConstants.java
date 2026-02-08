package frc.robot.constants;

import static edu.wpi.first.units.Units.Kilograms;
import static edu.wpi.first.units.Units.Meters;

import org.ironmaple.simulation.drivesims.COTS;
import org.ironmaple.simulation.drivesims.configs.DriveTrainSimulationConfig;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.Mass;

public class DrivetrainConstants {


  private static final Distance BUMPER_SIZE_X = Meters.of(0.9);
  private static final Distance BUMPER_SIZE_Y = Meters.of(0.9);

  private static final Mass ROBOT_MASS = Kilograms.of(57.924);

  private static final double WHEELCOF = 1.19;
  private static final int GEAR_RATIO_LEVEL = 6;
  

  public static final DriveTrainSimulationConfig DRIVETRAIN_CONFIG = DriveTrainSimulationConfig.Default()
  .withBumperSize(BUMPER_SIZE_X, BUMPER_SIZE_Y)
  .withRobotMass(ROBOT_MASS)
  .withGyro(COTS.ofNav2X())
  .withSwerveModule(
    COTS.ofThriftySwerve(
      DCMotor.getNEO(1), 
      DCMotor.getNEO(1), 
      WHEELCOF, 
      GEAR_RATIO_LEVEL)
    );

  public static final SwerveDriveKinematics KINEMATICS = new SwerveDriveKinematics(
    new Translation2d(Meters.of(+0.3310000009), Meters.of(+0.2679999999)),
    new Translation2d(Meters.of(+0.3310000009), Meters.of(-0.2679999999)),
    new Translation2d(Meters.of(-0.3310000009), Meters.of(+0.2679999999)),
    new Translation2d(Meters.of(-0.3310000009), Meters.of(-0.2679999999))
  ); 
}
