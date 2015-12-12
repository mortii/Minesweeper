package minesweeper;

import java.util.ArrayList;

public class Square{
	public int square;
	public int numberOnSquare;
	public int row;
	public int column;
	private ArrayList<Coordinates> surroundingSquares;
	public ArrayList<Integer> surroundingNonClickedSquares;
	public ArrayList<Integer> surroundingSquaresWithNumbers;
	public int surroundingFlags;
	
	public Square(int square){
		this.square = square;
		this.row = Board.getRow(square);
		this.column = Board.getColumn(square);
		this.numberOnSquare = Board.board[row][column];
		this.surroundingSquares = getSurroundingSquares(square);
		this.surroundingFlags = getSurroundingFlags();
		this.surroundingNonClickedSquares = getSurroundingNonClicked();
	}
	
	private static ArrayList<Coordinates> getSurroundingSquares(int square){
		ArrayList<Coordinates> surroundingSquaresList = new ArrayList<Coordinates>();
		
		int[][] board = Board.board;
		int[] offset = {-1, 0, 1};
		int row = Board.getRow(square);
		int column = Board.getColumn(square);
		
		for (int rowOffset : offset){
			for (int columnOffset : offset){
				
				int newRow = row + rowOffset;
				int newColumn = column + columnOffset;
				
				if (newRow != row || newColumn != column){
					if (newRow < board.length && newRow > -1){
						if (newColumn < board[0].length && newColumn > -1){
								Coordinates surroundingSquare = new Coordinates(newRow, newColumn);
								surroundingSquaresList.add(surroundingSquare);
						}
					}
				}
			}
		}
		return surroundingSquaresList;
	}


	public int getSurroundingFlags(){
		int surroundingFlags = 0;
		
		for (Coordinates surroundingSquare : this.surroundingSquares){
			if (Board.board[surroundingSquare.row][surroundingSquare.column] == 9){
				surroundingFlags++;
			}
		}
		return surroundingFlags;
	}
	
	public ArrayList<Integer> getSurroundingNonClicked(){
		ArrayList<Integer> surroundingNonClicked = new ArrayList<Integer>();
		
		for (Coordinates surroundingSquare : this.surroundingSquares){
			if (Board.board[surroundingSquare.row][surroundingSquare.column] == 8){
				surroundingNonClicked.add(surroundingSquare.square);
			}
		}
		
		return surroundingNonClicked;
	}
	
	public void updateSurroundings(){
		this.surroundingFlags = getSurroundingFlags();
		this.surroundingNonClickedSquares = getSurroundingNonClicked();
	}

	public static ArrayList<Integer> getSurroundingSquaresWithNumbers(int square){
		ArrayList<Coordinates> surroundingSquares = getSurroundingSquares(square);
		ArrayList<Integer> surroundingSquaresWithNumbers = new ArrayList<Integer>();
		
		for (Coordinates surrSquare : surroundingSquares){
			if (Main.squaresWithNumbers.contains(surrSquare.square)){
				surroundingSquaresWithNumbers.add(surrSquare.square);
			}
		}
		return surroundingSquaresWithNumbers;
	}

	public static void updateTheSurroundingSquares(int square){
		ArrayList<Coordinates> surroundingSquares = getSurroundingSquares(square);
		int row = Board.getRow(square);
		int column = Board.getColumn(square);
		
		for (Coordinates surrSquare : surroundingSquares){
			Square squareWithNumber = Main.squaresMap.get(surrSquare.square);
			
			if (squareWithNumber != null){
				
				if (Board.board[row][column] == 9){
					squareWithNumber.surroundingFlags++;
				}
				squareWithNumber.removeNonClicked(square);
				Main.squaresMap.put(surrSquare.square, squareWithNumber);
			}
		}
	}
	
//	public static removeFromSurroundingSquares()
	
	private void removeNonClicked(int squareToRemove){
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
		this.square = Board.getSquare(row, column);
	}
}