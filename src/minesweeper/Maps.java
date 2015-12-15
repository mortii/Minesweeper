package minesweeper;

import java.util.HashMap;

public class Maps {
	public static HashMap<Integer, Square> squareMap = new HashMap<Integer, Square>();
	
	public static void fillSquareMap(){
		for (int square : Lists.squaresWithNumbers){
			Square squareWithNumber = new Square(square);
			squareMap.put(square, squareWithNumber);
		}
	}
}
