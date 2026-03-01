package frc.robot.subsystems.feeder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FeederSubsystem extends SubsystemBase {
  private final FeederIO io;

  public FeederSubsystem(FeederIO io) {
    this.io = io;
  }

  public void setRunning(boolean run) {
    io.setRunning(run);
  }

  @Override
  public void periodic() {
  }

  public void end(){
    io.end();
  };
}