package minesweeper;

import java.util.ArrayList;

public class Basic {
	private static Square squareWithNumber;
	private static int number;
	private static int flags;
	private static int nonClicked;
	private static boolean clickedSquares;
	
	public static boolean doBasicSolving(){
		//copy ArrayList to avoid concurrency issues
		ArrayList<Integer> squaresWithNumbersCopy = new ArrayList<Integer>(Lists.squaresWithNumbers);
		clickedSquares = false;
		
		for (int square : squaresWithNumbersCopy){
			setClassVariables(square);
			solve(square);
		}
		return clickedSquares;
	}
	
	//http://www.minesweeper.info/wiki/Strategy
	private static void solve(int square){
		if (number == flags){
			Mouse.clickSurroundingNonClickedAndUpdateSurroundingSquares(squareWithNumber);
			Lists.removeFromSquaresWithNumbers(square);
			clickedSquares = true;
			Main.basic++;
		}
		else if (number == flags + nonClicked){
			Mouse.flagSurroudingNonClicked(squareWithNumber);
			Lists.removeFromSquaresWithNumbers(square);
			clickedSquares = true;
			Main.basic++;
		}
	}
	
	private static void setClassVariables(int square){
		squareWithNumber = Maps.squareMap.get(square);
		number = squareWithNumber.numberOnSquare;
		flags = squareWithNumber.surroundingFlags;
		nonClicked = squareWithNumber.surroundingNonClickedSquares.size();
	}
}