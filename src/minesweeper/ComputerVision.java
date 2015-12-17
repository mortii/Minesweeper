package minesweeper;

import java.awt.Color;
import java.awt.image.BufferedImage;
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
			OnDisk.getHashMap("squareCoordinates.ser");
	
	public static int getNumber(int square){
		Pixel squareOffset = getSquareOffset(square);
		int numberOnSquare = 0;
		
		if (squareIsFlag(squareOffset)){
			numberOnSquare = 9;
		}
		else if (squareIsNonClicked(squareOffset)){
			numberOnSquare = 8;
		}
		else if (numberIsOne(squareOffset)){
			numberOnSquare = 1;
		}
		else if (numberIsTwo(squareOffset)){
			numberOnSquare = 2;
		}		
		else if (numberIsThree(squareOffset)){
			numberOnSquare = 3;
		}
		else if (numberIsFour(squareOffset)){
			numberOnSquare = 4;
		}
		else if (numberIsFive(squareOffset)){
			numberOnSquare = 5;
		}
		else if (numberIsSix(squareOffset)){
			numberOnSquare = 6;
		}
		else if (numberIsSeven(squareOffset)){
			numberOnSquare = 7;
		}
		
		return numberOnSquare;
	}
	
	private static Pixel getSquareOffset(int square){
		return squareCoordinates.get(square);
	}

	private static boolean squareIsNonClicked(Pixel squareOffset){
		PixelColors nonClickedPixelColors = new PixelColors(nonClickedPixel, squareOffset);
		
		if (nonClickedPixelColors.blue > 40){
			return true;
		}
		return false;
	}

	private static boolean squareIsFlag(Pixel squareOffset){
		PixelColors flagPixelColors = new PixelColors(flagPixel, squareOffset);
		
		if (flagPixelColors.blue < 50){
			return true;
		}
		return false;
	}

	private static boolean numberIsOne(Pixel squareOffset){
		PixelColors onePixelColors = new PixelColors(onePixel, squareOffset);
		
		if (onePixelColors.red < 75 && onePixelColors.red > 60 &&
				onePixelColors.green < 100){
			return true;
		}
		return false;
	}
	
	private static boolean numberIsTwo(Pixel squareOffset){
		PixelColors twoPixelColors = new PixelColors(twoPixel, squareOffset);
		
		if (twoPixelColors.blue < 10){
			return true;
		}
		return false;
	}
	
	private static boolean numberIsThree(Pixel squareOffset){
		PixelColors threePixelColors = new PixelColors(threePixel, squareOffset);
		
		if (threePixelColors.blue < 20 && threePixelColors.green < 20){
			return true;
		}
		return false;
	}
	
	private static boolean numberIsFour(Pixel squareOffset){
		PixelColors fourPixelColors = new PixelColors(fourPixel, squareOffset);
		
		if (fourPixelColors.red < 5){
			return true;
		}	
		return false;
	}
	
	private static boolean numberIsFive(Pixel squareOffset){
		PixelColors fivePixelColors = new PixelColors(fivePixel, squareOffset);
		
		if (fivePixelColors.red < 200 && fivePixelColors.green < 10 &&
				fivePixelColors.blue < 10){
			return true;
		}
		return false;
	}
	
	private static boolean numberIsSix(Pixel squareOffset){
		PixelColors sixPixelColors = new PixelColors(sixPixel, squareOffset);
		
		if (sixPixelColors.red < 10 && sixPixelColors.green > 100){
			return true;
		}
		return false;
	}
	
	private static boolean numberIsSeven(Pixel squareOffset){
		PixelColors sevenPixelColors = new PixelColors(sevenPixel, squareOffset);
		
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
	
	public PixelColors(Pixel identityPixel, Pixel squareOffset) {
		Pixel realPixel = getRealPixel(identityPixel, squareOffset);
		BufferedImage boardImage = Board.getBoardImage();
		Color pixelColors = new Color(boardImage.getRGB(realPixel.x, realPixel.y));
		setPixelColors(pixelColors);
	}
	
	private Pixel getRealPixel(Pixel identityPixel, Pixel squareOffset){
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