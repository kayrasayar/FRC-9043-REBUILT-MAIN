package frc.robot.subsystems.feeder;


import frc.robot.constants.FeederConstants;
import frc.robot.constants.MotorConstants;

import com.revrobotics.spark.SparkMax;

public class FeederIOReal implements FeederIO {

  SparkMax feederMotor1;

  public FeederIOReal() {

    this.feederMotor1 = new SparkMax(FeederConstants.feederMotor1ID, FeederConstants.feederMotor1type);
    this.feederMotor1.configure(FeederConstants.feederMotor1Config, MotorConstants.resetMode, MotorConstants.persistMode);
    
  }

  @Override
  public void setRunning(boolean run) {
    if(run){
      feederMotor1.set(0.3);
    }
    else{
      feederMotor1.set(0);
    }
  }
  
  public void end(){
    feederMotor1.set(0);
  }
}