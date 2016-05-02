# RecordingJoystick
This is a (nearly) plug-and-play autonomous recording feature for First Robotics Competition robots coded in Java. It emulates a Joystick, so it requires no major integrations with existing code to enable an FRC team to record and playback automonous programs using joystick commands.

It works by wrapping a Joystick object, which can then either record states of the Driver Station joystick and save them to a file,
or playback previously-saved states from a file (during the autonomous period.)

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
