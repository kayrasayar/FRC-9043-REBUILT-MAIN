package frc.robot.subsystems.intake;

import com.revrobotics.spark.SparkMax;

import frc.robot.constants.IntakeConstants;

import frc.robot.constants.MotorConstants;


public class IntakeIOReal implements IntakeIO {

  SparkMax fuelMotor1, setIntakeMotor1;

  public IntakeIOReal() {

    this.fuelMotor1 = new SparkMax(IntakeConstants.fuelMotor1ID, IntakeConstants.fuelMotor1type);
    this.fuelMotor1.configure(IntakeConstants.fuelMotor1Config, MotorConstants.resetMode, MotorConstants.persistMode);

    this.setIntakeMotor1 = new SparkMax(IntakeConstants.setIntakeMotor1ID, IntakeConstants.setIntakeMotor1type); 
    this.setIntakeMotor1.configure(IntakeConstants.setIntakeMotor1Config, MotorConstants.resetMode, MotorConstants.persistMode);
    
  }

  @Override
  public void setRunning(boolean run) {
    if(run){
      fuelMotor1.set(0.3);
    }
    else{
      fuelMotor1.set(0);
    }
  }

  @Override
  public void OpenIntake(boolean run){
    if(run){
      setIntakeMotor1.set(0.3);
    }
    else{
      setIntakeMotor1.set(0);
    } 
  }

  @Override
  public void CloseIntake(boolean run){
    if(run){
      setIntakeMotor1.set(-0.3);
    }
    else{
      setIntakeMotor1.set(0);
    } 
  }

  @Override
  public int getGamePieceCount() {
    return 0;
  }

  @Override
  public boolean useFuel() {
    return true;
  }

  public void end(){
    fuelMotor1.set(0);
    setIntakeMotor1.set(0);
  }
}