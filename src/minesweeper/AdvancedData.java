package minesweeper;

import java.util.Arrays;

public class AdvancedData {
	public SquareData squareData;
	
	public AdvancedData(SquareData squareData){
		this.squareData = squareData;
	}
		
	public class OneAndOne{
		public SquareData squareData;
		public int square;
		public int adjecentSquareWithNumber;
		public int nonClickedEdge;
		public int nonClickedAdjecentToEdge;
		
		public OneAndOne(){
			this.squareData = AdvancedData.this.squareData;
			this.square = squareData.square;
		}
		
		public boolean nonClickedAreNextToEachOther(){
			setEdgeAndAdjecentToEdge();
			
			if (nonClickedEdge + 1 == nonClickedAdjecentToEdge){
				this.adjecentSquareWithNumber = this.square + 1;
				return true;
			}
			
			else if (nonClickedEdge - 1 == nonClickedAdjecentToEdge){
				this.adjecentSquareWithNumber = this.square - 1;
				return true;
			}
			
			else if (nonClickedEdge + 30 == nonClickedAdjecentToEdge){
				this.adjecentSquareWithNumber = this.square + 30;
				return true;
			}
			
			else if (nonClickedEdge - 30 == nonClickedAdjecentToEdge){
				this.adjecentSquareWithNumber = this.square - 30;
				return true;
			}
			
			
			return false;
		}

		private void setEdgeAndAdjecentToEdge(){
			/*Edge means that it only has one nonClicked square next to it
			 * in the same column or row, depending on the orientation to
			 * the square we are testing.
			 * 
			 * The edge has to be in a 90 degree direction (i.e. +) from the
			 *square we are testing.
			 *
			 *examples: http://www.minesweeper.info/wiki/Strategy
			 */
			
			int tempEdge = this.squareData.surroundingNonClickedSquares.get(0);
			int tempAdjecentToEdge = this.squareData.surroundingNonClickedSquares.get(1);

			if (tempEdge + 1 == square || tempEdge - 1 == square ||
					tempEdge + 30 == square || tempEdge - 30 == square){
				
				this.nonClickedEdge = tempEdge;
				this.nonClickedAdjecentToEdge = tempAdjecentToEdge;
			}
			else{
				this.nonClickedEdge = tempAdjecentToEdge;
				this.nonClickedAdjecentToEdge = tempEdge;
			}
		}
	}

	public class OneAndTwo{
		public SquareData squareData;
		public int square;
		
		public int firstAdjecentNumbered;
		public int lastAdjecentNumbered;
		
		public int firstNonClicked;
		public int lastNonClicked;
		
		public OneAndTwo(){
			this.squareData = AdvancedData.this.squareData;
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
}