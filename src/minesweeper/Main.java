package minesweeper;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {
	public static ArrayList<Integer> nonClickedSquares;
	public static ArrayList<Integer> squaresWithNumbers;
	public static HashMap<Integer, AdvancedData> advancedDataMap;
	public static HashMap<Integer, SquareData> squareDataMap;
	public static Robot robot;
	public static int guessed = 0;
	public static int advancedTechniques = 0;

	public static void main(String[] args) throws AWTException{
		nonClickedSquares = new ArrayList<Integer>();
		squaresWithNumbers = new ArrayList<Integer>();
		advancedDataMap = new HashMap<Integer, AdvancedData>();
		squareDataMap = new HashMap<Integer, SquareData>();
		robot = new Robot();

		WindowManipulation.setMinesweeperSizeAndPosition();
		WindowManipulation.setMinesweeperToForeground();
		solve();
		
		System.out.println("Used advanced techniques: "+advancedTechniques+" times");
		System.out.println("Gussed: "+guessed+" times, probability of all being correct: "+0.5/guessed);
		Board.printBoard();
	}
	
	public static void solve(){
		Mouse.clickFirstSquare();
		Board.updateBoard();
		fillArrayLists();
		updateSquareData(squaresWithNumbers);
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
					doAdvancedTechniques();
					advancedTechniques++;
				}
				else if (roundsWithoutAction == 3){
					System.out.println("guessed");
					Mouse.clickRandomNonClicked();
					guessed++;
					roundsWithoutAction = 0;
					robot.delay(250);
				}
			}
			roundsWithoutAction++;
		}
	}
	
	public static void fillArrayLists(){
		int[][] board = Board.board;
		
		for (int row = 0; row < board.length; row++){
			for (int column = 0; column < board[0].length; column++){
				
				if (board[row][column] == 8){
					addToNonClicked(row, column);
				}
				else if (board[row][column] != 0 || board[row][column] != 9){
					addToSquaresWithNumbers(row, column);
				}
			}
		}
	}

	public static void addToSquaresWithNumbers(int row, int column){
		int square = MatrixConversion.getSquare(row, column);
		squaresWithNumbers.add(square);
	}

	public static void addToNonClicked(int row, int column){
		int square = MatrixConversion.getSquare(row, column);
		nonClickedSquares.add(square);
	}

	public static void updateSquareData(ArrayList<Integer> squares){
		for (int square : squares){
			SquareData.updateSquareData(square);
		}
	}

	private static boolean gameOver(){
		Color gameLost = robot.getPixelColor(560, 326);
		Color gameWon = robot.getPixelColor(580, 310);
		
		if (gameLost.getBlue() == 255 &&
			gameLost.getGreen() == 255 &&
			gameLost.getRed() == 255){
			
			System.out.println("Game Lost");
			return true;
		}
		else if (gameWon.getBlue() == 255 &&
			gameWon.getGreen() == 255 &&
			gameWon.getRed() == 255){
			
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
				Mouse.clickAllSurroundingNonClicked(squareData);
				removeFromSquaresWithNumbers(square);
				clickedSquares = true;
			}
			else if (number == flags + nonClicked){
				Mouse.flagSurroudingSquares(squareData);
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
			Board.updateNumberOnSquare(square);
			
			int row = MatrixConversion.getRow(square);
			int column = MatrixConversion.getColumn(square);
			
			if (Board.board[row][column] != 8){
				removeFromNonClicked(square);
	
				if (Board.board[row][column] != 0 || Board.board[row][column] != 9){
					addToSquaresWithNumbers(row, column);
					SquareData.updateSquareData(square);
				}
			}
		}
	}
	
	public static void removeFromNonClicked(int square){
		int index = nonClickedSquares.indexOf(square);
		nonClickedSquares.remove(index);
	}
	
	public static void doAdvancedTechniques(){
//		 http://www.minesweeper.info/wiki/Strategy
		
		for (int square : squaresWithNumbers){
			SquareData squareData = squareDataMap.get(square);
			int number = squareData.numberOnSquare - squareData.surroundingFlags;
			
			if (number == 1){
				if (squareData.surroundingNonClickedSquares.size() == 2){
					if (squareIsEdge(squareData)){
						oneAndOneTechnique(square);
					}
				}
			}
			
			else if (number == 2){
				if (squareData.surroundingNonClickedSquares.size() == 3){
					if (squaresAreNextToEachOher(squareData)){
						if (squareNextToOriginIsOne(squareData)){
							oneAndTwoTechnique(squareData);
						}
					}
				}
			}
		}
	}
	
	public static boolean squareNextToOriginIsOne(SquareData originSquareData){
		AdvancedData edgeData = advancedDataMap.get(originSquareData.square);
		int firstSquare = edgeData.firstNumSquareNextToOrigin;
		SquareData firstSquareSquareData = squareDataMap.get(firstSquare);
		
		if (firstSquareSquareData != null){
			int Number = firstSquareSquareData.numberOnSquare - firstSquareSquareData.surroundingFlags;
			
			if (Number == 1){
				return true;
			}
		}
		return false;
	}
	
	public static void oneAndTwoTechnique(SquareData originSquareData){
		AdvancedData advancedData = advancedDataMap.get(originSquareData.square);
		int firstSquare = advancedData.firstNumSquareNextToOrigin;
		SquareData firstSquareSquareData = squareDataMap.get(firstSquare);
		
		if (firstSquareSquareData.hasNextNonClicked()){
			Mouse.clickAllNonClickedExceptEdgeAndNonEdge(firstSquareSquareData, advancedData);
			flagOppositeSide(advancedData);
		}
		
	}

	public static void flagOppositeSide(AdvancedData advancedData){
		int lastNonClicked = advancedData.lastNonClicked;
		Mouse.flagSquare(lastNonClicked);
	}
	
	public static boolean squaresAreNextToEachOher(SquareData squareData){
		AdvancedData advancedData = new AdvancedData();
		advancedData.orderTheNonClickedSquares(squareData);
		
		if (advancedData.squaresThreeAreNextToEachOther()){
			
			advancedData.storeNonClicked(squareData);
			advancedDataMap.put(squareData.square, advancedData);
			return true;
			
		}
		return false;
	}
	
	public static boolean squareIsEdge(SquareData squareData){
		AdvancedData advancedData = new AdvancedData();
		advancedData.originSquare = squareData.square;
		advancedData.setEdgeAndNonEdge(squareData);

		int edge = advancedData.edge;
		int nonEdge = advancedData.nonEdge;
		
		if (advancedData.squaresAreNextToEachOther(edge, nonEdge)){
			if (advancedData.squareIsNotAFlag(advancedData.otherNumberedSquare)){
				
				advancedData.storeNonClicked(squareData);
				
				advancedDataMap.put(advancedData.originSquare, advancedData);
				return true;
			}
		}
			
		return false;
	}
	
	public static void oneAndOneTechnique(int originSquare){
		AdvancedData advancedData = advancedDataMap.get(originSquare);
		SquareData otherSquareData = squareDataMap.get(advancedData.otherNumberedSquare);
		
		if (otherSquareData != null){
			int number = otherSquareData.numberOnSquare - otherSquareData.surroundingFlags;
			if (number == 1){
				Mouse.clickAllNonClickedExceptEdgeAndNonEdge(otherSquareData, advancedData);
			}
		}
	}

	public static void printArrayList(ArrayList<Integer> liste){
		for (int element : liste){
			System.out.print(element + " ");
		}
		System.out.println();
	}
}