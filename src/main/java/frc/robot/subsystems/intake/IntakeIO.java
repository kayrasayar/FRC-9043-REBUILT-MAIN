package frc.robot.subsystems.intake;

public interface IntakeIO {
  void setRunning(boolean run);

  int getGamePieceCount();
  
  boolean useFuel();

  void CloseIntake(boolean run);
  
  void OpenIntake(boolean run);

  void end();
}