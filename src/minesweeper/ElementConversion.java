package minesweeper;

public class ElementConversion {
	
	
	public static int getRow(int element){
		int row = element / 30;
		return row;
	}
	
	
	public static int getColumn(int element){
		int column = element % 30;
		return column;
	}
	
	
	public static int getElement(int row, int column){
		int element;
		
		if (row == 0){
			element = column;
		}
		else if (column == 0){
			element = row*30;
		}
		else{
			element = row*30 + column;
		}
		
		return element;
	}
}
