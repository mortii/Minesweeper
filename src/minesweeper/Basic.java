package minesweeper;

import java.util.ArrayList;

public class Basic {
	private static Square squareWithNumber;
	private static int number;
	private static int flags;
	private static int nonClicked;
	private static boolean hasClickedSquares;
	
	public static boolean doBasicSolving(){
		//copy ArrayList to avoid concurrency issues
		ArrayList<Integer> squaresWithNumbersCopy = new ArrayList<Integer>(Lists.squaresWithNumbers);
		hasClickedSquares = false;
		
		for (int square : squaresWithNumbersCopy){
			setClassVariables(square);
			solve();
		}
		return hasClickedSquares;
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

	private static void successUpdate(){
		Main.basic++;
		hasClickedSquares = true;
		Lists.removeFromSquaresWithNumbers(squareWithNumber.square);
	}
}