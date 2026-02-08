package frc.robot.subsystems.shooter;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.LinearVelocity;

import frc.robot.constants.FuelShooterConstants;
import frc.robot.utils.Logger;

import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.drivesims.SwerveDriveSimulation;
import org.ironmaple.simulation.seasonspecific.rebuilt2026.RebuiltFuelOnFly; 

public class ShooterIOSim implements ShooterIO {
  private final SwerveDriveSimulation driveSim;

  public ShooterIOSim(SwerveDriveSimulation driveSim) {
    this.driveSim = driveSim;
  }

  @Override
  public void launchFuel(LinearVelocity velocityMps, Angle angleRad, Runnable onHit) {

    RebuiltFuelOnFly fuelProjectile = new RebuiltFuelOnFly(
      driveSim.getSimulatedDriveTrainPose().getTranslation(), // Robotun sahadaki konumu
      FuelShooterConstants.FUEL_SHOOTER_POSITION, // Shooter konumu
      driveSim.getDriveTrainSimulatedChassisSpeedsFieldRelative(), // Şasinin anlık hızı
      driveSim.getSimulatedDriveTrainPose().getRotation(), // Robotun baktığı yön
      FuelShooterConstants.FUEL_SHOOTER_HEIGHT, // Fırlatılış yüksekliği
      velocityMps, // Fırlatılış hızı (m/s)
      angleRad // Shooter açısı (Derece)
    );

    fuelProjectile.withProjectileTrajectoryDisplayCallBack(
      (poses) -> Logger.log("Shooter/SuccessPath", poses.toArray(Pose3d[]::new)),
      (poses) -> Logger.log("Shooter/FuelPath", poses.toArray(Pose3d[]::new))
    );

    SimulatedArena.getInstance().addGamePieceProjectile(fuelProjectile);
    fuelProjectile.withHitTargetCallBack(onHit); //top hedefe değdiğinde "onhit" çalıştırır.
  }
}