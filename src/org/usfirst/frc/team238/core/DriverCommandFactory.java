package org.usfirst.frc.team238.core;

import java.util.HashMap;

import org.usfirst.frc.team238.commands.CommandShiftHigh;
import org.usfirst.frc.team238.commands.CommandShiftLow;
import org.usfirst.frc.team238.commands.CommandTankDrive;
import org.usfirst.frc.team238.commands.NoDriverCommand;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Navigation;
import org.usfirst.frc.team238.robot.Vision;
import org.usfirst.frc.team238.testCommands.CommandDeccrementTestDriveWithButtons;
import org.usfirst.frc.team238.testCommands.CommandDecrementOnePercent;
import org.usfirst.frc.team238.testCommands.CommandIncrementOnePercent;
import org.usfirst.frc.team238.testCommands.CommandIncrementTestDriveWithButtons;
import org.usfirst.frc.team238.testCommands.CommandResetTestDriveWithButtons;

import edu.wpi.first.wpilibj.RobotDrive;

public class DriverCommandFactory 
{

	NoDriverCommand NoDriveCommand;
	CommandShiftHigh commandShiftHigh;
	CommandShiftLow commandShiftLow;
	CommandResetTestDriveWithButtons cmdReset;
	CommandDeccrementTestDriveWithButtons cmdDecrement;
	CommandIncrementTestDriveWithButtons cmdIncrement;
	CommandDecrementOnePercent cmdDecrementOne;
	CommandIncrementOnePercent cmdIncrementOne;
	
	HashMap <Integer, Command> driverLeftCommands;
	HashMap <Integer, Command> driverRightCommands;
	HashMap <Integer, Command> driverCommands;
	
	public void init()
	{
		driverLeftCommands = new HashMap<Integer, Command>(10);
		driverRightCommands = new HashMap<Integer, Command>(10);
		driverCommands = new HashMap<Integer, Command>(10);
	}
	
	/**
	 * Creates commands for the left driver joystick
	 * @param driveTrain
	 * @param myNavigation
	 * @param myVision
	 * @param myFuelHandler
	 * @return
	 */
	public HashMap<Integer, Command> createDriverLeftCommands(Drivetrain driveTrain, Navigation myNavigation,
	    Vision myVision)
	{
		
		NoDriveCommand = new NoDriverCommand(driveTrain);
		
		driverLeftCommands.put(0, NoDriveCommand);
		
		commandShiftLow = new CommandShiftLow(driveTrain);
		
		driverLeftCommands.put(1, commandShiftLow);

		
		//cmdReverseClimb = new CommandReverseClimber(theClimber);
		
		//driverLeftCommands.put(10, cmdReverseClimb);
		
		return driverLeftCommands;
		
	}

	/**
	 * Creates commands for the right driver joystick
	 * @param driveTrain
	 * @param myNavigation
	 * @param myVision
	 * @param myFuelHandler
	 * @return
	 */
	public HashMap<Integer, Command> createDriverRightCommands(Drivetrain driveTrain, Navigation myNavigation,
	    Vision myVision)
	{
	  
		NoDriveCommand  = new NoDriverCommand(driveTrain);
		driverRightCommands.put(0, NoDriveCommand);
		
		commandShiftHigh = new CommandShiftHigh(driveTrain);
		driverRightCommands.put(1, commandShiftHigh);

		return driverRightCommands;
		
	}
	
	/**
	 * Creates a command for both driver joysticks
	 * (In this case, enables the driver to actually drive the robot)
	 * @param myRobotDrive
	 * @return
	 */
	public HashMap<Integer, Command> createDriverCommands(Drivetrain theDT)
	{
		
	 	CommandTankDrive cmdToDriveTheRobot = new CommandTankDrive(theDT);
		
		driverCommands.put(0, cmdToDriveTheRobot);
		
		return driverCommands;
		
	}
}
