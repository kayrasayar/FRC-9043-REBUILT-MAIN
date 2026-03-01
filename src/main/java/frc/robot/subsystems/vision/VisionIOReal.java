package frc.robot.subsystems.vision;

import java.util.List;

import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

public class VisionIOReal implements VisionIO {

  public VisionIOReal() {
  }

  @Override
  public void update() {
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public PhotonTrackedTarget getBestTarget() {
    throw new UnsupportedOperationException("Unimplemented method 'getBestTarget'");
  }

  @Override
  public PhotonTrackedTarget getTarget(AprilTagID apriltag) {
    throw new UnsupportedOperationException("Unimplemented method 'getTarget'");
  }

  @Override
  public boolean isSeen(AprilTagID aprilTag) {
    throw new UnsupportedOperationException("Unimplemented method 'isSeen'");
  }

  @Override
  public List<PhotonTrackedTarget> getSeenTargets() {
    throw new UnsupportedOperationException("Unimplemented method 'getSeenTargets'");
  }

  @Override
  public PhotonPipelineResult getPipelineResult() {
    throw new UnsupportedOperationException("Unimplemented method 'getPipelineResult'");
  }

}