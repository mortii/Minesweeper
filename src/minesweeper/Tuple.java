package minesweeper;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Tuple implements Serializable{
	int x;
	int y;
	
	
	public Tuple(int x, int y){
		this.x = x;
		this.y = y;
	}
	
}
