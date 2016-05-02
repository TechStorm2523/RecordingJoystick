/**
 * An example Robot.java file showcasing how to use a RecordingJoystick
 * on a robot.
 * 
 * This is based off the IterativeRobot model, and our team uses this
 * in tandem with Command-Based Programming, thought the outline here
 * should apply to any robot programming method, as long as the 
 * correct methods are called at the correct initialization points 
 * and periodically when need be.
 * 
 * @author Mckenna Cisler (Team 2523)
 */

package org.usfirst.frc.team####.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	// This is a constant for the location on the RoboRio to save joystick recordings. 
	// You may have to SSH in to the Rio to create this location or your own.
	static final String JOYSTICK_RECORDINGS_SAVE_LOCATION = "/home/lvuser/joystickRecordings/";
	
	// The joysticks used here
	RecordingJoystick driveStick;
	RecordingJoystick utilStick;
	
	// Global variables for use in the various SmartDashboard choosers to select playback and 
	// recording files.
    public static SendableChooser playbackChooser;
    public static SendableChooser recordingChooser;
    
    // From example code for a generic Command-Based Programming auto chooser
    Command autonomousCommand;
    SendableChooser autoChooser;

    public Robot()
    {
    	driveStick = new RecordingJoystick(0);
    	utilStick = new RecordingJoystick(1);
    }
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {  
    	// This is a normal SmartDashboard autonomous chooser, used with Command-Based Programming.
    	// It is included to demonstrate how to integrate it with the RecordingJoystick.
    	// This is completely unnecessary.
        autoChooser = new SendableChooser();
        autoChooser.addDefault("My Example Auto Program", new ExampleAutoCommand());
        SmartDashboard.putData("Auto Program", autoChooser);
        
        // RECORDING JOYSTICK CHOOSERS
        
        // SmartDashboard chooser to be used to choose recording to PLAY BACK
        playbackChooser = new SendableChooser();
        
        // SmartDashboard chooser to be used to choose recording to RECORD
        recordingChooser = new SendableChooser();
        
        playbackChooser.addDefault("Don't Play Back", null);
        recordingChooser.addDefault("Don't Record", null);
        
        // here, the first parameter is the display name, while the second is the 
        // Object to be passed from the getSelected() call. 
        // In this case, it's the stored file's name.
        playbackChooser.addObject("RecordingJoystick Recording 1", "recording_1");
        recordingChooser.addObject("RecordingJoystick Recording 1", "recording_1");
        
        playbackChooser.addObject("RecordingJoystick Recording 2", "recording_2");
        recordingChooser.addObject("RecordingJoystick Recording 2", "recording_2");
        
        SmartDashboard.putData("Joystick Playback Program", playbackChooser);
        SmartDashboard.putData("Joystick Recording Program", recordingChooser);
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		allPeriodic();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {    	      
		String joystickRecording = (String) playbackChooser.getSelected();
		autonomousCommand = (Command) autoChooser.getSelected();

		// When choosing the program to run, a normal auto command is prioritized 
		// in case both are selected.
		if (autonomousCommand != null)
		{			
	    	// Schedule the chosen autonomous command via Command-Based Programming paradigms
	        autonomousCommand.start();
		}
		// We set the Object parameter of the "Don't play back" selection box above to be null, so we can tell here. 
		else if (joystickRecording != null)
		{
			driveStick.startPlayback(JOYSTICK_RECORDINGS_SAVE_LOCATION + joystickRecording + "_drive");
			utilStick.startPlayback(JOYSTICK_RECORDINGS_SAVE_LOCATION + joystickRecording + "_util");
		}
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {    	
        // The JoystickRecording must check if its state needs to be
    	// either updated or recorded, and this must be done both 
    	// during the auto mode and during teleop if recording.
        // This could be seperated into another method.
        driveStick.updateState();
        utilStick.updateState();
    }

    public void teleopInit() {    	
		// (From example comments)
    	// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        
        // Stops playback if RecordingJoystick hasn't done so internally.
    	driveStick.stopPlayback();
    	utilStick.stopPlayback();
        
        // Get the SmartDashboard RECORDING selection to see if 
    	// we should start recording the joystick at the start of teleop
		String joystickRecording = (String) recordingChooser.getSelected();
        if (joystickRecording != null)
        {        	
        	// Record on both for the length of autonomous (15 seconds)
        	driveStick.startRecording(JOYSTICK_RECORDINGS_SAVE_LOCATION + joystickRecording + "_drive", 15);
        	utilStick.startRecording(JOYSTICK_RECORDINGS_SAVE_LOCATION + joystickRecording + "_util", 15);
        }
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {  		
        // The JoystickRecording must check if its state needs to be
    	// either updated or recorded, and this must be done both 
    	// during the auto mode and during teleop if recording.
        // This could be seperated into another method.
        driveStick.updateState();
        utilStick.updateState();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
