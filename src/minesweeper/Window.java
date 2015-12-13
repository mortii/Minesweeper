package minesweeper;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

public class Window {
	static HWND minesweeperWindow = User32.INSTANCE.FindWindow(null, "Minesweeper");
	
	public static void setMinesweeperSizeAndPosition(){
		if (minesweeperWindow == null) {
			System.out.println("Minesweeper is not running");
			System.exit(1);
		}
		else{
			User32.INSTANCE.SetWindowPos(minesweeperWindow, null, 20, 80, 1100, 1100, 68);
		}
	}
	
	public static void setMinesweeperToForeground() {
		User32.INSTANCE.SetForegroundWindow(minesweeperWindow);
	}
	
	public static boolean gameWasLost(){
		HWND gameLostPopUp = User32.INSTANCE.FindWindow(null, "Game Lost");
		
		if (gameLostPopUp == null){
			return false;
		}
		System.out.println("Game Lost");
		return true;
	}
	
	public static boolean gameWasWon(){
		HWND gameWonPopUp = User32.INSTANCE.FindWindow(null, "Game Won");
		
		if (gameWonPopUp == null){
			return false;
		}
		System.out.println("Game Won");
		return true;
	}
}