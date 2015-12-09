package minesweeper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class HashMapsOnDisk {
	
	public static <E> HashMap<Integer, E> getHashMap(String file){
		HashMap<Integer, E> map = null;
		
		try {
			map = tryToGetHashMap(file);
		} 
		catch (Exception e) {
			System.out.println("Error when getting hashmap");
		}
		
		return map;
	}
	
	@SuppressWarnings({ "resource", "unchecked" })
	private static <E> HashMap<Integer, E> tryToGetHashMap(String file) throws Exception{
		FileInputStream inputFile = new FileInputStream(file);
		ObjectInputStream inputObject = new ObjectInputStream(inputFile);
		HashMap<Integer, E> map = (HashMap<Integer, E>) inputObject.readObject();
		return map;
	}
	
	public static <E> void setHashMap(String file, HashMap<Integer, E> map){
		try {
			tryToSetHashMap(file, map);
		}
		catch (Exception e) {
			System.out.println("Error setting hashmap");
		}
	}
	
	@SuppressWarnings("resource")
	private static <E> void tryToSetHashMap(String file, HashMap<Integer, E> map) throws Exception {
		FileOutputStream fileOutput = new FileOutputStream(file);
		ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
		objectOutput.writeObject(map);
	}
}