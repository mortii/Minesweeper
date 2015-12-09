package minesweeper;

import java.util.ArrayList;

public class SquareData{
	public int square;
	public int numberOnSquare;
	public int row;
	public int column;
	public int surroundingFlags;
	public ArrayList<Integer> surroundingNonClickedSquares;
	
	public SquareData(int square){
		this.square = square;
		this.row = MatrixConversion.getRow(square);
		this.column = MatrixConversion.getColumn(square);
		this.numberOnSquare = 0;
		this.surroundingFlags = 0;
		this.surroundingNonClickedSquares = new ArrayList<Integer>();
	}
	
	public static ArrayList<Coordinates> surroundingSquares(int square){
		ArrayList<Coordinates> surroundingSquaresList = new ArrayList<Coordinates>();
		
		int[][] board = Board.board;
		int[] offset = {-1, 0, 1};
		int row = MatrixConversion.getRow(square);
		int column = MatrixConversion.getColumn(square);
		
		for (int rowOffset : offset){
			for (int columnOffset : offset){
				
				int newRow = row + rowOffset;
				int newColumn = column + columnOffset;
				
				if (newRow < board.length && newRow > -1){
					if (newColumn < board[0].length && newColumn > -1){
						
						Coordinates surroundingSquare = new Coordinates(newRow, newColumn);
						surroundingSquaresList.add(surroundingSquare);
					}
				}
			}
		}
		return surroundingSquaresList;
	}
	
	public static void updateSquareData(int square){
		ArrayList<Coordinates> surroundingSquares = surroundingSquares(square);
		int[][] board = Board.board;
		
		SquareData squareData = new SquareData(square);
		int row = squareData.row;
		int column = squareData.column;
		squareData.numberOnSquare = board[row][column];
		
		for (Coordinates surroundingSquare : surroundingSquares){
			
			if (board[surroundingSquare.row][surroundingSquare.column] == 9){
				squareData.surroundingFlags++;
			}
			else if (board[surroundingSquare.row][surroundingSquare.column] == 8){
				squareData.surroundingNonClickedSquares.add(surroundingSquare.square);
			}
		}
//		squareData.printData();
		Main.squareDataMap.put(square, squareData);
	}
	
	public static void updateSurroundingSquares(int square){
		ArrayList<Coordinates> surroundingSquares = surroundingSquares(square);
		
		int originalRow = MatrixConversion.getRow(square);
		int originalColumn = MatrixConversion.getColumn(square);

		for (Coordinates surroundingSquare : surroundingSquares){
			SquareData squareData = Main.squareDataMap.get(surroundingSquare.square);
			
			if (squareData != null){
				squareData.removeNonClicked(square);
				
				if (Board.board[originalRow][originalColumn] == 9){
					squareData.surroundingFlags++;
				}
				
				Main.squareDataMap.put(surroundingSquare.square, squareData);
			}
		}
	}
	
	public void removeNonClicked(int squareToRemove){
		int index = surroundingNonClickedSquares.indexOf(squareToRemove);
		try{
			surroundingNonClickedSquares.remove(index);
		}
		catch(Exception E){
		}
	}

	public boolean hasNextNonClicked() {
		if (!surroundingNonClickedSquares.isEmpty()){
			return true;
		}
		return false;
	}
	
	public Integer nextNonClicked() {
		return surroundingNonClickedSquares.get(0);
	}
	
	public void printData(){
		System.out.print("square:"+this.square);
		System.out.print(" number:"+this.numberOnSquare);
		System.out.print(" flags:"+this.surroundingFlags);
		System.out.print(" nonClicked:"+this.surroundingNonClickedSquares.size());
		System.out.println();
	}
}

class Coordinates{
	public int row;
	public int column;
	public int square;
	
	public Coordinates(int row, int column){
		this.row = row;
		this.column = column;
		this.square = MatrixConversion.getSquare(row, column);
	}
}