package minesweeper;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {
	public static ArrayList<Integer> nonClickedSquares;
	public static ArrayList<Integer> squaresWithNumbers;
	public static HashMap<Integer, SquareData> squareDataMap;
	public static Robot robot;
	public static int advancedTechniques = 0;
	public static int guessed = 0;

	public static void main(String[] args) throws AWTException{
		nonClickedSquares = new ArrayList<Integer>();
		squaresWithNumbers = new ArrayList<Integer>();
		squareDataMap = new HashMap<Integer, SquareData>();
		robot = new Robot();
		
		start();
		solve();
		
		System.out.println("Used advanced techniques: "+advancedTechniques+" times");
		System.out.println("Gussed: "+guessed+" times");
		Board.printBoard();
	}
	
	public static void start(){
		WindowManipulation.setMinesweeperSizeAndPosition();
		WindowManipulation.setMinesweeperToForeground();
		Mouse.clickFirstSquare();
		Board.updateEntireBoard();
		fillArrayLists();
		updateSquareData(squaresWithNumbers);
	}

	public static void fillArrayLists(){
		int[][] board = Board.board;
		
		for (int row = 0; row < board.length; row++){
			for (int column = 0; column < board[0].length; column++){
				
				if (board[row][column] == 8){
					addToNonClickedSquares(row, column);
				}
				else if (board[row][column] != 0 && board[row][column] != 9){
					addToSquaresWithNumbers(row, column);
				}
			}
		}
	}

	public static void updateSquareData(ArrayList<Integer> squaresList){
		for (int square : squaresList){
			SquareData.updateSquareData(square);
		}
	}

	public static void solve(){
		int roundsWithoutAction = 0;

		while (!gameOver()){
			
			if (doSimpleTechniques()){
				roundsWithoutAction = 0;
			}
			else{
				if (roundsWithoutAction == 1){
					updateNonClickedSquares();
					updateSquareData(squaresWithNumbers);
				}
				else if (roundsWithoutAction == 2){
					if (doAdvancedTechniques()){
						roundsWithoutAction = 0;
					}
				}
				else if (roundsWithoutAction == 3){
					if(Mouse.clickRandomSurroundingNonClicked()){
						roundsWithoutAction = 0;
					}
				}
				else if (roundsWithoutAction == 4){
					Mouse.clickRandomNonClicked();
					roundsWithoutAction = 0;
				}
			}
			roundsWithoutAction++;			
		}
	}
	
	public static void addToSquaresWithNumbers(int row, int column){
		int square = MatrixConversion.getSquare(row, column);
		squaresWithNumbers.add(square);
	}

	public static void addToNonClickedSquares(int row, int column){
		int square = MatrixConversion.getSquare(row, column);
		nonClickedSquares.add(square);
	}

	private static boolean gameOver(){
		Color gameLost = robot.getPixelColor(560, 326);
		Color gameWon = robot.getPixelColor(580, 310);
		
		if (gameLost.equals(Color.white)){
			System.out.println("Game Lost");
			return true;
		}
		else if (gameWon.equals(Color.white)){
			System.out.println("Game Won");
			return true;
		}
			
		return false;
	}
	
	public static boolean doSimpleTechniques(){
		ArrayList<Integer> squaresWithNumbersCopy = new ArrayList<Integer>(squaresWithNumbers);
		boolean clickedSquares = false;
		
		for (int square : squaresWithNumbersCopy){
			SquareData squareData = squareDataMap.get(square);
			
			int number = squareData.numberOnSquare;
			int flags = squareData.surroundingFlags;
			int nonClicked = squareData.surroundingNonClickedSquares.size();
			
			if (number == flags){
				Mouse.clickSurroundingNonClicked(squareData);
				removeFromSquaresWithNumbers(square);
				clickedSquares = true;
			}
			else if (number == flags + nonClicked){
				Mouse.flagSurroudingNonClicked(squareData);
				removeFromSquaresWithNumbers(square);
				clickedSquares = true;
			}
		}
		return clickedSquares;
	}

	public static void removeFromSquaresWithNumbers(int square){
		int index = squaresWithNumbers.indexOf(square);
		squaresWithNumbers.remove(index);
	}

	public static void updateNonClickedSquares(){
		ArrayList<Integer> nonClickedCopy = new ArrayList<Integer>(nonClickedSquares);
		Board.updateBoardImage();
		
		for (int square : nonClickedCopy){
			int number = ComputerVision.getNumber(square);
			
			if (number != 8){
				Board.placeNumberOnBoard(square, number);
				removeFromNonClicked(square);
	
				if (number != 0 && number != 9){
					squaresWithNumbers.add(square);
					SquareData.updateSquareData(square);
				}
			}
		}
	}
	
	public static void removeFromNonClicked(int square){
		int index = nonClickedSquares.indexOf(square);
		nonClickedSquares.remove(index);
	}
	
	public static boolean doAdvancedTechniques(){
//		 http://www.minesweeper.info/wiki/Strategy
		
		boolean clickedSquares = false;
		
		for (int square : squaresWithNumbers){
			SquareData squareData = squareDataMap.get(square);
			int number = squareData.numberOnSquare - squareData.surroundingFlags;
			
			if (number == 1){
				if (oneAndOneTechnique(squareData)){
					clickedSquares = true;
				}
			}
			else if (number == 2){
				if(oneAndTwoTechnique(squareData)){
					clickedSquares = true;
				}
			}
		}
		return clickedSquares;
	}
	
	public static boolean oneAndOneTechnique(SquareData squareData){
		AdvancedData advancedData = new AdvancedData(squareData);
		
		if (squareData.surroundingNonClickedSquares.size() == 2){
			if (advancedData.nonClickedIsEdge(squareData)){
				if (adjacentSquareIsOne(advancedData.otherNumberedSquare)){
					if (Mouse.clickAllExceptEdgeAndNonEdge(advancedData)){
						System.out.println("advancedTechnique 1-1");
						advancedTechniques++;
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean adjacentSquareIsOne(int adjecentSquare){
		SquareData adjecentSquareData = Main.squareDataMap.get(adjecentSquare);
		
		if (adjecentSquareData != null){
			int number = adjecentSquareData.numberOnSquare - adjecentSquareData.surroundingFlags;
			
			if (number == 1){
				return true;
			}
		}
		return false;
	}

	public static boolean oneAndTwoTechnique(SquareData squareData){
		AdvancedData advancedData = new AdvancedData(squareData);
		
		if (squareData.surroundingNonClickedSquares.size() == 3){
			if (advancedData.squaresThreeAreNextToEachOther(squareData)){
				if (adjacentSquareIsOne(advancedData.firstNumSquareNextToOrigin)){
					System.out.println("advancedTechnique 1-2");
					Mouse.flagSquare(advancedData.lastNonClicked);
					SquareData.updateSurroundingSquares(advancedData.lastNonClicked);
					advancedTechniques++;
					return true;
				}
				else if (adjacentSquareIsOne(advancedData.lastNumSquareNextToOrigin)){
					System.out.println("advancedTechnique 1-2");
					Mouse.flagSquare(advancedData.firstNonClicked);
					SquareData.updateSurroundingSquares(advancedData.firstNonClicked);
					advancedTechniques++;
					return true;
				}
			}
		}
		return false;
	}

	public static void printArrayList(ArrayList<Integer> liste){
		for (int element : liste){
			System.out.print(element + " ");
		}
		System.out.println();
	}
}