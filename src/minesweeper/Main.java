package minesweeper;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {
	public static int[] offset = {-1, 0, 1};
	public static int[] notValidNumbersOnSquares = {0, 8, 9};
	public static ArrayList<Integer> squaresThatHaveValidNumbersList;
	public static ArrayList<Integer> nonClickedSquaresList;
	public static HashMap<Integer, AdvancedData> advancedDataMap;
	public static HashMap<Integer, SquareData> squareDataMap;
	public static Robot robot;
	public static int guessed = 0;
	public static int advancedTechniques = 0;

	
	public static void main(String[] args) throws AWTException{
		squaresThatHaveValidNumbersList = new ArrayList<Integer>();
		nonClickedSquaresList = new ArrayList<Integer>();
		advancedDataMap = new HashMap<Integer, AdvancedData>();
		squareDataMap = new HashMap<Integer, SquareData>();
		robot = new Robot();
		
		WindowManipulation.setMinsweeperSizeAndPosition();
		WindowManipulation.setMinesweeperToForeground();
		solve();
		System.out.println("Used advanced techniques: "+advancedTechniques+" times");
		System.out.println("Gussed: "+guessed+" times, probability of all being correct: "+0.5/guessed);
		Board.printBoard();
	}
	
	
	public static void solve(){
		MouseOperations.clickFirstSquare();
		Board.getNumbersOnAllSquares();
		fillNonClickedListAndValidNumberList();
		updateSquareDataForSquaresInValidNumberList();
		
		int roundsWithoutAction = 0;
		
		while (!gameOver()){
			
			if (doSimpleTechniques()){
				roundsWithoutAction = 0;
			}

			if (roundsWithoutAction == 1){
				updateAllNonClickedSquares();
				updateSquareDataForSquaresInValidNumberList();
			}
			else if (roundsWithoutAction == 2){
				advancedTechniques();
				advancedTechniques++;
				
				int arrLength = squaresThatHaveValidNumbersList.size();
				
				updateAllNonClickedSquares();
				updateSquareDataForSquaresInValidNumberList();
				
				if (arrLength < squaresThatHaveValidNumbersList.size()){
					roundsWithoutAction = 0;
				}
				
				
			}
			else if (roundsWithoutAction == 3){
				System.out.println("guessed");
				MouseOperations.clickRandomNonClickedSquare();
				guessed++;
				roundsWithoutAction = 0;
			}
			
			roundsWithoutAction++;
		}
	}
	

	public static void fillNonClickedListAndValidNumberList(){
		int[][] board = Board.board;
		
		for (int row = 0; row < board.length; row++){
			for (int column = 0; column < board[0].length; column++){
				
				if (squareHasValidValue(row, column)){
					addSquareToValidValueList(row, column);
				}
				else if (board[row][column] == 8){
					addSquareToNonClickedList(row, column);
				}
			}
		}
	}


	public static boolean squareHasValidValue(int row, int column){
		for (int notValid : notValidNumbersOnSquares){
			if (Board.board[row][column] == notValid){
				return false;
			}
		}
		return true;
	}


	public static void addSquareToValidValueList(int row, int column){
		int square = ElementConversion.getElement(row, column);
		squaresThatHaveValidNumbersList.add(square);
	}


	public static void addSquareToNonClickedList(int row, int column){
		int element = ElementConversion.getElement(row, column);
		nonClickedSquaresList.add(element);
	}


	public static void updateSquareDataForSquaresInValidNumberList(){
		for (int square : squaresThatHaveValidNumbersList){
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
		ArrayList<Integer> squaresThatHaveValidNumbersListCopy = new ArrayList<Integer>(squaresThatHaveValidNumbersList);
		boolean worked = false;
		
		for (int square : squaresThatHaveValidNumbersListCopy){
			SquareData squareData = squareDataMap.get(square);
//			squareData.printData();
			
			int number = squareData.numberOnSquare;
			int flags = squareData.surroundingFlags;
			int nonClicked = squareData.surroundingNonClickedSquares.size();
			
			if (number == flags){
				MouseOperations.clickAllSurroudingNonClicked(squareData);
				removeFromSquaresThatHaveValidNumbers(square);
				worked = true;
			}
			else if (number == flags + nonClicked){
				MouseOperations.flagSurroudingSquares(squareData);
				removeFromSquaresThatHaveValidNumbers(square);
				worked = true;
			}
		}
		return worked;
	}

	
	public static void removeFromSquaresThatHaveValidNumbers(int square){
		int index = squaresThatHaveValidNumbersList.indexOf(square);
		squaresThatHaveValidNumbersList.remove(index);
	}


	public static void updateAllNonClickedSquares(){
		ArrayList<Integer> nonClickedCopy = new ArrayList<Integer>(nonClickedSquaresList);
		Board.updateBoardImage();
		
		for (int square : nonClickedCopy){
			Board.updateSquareNumber(square);
			
			int row = ElementConversion.getRow(square);
			int column = ElementConversion.getColumn(square);
			
			if (Board.board[row][column] != 8){
				removeFromNonClickedList(square);
	
				if (squareHasValidValue(row, column)){
					addSquareToValidValueList(row, column);
					SquareData.updateSquareData(square);
				}
			}
		}
	}
	
	
	public static void removeFromNonClickedList(int square){
		int index = nonClickedSquaresList.indexOf(square);
		nonClickedSquaresList.remove(index);
	}
	
	
	public static void advancedTechniques(){
//		 http://www.minesweeper.info/wiki/Strategy
		
		for (int square : squaresThatHaveValidNumbersList){
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
			MouseOperations.clickAllNonClickedExceptEdgeAndNonEdge(firstSquareSquareData, advancedData);
		}
		
		flagOppositeSide(advancedData);
	}


	public static void flagOppositeSide(AdvancedData advancedData){
		int lastNonClicked = advancedData.lastNonClicked;
		MouseOperations.flagSquare(lastNonClicked);
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
				MouseOperations.clickAllNonClickedExceptEdgeAndNonEdge(otherSquareData, advancedData);
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