package minesweeper;

public class Maps {
	public static void fillSquareMap(){
		for (int square : Main.squaresWithNumbers){
			Square squareWithNumber = new Square(square);
			Main.squareMap.put(square, squareWithNumber);
		}
	}
}
