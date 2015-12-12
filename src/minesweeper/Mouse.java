package minesweeper;

import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Mouse {
	private static int milliSecondClickDelay = 525;
	public static HashMap<Integer, Pixel> centerOfSquares =
			HashMapsOnDisk.getHashMap("centerOfSquares.ser");
	
	public static void clickFirstSquare(){
		int startSquare = 0;
		int milliseconds = 200;
		Main.robot.delay(milliseconds);
		leftClickSquare(startSquare);
	}
	
	public static void leftClickSquare(int square){
		moveMouse(square);
		leftClickMouse();
	}

	public static void moveMouse(int square){
		Pixel pixel = centerOfSquares.get(square);
		Main.robot.mouseMove(pixel.x, pixel.y);
	}
	
	public static void leftClickMouse(){
		Main.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		Main.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		Main.robot.delay(milliSecondClickDelay);
		Main.robot.mouseMove(0, 0);
	}
	
	public static boolean clickRandomSurroundingNonClicked(){
		int square = randomSquare(Main.squaresWithNumbers);
		
		if (square == -1){
			return false;
		}

		SquareData squareData = Main.squareDataMap.get(square);
		square = randomSquare(squareData.surroundingNonClickedSquares);
		
		if (square == -1){
			return false;
		}
		else{
			System.out.println("guessed");
			Main.guessed++;
			leftClickSquare(square);
			SquareData.updateSurroundingSquares(square);
			return true;
		}
	}
	
	public static void clickRandomNonClicked(){
		int square = randomSquare(Main.nonClickedSquares);
		
		if (square != -1){
			System.out.println("guessed empty");
			Main.guessed++;
			leftClickSquare(square);
			SquareData.updateSurroundingSquares(square);
		}
	}

	public static int randomSquare(ArrayList<Integer> squaresList){
		Random random = new Random();
		int randomNumber = 0;
		
		if (squaresList.size() == 0){
			return -1;
		}
		else if (squaresList.size() > 1){
			randomNumber = random.nextInt(squaresList.size()-1);
		}
		
		int randomSquare = squaresList.get(randomNumber);
		return randomSquare;
	}
	
	public static boolean clickSurroundingNonClicked(SquareData squareData){
		boolean clickedSquare = false;
		
		while(squareData.hasNextNonClicked()){
			int next = squareData.nextNonClicked();
			
			leftClickSquare(next);
			SquareData.updateSurroundingSquares(next);
			
			clickedSquare = true;
		}
		return clickedSquare;
	}
	
	public static void flagSurroudingNonClicked(SquareData squareData){
		while(squareData.hasNextNonClicked()){
			int next = squareData.nextNonClicked();

			flagSquare(next);
			SquareData.updateSurroundingSquares(next);
		}
	}
	
	public static void flagSquare(int square){
		moveMouse(square);
		rightClickMouse();
		Board.placeNumberOnBoard(square, 9);
		Main.removeFromNonClicked(square);
	}
	
	public static void rightClickMouse(){
		Main.robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		Main.robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
		Main.robot.delay(milliSecondClickDelay);
	}

	public static boolean clickAllExceptEdgeAndNextToEdge(AdvancedData.OneAndOne advancedData){
		ArrayList<Integer> squaresToClick = new ArrayList<Integer>();
		SquareData otherNumberedSquareData = Main.squareDataMap.get(advancedData.adjecentSquareWithNumber);
		
		addSquaresToClick(squaresToClick, otherNumberedSquareData);
		removeEdgeAndNextToEdge(squaresToClick, advancedData);

		boolean clickedSquares = false;
		if (squaresToClick.size() > 0){
			clickSquares(squaresToClick);
			clickedSquares = true;
		}

		return clickedSquares;
	}
	
	public static void addSquaresToClick(ArrayList<Integer> squaresToClick,
			SquareData squareData){
		
		for (int square : squareData.surroundingNonClickedSquares){
			squaresToClick.add(square);
		}
	}

	public static void removeEdgeAndNextToEdge(ArrayList<Integer> squaresToClick,
				AdvancedData.OneAndOne advancedData){
			
			int indexEdge = squaresToClick.indexOf(advancedData.edge);
			squaresToClick.remove(indexEdge);
			
			int indexNonEdge = squaresToClick.indexOf(advancedData.nextToEdge);
			squaresToClick.remove(indexNonEdge);
		}

	public static void clickSquares(ArrayList<Integer> squaresToClick){
		for (int square : squaresToClick){
			leftClickSquare(square);
			SquareData.updateSurroundingSquares(square);
		}
	}
}
