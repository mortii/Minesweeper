package minesweeper;

import java.util.ArrayList;

public class Lists {

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
		Main.nonClickedSquares.add(square);
	}

	public static void addToSquaresWithNumbers(int row, int column){
		int square = Board.getSquare(row, column);
		Main.squaresWithNumbers.add(square);
	}
	
	public static void updateSquaresWithNumbers(){
		for (int square : Main.squaresWithNumbers){
			Square squareWithNumber = Main.squareMap.get(square);
			squareWithNumber.updateSurroundings();
			Main.squareMap.put(square, squareWithNumber);
		}
	}
	
	public static void removeFromSquaresWithNumbers(int square){
		int index = Main.squaresWithNumbers.indexOf(square);
		Main.squaresWithNumbers.remove(index);
	}
	
	public static void removeFromNonClicked(int square){
		int index = Main.nonClickedSquares.indexOf(square);
		Main.nonClickedSquares.remove(index);
	}
	
	public static void printArrayList(ArrayList<Integer> liste){
		for (int element : liste){
			System.out.print(element + " ");
		}
		System.out.println();
	}
}
