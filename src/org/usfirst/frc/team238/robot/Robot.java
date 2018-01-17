package org.usfirst.frc.team238.robot;


import java.util.HashMap;

import org.usfirst.frc.team238.core.AutonomousController;
import org.usfirst.frc.team238.core.AutonomousDataHandler;
import org.usfirst.frc.team238.core.CommandController;
import org.usfirst.frc.team238.core.Logger;
import org.usfirst.frc.team238.robot.Navigation;
import org.usfirst.frc.team238.robot.Drivetrain;
import org.usfirst.frc.team238.robot.Vision;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.DriverStation;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.ControlMode;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

// @SuppressWarnings("deprecation")
public class Robot extends IterativeRobot {

	private static int count = 1;
	double[] dataFromVision;
		
	TalonSRX leftFrontDrive; 
	TalonSRX leftRearDrive; 
	TalonSRX rightFrontDrive; 
	TalonSRX rightRearDrive; 
	
	Robot myRobot;
	Preferences myPreferences;
	ControlBoard myControlBoard;
	CommandController theMCP;
	DifferentialDrive myRobotDrive;
	Navigation myNavigation;
	Drivetrain myDriveTrain;
	DriverStation myDriverstation;
	Vision theVision;
  Logger myLogger;
  DriverStation myDriverStation;
  
  //There shouldn't be two of these
  Alliance myAllianceTeam;
	
	// Autonomous Mode Support
	String autoMode;
	/*AutonomousDrive autonomousDrive;*/
	private AutonomousDataHandler myAutonomousDataHandler;
	//private TargetingDataHandler myTargetingData;
	private AutonomousController theMACP;
	
  //SendableChooser<String> autonomousChooser;
  SendableChooser<String> autonomousSaveChooser;
	//SendableChooser<String> targetingStateParamsUpdate;
  //SendableChooser<String> targetingSaveChooser;
  //SendableChooser<String> aModeSelector;
  SendableChooser<String> autonomousStateParamsUpdate;
	
  String teamColorString;

	Alliance teamColor;
	
	/**
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * !!!!!!!!!!!!!!!!!THIS SHOULDN'T BE HERE!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * !!!!!!!This only gets called through CommandCurlForward!!!!!!!!!!
	 * !!Instead, pass theFuelHandler through the init as a parameter!!!
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * @return
	 */
	
	/**
	 * Called when the robot is disabled
	 */
	public void disabledInit() {
		try {
		  
			// only use checkForSmartDashboardChanges function in init methods
			// or you will smoke the roborio into a useless pile of silicon
			//checkForSmartDashboardChanges(CrusaderCommon.PREFVALUE_OP_AUTO, CrusaderCommon.PREFVALUE_OP_AUTO_DEFAULT);
			
			Logger.Log("Robot(): disabledInit:");
		
		} catch (Exception e) {
		  e.printStackTrace();
			Logger.Log("Robot(): disabledInit Exception: "+e);
		}
	}

