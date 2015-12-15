package minesweeper;

import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Mouse {
	private static final int MILLISECONDS_CLICK_DELAY = 5;
	private static HashMap<Integer, Pixel> centerOfSquares =
			OnDisk.tryToGetHashMap("centerOfSquares.ser");
	
	public static void clickFirstSquare(){
		int startSquare = 0;
		int milliseconds = 200;
		Main.robot.delay(milliseconds);
		leftClickSquare(startSquare);
	}
	
	private static void leftClickSquare(int square){
		moveMouse(square);
		leftClickMouse();
	}

	private static void moveMouse(int square){
		Pixel pixel = centerOfSquares.get(square);
		Main.robot.mouseMove(pixel.x, pixel.y);
	}
	
	private static void leftClickMouse(){
		Main.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		Main.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		Main.robot.delay(MILLISECONDS_CLICK_DELAY);
		Main.robot.mouseMove(0, 0);
	}
	
	public static boolean clickRandomSurroundingNonClicked(){
		try{
			int randomSquare = getRandomSquare(Main.squaresWithNumbers);
			Square squareWithNumber = Main.squareMap.get(randomSquare);

			randomSquare = getRandomSquare(squareWithNumber.surroundingNonClickedSquares);
			leftClickSquare(randomSquare);
			Square.updateTheSurroundingSquares(randomSquare);
			System.out.println("guessed");
			Main.guessed++;
			return true;
			
		}
		catch (Exception exeption){
			return false;	
		}
	}
	
	public static boolean clickRandomNonClicked(){
		try{
			int square = getRandomSquare(Main.nonClickedSquares);
			leftClickSquare(square);
			Square.updateTheSurroundingSquares(square);
			System.out.println("guessed empty");
			Main.guessed++;
			return true;
		}
		catch (Exception exeption){
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
	
	public static boolean clickSurroundingNonClicked(Square square){
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
		moveMouse(square);
		rightClickMouse();
		Board.placeNumberOnBoard(square, 9);
		Lists.removeFromNonClicked(square);
	}
	
	private static void rightClickMouse(){
		Main.robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		Main.robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
		Main.robot.delay(MILLISECONDS_CLICK_DELAY);
	}

	public static boolean clickAllExceptEdgeAndNextToEdge(Advanced.OneAndOne advanced){
			ArrayList<Integer> squaresToClick = new ArrayList<Integer>();
		Square otherNumberedSquareData = Main.squareMap.get(advanced.adjecentSquareWithNumber);
		
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
