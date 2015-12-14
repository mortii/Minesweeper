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
		fillSquareMap();
	}

	public static void fillSquareMap(){
		for (int square : squaresWithNumbers){
			Square squareWithNumber = new Square(square);
			squareMap.put(square, squareWithNumber);
		}
	}

	public static void solve(){
		int roundsWithoutClicking = 0;

		while (!gameOver()){
			if (doBasicSolving()){
				roundsWithoutClicking = 0;
			}
			else{
				if (roundsWithoutClicking == 1){
					Board.updateBoardImage();
					updateNonClickedSquares();
					Lists.updateSquaresWithNumbers();
				}
				else if (roundsWithoutClicking == 2){
					if (doAdvancedSolving()){
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
	
	private static boolean gameOver(){
		if (Window.gameWasWon() || Window.gameWasLost()){
			return true;
		}
		return false;
	}
	
	/* 
	 * http://www.minesweeper.info/wiki/Strategy
	 */
	public static boolean doBasicSolving(){
		//copy ArrayList to avoid concurrency issues
		ArrayList<Integer> squaresWithNumbersCopy = new ArrayList<Integer>(squaresWithNumbers);
		boolean clickedSquares = false;
		
		for (int square : squaresWithNumbersCopy){
			Square squareWithNumber = squareMap.get(square);
			
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


	public static void updateNonClickedSquares(){
		//copy ArrayList to avoid concurrency issues
		ArrayList<Integer> nonClickedCopy = new ArrayList<Integer>(nonClickedSquares);
		
		for (int square : nonClickedCopy){
			int number = ComputerVision.getNumber(square);
			
			if (number != 8){
				Board.placeNumberOnBoard(square, number);
				removeFromNonClicked(square);
	
				if (number != 0 && number != 9){
					Square squareWithNumber = new Square(square);
					squareMap.put(square, squareWithNumber);
					squaresWithNumbers.add(square);
				}
			}
		}
	}
	
	public static boolean doAdvancedSolving(){
//		 http://www.minesweeper.info/wiki/Strategy
		
		boolean clickedSquares = false;
		
		for (int square : squaresWithNumbers){
			Square squareWithNumber = squareMap.get(square);
			
			int number = squareWithNumber.numberOnSquare
					- squareWithNumber.surroundingFlags;
			
			if (number == 1){
				if (squareWithNumber.surroundingNonClickedSquares.size() == 2){
					if (oneAndOneTechnique(squareWithNumber)){
						clickedSquares = true;
					}
				}
			}
			else if (number == 2){
				if (squareWithNumber.surroundingNonClickedSquares.size() == 3){
					if (oneAndTwoTechnique(squareWithNumber)){
						clickedSquares = true;
					}
				}
			}
		}
		return clickedSquares;
	}
	
	public static boolean oneAndOneTechnique(Square squareWithNumber){
		OneAndOne oneAndOneData = new OneAndOne(squareWithNumber);
		
		if (oneAndOneData.nonClickedAreNextToEachOther()){
			if (squareIsOne(oneAndOneData.adjecentSquareWithNumber)){
				if (Mouse.clickAllExceptEdgeAndNextToEdge(oneAndOneData)){
					System.out.println("advancedTechnique 1-1");
					advancedTechniques++;
					return true;
				}
			}
		}
		return false;
	}

	public static boolean squareIsOne(int adjecentSquare){
		int row = Board.getRow(adjecentSquare);
		int column = Board.getColumn(adjecentSquare);
		
		if (Board.board[row][column] == 1){
			return true;
		}
		return false;
	}

	public static boolean oneAndTwoTechnique(Square squareWithNumber){
		OneAndTwo oneAndTwoData = new OneAndTwo(squareWithNumber);
		
		if (oneAndTwoData.nonClickedAreNextToEachOther()){
			if (squareIsOne(oneAndTwoData.firstAdjecentNumbered)){
				flagAndUpdate(oneAndTwoData.lastNonClicked);
				return true;
			}
			else if (squareIsOne(oneAndTwoData.lastAdjecentNumbered)){
				flagAndUpdate(oneAndTwoData.firstNonClicked);
				return true;
			}
		}
		return false;
	}
	
	public static void flagAndUpdate(int square){
		System.out.println("advancedTechnique 1-2");
		Mouse.flagSquare(square);
		Square.updateTheSurroundingSquares(square);
		advancedTechniques++;
	}


}