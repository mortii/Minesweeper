

![](http://gfycat.com/HelpfulObeseCorydorascatfish)


[![Demo CountPages alpha](http://share.gifyoutube.com/KzB6Gb.gif)]


### Download the old minesweeper here:
http://forum.xda-developers.com/showthread.php?t=2096449

### How to use:
**This only works with a 1920x1080 screen resolution on Windows.**

The minesweeper board must have the same tile grid as the "Advanced" difficulty, i.e. 16x30.
You also need to untick the "Display animations" and "Allow question marks" boxes to get the optimal results.

The speed of the program is dependent on the milliSecondClickDelay variable in MouseOperations.java, but
due to race conditions in the Robot class it can not be less than 5, but should be at least 25 to produce
consistent results. (The race condition causes the mouse to move before the click is registered).
	
###Dependencies:
jna.jar and jna-platform.jar which can be downloaded here:
https://github.com/java-native-access/jna




