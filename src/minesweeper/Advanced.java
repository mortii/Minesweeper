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

//	http://www.minesweeper.info/wiki/Strategy
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
			if (squareIsOne(oneAndOne.adjecentSquareWithNumber)){
				if (oneAndOne.adjecentNumberedHasMoreThanTwoNonClicked()){
					System.out.println("advancedTechnique 1-1");
					clickAllExceptEdgeAndNextToEdge(oneAndOne);
					successUpdate(squareWithNumber.square);
				}
			}
		}
	}
	
	private static boolean squareIsOne(int adjecentSquare){
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

	private static boolean squareHasMoreThanTwoNonClicked(int square){
		Square squareWithNumber = Maps.squaresWithNumbersMap.get(square);
		if (squareWithNumber.surroundingNonClickedSquares.size() > 2){
			return true;
		}
		return false;
	}

	private static void clickAllExceptEdgeAndNextToEdge(Advanced.OneAndOne advanced){
		ArrayList<Integer> squaresToClick = new ArrayList<Integer>();
		Square otherNumberedSquareData = Maps.squaresWithNumbersMap.get(advanced.adjecentSquareWithNumber);
		
		addSquaresToClick(squaresToClick, otherNumberedSquareData);
		removeEdgeAndNextToEdge(squaresToClick, advanced);
	
		if (squaresToClick.size() > 0){
			clickSquares(squaresToClick);
		}
	}

	private static void addSquaresToClick(ArrayList<Integer> squaresToClick, Square square){
		for (int nonClicked : square.surroundingNonClickedSquares){
			squaresToClick.add(nonClicked);
		}
	}

	private static void removeEdgeAndNextToEdge(ArrayList<Integer> squaresToClick, Advanced.OneAndOne advanced){
		int indexEdge = squaresToClick.indexOf(advanced.nonClickedEdge);
		squaresToClick.remove(indexEdge);
		int indexNonEdge = squaresToClick.indexOf(advanced.nonClickedAdjecentToEdge);
		squaresToClick.remove(indexNonEdge);
	}

	private static void clickSquares(ArrayList<Integer> squaresToClick){
		for (int square : squaresToClick){
			Mouse.leftClickSquare(square);
			Square.updateTheSurroundingSquares(square);
		}
	}
	
	private static void successUpdate(int square){
		Square.updateTheSurroundingSquares(square);
		Main.advanced++;
		hasClickedSquares = true;
	}

	private static void oneAndTwoTechnique(){
		OneAndTwo oneAndTwoData = new OneAndTwo(squareWithNumber);
		
		if (oneAndTwoData.nonClickedAreNextToEachOther()){
			if (squareIsOne(oneAndTwoData.firstAdjecentNumbered)){
				System.out.println("advancedTechnique 1-2");
				Mouse.flagSquare(oneAndTwoData.lastNonClicked);
				successUpdate(oneAndTwoData.lastNonClicked);
			}
			else if (squareIsOne(oneAndTwoData.lastAdjecentNumbered)){
				System.out.println("advancedTechnique 1-2");
				Mouse.flagSquare(oneAndTwoData.firstNonClicked);
				successUpdate(oneAndTwoData.firstNonClicked);
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
		
		public boolean adjecentNumberedHasMoreThanTwoNonClicked(){
			Square adjecentNumbered = Maps.squaresWithNumbersMap.get(adjecentSquareWithNumber);
			if (adjecentNumbered.surroundingNonClickedSquares.size() > 2){
				return true;
			}
			return false;
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