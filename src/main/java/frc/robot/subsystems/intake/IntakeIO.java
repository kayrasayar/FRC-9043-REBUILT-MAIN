package frc.robot.subsystems.intake;

public interface IntakeIO {
  void setRunning(boolean run);

  int getGamePieceCount();
  
  boolean useFuel();
}