package minesweeper;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

public class Window {
	
	public static void setMinesweeperSizeAndPosition(){
		HWND window = User32.INSTANCE.FindWindow(null, "Minesweeper");

		if (window == null) {
			System.out.println("Minesweeper is not running");
			System.exit(1);
		}
		else{
			User32.INSTANCE.SetWindowPos(window, null, 20, 80, 1100, 1100, 68);
		}
	}
	
	public static void setMinesweeperToForeground() {
		User32.INSTANCE.SetForegroundWindow(window);
	}
	
	public static boolean gameWasLost(){
		HWND window = User32.INSTANCE.FindWindow(null, "Game Lost");
		
		if (window == null){
			return false;
		}
		
		System.out.println("game lost");
		return true;
	}
	
	public static boolean gameWasWon(){
		HWND window = User32.INSTANCE.FindWindow(null, "Game Won");
		
		if (window == null){
			return false;
		}
		
		System.out.println("game won");
		return true;
	}
}