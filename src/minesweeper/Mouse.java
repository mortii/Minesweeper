package minesweeper;

import java.util.ArrayList;
import java.util.Random;

public class Mouse {
	
	public static void clickFirstSquare(){
		int startSquare = 0;
		int milliseconds = 200;
		Bot.delay(milliseconds);
		leftClickSquare(startSquare);
	}
	
	private static void leftClickSquare(int square){
		Bot.moveMouse(square);
		Bot.leftClickMouse();
	}

	public static boolean clickRandomSurroundingNonClicked(){
		try{
			int randomSquare = getRandomSquare(Lists.squaresWithNumbers);
			Square squareWithNumber = Maps.squareMap.get(randomSquare);
			randomSquare = getRandomSquare(squareWithNumber.surroundingNonClickedSquares);
			
			leftClickSquare(randomSquare);
			Square.updateTheSurroundingSquares(randomSquare);
			System.out.println("guessed");
			Main.guessed++;
			return true;
			
		}
		catch (Exception exeption){
			System.out.println("list is empy");
			return false;	
		}
	}
	
	public static boolean clickRandomNonClicked(){
		try{
			int square = getRandomSquare(Lists.nonClickedSquares);
			leftClickSquare(square);
			Square.updateTheSurroundingSquares(square);
			System.out.println("guessed empty");
			Main.guessed++;
			return true;
		}
		catch (Exception exeption){
			System.out.println("list is empy");
			return false;
		}
	}

	private static int getRandomSquare(ArrayList<Integer> squaresList) throws Exception{
		Random random = new Random();
		int randomNumber = 0;
		
		if (squaresList.size() > 1){
			randomNumber = random.nextInt(squaresList.size()-1);
		}
		
		int randomSquare = squaresList.get(randomNumber);
		return randomSquare;
	}
	
	public static boolean clickSurroundingNonClickedAndUpdateSurroundingSquares(Square square){
		boolean clickedSquare = false;
		
		while(square.hasNextNonClicked()){
			int next = square.nextNonClicked();
			leftClickSquare(next);
			Square.updateTheSurroundingSquares(next);
			clickedSquare = true;
		}
		return clickedSquare;
	}
	
	public static void flagSurroudingNonClicked(Square square){
		while(square.hasNextNonClicked()){
			int next = square.nextNonClicked();

			flagSquare(next);
			Square.updateTheSurroundingSquares(next);
		}
	}
	
	public static void flagSquare(int square){
		Bot.moveMouse(square);
		Bot.rightClickMouse();
		Board.placeNumberOnBoard(square, 9);
		Lists.removeFromNonClicked(square);
	}

	public static boolean clickAllExceptEdgeAndNextToEdge(Advanced.OneAndOne advanced){
			ArrayList<Integer> squaresToClick = new ArrayList<Integer>();
		Square otherNumberedSquareData = Maps.squareMap.get(advanced.adjecentSquareWithNumber);
		
		addSquaresToClick(squaresToClick, otherNumberedSquareData);
		removeEdgeAndNextToEdge(squaresToClick, advanced);

		boolean clickedSquares = false;
		if (squaresToClick.size() > 0){
			clickSquares(squaresToClick);
			clickedSquares = true;
		}

		return clickedSquares;
	}
	
	private static void addSquaresToClick(ArrayList<Integer> squaresToClick,
			Square square){
		
		for (int nonClicked : square.surroundingNonClickedSquares){
			squaresToClick.add(nonClicked);
		}
	}

	private static void removeEdgeAndNextToEdge(ArrayList<Integer> squaresToClick,
				Advanced.OneAndOne advanced){
			
		int indexEdge = squaresToClick.indexOf(advanced.nonClickedEdge);
		squaresToClick.remove(indexEdge);
		
		int indexNonEdge = squaresToClick.indexOf(advanced.nonClickedAdjecentToEdge);
		squaresToClick.remove(indexNonEdge);
	}

	public static void clickSquares(ArrayList<Integer> squaresToClick){
		for (int square : squaresToClick){
			leftClickSquare(square);
			Square.updateTheSurroundingSquares(square);
		}
	}
}
