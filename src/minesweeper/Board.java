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
			getNumberOnSquare(square);
		}
	}
	
	public static void updateBoardImage(){
		boardImage = Main.robot.createScreenCapture(boardRectangle);
	}
	
	public static void getNumberOnSquare(int square){
			int row = MatrixConversion.getRow(square);
			int column = MatrixConversion.getColumn(square);
			int number = ComputerVision.getNumber(square);
			
	//		System.out.println("square:"+ square +" number:"+ number);
			board[row][column] = number;
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
