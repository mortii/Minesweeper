package minesweeper;

import java.util.Arrays;

public class Advanced {
	
	
	public static boolean doAdvancedSolving(){
//		 http://www.minesweeper.info/wiki/Strategy
		
		boolean clickedSquares = false;
		
		for (int square : Lists.squaresWithNumbers){
			Square squareWithNumber = Maps.squareMap.get(square);
			
			int number = squareWithNumber.numberOnSquare
					- squareWithNumber.surroundingFlags;
			
			if (number == 1){
				if (squareWithNumber.surroundingNonClickedSquares.size() == 2){
					if (oneAndOneTechnique(squareWithNumber)){
						clickedSquares = true;
					}
				}
			}
			else if (number == 2){
				if (squareWithNumber.surroundingNonClickedSquares.size() == 3){
					if (oneAndTwoTechnique(squareWithNumber)){
						clickedSquares = true;
					}
				}
			}
		}
		return clickedSquares;
	}
	
	public static boolean oneAndOneTechnique(Square squareWithNumber){
		OneAndOne oneAndOneData = new Advanced().new OneAndOne(squareWithNumber);
		
		if (oneAndOneData.nonClickedAreNextToEachOther()){
			if (squareIsOne(oneAndOneData.adjecentSquareWithNumber)){
				if (Mouse.clickAllExceptEdgeAndNextToEdge(oneAndOneData)){
					System.out.println("advancedTechnique 1-1");
					Main.advancedTechniques++;
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean squareHasNumber(int square){
		int[] notValidNumbers = {0, 8, 9};
		int row = Board.getRow(square);
		int column = Board.getColumn(square);
		
		for (int notValid : notValidNumbers){
			if (Board.board[row][column] == notValid){
				return false;
			}
		}
		return true;
	}

	public static boolean squareIsOne(int adjecentSquare){
		if (squareHasNumber(adjecentSquare)){
			Square squareData = Maps.squareMap.get(adjecentSquare);
			
			int number = squareData.numberOnSquare - squareData.surroundingFlags;
			
			if (number == 1){
				return true;
			}
		}
		return false;
	}

	public static boolean oneAndTwoTechnique(Square squareWithNumber){
		OneAndTwo oneAndTwoData = new Advanced().new OneAndTwo(squareWithNumber);
		
		if (oneAndTwoData.nonClickedAreNextToEachOther()){
			if (squareIsOne(oneAndTwoData.firstAdjecentNumbered)){
				flagAndUpdate(oneAndTwoData.lastNonClicked);
				return true;
			}
			else if (squareIsOne(oneAndTwoData.lastAdjecentNumbered)){
				flagAndUpdate(oneAndTwoData.firstNonClicked);
				return true;
			}
		}
		return false;
	}
	
	public static void flagAndUpdate(int square){
		System.out.println("advancedTechnique 1-2");
		Mouse.flagSquare(square);
		Square.updateTheSurroundingSquares(square);
		Main.advancedTechniques++;
	}
	
	public class OneAndOne {
		public Square squareData;
		
		public int square;
		public int adjecentSquareWithNumber;
		
		public int nonClickedEdge;
		public int nonClickedAdjecentToEdge;
		
		public OneAndOne(Square squareData){
			this.squareData = squareData;
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

	private class OneAndTwo {
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
}
