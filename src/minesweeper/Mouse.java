package minesweeper;

import java.util.ArrayList;
import java.util.Random;

public class Mouse {
	private static Random random = new Random();
	
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

	public static void clickSurroundingNonClicked(Square square){
		while(square.hasNextNonClicked()){
			int next = square.nextNonClicked();
			leftClickSquare(next);
			Square.updateTheSurroundingSquares(next);
		}
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
			leftClickSquare(square);
			Square.updateTheSurroundingSquares(square);
		}
	}

	public static boolean clickRandomSurroundingNonClicked(){
		try{
			int randomSquare = getRandomSquare(Lists.squaresWithNumbers);
			Square squareWithNumber = Maps.squareMap.get(randomSquare);
			randomSquare = getRandomSquare(squareWithNumber.surroundingNonClickedSquares);
			clickAndUpdateSurrounding(randomSquare);
			return true;
		}
		catch (Exception e){
			return false;	
		}
	}
	
	private static void clickAndUpdateSurrounding(int randomSquare){
		leftClickSquare(randomSquare);
		Square.updateTheSurroundingSquares(randomSquare);
		System.out.println("guessed");
		Main.guessed++;
	}
	
	public static boolean clickRandomNonClicked(){
		try{
			int randomSquare = getRandomSquare(Lists.nonClickedSquares);
			clickAndUpdateSurrounding(randomSquare);
			return true;
		}
		catch (Exception e){
			return false;
		}
	}

	private static int getRandomSquare(ArrayList<Integer> squaresList) throws Exception{
		int randomNumber = 0;
		if (squaresList.size() > 1){
			randomNumber = random.nextInt(squaresList.size()-1);
		}
		return squaresList.get(randomNumber);
	}
}
