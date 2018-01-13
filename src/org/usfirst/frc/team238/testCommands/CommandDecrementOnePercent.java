package org.usfirst.frc.team238.testCommands;

import org.usfirst.frc.team238.core.AbstractCommand;
import org.usfirst.frc.team238.core.Command;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.ControlBoard;
import org.usfirst.frc.team238.robot.CrusaderCommon;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CommandDecrementOnePercent extends AbstractCommand {
	
	Drivetrain myRobotDrive;
	
	double motorValue = 0;
	double targetValue;
	double newTargetYaw;
	int count;
	int increaseCount = 0;

	public CommandDecrementOnePercent(Drivetrain theRobotDrive) {
		// TODO Auto-generated constructor stub
		
		this.myRobotDrive = theRobotDrive;
		count = 0;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		//Logger.Log("!!!!!DEBUG!!!!!!!!!!!!   " + motorValue);
		
    if(increaseCount > 40)
    {
      increaseCount = 0;
    }
    else
    {
      increaseCount++;
    }
		
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setParams() {
		// TODO Auto-generated method stub
	  /*public void increaseTen()
	  {
	    
	    
	    talonSpeed = talonSpeed + 0.1;
	    
	    theShooter.setTalonSpeed(talonSpeed);
	    
	    Logger.Log("FuelHandler() : increaseTen() : SHOOTER SPEED IS   " + talonSpeed);
	    
	    
	  }
	  
	  public void increaseOne()
	  {
	    
	    talonSpeed = talonSpeed + 0.01;
	    
	    theShooter.setTalonSpeed(talonSpeed);
	    
	    Logger.Log("FuelHandler() : increaseOne() : SHOOTER SPEED IS   " + talonSpeed);
	    
	  }
	  
	  public void decreaseTen()
	  {
	    
	    
	    talonSpeed = talonSpeed - 0.1;
	    
	    theShooter.setTalonSpeed(talonSpeed);
	    
	    Logger.Log("FuelHandler() : decreaseTen() : SHOOTER SPEED IS   " + talonSpeed);
	    
	    
	  }
	  
	  public void decreaseOne()
	  {
	    
	    talonSpeed = talonSpeed - 0.01;   
	    
	    theShooter.setTalonSpeed(talonSpeed);
	    
	    Logger.Log("FuelHandler() : decreaseOne() : SHOOTER SPEED IS   " + talonSpeed);
	  }
	  
	  public void serialDecreaseTen()
	  {
	    
	    serializerSpeed = serializerSpeed - 0.7;
	    
	    theSerializer.setTalonSpeed(serializerSpeed);
	    
	    Logger.Log("FuelHandler() : serialDecreaseTen() : SERIALIZER SPEED IS   " + serializerSpeed);
	    
	  }
	  
	  public void serialIncreaseTen()
	  {
	    
	    serializerSpeed = serializerSpeed + 0.7;
	    
	    theSerializer.setTalonSpeed(serializerSpeed);
	    
	    Logger.Log("FuelHandler() : serialIncreaseTen() : SERIALIZER SPEED IS   " + serializerSpeed);
	    
	  }
	  
	  /**
	   * this method resets the motor.
	   
	  public void resetMotor()
	  {
	    
	    talonSpeed = 0;
	    serializerSpeed = 0;
	    
	    theShooter.setTalonSpeed(talonSpeed);
	    theSerializer.setTalonSpeed(serializerSpeed);
	    
	    Logger.Log("FuelHandler() : resetMotor() : SHOOTER SPEED IS   " + talonSpeed);
	    Logger.Log("FuelHandler() : resetMotor() : SERIALIZER SPEED IS   " + serializerSpeed);
	    
	    
	  }*/
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return true;
	}

}
