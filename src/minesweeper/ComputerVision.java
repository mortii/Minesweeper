package minesweeper;

import java.awt.Color;
import java.util.HashMap;


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
	private static HashMap<Integer, Pixel> squareCoordinates =
			ObjectsOnDisk.getHashMap("squareCoordinates.ser");
	
	
	public static int getNumber(int square){
		Pixel pixelOffset = getPixelOffset(square);
		int numberOnSquare = 0;
		
		PixelColors pixelFlagColors = new PixelColors(flagPixel, pixelOffset);
		
		if (pixelFlagColors.blue < 50){
			numberOnSquare = 9;
			return numberOnSquare;
		}
		
		PixelColors pixelNonClickedColors = new PixelColors(nonClickedPixel, pixelOffset);
		
		if (pixelNonClickedColors.blue > 40){
			numberOnSquare = 8;
			return numberOnSquare;
		}
		
		PixelColors pixelOneColors = new PixelColors(onePixel, pixelOffset);
		
		if (pixelOneColors.red < 75 && pixelOneColors.red > 60 &&
			pixelOneColors.green < 100){
			numberOnSquare = 1;
			return numberOnSquare;
		}
		
		PixelColors pixelTwoColors = new PixelColors(twoPixel, pixelOffset);
		
		if (pixelTwoColors.blue < 10){
			numberOnSquare = 2;
			return numberOnSquare;
		}
		
		PixelColors pixelThreeColors = new PixelColors(threePixel, pixelOffset);
		
		if (pixelThreeColors.blue < 20){
			numberOnSquare = 3;
			return numberOnSquare;
		}
		
		PixelColors pixelFourColors = new PixelColors(fourPixel, pixelOffset);
		
		if (pixelFourColors.red < 5){
			numberOnSquare = 4;
			return numberOnSquare;
		}
		
		PixelColors pixelFiveColors = new PixelColors(fivePixel, pixelOffset);
		
		if (pixelFiveColors.red < 200 && pixelFiveColors.green < 10 &&
			pixelFiveColors.blue < 10){
			
			numberOnSquare = 5;
			return numberOnSquare;
		}
		
		PixelColors pixelSixColors = new PixelColors(sixPixel, pixelOffset);
		
		if (pixelSixColors.red < 10 && pixelSixColors.green > 100){
			numberOnSquare = 6;
			return numberOnSquare;
		}
		
		PixelColors pixelSevenColors = new PixelColors(sevenPixel, pixelOffset);
		
		if (pixelSevenColors.green < 30){
			numberOnSquare = 7;
			return numberOnSquare;
		}
		
		return numberOnSquare;
	}

	
	private static Pixel getPixelOffset(int square){
		int pixelX = squareCoordinates.get(square).x;
		int pixelY = squareCoordinates.get(square).y;
		
		Pixel tuple = new Pixel(pixelX, pixelY);
		return tuple;
	}
}


class PixelColors {
	public int red;
	public int green;
	public int blue;
	
	
	public PixelColors(Pixel pixel, Pixel pixelOffset) {
		Pixel pixelCopy = new Pixel(pixel.x, pixel.y);
		
		pixelCopy.x += pixelOffset.x;
		pixelCopy.y += pixelOffset.y;
		
		Color imageColors = new Color(Board.boardImage.getRGB(pixelCopy.x, pixelCopy.y));
		this.red = imageColors.getRed();
		this.green = imageColors.getGreen();
		this.blue = imageColors.getBlue();
	}
}











