package letsrock;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.carleton.comp4601.cf.dao.SimpleDataAccessObject;

public class PartyTimeSVD {
	
	
	File file;
	private int[][] ratings;
	private String[] items;
	private String[] users;
	
	public PartyTimeSVD(File file) {
		this.file = file;
	}
	
	//Read in a file
	public boolean input() throws FileNotFoundException {
		boolean okay = true;
		
		Scanner s = new Scanner(file);
		int nUsers = s.nextInt();
		int nItems = s.nextInt();
		
		users = new String[nUsers];
		for (int i = 0; i < nUsers; i++)
			users[i] = s.next();
		items = new String[nItems];
		for (int j = 0; j < nItems; j++)
			items[j] = s.next();
		
		ratings = new int[nUsers][nItems];
		for (int i = 0; i < nUsers; i++) {
			for (int j = 0; j < nItems; j++) {
				ratings[i][j] = s.nextInt();
			}
		}
					
		s.close();
		return okay;
	}
	
	
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		
		buf.append("SimpleDataAccessObject\n\n");
		for (String u : users) {
			buf.append(u);
			buf.append(" ");
		}
		buf.append("\n");
		for (String i : items) {
			buf.append(i);
			buf.append(" ");
		}		
		buf.append("\n");
		for (int i = 0; i < users.length; i++) {
			for (int j = 0; j < items.length; j++) {
				if (ratings[i][j] == -1)
					buf.append("?");
				else
					buf.append(ratings[i][j]);
				buf.append(" ");
			}
			buf.append("\n");
		}
		return buf.toString();
	}
	
	//main
	public static void main(String[] args) throws FileNotFoundException {
		PartyTimeSVD svd = new PartyTimeSVD(new File("test.txt"));
		sdao.input();
		System.out.println(sdao);
		System.out.println("User Based");
		sdao.userBased();
		System.out.println("\nItem Based");
		sdao.itemBased();
		System.out.println("=====================================");
		
		sdao = new PartyTimeSVD(new File("test2.txt"));
		sdao.input();
		System.out.println(sdao);
		System.out.println("User Based");
		sdao.userBased();
		System.out.println("\nItem Based");
		sdao.itemBased();
		System.out.println("=====================================");
		
		sdao = new PartyTimeSVD(new File("test3.txt"));
		sdao.input();
		System.out.println(sdao);
		System.out.println("User Based");
		sdao.userBased();
		System.out.println("\nItem Based");
		sdao.itemBased();
		System.out.println("=====================================");
		
	}
	
	

}
