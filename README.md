# RecordingJoystick
This is a (nearly) plug-and-play autonomous recording feature for First Robotics Competition robots coded in Java. It emulates a Joystick, and therefore can be simply integrated with existing code to enable an FRC team to record and playback automonous programs using joystick inputs.

The rationale in making this was not to create the most robust or precise autonomous-recording method, but this class can add recorded auto programs to nearly any Java-programmed FRC robot. However, if your normal joystick inputs are fed into closed-loop control systems (i.e. encoder-based PID control systems for drive wheel or arm RPM, etc.), this is likely to have the precision of normal closed-loop auto programs. 

<strong>How it works:</strong>

It operates by wrapping a Joystick object, which can then either record states of the Driver Station joystick and save them to a file, or playback previously-saved states from a file (during the autonomous period.)

The joystick records all axes, POVs, and buttons on the specified joystick and will play them back such that calls to getX(), getRawButton(), etc. return values as if recieving inputs from the joystick attached to the Driver Station.

As long as your joystick can be interfaced via <coce>edu.wpi.first.wpilibj.Joystick</code> OR extends GenericHID, it can be replaced with a RecordingJoystick for this functionality. There are also constants in the class that can be modified to accomodate its number of axis or buttons.

<strong>Integration:</strong>

The integration is simple, and includes three basic steps.<br>
Look at the examples/Robot.java file for a more detailed explanation.
<br><br>After initializing: <code>RecordingJoystick joystick = new RecordingJoystick(0); // DS port 0</code>
<ol>
<li>Call <code>joystick.startRecording(String filename, double duration)</code> to begin recording at some point. 
This is best done conditionally based on driver station input during the start of teleop. Using a joystick button shouldn't be done because the joystick could start itself recording.</li>
<li>Call <code>joystick.startPlayback(String filename)</code> to begin recording at the beginning of autonomous.</li>
<li>Call <code>joystick.updateState()</code> periodically in some way so the joystick can check for and execute state updates.</li> 
</ol>

<strong>Notes:</strong>
<ul>
<li>The playback/recording files are serialized Java objects, and are saved in the RoboRio.</li>
<li>The best way we've found to interface with it is through a SmartDashboard sendable chooser. (See examples/Robot.java)</li>
<li>This has not been thoroughly tested, and the state update frequency has not been optimized.</li>
<li>Please feel free to send feedback on how it works for your robots and push any improvements you make.</li>
