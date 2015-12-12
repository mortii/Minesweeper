package minesweeper;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Board {
	public static int[][] board = new int[16][30];
	public static BufferedImage boardImage;
	public static Rectangle boardRectangle = new Rectangle(83, 186, 975, 520);

	public static void updateEntireBoard(){
		updateBoardImage();
		
		for (int square = 0; square < 30*16; square++){
			int number = ComputerVision.getNumber(square);
			placeNumberOnBoard(square, number);
		}
	}
	
	public static void updateBoardImage(){
		boardImage = Main.robot.createScreenCapture(boardRectangle);
	}
	
	public static void placeNumberOnBoard(int square, int number){
		int row = getRow(square);
		int column = getColumn(square);
		board[row][column] = number;
	}

	public static int getRow(int square){
		int row = square / 30;
		return row;
	}
	
	public static int getColumn(int square){
		int column = square % 30;
		return column;
	}
	
	public static int getSquare(int row, int column){
		int square;
		
		if (row == 0){
			square = column;
		}
		else if (column == 0){
			square = row*30;
		}
		else{
			square = row*30 + column;
		}
		return square;
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