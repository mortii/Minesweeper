package minesweeper;

import java.awt.Color;
import java.util.HashMap;


public class NumberOnSquares {
	private static Tuple onePixel = new Tuple(16, 16);
	private static Tuple twoPixel = new Tuple(23, 26);
	private static Tuple threePixel = new Tuple(20, 11);
	private static Tuple fourPixel = new Tuple(10, 20);
	private static Tuple fivePixel = new Tuple(12, 11);
	private static Tuple sixPixel = new Tuple(13, 20);
	private static Tuple sevenPixel = new Tuple(24, 7);
	private static Tuple flagPixel = new Tuple(8, 10);
	private static Tuple nonClickedPixel = new Tuple(1, 0);
	private static HashMap<Integer, Tuple> squareCoordinates =
			ObjectsOutAndIn.getHashMap("squareCoordinates.ser");
	
	
	
	public static int getNumber(int square){
		Tuple pixelOffset = getPixelOffset(square);
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

	
	private static Tuple getPixelOffset(int square){
		int pixelX = squareCoordinates.get(square).x;
		int pixelY = squareCoordinates.get(square).y;
		
		Tuple tuple = new Tuple(pixelX, pixelY);
		return tuple;
	}
		
}


class PixelColors {
	public int red;
	public int green;
	public int blue;
	
	
	public PixelColors(Tuple pixel, Tuple pixelOffset) {
		Tuple pixelCopy = new Tuple(pixel.x, pixel.y);
		
		pixelCopy.x += pixelOffset.x;
		pixelCopy.y += pixelOffset.y;
		
		Color imageColors = new Color(Board.boardImage.getRGB(pixelCopy.x, pixelCopy.y));
		this.red = imageColors.getRed();
		this.green = imageColors.getGreen();
		this.blue = imageColors.getBlue();
//		System.out.println(imageColors);
	}
}











