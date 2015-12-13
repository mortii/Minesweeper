package minesweeper;

import java.util.Arrays;

public class OneAndTwo {
	public Square squareData;
	public int square;
	
	public int firstAdjecentNumbered;
	public int lastAdjecentNumbered;
	
	public int firstNonClicked;
	public int lastNonClicked;
	
	public OneAndTwo(Square squareData){
		this.squareData = squareData;
		this.square = squareData.square;
	}
	
	private int[] orderTheNonClickedSquares(){
		int tempFirst = this.squareData.surroundingNonClickedSquares.get(0);
		int tempMiddle = this.squareData.surroundingNonClickedSquares.get(1);
		int tempLast = this.squareData.surroundingNonClickedSquares.get(2);
		
		int[] sortArray = {tempFirst, tempMiddle, tempLast};
		Arrays.sort(sortArray);
		
		return sortArray;
	}
	
	public boolean nonClickedAreNextToEachOther(){
		int[] sortedSquares = orderTheNonClickedSquares();
		
		int firstNonClicked = sortedSquares[0];
		int middleNonClicked = sortedSquares[1];
		int lastNonClicked = sortedSquares[2];
		
		this.firstNonClicked = firstNonClicked;
		this.lastNonClicked = lastNonClicked;
		
		if (firstNonClicked + 1 == middleNonClicked && middleNonClicked + 1 == lastNonClicked){
			
			if (square > middleNonClicked){
				firstAdjecentNumbered = firstNonClicked + 30;
				lastAdjecentNumbered = lastNonClicked + 30;
			}
			else{
				firstAdjecentNumbered = firstNonClicked - 30;
				lastAdjecentNumbered = lastNonClicked - 30;
			}
			return true;
		}
		else if (firstNonClicked + 30 == middleNonClicked && middleNonClicked + 30 == lastNonClicked){
			
			if (square > middleNonClicked){
				firstAdjecentNumbered = firstNonClicked + 1;
				lastAdjecentNumbered = lastNonClicked + 1;
			}
			else{
				firstAdjecentNumbered = firstNonClicked - 1;
				lastAdjecentNumbered = lastNonClicked - 1;
			}
			return true;
		}
		
		return false;
	}
}
