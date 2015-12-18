package minesweeper;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/*
 * The colors are unfortunately not distributed uniformly across the Board
 * making this task a lot harder than expected and took a lot of trial and error.
 * Different graphics cards may cause different results, so you may have to tweak
 * some values.
 */
public class ComputerVision {
	private static Pixel onePixel = new Pixel(16, 16);
	private static Pixel twoPixel = new Pixel(23, 26);
	private static Pixel threePixel = new Pixel(20, 11);
	private static Pixel fourPixel = new Pixel(10, 20);
	private static Pixel fivePixel = new Pixel(12, 11);
	private static Pixel sixPixel = new Pixel(13, 20);
	private static Pixel sevenPixel = new Pixel(24, 7);
	private static Pixel flagPixel = new Pixel(8, 10);
	private static Pixel nonClickedPixel = new Pixel(1, 0);
	private static Pixel squareOffset;
	private static HashMap<Integer, Pixel> squareCoordinates = OnDisk.getHashMap("squareCoordinates.ser");
	
	public static int getNumber(int square){
		squareOffset = squareCoordinates.get(square);;
		int numberOnSquare = 0;
		
		if (squareIsFlag()){
			numberOnSquare = 9;
		}
		else if (squareIsNonClicked()){
			numberOnSquare = 8;
		}
		else if (numberIsOne()){
			numberOnSquare = 1;
		}
		else if (numberIsTwo()){
			numberOnSquare = 2;
		}		
		else if (numberIsThree()){
			numberOnSquare = 3;
		}
		else if (numberIsFour()){
			numberOnSquare = 4;
		}
		else if (numberIsFive()){
			numberOnSquare = 5;
		}
		else if (numberIsSix()){
			numberOnSquare = 6;
		}
		else if (numberIsSeven()){
			numberOnSquare = 7;
		}
		
		return numberOnSquare;
	}

	private static boolean squareIsNonClicked(){
		PixelColors nonClickedPixelColors = new PixelColors(nonClickedPixel);
		
		if (nonClickedPixelColors.blue > 40){
			return true;
		}
		return false;
	}

	private static boolean squareIsFlag(){
		PixelColors flagPixelColors = new PixelColors(flagPixel);
		
		if (flagPixelColors.blue < 50){
			return true;
		}
		return false;
	}

	private static boolean numberIsOne(){
		PixelColors onePixelColors = new PixelColors(onePixel);
		
		if (onePixelColors.red < 75 && onePixelColors.red > 60 &&
				onePixelColors.green < 100){
			return true;
		}
		return false;
	}
	
	private static boolean numberIsTwo(){
		PixelColors twoPixelColors = new PixelColors(twoPixel);
		
		if (twoPixelColors.blue < 10){
			return true;
		}
		return false;
	}
	
	private static boolean numberIsThree(){
		PixelColors threePixelColors = new PixelColors(threePixel);
		
		if (threePixelColors.blue < 20 && threePixelColors.green < 20){
			return true;
		}
		return false;
	}
	
	private static boolean numberIsFour(){
		PixelColors fourPixelColors = new PixelColors(fourPixel);
		
		if (fourPixelColors.red < 5){
			return true;
		}	
		return false;
	}
	
	private static boolean numberIsFive(){
		PixelColors fivePixelColors = new PixelColors(fivePixel);
		
		if (fivePixelColors.red < 200 && fivePixelColors.green < 10 &&
				fivePixelColors.blue < 10){
			return true;
		}
		return false;
	}
	
	private static boolean numberIsSix(){
		PixelColors sixPixelColors = new PixelColors(sixPixel);
		
		if (sixPixelColors.red < 10 && sixPixelColors.green > 100){
			return true;
		}
		return false;
	}
	
	private static boolean numberIsSeven(){
		PixelColors sevenPixelColors = new PixelColors(sevenPixel);
		
		if (sevenPixelColors.green < 30){
			return true;
		}
		return false;
	}
	
	private static class PixelColors{
		public int red;
		public int green;
		public int blue;
		
		public PixelColors(Pixel identityPixel) {
			Pixel realPixel = getRealPixel(identityPixel);
			BufferedImage boardImage = Board.getBoardImage();
			Color pixelColors = new Color(boardImage.getRGB(realPixel.x, realPixel.y));
			setPixelColors(pixelColors);
		}
		
		private Pixel getRealPixel(Pixel identityPixel){
			Pixel realPixel = new Pixel(identityPixel.x, identityPixel.y);
			realPixel.x += squareOffset.x;
			realPixel.y += squareOffset.y;
			return realPixel;
		}
		
		private void setPixelColors(Color pixelColors){
			this.red = pixelColors.getRed();
			this.green = pixelColors.getGreen();
			this.blue = pixelColors.getBlue();
		}
	}
}