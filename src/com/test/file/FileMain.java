package com.test.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class FileMain {

	public static void main(String[] args) {

		FileMenu.displayFileMenu();

	}

}

class FileMenu {
	public static void displayFileMenu() {
		String folderName = "/home/visachendrayang/main";
		boolean flag = false;
		Scanner in = new Scanner(System.in);
		System.out.println("****** File Operations *******");
		System.out.println("***** Visalatchi Chendrayan ********");
		System.out.println("**************************************");
		while (!flag) {
			System.out.println("1. List /main directory");
			System.out.println("2. File Operations(Add,Search,Delete)");
			System.out.println("3. Exit");
			System.out.println("Enter the option to continue:");
			int option = in.nextInt();
			switch (option) {
			case 1:
				FileOperations.listFiles(folderName);
				break;
			case 2:
				displayFileOperationsMenu(folderName);
				break;
			case 3:
				flag = true;
				break;
			default:
				System.out.println("Enter the correct option from (1 to 3).......");
			}

		}
	}

	private static void displayFileOperationsMenu(String dirName) {
		boolean flag = false;
		Scanner in = new Scanner(System.in);

		String fileName = "";
		while (!flag) {
			System.out.println("File Operations");
			System.out.println("1.Add File");
			System.out.println("2.Delete File");
			System.out.println("3.Search File");
			System.out.println("4.exit");
			System.out.println("Enter the option to continue(1-4):");
			int choice = in.nextInt();

			switch (choice) {
			case 1:
				System.out.println("Enter the file name to add:");
				fileName = in.next();
				FileOperations.addFile(dirName, fileName);
				break;
			case 2:
				System.out.println("Enter the file name to delete:");
				fileName = in.next();
				FileOperations.deleteFile(dirName, fileName);
				break;
			case 3:
				System.out.println("Enter the file name to search:");
				fileName = in.next();
				boolean res=FileOperations.searchFile(dirName, fileName);
				System.out.println("File search "+((res)?"successfull":"unsuccessfull"));
				break;
			case 4:
				flag = true;
				break;
			default:
				System.out.println("Enter the correct option(1-4)");
			}
		}

	}
}

class FileOperations {
	public static boolean createDirectoryIfNotExists(String dirName) {
		File file = new File(dirName);
		boolean flag = false;
		if (!file.exists()) {
			flag = file.mkdir();
			if (flag) {
				System.out.println(dirName + " created successfully.");
			} else {
				System.out.println(dirName + " not created successfully");
			}
		}
		return flag;

	}

	public static void listFiles(String dirName) {
		createDirectoryIfNotExists(dirName);
		File[] files = new File(dirName).listFiles();
		Arrays.sort(files);
		for (File file : files) {
			if (file.isDirectory()) {
				System.out.println("Directory:" + file.getName());
				listFiles(file.getPath());
			} else {
				System.out.println("File:" + file.getName());
			}
		}
	}

	public static void deleteFile(String dirName, String fileName) {

		File file = new File(dirName, fileName);
		System.out.println(file.getPath());
		if (file.exists()) {
			if (file.delete()) {
				System.out.println(fileName + " deleted successfully");
			} else {
				System.out.println(fileName + " not deleted");
			}
		} else {
			System.out.println(fileName + " not exists");
		}
	}
    public static boolean searchFile(String dirName,String fileName) {
    	File[] files = new File(dirName).listFiles();
    	boolean flag=false;
    	for(File file:files) {
    		if(file.isDirectory()) {
    			searchFile(file.getPath(), fileName);
    		}
    		if(file.getName().startsWith(fileName)) {
    			flag=true;
    			System.out.println("Found file:"+file.getName());
    		}
    	}
		return flag;
    }
	public static void addFile(String dirName, String fileName) {
		createDirectoryIfNotExists(dirName);
		File file = new File(dirName, fileName);
		Scanner in = new Scanner(System.in);
		try {
			boolean flag = file.createNewFile();
			if (flag) {
				System.out.println(fileName + " created successfully");
			} else {
				System.out.println(fileName + " already exists");
			}
			System.out.println("Do you wish to add content.Enter y or n:");
			String choice = in.next();
			in.nextLine();
			if (choice.equalsIgnoreCase("y")) {
				System.out.println(file.getName());
				System.out.println("Enter the file content to be added:");
				String content = in.nextLine();
				
				FileWriter fileWriter =new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(fileWriter);
				bw.write(content);
				bw.close();
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