	/**
	 * Called periodically when the robot is disabled
	 */
	public void disabledPeriodic() {
		//boolean debug;
		try {
			if (count > 150) {
				
				count = 0;
				
				
				
				myDriveTrain.resetEncoders();
				
				//Send the list of AutonomousModes into the AutonomousController for processing
        theMACP.setAutonomousControllerData(myAutonomousDataHandler);
				
				int autoModeSelection = myAutonomousDataHandler.getModeSelectionFromDashboard();
				Logger.Log("Robot(): disabledPeriodic(): The chosen AutoMode =  " + String.valueOf(autoModeSelection));
			
				//backups  in case sendable chooser disappear
				boolean updateBackup_BecauseTheSendableChooserSucks = false;
        boolean saveBackup_BecauseTheSendableChooserSucks = false;
        boolean  readAmode238DotTxt = false;
        
				//see if we need to modify the params on a state
				String updateParams = (String) autonomousStateParamsUpdate.getSelected();
				int update = Integer.parseInt(updateParams);
				
				if(updateParams == null)
				{
				  updateBackup_BecauseTheSendableChooserSucks = SmartDashboard.getBoolean("Update Params", false);
				  
				  if(updateBackup_BecauseTheSendableChooserSucks)
				  {
				    update = 1;
				  }
				}
				
				String saveParam = (String) autonomousSaveChooser.getSelected();
				int save = 0;
				if(saveParam == null)
				{
				  saveBackup_BecauseTheSendableChooserSucks = SmartDashboard.getBoolean("Save to Amode238", false);
          if(saveBackup_BecauseTheSendableChooserSucks)
          {
            save = CrusaderCommon.AUTONOMOUS_SAVE;            
          }
          
				  readAmode238DotTxt = SmartDashboard.getBoolean("Read Amode238", false);
				  if(readAmode238DotTxt)
				  {
				    save = CrusaderCommon.AUTONOMOUS_READ_FILE;
				  }
				}
				else
				{
				  save = Integer.parseInt(saveParam); 
				}
				 
				
				
				Logger.Log("Robot:DisablePeriodic: save = " + save);
				
				
				theMACP.pickAMode(autoModeSelection);
				
				
				if(update == CrusaderCommon.AUTONOMOUS_UPDATE)
				{
					theMACP.updateStateParameters(autoModeSelection);
				}
				
				if(save == CrusaderCommon.AUTONOMOUS_SAVE) 
				{
				  myAutonomousDataHandler.save();	
				}
				
				
				if(save == CrusaderCommon.AUTONOMOUS_READ_FILE)
				{
				  myAutonomousDataHandler.readJson(theMCP);
				}
				
				myAutonomousDataHandler.dump();
				
				SmartDashboard.putString("Last Modified : ", myAutonomousDataHandler.getModificationDate());  
				
				 String teamColorString = SmartDashboard.getString("Alliance Color", "Red");
			    if(teamColorString.equals("Red"))
			    {
			      teamColor = Alliance.Red;
			    }
			    else
			    {
			      teamColor = Alliance.Blue;
			    }
			    
			}
			
			count++;
			
		} catch (Exception e) {
		  e.printStackTrace();
			Logger.Log("Robot(): disabledPeriodic(): disabledPriodic exception: " + e);
		}

	}

	public void teleopInit() {
		try {
			Logger.Log("Robot(): TeleopInit()");
			myControlBoard.checkXboxController();
			
		} catch (Exception e) {
		  e.printStackTrace();
			Logger.Log("Robot(): TeleopInit() Exception: "+ e);
		}

	}

