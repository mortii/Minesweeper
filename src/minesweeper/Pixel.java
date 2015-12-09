package minesweeper;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Pixel implements Serializable{
	int x;
	int y;
	
	public Pixel(int x, int y){
		this.x = x;
		this.y = y;
	}
}

