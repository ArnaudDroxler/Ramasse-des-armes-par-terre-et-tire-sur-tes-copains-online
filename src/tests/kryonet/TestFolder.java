package tests.kryonet;

import java.io.File;

public class TestFolder {

	public static void main(String[] args){
		File folder = new File("pika");
		File[] listOfFiles = folder.listFiles();
		
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        System.out.println("File " + listOfFiles[i].getName());
		      } else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + listOfFiles[i].getName());
		      }
		    }
	}
	
}
