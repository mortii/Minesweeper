package minesweeper;

import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class Mouse {
	private static int milliSecondClickDelay = 925;
	public static HashMap<Integer, Pixel> centerOfSquares =
			ObjectsOnDisk.getHashMap("centerOfSquares.ser");
	
	
	public static void clickFirstSquare(){
		int startSquare = 0;
		int milliseconds = 200;
		Main.robot.delay(milliseconds);
		moveMouse(startSquare);
		leftClickMouse();
	}

	
	public static void moveMouse(int square){
//		System.out.println("moved mouse");
		Pixel pixel = centerOfSquares.get(square);
		Main.robot.mouseMove(pixel.x, pixel.y);
//		Main.robot.delay(100);
	}
	
	
	public static void leftClickSquare(int square){
		moveMouse(square);
		leftClickMouse();
	}
	
	
	public static void leftClickMouse(){
//		System.out.println("left click mouse");
		Main.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		Main.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		Main.robot.delay(milliSecondClickDelay);
		Main.robot.mouseMove(0, 0);
	}

	
	public static void rightClickMouse(){
//		System.out.println("right click mouse");
		Main.robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		Main.robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
		Main.robot.delay(milliSecondClickDelay);
	}
	
	
	public static void clickRandomNonClicked(){
		Random random = new Random();
		
		if (Main.squaresWithNumbers.size() > 0){
			int randomNumber = random.nextInt(Main.squaresWithNumbers.size()-1);
			int randomSquare = Main.squaresWithNumbers.get(randomNumber);
			
			SquareData squareData = Main.squareDataMap.get(randomSquare);
			int listLength = squareData.surroundingNonClickedSquares.size();
			
			randomNumber = random.nextInt(listLength-1);
			randomSquare = squareData.surroundingNonClickedSquares.get(randomNumber);
				
			moveMouse(randomSquare);
			leftClickMouse();
			SquareData.updateSurroundingSquares(randomSquare);
		}
		
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
		
		int row = MatrixConversion.getRow(square);
		int column = MatrixConversion.getColumn(square);
		
		Board.board[row][column] = 9;
		Main.removeFromNonClicked(square);

		SquareData.updateSurroundingSquares(square);
	}
	
	
	public static boolean clickAllNonClickedExceptEdgeAndNonEdge(SquareData otherSquareData,
			AdvancedData edgeData){
		
		ArrayList<Integer> squaresToClick = new ArrayList<Integer>();
		
		for (int square : otherSquareData.surroundingNonClickedSquares){
			squaresToClick.add(square);
		}
		
		for (int square : edgeData.nonClickedSquaresNotToClick){
			int index = squaresToClick.indexOf(square);
			try{
				squaresToClick.remove(index);
			}
			catch (Exception E){
			}
		}
		
		boolean clickedSquares = false;
		
		for (int square : squaresToClick){
			leftClickSquare(square);
			SquareData.updateSurroundingSquares(square);
			clickedSquares = true;
		}
		
		return clickedSquares;
	}
	
}









