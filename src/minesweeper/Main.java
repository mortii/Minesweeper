package minesweeper;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
	public static ArrayList<Integer> nonClickedSquares;
	public static ArrayList<Integer> squaresWithNumbers;
	public static HashMap<Integer, Square> squaresMap;
	public static Robot robot;
	public static int advancedTechniques = 0;
	public static int guessed = 0;

	public static void main(String[] args) throws AWTException{
		nonClickedSquares = new ArrayList<Integer>();
		squaresWithNumbers = new ArrayList<Integer>();
		squaresMap = new HashMap<Integer, Square>();
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
		fillArrayLists();
		fillSquaresMap();
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

	public static void addToNonClickedSquares(int row, int column){
		int square = Board.getSquare(row, column);
		nonClickedSquares.add(square);
	}

	public static void addToSquaresWithNumbers(int row, int column){
		int square = Board.getSquare(row, column);
		squaresWithNumbers.add(square);
	}

	public static void fillSquaresMap(){
		for (int square : squaresWithNumbers){
			Square squareWithNumber = new Square(square);
			squaresMap.put(square, squareWithNumber);
		}
	}

	public static void updateSquaresWithNumbers(){
		for (int square : squaresWithNumbers){
			Square squareWithNumber = squaresMap.get(square);
			squareWithNumber.updateSurroundings();
			squaresMap.put(square, squareWithNumber);
		}
	}

	public static void solve(){
		int roundsWithoutClicking = 0;

		while (!gameOver()){
			if (doSimpleTechniques()){
				roundsWithoutClicking = 0;
			}
			else{
				if (roundsWithoutClicking == 1){
					updateNonClickedSquares();
					updateSquaresWithNumbers();
				}
				else if (roundsWithoutClicking == 2){
					if (doAdvancedTechniques()){
						roundsWithoutClicking = 0;
					}
				}
				else if (roundsWithoutClicking == 3){
					if(Mouse.clickRandomSurroundingNonClicked()){
						roundsWithoutClicking = 0;
					}
				}
				else if (roundsWithoutClicking == 4){
					Mouse.clickRandomNonClicked();
					roundsWithoutClicking = 0;
				}
			}
			roundsWithoutClicking++;			
		}
	}
	
	private static boolean gameOver(){
		//The pop-up window is in different position depending on whether
		//the game is won or lost. 
		//The windows 10 pop-up window is white.
		
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
//		 http://www.minesweeper.info/wiki/Strategy
		//copy ArrayList to avoid concurrency issues
		ArrayList<Integer> squaresWithNumbersCopy = new ArrayList<Integer>(squaresWithNumbers);
		boolean clickedSquares = false;
		
		for (int square : squaresWithNumbersCopy){
			Square squareData = squaresMap.get(square);
			
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
		//copy ArrayList to avoid concurrency issues
		ArrayList<Integer> nonClickedCopy = new ArrayList<Integer>(nonClickedSquares);
		Board.updateBoardImage();
		
		for (int square : nonClickedCopy){
			int number = ComputerVision.getNumber(square);
			
			if (number != 8){
				Board.placeNumberOnBoard(square, number);
				removeFromNonClicked(square);
	
				if (number != 0 && number != 9){
					Square squareWithNumber = new Square(square);
					squaresMap.put(square, squareWithNumber);
					squaresWithNumbers.add(square);
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
		
		for (int numberedSquare : squaresWithNumbers){
			Square square = squaresMap.get(numberedSquare);
			AdvancedData advancedData = new AdvancedData(square);
			
			int number = square.numberOnSquare - square.surroundingFlags;
			
			if (number == 1){
				if (square.surroundingNonClickedSquares.size() == 2){
					if (oneAndOneTechnique(advancedData)){
						clickedSquares = true;
					}
				}
			}
			else if (number == 2){
				if (square.surroundingNonClickedSquares.size() == 3){
					if (oneAndTwoTechnique(advancedData)){
						clickedSquares = true;
					}
				}
			}
		}
		return clickedSquares;
	}
	
	public static boolean oneAndOneTechnique(AdvancedData advancedData){
		AdvancedData.OneAndOne oneAndOneData = advancedData.new OneAndOne();
		
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

	public static boolean oneAndTwoTechnique(AdvancedData advancedData){
		AdvancedData.OneAndTwo oneAndTwoData = advancedData.new OneAndTwo();
		
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

	public static void printArrayList(ArrayList<Integer> liste){
		for (int element : liste){
			System.out.print(element + " ");
		}
		System.out.println();
	}
}