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
			HashMapsOnDisk.getHashMap("squareCoordinates.ser");
	
	public static int getNumber(int square){
		Pixel pixelOffset = getPixelOffset(square);
		int numberOnSquare = 0;
		
		if (squareIsFlag(pixelOffset)){
			numberOnSquare = 9;
		}
		else if (squareIsNonClicked(pixelOffset)){
			numberOnSquare = 8;
		}
		else if (numberIsOne(pixelOffset)){
			numberOnSquare = 1;
		}
		else if (numberIsTwo(pixelOffset)){
			numberOnSquare = 2;
		}		
		else if (numberIsThree(pixelOffset)){
			numberOnSquare = 3;
		}
		else if (numberIsFour(pixelOffset)){
			numberOnSquare = 4;
		}
		else if (numberIsFive(pixelOffset)){
			numberOnSquare = 5;
		}
		else if (numberIsSix(pixelOffset)){
			numberOnSquare = 6;
		}
		else if (numberIsSeven(pixelOffset)){
			numberOnSquare = 7;
		}
		
		return numberOnSquare;
	}
	
	private static Pixel getPixelOffset(int square){
		int pixelX = squareCoordinates.get(square).x;
		int pixelY = squareCoordinates.get(square).y;
		
		Pixel pixelOffset = new Pixel(pixelX, pixelY);
		return pixelOffset;
	}

	private static boolean squareIsNonClicked(Pixel pixelOffset){
		PixelColors nonClickedPixelColors = new PixelColors(nonClickedPixel, pixelOffset);
		
		if (nonClickedPixelColors.blue > 40){
			return true;
		}
		return false;
	}

	private static boolean squareIsFlag(Pixel pixelOffset){
		PixelColors flagPixelColors = new PixelColors(flagPixel, pixelOffset);
		
		if (flagPixelColors.blue < 50){
			return true;
		}
		return false;
	}

	private static boolean numberIsOne(Pixel pixelOffset){
		PixelColors onePixelColors = new PixelColors(onePixel, pixelOffset);
		
		if (onePixelColors.red < 75 && onePixelColors.red > 60 &&
				onePixelColors.green < 100){
			return true;
		}
		return false;
	}
	
	private static boolean numberIsTwo(Pixel pixelOffset){
		PixelColors twoPixelColors = new PixelColors(twoPixel, pixelOffset);
		
		if (twoPixelColors.blue < 10){
			return true;
		}
		return false;
	}
	
	private static boolean numberIsThree(Pixel pixelOffset){
		PixelColors threePixelColors = new PixelColors(threePixel, pixelOffset);
		
		if (threePixelColors.blue < 20 && threePixelColors.green < 10){
			return true;
		}
		return false;
	}
	
	private static boolean numberIsFour(Pixel pixelOffset){
		PixelColors fourPixelColors = new PixelColors(fourPixel, pixelOffset);
		
		if (fourPixelColors.red < 5){
			return true;
		}	
		return false;
	}
	
	private static boolean numberIsFive(Pixel pixelOffset){
		PixelColors fivePixelColors = new PixelColors(fivePixel, pixelOffset);
		
		if (fivePixelColors.red < 200 && fivePixelColors.green < 10 &&
				fivePixelColors.blue < 10){
			return true;
		}
		return false;
	}
	
	private static boolean numberIsSix(Pixel pixelOffset){
		PixelColors sixPixelColors = new PixelColors(sixPixel, pixelOffset);
		
		if (sixPixelColors.red < 10 && sixPixelColors.green > 100){
			return true;
		}
		return false;
	}
	
	private static boolean numberIsSeven(Pixel pixelOffset){
		PixelColors sevenPixelColors = new PixelColors(sevenPixel, pixelOffset);
		
		if (sevenPixelColors.green < 30){
			return true;
		}
		return false;
	}
}

class PixelColors {
	public int red;
	public int green;
	public int blue;
	
	public PixelColors(Pixel identityPixel, Pixel pixelOffset) {
		Pixel identityPixelCopy = new Pixel(identityPixel.x, identityPixel.y);
		
		identityPixelCopy.x += pixelOffset.x;
		identityPixelCopy.y += pixelOffset.y;
		
		Color imageColors = new Color(Board.boardImage.getRGB(identityPixelCopy.x, identityPixelCopy.y));
		this.red = imageColors.getRed();
		this.green = imageColors.getGreen();
		this.blue = imageColors.getBlue();
	}
}