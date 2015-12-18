package minesweeper;

import java.util.HashMap;

public class Maps {
	public static HashMap<Integer, Square> squaresWithNumbersMap = new HashMap<Integer, Square>();
	
	public static void fillSquaresWithNumbersMap(){
		for (int square : Lists.squaresWithNumbers){
			Square squareWithNumber = new Square(square);
			squaresWithNumbersMap.put(square, squareWithNumber);
		}
	}
}
