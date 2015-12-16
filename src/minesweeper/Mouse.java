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
	
	public static void leftClickSquare(int square){
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

	public static boolean clickRandomSurroundingNonClicked(){
		try{
			int randomSquare = getRandomSquare(Lists.squaresWithNumbers);
			Square squareWithNumber = Maps.squareMap.get(randomSquare);
			randomSquare = getRandomSquare(squareWithNumber.surroundingNonClickedSquares);
			clickSquareAndUpdateSurrounding(randomSquare);
			return true;
		}
		catch (Exception e){
			return false;	
		}
	}
	
	private static void clickSquareAndUpdateSurrounding(int randomSquare){
		leftClickSquare(randomSquare);
		Square.updateTheSurroundingSquares(randomSquare);
		System.out.println("guessed");
		Main.guessed++;
	}
	
	public static boolean clickRandomNonClicked(){
		try{
			int randomSquare = getRandomSquare(Lists.nonClickedSquares);
			clickSquareAndUpdateSurrounding(randomSquare);
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
