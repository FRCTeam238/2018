package org.usfirst.frc.team238.core;

import java.util.HashMap;


import org.usfirst.frc.team238.robot.CrusaderCommon;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;
import org.usfirst.frc.team238.robot.Robot;
import org.usfirst.frc.team238.robot.Vision;
import org.usfirst.frc.team238.testCommands.CommandDeccrementSerializer;
import org.usfirst.frc.team238.testCommands.CommandDeccrementTestDriveWithButtons;
import org.usfirst.frc.team238.testCommands.CommandDecrementOnePercent;
import org.usfirst.frc.team238.testCommands.CommandIncrementOnePercent;
import org.usfirst.frc.team238.testCommands.CommandIncrementSerialaizer;
import org.usfirst.frc.team238.testCommands.CommandIncrementTestDriveWithButtons;
import org.usfirst.frc.team238.testCommands.CommandResetTestDriveWithButtons;

import org.usfirst.frc.team238.commands.CommandTrackTarget;
import org.usfirst.frc.team238.commands.CommandStopEverything;

public class OperatorCmdFactory {

	CommandTrackTarget commandTrackTarget;
	
	CommandStopEverything commandStopEverything;
	
	CommandIncrementTestDriveWithButtons commandIncreaseTen;
	
	CommandIncrementOnePercent commandIncreaseOne;
	
	CommandDeccrementTestDriveWithButtons commandDecreaseTen;
	
	CommandDecrementOnePercent commandDecreaseOne;
	
	CommandResetTestDriveWithButtons commandReset;
	
	CommandDeccrementSerializer commandDecSerial;
	
	CommandIncrementSerialaizer commandIncSerial;

	
	HashMap<Integer, Command> operatorCommands;
	
	
	public void init()
	{
	
	  operatorCommands = new HashMap<Integer, Command>(16);
	
	}
	
	/**
	 * Operator controls get binded here. Assigning a series of buttons and commands to a HashMap
	 * @param driveTrain
	 * @param theNavigation
	 * @param theVision
	 * @param theFuelHandler
	 * @param theClimber
	 * @param theRobot
	 * @return
	 */
	public HashMap<Integer, Command> createOperatorCommands(Drivetrain driveTrain,
	    Navigation theNavigation, Vision theVision, Robot theRobot)
	{
	  //Inputs get defined in CrusaderCommon
	  Integer[] multiButtonTestInput = {1,2,3,4,5}; //Test : Button input
	  
	  //Create command objects, passing objects into each of them
	  commandStopEverything = new CommandStopEverything();     //<-------------------------------- EXAMPLE
	  
	  
	  //Assigns all command arrays and their specific inputs to the HashMap
	  operatorCommands.put(CrusaderCommon.stopEverythingInput, commandStopEverything); // <------- EXAMPLE

    
    //operatorCommands.put(multiButtonTestInput, twoButtonTestCommandArray); //Test : Command put
		
	  
		return operatorCommands;
		
	}

	
}
