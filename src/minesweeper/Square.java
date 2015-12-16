package minesweeper;

import java.util.ArrayList;

public class Square{
	public int square;
	public int numberOnSquare;
	public ArrayList<Integer> surroundingSquares;
	public ArrayList<Integer> surroundingNonClickedSquares;
	public int surroundingFlags;
	
	public Square(int square){
		this.square = square;
		this.numberOnSquare = Board.getNumberOnSquare(square);
		this.surroundingSquares = Board.getSurroundingSquares(square);
		this.surroundingNonClickedSquares = getSurroundingNonClicked();
		this.surroundingFlags = getSurroundingFlags();
	}
	
	public int getSurroundingFlags(){
		int surroundingFlags = 0;
		
		for (Integer surroundingSquare : this.surroundingSquares){
			if (Board.getNumberOnSquare(surroundingSquare) == 9){
				surroundingFlags++;
			}
		}
		return surroundingFlags;
	}
	
	public ArrayList<Integer> getSurroundingNonClicked(){
		ArrayList<Integer> surroundingNonClicked = new ArrayList<Integer>();
		
		for (int surroundingSquare : this.surroundingSquares){
			if (Board.getNumberOnSquare(surroundingSquare) == 8){
				surroundingNonClicked.add(surroundingSquare);
			}
		}
		return surroundingNonClicked;
	}
	
	public void updateSurroundings(){
		this.surroundingFlags = getSurroundingFlags();
		this.surroundingNonClickedSquares = getSurroundingNonClicked();
	}

	private void removeNonClicked(int squareToRemove){
		try{
			int index = surroundingNonClickedSquares.indexOf(squareToRemove);
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

	public static void updateTheSurroundingSquares(int square){
		ArrayList<Integer> surroundingSquares = Board.getSurroundingSquares(square);
		
		for (int surrSquare : surroundingSquares){
			Square squareWithNumber = Maps.squareMap.get(surrSquare);
			
			if (squareWithNumber != null){
				if (Board.getNumberOnSquare(square) == 9){
					squareWithNumber.surroundingFlags++;
				}
				squareWithNumber.removeNonClicked(square);
				Maps.squareMap.put(surrSquare, squareWithNumber);
			}
		}
	}
}