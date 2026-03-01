package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.Logger;

public class IntakeSubsystem extends SubsystemBase {
  private final IntakeIO io;

  public IntakeSubsystem(IntakeIO io) {
    this.io = io;
  }

  public void setRunning(boolean run) {
    io.setRunning(run);
  }

  public void OpenIntake(boolean run) {
    io.OpenIntake(run);
  }

  public void CloseIntake(boolean run) {
    io.CloseIntake(run);
  }

  @Override
  public void periodic() {
    Logger.log("Intake/FuelCount", io.getGamePieceCount());
  }
  
  public boolean useFuel() {
    return io.useFuel();
  }

  public void end(){
    io.end();
  };
}