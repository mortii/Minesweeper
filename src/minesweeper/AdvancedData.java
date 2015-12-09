package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;

public class AdvancedData {
	public int originSquare;
	public int otherNumberedSquare;
	public int edge;
	public int nonEdge;
	
	public int firstNonClicked;
	public int middleNonClicked;
	public int lastNonClicked;
	
	public int firstNumSquareNextToOrigin;
	public int lastNumSquareNextToOrigin;
	
	public ArrayList<Integer> nonClickedSquaresNotToClick =
			new ArrayList<Integer>();
	
	public void setEdgeAndNonEdge(SquareData squareData){
		originSquare = squareData.square;
		int possibleEdge = squareData.surroundingNonClickedSquares.get(0);
		int possibleNonEdge = squareData.surroundingNonClickedSquares.get(1);
		
		if (possibleEdge + 1 == originSquare || possibleEdge - 1 == originSquare ||
			possibleEdge + 30 == originSquare || possibleEdge - 30 == originSquare){
			
			this.edge = possibleEdge;
			this.nonEdge = possibleNonEdge;
		}
		else{
			this.edge = possibleNonEdge;
			this.nonEdge = possibleEdge;
		}
	}
	
	public boolean squaresAreNextToEachOther(int edge, int nonEdge){
		if (edge + 1 == nonEdge){
			this.otherNumberedSquare = this.originSquare + 1;
			return true;
		}
		
		else if (edge - 1 == nonEdge){
			this.otherNumberedSquare = this.originSquare - 1;
			return true;
		}
		
		else if (edge + 30 == nonEdge){
			this.otherNumberedSquare = this.originSquare + 30;
			return true;
		}
		
		else if (edge - 30 == nonEdge){
			this.otherNumberedSquare = this.originSquare - 30;
			return true;
		}
		
		return false;
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
	
	public void orderTheNonClickedSquares(SquareData squareData){
		int tempFirst = squareData.surroundingNonClickedSquares.get(0);
		int tempMiddle = squareData.surroundingNonClickedSquares.get(1);
		int tempLast = squareData.surroundingNonClickedSquares.get(2);
		
		int[] sortArray = {tempFirst, tempMiddle, tempLast};
		Arrays.sort(sortArray);
		
		firstNonClicked = sortArray[0];
		middleNonClicked = sortArray[1];
		lastNonClicked = sortArray[2];
		originSquare = squareData.square;
	}
	
	public boolean squaresThreeAreNextToEachOther(){
		if (firstNonClicked + 1 == middleNonClicked && middleNonClicked + 1 == lastNonClicked){
			
			if (originSquare > middleNonClicked){
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
			
			if (originSquare > middleNonClicked){
				firstNumSquareNextToOrigin = firstNonClicked + 1;
				lastNumSquareNextToOrigin = lastNonClicked + 1;
			}
			else{
				firstNumSquareNextToOrigin = firstNonClicked - 1;
				lastNumSquareNextToOrigin = lastNonClicked - 1;
			}
			return true;
		}
		
		return true;
	}
}