package frc.robot.subsystems.vision;

import java.util.List;

import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

public interface VisionIO {
    
    void update();

    PhotonTrackedTarget getBestTarget();

    PhotonTrackedTarget getTarget(AprilTagID apriltag);

    boolean isSeen(AprilTagID aprilTag);

    List<PhotonTrackedTarget> getSeenTargets();

    PhotonPipelineResult getPipelineResult();
    
} 
