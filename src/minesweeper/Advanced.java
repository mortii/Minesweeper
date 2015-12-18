package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;

public class Advanced {
	private static Square squareWithNumber;
	private static int number;
	private static int flags;
	private static int nonClicked;
	private static boolean hasClickedSquares;
	
	public static boolean doAdvancedSolving(){
		hasClickedSquares = false;
		
		for (int square : Lists.squaresWithNumbers){
			setClassVariables(square);
			solve();
		}
		return hasClickedSquares;
	}
	
	private static void setClassVariables(int square){
		squareWithNumber = Maps.squaresWithNumbersMap.get(square);
		number = squareWithNumber.numberOnSquare;
		flags = squareWithNumber.surroundingFlags;
		nonClicked = squareWithNumber.surroundingNonClickedSquares.size();
	}

	//http://www.minesweeper.info/wiki/Strategy
	private static void solve(){
		if (number - flags == 1){
			if (nonClicked == 2){
				oneAndOneTechnique();
			}
		}
		else if (number - flags == 2){
			if (nonClicked == 3){
				oneAndTwoTechnique();
			}
		}
	}
	
	private static void oneAndOneTechnique(){
		OneAndOne oneAndOne = new OneAndOne(squareWithNumber);
		
		if (oneAndOne.nonClickedAreNextToEachOther()){
			if (squareHasNumberOne(oneAndOne.adjecentSquareWithNumber)){
				if (hasMoreThanTwoNonClicked(oneAndOne.adjecentSquareWithNumber)){
					clickAllExceptEdgeAndAdjecentToEdge(oneAndOne);
					successUpdate(squareWithNumber.square);
				}
			}
		}
	}
	
	private static boolean squareHasNumberOne(int adjecentSquare){
		if (squareHasNumber(adjecentSquare)){
			Square adjecentNumbered = Maps.squaresWithNumbersMap.get(adjecentSquare);
			int adjecentNumber = adjecentNumbered.numberOnSquare - adjecentNumbered.surroundingFlags;
			
			if (adjecentNumber == 1){
				return true;
			}
		}
		return false;
	}

	private static boolean squareHasNumber(int square){
		int[] notValidNumbers = {0, 8, 9};
		int numberOnSquare = Board.getNumberOnSquare(square);
		
		for (int notValid : notValidNumbers){
			if (numberOnSquare == notValid){
				return false;
			}
		}
		return true;
	}

	private static boolean hasMoreThanTwoNonClicked(int square){
		Square squareWithNumber = Maps.squaresWithNumbersMap.get(square);
		if (squareWithNumber.surroundingNonClickedSquares.size() > 2){
			return true;
		}
		return false;
	}

	private static void clickAllExceptEdgeAndAdjecentToEdge(OneAndOne oneAndOne){
		ArrayList<Integer> squaresToClick = new ArrayList<Integer>();
		Square adjecentNumbered = Maps.squaresWithNumbersMap.get(oneAndOne.adjecentSquareWithNumber);
		
		addSquaresToClick(squaresToClick, adjecentNumbered);
		removeEdgeAndAdjecentToEdge(squaresToClick, oneAndOne);
		clickSquares(squaresToClick);
	}

	private static void addSquaresToClick(ArrayList<Integer> squaresToClick, Square square){
		for (int nonClicked : square.surroundingNonClickedSquares){
			squaresToClick.add(nonClicked);
		}
	}

	private static void removeEdgeAndAdjecentToEdge(ArrayList<Integer> squaresToClick, OneAndOne oneAndOne){
		int indexEdge = squaresToClick.indexOf(oneAndOne.nonClickedEdge);
		squaresToClick.remove(indexEdge);
		int indexNonEdge = squaresToClick.indexOf(oneAndOne.nonClickedAdjecentToEdge);
		squaresToClick.remove(indexNonEdge);
	}

	private static void clickSquares(ArrayList<Integer> squaresToClick){
		for (int square : squaresToClick){
			Mouse.leftClickSquare(square);
			Square.updateTheSurroundingSquares(square);
		}
	}
	
	private static void successUpdate(int square){
		System.out.println("advanced technique");
		Square.updateTheSurroundingSquares(square);
		Main.advanced++;
		hasClickedSquares = true;
	}

	private static void oneAndTwoTechnique(){
		OneAndTwo oneAndTwo = new OneAndTwo(squareWithNumber);
		
		if (oneAndTwo.nonClickedAreNextToEachOther()){
			if (squareHasNumberOne(oneAndTwo.firstAdjecentNumbered)){
				Mouse.flagSquare(oneAndTwo.lastNonClicked);
				successUpdate(oneAndTwo.lastNonClicked);
			}
			else if (squareHasNumberOne(oneAndTwo.lastAdjecentNumbered)){
				Mouse.flagSquare(oneAndTwo.firstNonClicked);
				successUpdate(oneAndTwo.firstNonClicked);
			}
		}
	}
	
	private static class OneAndOne {
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

	private static class OneAndTwo {
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
		
		private int[] sortNonClickedSquares(){
			int tempFirst = this.squareData.surroundingNonClickedSquares.get(0);
			int tempMiddle = this.squareData.surroundingNonClickedSquares.get(1);
			int tempLast = this.squareData.surroundingNonClickedSquares.get(2);
			
			int[] sortArray = {tempFirst, tempMiddle, tempLast};
			Arrays.sort(sortArray);
			return sortArray;
		}
		
		public boolean nonClickedAreNextToEachOther(){
			int[] sortedSquares = sortNonClickedSquares();
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