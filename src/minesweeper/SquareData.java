package minesweeper;

import java.util.ArrayList;

public class SquareData{
	public int square;
	public int numberOnSquare;
	public int row;
	public int column;
	public int index;
	public int surroundingFlags;
	public ArrayList<Integer> surroundingNonClickedSquares;
	

	
	
	public SquareData(int square){
		this.square = square;
		this.row = ElementConversion.getRow(square);
		this.column = ElementConversion.getColumn(square);
		this.numberOnSquare = 0;
		this.index = 0;
		this.surroundingFlags = 0;
		this.surroundingNonClickedSquares = new ArrayList<Integer>();
	}
	
	
	public static void updateSquareData(int square){
		int[][] board = Board.board;
		int[] offset = Main.offset;
		
		SquareData squareData = new SquareData(square);
		int row = squareData.row;
		int column = squareData.column;
		squareData.numberOnSquare = board[row][column];
		
		for (int rowOffset : offset){
			for (int columnOffset : offset){
				
				int newRow = row + rowOffset;
				int newColumn = column + columnOffset;
				
				if (newRow < board.length && newRow > -1){
					if (newColumn < board[0].length && newColumn > -1){
	
						if (board[newRow][newColumn] == 9){
							squareData.surroundingFlags++;
						}
						else if (board[newRow][newColumn] == 8){
							int element = ElementConversion.getElement(newRow, newColumn);
							squareData.surroundingNonClickedSquares.add(element);
						}
					}
				}
			}
		}
		squareData.printData();
		Main.squareDataMap.put(square, squareData);
	}
	
	
	public static void removeSurroudingSquareDataClicked(int square){
		int[][] board = Board.board;
		int row = ElementConversion.getRow(square);
		int column = ElementConversion.getColumn(square);
		
		for (int rowOffset : Main.offset){
			for (int columnOffset : Main.offset){
				
				int newRow = row + rowOffset;
				int newColumn = column + columnOffset;
				
				if (newRow < board.length && newRow > -1){
					if (newColumn < board[0].length && newColumn > -1){
						
						int element = ElementConversion.getElement(newRow, newColumn);
						SquareData squareData = Main.squareDataMap.get(element);
						
						if (squareData != null){
//							System.out.println("current square:"+element+" square to remove:"+square);
							squareData.removeNonClicked(square);
							Main.squareDataMap.put(element, squareData);
						}
						
					}
				}
			}
		}
	}
	
	
	public static  void removeSquareDataFlag(int square){
		int[][] board = Board.board;
		int row = ElementConversion.getRow(square);
		int column = ElementConversion.getColumn(square);
		
		for (int rowOffset : Main.offset){
			for (int columnOffset : Main.offset){
				
				int newRow = row + rowOffset;
				int newColumn = column + columnOffset;
				
				if (newRow < board.length && newRow > -1){
					if (newColumn < board[0].length && newColumn > -1){
						
						int element = ElementConversion.getElement(newRow, newColumn);
						SquareData squareData = Main.squareDataMap.get(element);
						
						if (squareData != null){
//							System.out.println("current square:"+element+" square to remove:"+square);
							squareData.removeNonClicked(square);
							squareData.surroundingFlags++;
							Main.squareDataMap.put(element, squareData);
						}
					}
				}
			}
		}
	}
	
	
	public void removeNonClicked(int squareToRemove){
		int index = surroundingNonClickedSquares.indexOf(squareToRemove);
//		printNonClicked();
//		System.out.println("index of square:"+index);
		try{
			surroundingNonClickedSquares.remove(index);
		}
		catch(Exception E){
//			System.out.println("failed to remove nonClicked from squareData");
		}
//		System.out.println("removed!");
//		System.out.println();
	}
	
	public boolean inNonClickedList(int row, int column){
		int element = ElementConversion.getElement(row, column);
		for (int square : surroundingNonClickedSquares){
			if (element == square){
				return true;
			}
		}
		return false;
	}
	

	public void printNonClicked(){
		for (int square : surroundingNonClickedSquares){
			System.out.print(square + " ");
		}
		System.out.println();
	}
	

	public boolean hasNextNonClicked() {
		if (surroundingNonClickedSquares.iterator().hasNext()){
			return true;
		}
		return false;
	}

	public Integer nextNonClicked() {
		int next = surroundingNonClickedSquares.get(index);
		return next;
	}
	
	public void printData(){
		System.out.print("square:"+this.square);
		System.out.print(" number:"+this.numberOnSquare);
		System.out.print(" flags:"+this.surroundingFlags);
		System.out.print(" nonClicked:"+this.surroundingNonClickedSquares.size());
		System.out.println();
	}
	
	
}
