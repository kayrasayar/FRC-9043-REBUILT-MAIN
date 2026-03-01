package frc.robot.subsystems.intake;

import org.ironmaple.simulation.IntakeSimulation;
import org.ironmaple.simulation.drivesims.SwerveDriveSimulation;

import frc.robot.constants.IntakeConstants;

public class IntakeIOSim implements IntakeIO {
  private final IntakeSimulation intakeSim;

  public IntakeIOSim(SwerveDriveSimulation driveSim) {
    this.intakeSim = IntakeSimulation.OverTheBumperIntake(
      IntakeConstants.GAME_PIECE,
      driveSim,
      IntakeConstants.FUEL_INTAKE_WIDTH,
      IntakeConstants.FUEL_INTAKE_LENGTH, 
      IntakeConstants.FUEL_INTAKE_SIDE,
      IntakeConstants.FUEL_INTAKE_CAPATICY 
    );
  }

  @Override
  public void setRunning(boolean run) {
    if(run) {
      intakeSim.startIntake();
    } 
    else {
      intakeSim.stopIntake();
    }
  }

  public void OpenIntake(boolean run){
    if(run) {
      intakeSim.startIntake();;
    } 
    else {
      intakeSim.stopIntake();
    }
  }
  
  public void CloseIntake(boolean run){
    if(run) {
      intakeSim.startIntake();;
    } 
    else {
      intakeSim.stopIntake();
    }
  }
  

  @Override
  public int getGamePieceCount() {
    return intakeSim.getGamePiecesAmount();
  }

  @Override
  public boolean useFuel(){
    return intakeSim.obtainGamePieceFromIntake();
  }

  public void end(){
    intakeSim.stopIntake();
  }
  
}