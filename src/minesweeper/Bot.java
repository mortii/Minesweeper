package minesweeper;

import java.awt.AWTException;
import java.awt.Robot;

public class Bot {
	public static Robot robot;
	
	public static void initiateRobot(){
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
}