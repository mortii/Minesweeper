package minesweeper;

import java.util.ArrayList;

public class Basic {
	
	//http://www.minesweeper.info/wiki/Strategy
	public static boolean doBasicSolving(){
		//copy ArrayList to avoid concurrency issues
		ArrayList<Integer> squaresWithNumbersCopy = new ArrayList<Integer>(Lists.squaresWithNumbers);
		boolean clickedSquares = false;
		
		for (int square : squaresWithNumbersCopy){
			Square squareWithNumber = Maps.squareMap.get(square);
			
			int number = squareWithNumber.numberOnSquare;
			int flags = squareWithNumber.surroundingFlags;
			int nonClicked = squareWithNumber.surroundingNonClickedSquares.size();
			
			if (number == flags){
				Mouse.clickSurroundingNonClicked(squareWithNumber);
				Lists.removeFromSquaresWithNumbers(square);
				clickedSquares = true;
			}
			else if (number == flags + nonClicked){
				Mouse.flagSurroudingNonClicked(squareWithNumber);
				Lists.removeFromSquaresWithNumbers(square);
				clickedSquares = true;
			}
		}
		return clickedSquares;
	}
}
