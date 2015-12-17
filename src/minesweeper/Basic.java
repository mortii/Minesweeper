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
			solve();
		}
		return clickedSquares;
	}
	
	private static void setClassVariables(int square){
		squareWithNumber = Maps.squareMap.get(square);
		number = squareWithNumber.numberOnSquare;
		flags = squareWithNumber.surroundingFlags;
		nonClicked = squareWithNumber.surroundingNonClickedSquares.size();
	}

	//http://www.minesweeper.info/wiki/Strategy
	private static void solve(){
		if (number == flags){
			Mouse.clickSurroundingNonClicked(squareWithNumber);
			successUpdate();
		}
		else if (number == flags + nonClicked){
			Mouse.flagSurroudingNonClicked(squareWithNumber);
			successUpdate();
		}
	}

	public static void successUpdate(){
		Main.basic++;
		clickedSquares = true;
		Lists.removeFromSquaresWithNumbers(squareWithNumber.square);
	}
}