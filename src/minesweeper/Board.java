package minesweeper;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Board {
	private static int[] offset = {-1, 0, 1};
	private static int[][] board = new int[16][30];
	private static BufferedImage boardImage;
	private static Rectangle boardRectangle = new Rectangle(83, 186, 975, 520);

	public static void updateEntireBoard(){
		updateBoardImage();
		
		for (int square = 0; square < board.length * board[0].length; square++){
			int number = ComputerVision.getNumber(square);
			placeNumberOnBoard(square, number);
		}
	}
	
	public static void updateBoardImage(){
		boardImage = Bot.screenShot(boardRectangle);
	}

	public static BufferedImage getBoardImage(){
		return boardImage;
	}

	public static void placeNumberOnBoard(int square, int number){
		int row = getRow(square);
		int column = getColumn(square);
		board[row][column] = number;
	}
	
	private static int getRow(int square){
		int row = square / board[0].length;
		return row;
	}
	
	private static int getColumn(int square){
		int column = square % board[0].length;
		return column;
	}
	
	private static int getSquare(int row, int column){
		int square;
		
		if (row == 0){
			square = column;
		}
		else if (column == 0){
			square = row * board[0].length;
		}
		else{
			square = row * board[0].length + column;
		}
		return square;
	}
	
	public static int getNumberOnSquare(int square){
		int row = getRow(square);
		int column = getColumn(square);
		int number = board[row][column];
		return number;
	}

	public static ArrayList<Integer> getSurroundingSquares(int centerSquare){
		ArrayList<Integer> surroundingSquaresList = new ArrayList<Integer>();
		int centerRow = getRow(centerSquare);
		int centerColumn = getColumn(centerSquare);
		
		for (int rowOffset : offset){
			for (int columnOffset : offset){
				
				int surroundingRow = centerRow + rowOffset;
				int surroundingColumn = centerColumn + columnOffset;
				
				if (validRow(surroundingRow) && validColumn(surroundingColumn)){
					int surroundingSquare = Board.getSquare(surroundingRow, surroundingColumn);
					surroundingSquaresList.add(surroundingSquare);
				}
			}
		}
		removeCenterSquareFromList(centerSquare, surroundingSquaresList);
		return surroundingSquaresList;
	}
	
	private static boolean validRow(int row){
		if (row < board.length && row > -1){
			return true;
		}
		return false;
	}

	private static boolean validColumn(int column){
		if (column < board[0].length && column > -1){
			return true;
		}
		return false;
	}
	
	private static void removeCenterSquareFromList(int centerSquare, ArrayList<Integer> surroundingSquaresList){
		int index = surroundingSquaresList.indexOf(centerSquare);
		surroundingSquaresList.remove(index);
	}

	public static void printBoard(){
		for (int row = 0; row < board.length; row++){
			for (int column = 0; column < board[0].length; column++){
				System.out.print(board[row][column] + " ");
			}
			System.out.println();
		}
	}
}