	public void autonomousInit() 
	{
		try 
		{
			  Logger.Log("Robot(): AutononousInit()");
			
			  myDriveTrain.resetEncoders();
			  
				int automousModeFromDS =  myAutonomousDataHandler.getModeSelectionFromDashboard(); 
				Logger.Log("Robot(): AutonomousInit(): The chosen One =  " + String.valueOf(automousModeFromDS));
				theMACP.pickAMode(automousModeFromDS);
				
				myDriveTrain.getEncoderTicks();
				
				 teamColorString = SmartDashboard.getString("Alliance Color", "Red");
			    if(teamColorString.equals("Red"))
			    {
			      teamColor = Alliance.Red;
			    }
			    else
			    {
			      teamColor = Alliance.Blue;
			    }
				
				
		}
		catch (Exception ex) 
		{
		  ex.printStackTrace();
			Logger.Log("Robot(): AutononousInit() Exception: "+ex);
		}
	}
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() 
	{
		try 
		{
			System.out.println("RobotInit()");
			
		  SmartDashboard.putBoolean("Output Log to File", true);
		   
		  SmartDashboard.putBoolean("Debug", true);
			
			myRobot = Robot.this; 
		
			InitSmartDashboardObjects();
			
			myLogger = new Logger();
			
			//object that is the code representation for the physical control board
			myControlBoard = new ControlBoard();
			myControlBoard.controlBoardInit();

			//Create robot core objects                                          // Test Robot | Actual Robot
			leftFrontDrive = new TalonSRX(CrusaderCommon.DRIVE_TRAIN_MASTER_LEFT);   //id =  
			leftRearDrive = new TalonSRX(CrusaderCommon.DRIVE_TRAIN_SLAVE_LEFT);     //id =  
			rightFrontDrive = new TalonSRX(CrusaderCommon.DRIVE_TRAIN_MASTER_RIGHT); //id =  
			rightRearDrive = new TalonSRX(CrusaderCommon.DRIVE_TRAIN_SLAVE_RIGHT);   //id =  
			
			//Setting the talons to follow master talons
			rightRearDrive.set(ControlMode.Follower, 0);
			leftRearDrive.set(ControlMode.Follower,0);
			rightRearDrive.set(ControlMode.PercentOutput, CrusaderCommon.DRIVE_TRAIN_MASTER_RIGHT);
			leftRearDrive.set(ControlMode.PercentOutput, CrusaderCommon.DRIVE_TRAIN_MASTER_LEFT);
			
			/*myRobotDrive = new DifferentialDrive(leftFrontDrive,rightFrontDrive);
			myRobotDrive.setSafetyEnabled(false);*/
			
			myNavigation = new Navigation();
			myNavigation.init();
			
			myDriveTrain = new Drivetrain(myControlBoard);
			myDriveTrain.init(leftFrontDrive, rightFrontDrive);
			
			leftFrontDrive.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
			rightFrontDrive.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
			
			leftFrontDrive.setNeutralMode(NeutralMode.Brake);
			rightFrontDrive.setNeutralMode(NeutralMode.Brake);
			leftRearDrive.setNeutralMode(NeutralMode.Brake);
			rightRearDrive.setNeutralMode(NeutralMode.Brake);
			
			theVision = new Vision();
			theVision.init();
			
			myDriveTrain.resetEncoders();
			
			//Controller object for telop
			theMCP = new CommandController();
			theMCP.init(myDriveTrain, myNavigation, theVision, myRobot);
			
			//The handler that handles everything JSON related 
			myAutonomousDataHandler = new AutonomousDataHandler();
			
		  //Takes the CommandController in order to create AutonomousStates that work with the control scheme
			myAutonomousDataHandler.init(theMCP);
			
			//Controller Object for autonomous
			theMACP = new AutonomousController(); 
			
			//Gives the newly read JSON data to the AutonomousController for processing
      theMACP.setAutonomousControllerData(myAutonomousDataHandler);
      
      
			Logger.Log("Robot(): robotInit(): Fully Initialized");
			
		  SmartDashboard.putBoolean("Output Log to File", false);
		   
		  SmartDashboard.putBoolean("Debug", false);
		  
		} 
		catch (Exception ex) 
		{
		  ex.printStackTrace();
			Logger.Log("Robot(): robotInit() Exception : "+ex);

		}
	}

	/**
	 * Creates all the objects to be used on the SmartDashboard
	 */
	@SuppressWarnings("deprecation")
  public void InitSmartDashboardObjects(){
	  
	 
	  SmartDashboard.putNumber("Chosen Auto Mode", 0);
  
	  SmartDashboard.putBoolean("Match Time Flag", false);
  
	  SmartDashboard.putNumber("Select Auto State", 0);
  
	  //Sendable Chooser for the state update function
	  autonomousStateParamsUpdate = new SendableChooser<String>();
	  autonomousStateParamsUpdate.addDefault("As Received", "0");
	  autonomousStateParamsUpdate.addObject("UPDATE", "1");
  
	  //Create a new SendableChooser for the save function
	  autonomousSaveChooser = new SendableChooser<String>();
	  autonomousSaveChooser.addDefault("DON'T Save", "0");
	  autonomousSaveChooser.addObject("Save", "1");
	  autonomousSaveChooser.addObject("Read", "2");
	  
	  
	  
	  SmartDashboard.putData("Edit State Params", autonomousStateParamsUpdate);
	  SmartDashboard.putData("Save Changes", autonomousSaveChooser);
	  
    SmartDashboard.putBoolean("Update Params", false);
    SmartDashboard.putBoolean("Save to Amode238", false);
    SmartDashboard.putBoolean("Read Amode238", false);

    SmartDashboard.putBoolean("CLIMBDEBUG", false);
    
    SmartDashboard.putNumber("Curl Turn Value", 0.5);

    SmartDashboard.putString("Alliance Color", "Red");
 
	}
	
