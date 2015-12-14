package minesweeper;

public class Advanced {


	public static boolean doAdvancedSolving(){
//		 http://www.minesweeper.info/wiki/Strategy
		
		boolean clickedSquares = false;
		
		for (int square : Main.squaresWithNumbers){
			Square squareWithNumber = Main.squareMap.get(square);
			
			int number = squareWithNumber.numberOnSquare
					- squareWithNumber.surroundingFlags;
			
			if (number == 1){
				if (squareWithNumber.surroundingNonClickedSquares.size() == 2){
					if (oneAndOneTechnique(squareWithNumber)){
						clickedSquares = true;
					}
				}
			}
			else if (number == 2){
				if (squareWithNumber.surroundingNonClickedSquares.size() == 3){
					if (oneAndTwoTechnique(squareWithNumber)){
						clickedSquares = true;
					}
				}
			}
		}
		return clickedSquares;
	}
	
	public static boolean oneAndOneTechnique(Square squareWithNumber){
		OneAndOne oneAndOneData = new OneAndOne(squareWithNumber);
		
		if (oneAndOneData.nonClickedAreNextToEachOther()){
			if (squareIsOne(oneAndOneData.adjecentSquareWithNumber)){
				if (Mouse.clickAllExceptEdgeAndNextToEdge(oneAndOneData)){
					System.out.println("advancedTechnique 1-1");
					Main.advancedTechniques++;
					return true;
				}
			}
		}
		return false;
	}

	public static boolean squareIsOne(int adjecentSquare){
		int row = Board.getRow(adjecentSquare);
		int column = Board.getColumn(adjecentSquare);
		
		if (Board.board[row][column] == 1){
			return true;
		}
		return false;
	}

	public static boolean oneAndTwoTechnique(Square squareWithNumber){
		OneAndTwo oneAndTwoData = new OneAndTwo(squareWithNumber);
		
		if (oneAndTwoData.nonClickedAreNextToEachOther()){
			if (squareIsOne(oneAndTwoData.firstAdjecentNumbered)){
				flagAndUpdate(oneAndTwoData.lastNonClicked);
				return true;
			}
			else if (squareIsOne(oneAndTwoData.lastAdjecentNumbered)){
				flagAndUpdate(oneAndTwoData.firstNonClicked);
				return true;
			}
		}
		return false;
	}
	
	public static void flagAndUpdate(int square){
		System.out.println("advancedTechnique 1-2");
		Mouse.flagSquare(square);
		Square.updateTheSurroundingSquares(square);
		Main.advancedTechniques++;
	}
}
