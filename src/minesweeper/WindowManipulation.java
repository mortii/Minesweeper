package minesweeper;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

public class WindowManipulation {
	private static HWND window = User32.INSTANCE.FindWindow(null, "Minesweeper");

	
	public static void setMinesweeperSizeAndPosition(){
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
	
	
}