package minesweeper;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ObsoleteFunctions {

	
	private static void saveAllSquareImages(int end) throws AWTException{
		int square = 0;
		for (Rectangle rect : imageCoordinates.values()){
			System.out.println(square);
			robot.saveScreenCapture(rect, square);
			square++;
			
			if (square >= end){
				break;
			}
		}
	}
	
	
	public void saveScreenCapture(int square) {
		BufferedImage screenCapture = getImageOfSquare(square);
		String path = "C:/Users/home/Desktop/screenshots/" + square + ".png";
		File outputFile = new File(path);
		String fileType = "png";
		
		try {ImageIO.write(screenCapture, fileType, outputFile);}
		catch (Exception e) {System.out.println("error savnig screencapture");}
	}
	
	public Rectangle getRectangle(int element) {
		int elementX_help = element % 30;
		int actualHeight = 29;
		int actualWidth = 28;
		int startX = 61;
		int startY = 103;
		int width = 18;
		int height = 20;
		int offsetX = (elementX_help * actualWidth) + (elementX_help/3) + (elementX_help/20);
		int offsetY = ((element /30) * actualHeight) - (element /30) + (element /90);
		
		Rectangle rect = new Rectangle(startX + offsetX, startY + offsetY, width, height);
		return rect;
	}
	
	
	
	// new way of getting perfect rectangles
//	
//	int x = 83-33;
//	int y = 186;
//	int width = 0;
//	int height = 32;
//	int meh = 32;
//	int mel = 33;
//	
//	
//	
//	
//	for (int square = 0; square < 480; square++){
//		
//		int rest = square % 30;
//		
//		System.out.println("REST:"+rest);
//		
//		if (rest == 0 && square > 1){
//			System.out.println("ny linje");
//			x = 83-33;
//			meh = 32;
//			mel = 33;
//			
//			
//			if (square > 29 && square < 59){
//				y += 32;
//			}
//			else if (square > 59 && square < 89){
//				y += 33;
//			}
//			else if (square > 89 && square < 119){
//				y += 32;
//			}
//			else if (square > 119 && square < 149){
//				y += 33;
//			}
//			else if (square > 149 && square < 179){
//				y += 32;
//			}
//			else if (square > 179 && square < 209){
//				y += 33;
//			}
//			else if (square > 209 && square < 239){
//				y += 32;
//			}
//			else if (square > 239 && square < 269){
//				y += 33;
//			}
//			else if (square > 269 && square < 299){
//				y += 32;
//			}
//			else if (square > 299 && square < 329){
//				y += 33;
//			}
//			else if (square > 329 && square < 359){
//				y += 32;
//			}
//			else if (square > 359 && square < 389){
//				y += 33;
//			}
//			else if (square > 389 && square < 419){
//				y += 32;
//			}
//			else if (square > 419 && square < 449){
//				y += 33;
//			}
//			else if (square > 449 && square < 479){
//				y += 32;
//			}
//			
//		}
//		
//		
//		if (rest < 13 && rest % 2 == 0){
//			System.out.println("first");
//			x += 33;
//			width = meh;
//			System.out.println(width);
//		}
//		else if (rest < 13){
//			x += 32;
//			width = mel;
//		}
//		
//		else if (rest == 13){
//			mel++;
//			meh++;
//			x += 32;
//			width = meh;
//		}
//		
//		
//		else if (rest == 14 && rest % 2 == 0){
//			x += 33;
//			width = meh;
//		}
//		
//	
//		else  if (rest == 15){
//			x += 33;
//			mel -= 2;
//			width = mel;
//		}
//		
//
//		else if (rest == 16){
//			x += 32;
//			width = meh;
//		}
//		
//		else if (rest == 17){
//			x += 33;
//			width = mel;
//		}
//		
////		else if (square == 18){
////			x += 32;
////			width = mel;
////		}
////		
////		else if (square == 19){
////			x += 33;
////			width = meh;
////		}
////		
////		else if (square == 20){
////			x += 32;
////			width = mel;
////		}
//		
//		
//		else if (rest == 18){
//			x += 32;
//			width = meh;
//		}
//		
//		
//		else if (rest > 18 && rest < 26 && rest % 2 == 0){
//			x += 32;
//			width = meh;
//		}
//		else if (rest > 18 && rest < 26 && rest % 2 != 0){
//			x += 33;
//			width = mel;
//		}
//		
//		
//		else if (rest > 25 && rest % 2 == 0){
//			x += 32;
//			width = meh;
//		}
//		else if (rest > 25 && rest % 2 != 0){
//			x += 33;
//			width = mel;
//		}
//		
//		
//		Rectangle rect = screen.testfunc(x, y, width, height, square);
		
		
//		imageCoordinates.put(square, rect);
		
//	}
	
	
//	ObjectsOutAndIn.setHashMap("imageCoordinates.ser", imageCoordinates);
	
	
	
	
	public Rectangle testfunc(int x, int y, int width, int height, int square){
		
		
		System.out.println("x:"+x);
		System.out.println("y:"+y);
		System.out.println("width:"+width);
		System.out.println("height:"+height);
		System.out.println("square:"+square);
		System.out.println();
		
		
		
		Rectangle rect = new Rectangle(x, y, width, height);
		BufferedImage image = robot.createScreenCapture(rect);
		
		String path = "C:/Users/home/Desktop/screenshots/" + square + ".png";
		File outputFile = new File(path);
		String fileType = "png";
		
		try {ImageIO.write(image, fileType, outputFile);}
		catch (Exception e) {System.out.println("error savnig screencapture");}
		
		return rect;
		
	}
	
	
	
	public static void printPixekColors(int x, int y){
		
		String[] liste = {"1_0", "1_1", "1_2",
				"2_0", "2_1", "2_2",
				"3_0", "3_1", "3_2",
				"4_0", "4_1", "4_2",
				"5_0", "5_1", "5_2",
				"6_0", "6_1", "6_2",
				"7_0", "7_1", "7_2",
				"empty_0", "empty_1", "empty_2",
				"flag_0", "flag_1", "flag_2", 
				"nonClicked_0", "nonClicked_1", "nonClicked_2"};
		
		int i = 0;
		for (String value : liste){
			screen.getValue(value, x, y);
			i++;
			
			if (i % 3 == 0){
				System.out.println();
			}
		}
	}
	
	
	
	HashMap<Integer, Tuple> center = new HashMap<Integer, Tuple>();
	
	for(int square = 0; square < 480; square++){
		
		Rectangle rect = screen.getImageOfSquare(square);
		
		int x = (int) rect.getCenterX();
		int y = (int) rect.getCenterY();
		
		System.out.println(x);
		System.out.println(y);
				
		Tuple tuple = new Tuple(x, y);
		
		center.put(square, tuple);
		
		
		
		
	}
	
	
	ObjectsOutAndIn.setHashMap("centerOfSquares.ser", center);
	
	
	
	public static void setSquareTupleMap(){
		HashMap<Integer, Tuple> squareCoordinates = new HashMap<Integer, Tuple>();
		
		int x = -33;
		int y = 0;
		int width = 0;
		int height = 32;
		int meh = 32;
		int mel = 33;
		
		
		for (int square = 0; square < 480; square++){
			
			int rest = square % 30;
			
			System.out.println("REST:"+rest);
			
			if (rest == 0 && square > 1){
				System.out.println("ny linje");
				x = 0;
				meh = 32;
				mel = 33;
				
				
				if (square > 29 && square < 59){
					y += 32;
				}
				else if (square > 59 && square < 89){
					y += 33;
				}
				else if (square > 89 && square < 119){
					y += 32;
				}
				else if (square > 119 && square < 149){
					y += 33;
				}
				else if (square > 149 && square < 179){
					y += 32;
				}
				else if (square > 179 && square < 209){
					y += 33;
				}
				else if (square > 209 && square < 239){
					y += 32;
				}
				else if (square > 239 && square < 269){
					y += 33;
				}
				else if (square > 269 && square < 299){
					y += 32;
				}
				else if (square > 299 && square < 329){
					y += 33;
				}
				else if (square > 329 && square < 359){
					y += 32;
				}
				else if (square > 359 && square < 389){
					y += 33;
				}
				else if (square > 389 && square < 419){
					y += 32;
				}
				else if (square > 419 && square < 449){
					y += 33;
				}
				else if (square > 449 && square < 479){
					y += 32;
				}
				
				
				x = 0;
				
			}
			else{
				
				if (rest < 13 && rest % 2 == 0){
					System.out.println("first");
					x += 33;
					width = meh;
					System.out.println(width);
				}
				else if (rest < 13){
					x += 32;
					width = mel;
				}
				
				else if (rest == 13){
					mel++;
					meh++;
					x += 32;
					width = meh;
				}
				
				
				else if (rest == 14 && rest % 2 == 0){
					x += 33;
					width = meh;
				}
				
				
				else  if (rest == 15){
					x += 33;
					mel -= 2;
					width = mel;
				}
				
				
				else if (rest == 16){
					x += 32;
					width = meh;
				}
				
				else if (rest == 17){
					x += 33;
					width = mel;
				}
				
				
				else if (rest == 18){
					x += 32;
					width = meh;
				}
				
				
				else if (rest > 18 && rest < 26 && rest % 2 == 0){
					x += 32;
					width = meh;
				}
				else if (rest > 18 && rest < 26 && rest % 2 != 0){
					x += 33;
					width = mel;
				}
				
				
				else if (rest > 25 && rest % 2 == 0){
					x += 32;
					width = meh;
				}
				else if (rest > 25 && rest % 2 != 0){
					x += 33;
					width = mel;
				}
				
			}
			
			Tuple tuple = new Tuple(x, y);
			
			
			squareCoordinates.put(square, tuple);
		}
		
		ObjectsOutAndIn.setHashMap("squareCoordinates.ser", squareCoordinates);
	}
	
	
	public static void printPixekColors(int x, int y){
		
		String[] liste = {"1_0", "1_1", "1_2",
				"2_0", "2_1", "2_2",
				"3_0", "3_1", "3_2",
				"4_0", "4_1", "4_2",
				"5_0", "5_1", "5_2",
				"6_0", "6_1", "6_2",
				"7_0", "7_1", "7_2",
				"flag_0", "flag_1", "flag_2", 
				"empty_0", "empty_1", "empty_2", "empty_3",
				"nonClicked_0", "nonClicked_1", "nonClicked_2", "nonClicked_3",
				};
		
		int i = 0;
		for (String value : liste){
			screen.getValue(value, x, y);
			i++;
			
			if (i % 3 == 0){
				System.out.println();
			}
		}
	}
	
	
	public static void printSquareCoordinates(){
		HashMap<Integer, Tuple> squareCoordinates = ObjectsOutAndIn.getHashMap("squareCoordinates.ser");
		
		
		for (int tuple : squareCoordinates.keySet()){
			
			System.out.print("x:"+ squareCoordinates.get(tuple).x+
					" y:"+squareCoordinates.get(tuple).y);
			System.out.println();
		}
	}
	
	public BufferedImage imageYo(String value){
		String path = "C:/Users/home/Desktop/newscres/"+ value +".png";
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("fant ikke bilde");
		}

		return img;
		
	}
	
	public void getValue(String value, int x, int y){
		BufferedImage image = imageYo(value);
		
		
		Color colors = new Color(image.getRGB(x, y));
		
		System.out.println(colors);
		
	}
	
}