	public CommandController getTheMCP()
	{
		return theMCP;
	}
  
  public Alliance getAllianceTeam()
  {
    return teamColor; 
  }

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() 
	{
		SmartDashboard.putNumber("Left Encoder", leftFrontDrive.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Right Encoder", rightFrontDrive.getSelectedSensorPosition(0));
		
		try 
		{
			
			theMACP.process();
			myNavigation.navxValues();
			
			int currentYaw = (int) myNavigation.getYaw();			
			SmartDashboard.putNumber("AutonomousPeriodic: The CurrentYaw ", currentYaw);
			
		} 
		catch (Exception ex) 
		{
		  ex.printStackTrace();
			Logger.Log("Robot(): autonomousPeriodic() Exception: "+ex);
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() 
	{

		HashMap<Integer,Integer[]> commandValues;
		
		SmartDashboard.putNumber("Left Encoder", leftFrontDrive.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Right Encoder", rightFrontDrive.getSelectedSensorPosition(0));
		
		try 
		{

			//get the buttons that were pressed on the joySticks/controlBoard
			commandValues = myControlBoard.getControllerInputs();
			
			//pass the buttonsPressed into the commandController for command execution
			theMCP.joyStickCommandExecution(commandValues);
			
			//mjf do we need this?
			myNavigation.navxValues();

		} 
		catch (Exception e) 
		{
		  e.printStackTrace();
			Logger.Log("Robot(): teleopPeriodic() Exception: "+ e);
			
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() 
	{

	  try
	  {
	    
	    
	  }
	  catch(Exception e)
	  {
	    e.printStackTrace();
	  }
	  
	}
	
	
	/**
	 * This should ONLY be called from an init function
	 */
	@SuppressWarnings({ "unused", "deprecation" })
  private void checkForSmartDashboardChanges(String key, String value) 
	{
		myPreferences = Preferences.getInstance();

		String valueFromPrefs = myPreferences.getString(key, value);
		if (valueFromPrefs != null) 
		{
			Logger.Log("Robot(): CheckSDChanges(): valueFromPrefs : " + key + " = " + valueFromPrefs);
			String valueFromDS = null;
			
			try 
			{
				valueFromDS = SmartDashboard.getString(key, valueFromDS);
			} 
			catch (Exception ex) 
			{
				ex.printStackTrace();
				SmartDashboard.putString(key, valueFromPrefs);
			}

			Logger.Log("Robot(): CheckSDChanges(): ValFromDS : " + key + " = " + valueFromDS);

			// check for null and also if it's empty don't overwrite what's in
			// the preferences table
			if ((valueFromDS != null)  && (!valueFromDS.isEmpty())) 
			{
								// if they are not the same then update the preferences
				if (!valueFromPrefs.equalsIgnoreCase(valueFromDS)) 
				{
					
					Logger.Log("Robot(): CheckSDChanges(): UpdatePrefs" + key + " = " + valueFromDS);
					myPreferences.putString(key, valueFromDS);

					// NEVER NEVER use this save() in a periodic function or you
					// will destroy your RoboRio
					// making it an expensive chunk of useless plastic and
					// silicon
					
					//Thanks for the heads up bro!
					
					//myPreferences.save();
				}
			}
			
			if(( valueFromDS != null) && (valueFromDS.isEmpty()) && (!valueFromPrefs.isEmpty())) 
			{
			
				SmartDashboard.putString(key, valueFromPrefs);
			
			}
		}
	}
}
