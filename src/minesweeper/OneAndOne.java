package minesweeper;

public class OneAndOne {
	public Square squareData;
	
	public int square;
	public int adjecentSquareWithNumber;
	
	public int nonClickedEdge;
	public int nonClickedAdjecentToEdge;
	
	public OneAndOne(Square squareData){
		this.squareData = squareData;
		this.square = squareData.square;
	}
	
	public boolean nonClickedAreNextToEachOther(){
		setEdgeAndAdjecentToEdge();
		
		if (nonClickedEdge + 1 == nonClickedAdjecentToEdge){
			this.adjecentSquareWithNumber = this.square + 1;
			return true;
		}
		
		else if (nonClickedEdge - 1 == nonClickedAdjecentToEdge){
			this.adjecentSquareWithNumber = this.square - 1;
			return true;
		}
		
		else if (nonClickedEdge + 30 == nonClickedAdjecentToEdge){
			this.adjecentSquareWithNumber = this.square + 30;
			return true;
		}
		
		else if (nonClickedEdge - 30 == nonClickedAdjecentToEdge){
			this.adjecentSquareWithNumber = this.square - 30;
			return true;
		}
		
		
		return false;
	}

	private void setEdgeAndAdjecentToEdge(){
		/*Edge means that it only has one nonClicked square next to it
		 * in the same column or row, depending on the orientation to
		 * the square we are testing.
		 * 
		 * The edge has to be in a 90 degree direction (i.e. +) from the
		 *square we are testing.
		 *
		 *examples: http://www.minesweeper.info/wiki/Strategy
		 */
		
		int tempEdge = this.squareData.surroundingNonClickedSquares.get(0);
		int tempAdjecentToEdge = this.squareData.surroundingNonClickedSquares.get(1);

		if (tempEdge + 1 == square || tempEdge - 1 == square ||
				tempEdge + 30 == square || tempEdge - 30 == square){
			
			this.nonClickedEdge = tempEdge;
			this.nonClickedAdjecentToEdge = tempAdjecentToEdge;
		}
		else{
			this.nonClickedEdge = tempAdjecentToEdge;
			this.nonClickedAdjecentToEdge = tempEdge;
		}
	}
}
