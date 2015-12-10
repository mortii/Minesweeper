package minesweeper;

import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Mouse {
	private static int milliSecondClickDelay = 25;
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
	
	public static void clickRandomSurroundingNonClicked(){
		int square = randomSquare(Main.squaresWithNumbers);
		SquareData squareData = Main.squareDataMap.get(square);
		
		square = randomSquare(squareData.surroundingNonClickedSquares);
		leftClickSquare(square);
		SquareData.updateSurroundingSquares(square);
	}
	
	public static int randomSquare(ArrayList<Integer> squaresList){
		Random random = new Random();
		int randomNumber = 0;
		
		if (squaresList.size() > 1){
			randomNumber = random.nextInt(squaresList.size()-1);
		}
		
		int randomSquare = squaresList.get(randomNumber);
		return randomSquare;
	}
	
	public static void clickSurroundingNonClicked(SquareData squareData){
		while(squareData.hasNextNonClicked()){
			int next = squareData.nextNonClicked();
			
			leftClickSquare(next);
			SquareData.updateSurroundingSquares(next);
		}
	}
	
	public static void flagSurroudingNonClicked(SquareData squareData){
		while(squareData.hasNextNonClicked()){
			int newFlag = squareData.nextNonClicked();
			flagSquare(newFlag);
		}
	}
	
	public static void flagSquare(int square){
		moveMouse(square);
		rightClickMouse();

		Board.placeNumberOnBoard(square, 9);
		Main.removeFromNonClicked(square);

		SquareData.updateSurroundingSquares(square);
	}
	
	public static void rightClickMouse(){
		Main.robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		Main.robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
		Main.robot.delay(milliSecondClickDelay);
	}

	public static boolean clickAllExceptEdgeAndNonEdge(AdvancedData advancedData){
		ArrayList<Integer> squaresToClick = new ArrayList<Integer>();
		SquareData otherNumberedSquareData = Main.squareDataMap.get(advancedData.otherNumberedSquare);
		
		addSquaresToClick(squaresToClick, otherNumberedSquareData);
		removeEdgeAndNonEdge(squaresToClick, advancedData);

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

	public static void removeEdgeAndNonEdge(ArrayList<Integer> squaresToClick,
				AdvancedData advancedData){
			
			int indexEdge = squaresToClick.indexOf(advancedData.edge);
			squaresToClick.remove(indexEdge);
			
			int indexNonEdge = squaresToClick.indexOf(advancedData.nextToEdge);
			squaresToClick.remove(indexNonEdge);
	
	//		try{
	//			
	//		}
	//		catch (Exception E){
	//		}
			
			
		}

	public static void clickSquares(ArrayList<Integer> squaresToClick){
		for (int square : squaresToClick){
			leftClickSquare(square);
			SquareData.updateSurroundingSquares(square);
		}
	}
}
