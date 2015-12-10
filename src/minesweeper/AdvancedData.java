package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;

public class AdvancedData {
	public int square;
	public int otherNumberedSquare;
	public int edge;
	public int nextToEdge;
	
	public int firstNumSquareNextToOrigin;
	public int lastNumSquareNextToOrigin;
	
	public int lastNonClicked;
	public int firstNonClicked;
	
	public AdvancedData(SquareData squareData){
		this.square = squareData.square;
	}
	
	public boolean nonClickedIsEdge(SquareData squareData){
		setEdgeAndNonEdge(squareData);
	
		if (squaresAreNextToEachOther()){
			return true;
		}
		return false;
	}
	
	public boolean squaresAreNextToEachOther(){
		if (edge + 1 == nextToEdge){
			this.otherNumberedSquare = this.square + 1;
			return true;
		}
		
		else if (edge - 1 == nextToEdge){
			this.otherNumberedSquare = this.square - 1;
			return true;
		}
		
		else if (edge + 30 == nextToEdge){
			this.otherNumberedSquare = this.square + 30;
			return true;
		}
		
		else if (edge - 30 == nextToEdge){
			this.otherNumberedSquare = this.square - 30;
			return true;
		}
		
		return false;
	}

	public void setEdgeAndNonEdge(SquareData squareData){
		/*
		 * the edge has to be in a + direction to the origin square.
		 */
		square = squareData.square;
		int possibleEdge = squareData.surroundingNonClickedSquares.get(0);
		int possibleNonEdge = squareData.surroundingNonClickedSquares.get(1);
		
		if (possibleEdge + 1 == square || possibleEdge - 1 == square ||
			possibleEdge + 30 == square || possibleEdge - 30 == square){
			
			this.edge = possibleEdge;
			this.nextToEdge = possibleNonEdge;
		}
		else{
			this.edge = possibleNonEdge;
			this.nextToEdge = possibleEdge;
		}
	}
	
	public boolean squareIsNotAFlag(int square){
		int row = MatrixConversion.getRow(square);
		int column = MatrixConversion.getColumn(square);
		
		if (Board.board[row][column] == 9){
			return false;
		}
		
		return true;
	}
	
	public void storeNonClicked(SquareData squareData){
		for (int nonClickedSquare : squareData.surroundingNonClickedSquares){
			this.nonClickedSquaresNotToClick.add(nonClickedSquare);
		}
	}
	
	public int[] orderTheNonClickedSquares(SquareData squareData){
		int tempFirst = squareData.surroundingNonClickedSquares.get(0);
		int tempMiddle = squareData.surroundingNonClickedSquares.get(1);
		int tempLast = squareData.surroundingNonClickedSquares.get(2);
		
		int[] sortedArray = {tempFirst, tempMiddle, tempLast};
		Arrays.sort(sortedArray);
		
		
		return sortedArray;
	}
	
	public boolean squaresThreeAreNextToEachOther(SquareData squareData){
		int[] sortedSquares = orderTheNonClickedSquares(squareData);
		int firstNonClicked = sortedSquares[0];
		int middleNonClicked = sortedSquares[1];
		int lastNonClicked = sortedSquares[2];
		
		this.firstNonClicked = firstNonClicked;
		this.lastNonClicked = lastNonClicked;
		
		if (firstNonClicked + 1 == middleNonClicked && middleNonClicked + 1 == lastNonClicked){
			
			if (square > middleNonClicked){
				firstNumSquareNextToOrigin = firstNonClicked + 30;
				lastNumSquareNextToOrigin = lastNonClicked + 30;
			}
			else{
				firstNumSquareNextToOrigin = firstNonClicked - 30;
				lastNumSquareNextToOrigin = lastNonClicked - 30;
			}
			return true;
		}
		else if (firstNonClicked + 30 == middleNonClicked && middleNonClicked + 30 == lastNonClicked){
			
			if (square > middleNonClicked){
				firstNumSquareNextToOrigin = firstNonClicked + 1;
				lastNumSquareNextToOrigin = lastNonClicked + 1;
			}
			else{
				firstNumSquareNextToOrigin = firstNonClicked - 1;
				lastNumSquareNextToOrigin = lastNonClicked - 1;
			}
			return true;
		}
		
		return false;
	}
}