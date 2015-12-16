package minesweeper;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Bot {
	private static Robot robot;
	private static final int MILLISECONDS_CLICK_DELAY = 35;
	private static HashMap<Integer, Pixel> centerOfSquares = OnDisk.getHashMap("centerOfSquares.ser");
	
	public static void initiateRobot(){
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public static void moveMouse(int square){
		Pixel pixel = centerOfSquares.get(square);
		robot.mouseMove(pixel.x, pixel.y);
	}
	
	public static void leftClickMouse(){
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(MILLISECONDS_CLICK_DELAY);
		robot.mouseMove(0, 0);
	}
	
	public static void delay(int milliseconds){
		robot.delay(milliseconds);
	}
	
	public static void rightClickMouse(){
		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
		robot.delay(MILLISECONDS_CLICK_DELAY);
	}
	
	public static BufferedImage screenShot(Rectangle boardRectangle){
		return robot.createScreenCapture(boardRectangle);
	}
}