package minesweeper;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
	public static ArrayList<Integer> nonClickedSquares;
	public static ArrayList<Integer> squaresWithNumbers;
	public static HashMap<Integer, Square> squareMap;
	public static Robot robot;
	public static int advancedTechniques = 0;
	public static int guessed = 0;

	public static void main(String[] args) throws AWTException{
		nonClickedSquares = new ArrayList<Integer>();
		squaresWithNumbers = new ArrayList<Integer>();
		squareMap = new HashMap<Integer, Square>();
		robot = new Robot();
			
		start();
		solve();
		
		System.out.println("Used advanced techniques: "+advancedTechniques+" times");
		System.out.println("Gussed: "+guessed+" times");
		Board.printBoard();
	}
	
	public static void start(){
		Window.setMinesweeperSizeAndPosition();
		Window.setMinesweeperToForeground();
		Mouse.clickFirstSquare();
		Board.updateEntireBoard();
		Lists.fillArrayLists();
		Maps.fillSquareMap();
	}

	public static void solve(){
		int roundsWithoutClicking = 0;

		while (Window.gameNotOver()){
			if (Basic.doBasicSolving()){
				roundsWithoutClicking = 0;
			}
			else{
				if (roundsWithoutClicking == 1){
					Board.updateBoardImage();
					Lists.updateNonClickedSquares();
					Lists.updateSquaresWithNumbers();
				}
				else if (roundsWithoutClicking == 2){
					if (Advanced.doAdvancedSolving()){
						roundsWithoutClicking = 0;
					}
				}
				else if (roundsWithoutClicking == 3){
					if(Mouse.clickRandomSurroundingNonClicked()){
						roundsWithoutClicking = 0;
					}
				}
				else if (roundsWithoutClicking == 4){
					if (Mouse.clickRandomNonClicked()){
						roundsWithoutClicking = 0;
					}
				}

			}
			roundsWithoutClicking++;			
		}
	}
}