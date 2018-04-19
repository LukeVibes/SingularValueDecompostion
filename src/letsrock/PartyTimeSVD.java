package letsrock;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Jama.Matrix;
import Jama.SingularValueDecomposition;;
public class PartyTimeSVD {
	
	
	File file;
	private double[][] ratings;
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
		
		ratings = new double[nUsers][nItems];
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
	
	
	public void algorithm2(){
		Matrix r = new Matrix(ratings);
		
		int[] rows = {0};
		Matrix alice = r.getMatrix(rows, 0, r.getColumnDimension()-2);
		alice.print(alice.getColumnDimension(), 3);
		
		int subRowStart = 1;
		int subRowEnd   = 4;
		int[] subCols   = {0, 1, 2, 3};
		r = r.getMatrix(subRowStart,subRowEnd, subCols);
		
		Matrix u = r.svd().getU();
		Matrix v = r.svd().getV();
		Matrix s = r.svd().getS();
		
		u.print(r.getColumnDimension(), 3);
		v.print(r.getColumnDimension(), 3);
		s.print(r.getColumnDimension(), 3);
		
		Matrix v2 = v.getMatrix(0, u.getRowDimension()-1, 0, 1);
		Matrix s2 = s.getMatrix(0, s.getRowDimension()-1, 0, 1);
		v2.print(v2.getColumnDimension(), 3);
		s2.print(s2.getColumnDimension(), 3);
		
		Matrix alice2d = (alice.times(v2)).times(s2.inverse());
		alice2d.print(alice2d.getColumnDimension(), 3);
	}
	
	
	public void algorithm(){
		//Step One initalize Matrix
		Matrix r = new Matrix(ratings);
		
		int subRowStart = 1;
		int subRowEnd   = 4;
		int[] subCols   = {0, 1, 2, 3};
		
		r = r.getMatrix(subRowStart,subRowEnd, subCols);
		double[][] newArray = r.getArray();
		
		int l1 = newArray.length;
		int l2 = newArray[0].length;
		
		for(int i =0; i<l1; i++){
			for(int j=0; j<l2; j++){
				System.out.print(newArray[i][j] + " ");
			}
			System.out.println();
		}
		
		
		SingularValueDecomposition d = r.svd();
		
		Matrix u = d.getU().getMatrix(0, r.getRowDimension()-1, 0, 1);
		Matrix v = d.getV().getMatrix(0, r.getRowDimension()-1, 0, 1).transpose();
		Matrix s = d.getS().getMatrix(0, 1, 0, 1);
		
//		u.print(u.getColumnDimension(), 3);
//		s.print(v.getColumnDimension(), 3);
//		v.print(v.getColumnDimension(), 3);

		//v = v.transpose();
		
		Matrix aliceU = u.getMatrix(0, 0, 0, 1);
		Matrix aliceV = v.getMatrix(0, 1, 4, 4);
		Matrix aliceS = s.getMatrix(0, 1, 0, 1); 
		
		aliceU.print(aliceU.getColumnDimension(), 3);
		aliceV.print(aliceV.getColumnDimension(), 3);
		aliceS.print(aliceS.getColumnDimension(), 3);
		
		double userAvg = average(0);
		
		Matrix answerP1 = (aliceU.times(aliceS)).times(aliceV);
		System.out.println("svd pre: ");
		answerP1.print(answerP1.getColumnDimension(), 3);
		
		double answerP2 = answerP1.get(0, 0);
		
		
		double answer = userAvg + answerP2;
		System.out.println("\nANSWER: " + answer);
	}
	
	
	private double svd() {	
		
		
		Matrix f = new Matrix(ratings);
		
		//Skip Alice
		int[] getRow = {1,2,3,4};
		int[] getCol = {0,1,2,3};
		//Matrix m = f.getMatrix(getRow, getCol);

		//Matrix without Alice, without uknown
		double[][] classEx = {
			{3, 1, 2, 3},
			{4, 3, 4, 3},
			{3, 2, 1, 5},
			{1, 6, 5, 2}
		};
		Matrix m = new Matrix(classEx);
		//m.print(2, 1);
		Matrix u = m.svd().getU();
		Matrix s = m.svd().getS();
		Matrix v = m.svd().getV();
		u.print(4, 4);
		//s.print(4, 4);
		//v.print(4, 4);
		int[] vCol = {0,1};
		int[] vRow = {0};
		int[] uCol = {3};
		Matrix ukAlice = v.getMatrix(vRow, vCol);
		ukAlice.print(4, 4);
		Matrix sigmak = s.getMatrix(vCol, vCol);
		sigmak.print(4, 4);
		Matrix vkEPL = u.getMatrix(uCol, vCol);
		Matrix vkEPLtranspose = vkEPL.transpose();
		vkEPLtranspose.print(4, 4);
		//int[] vkRow = {1,2,3,4};
		//int[] vkCol = {4};
		//Matrix itemUnknown = f.getMatrix(vkRow, vkCol);
		//itemUnknown.print(4, 4);
		//int[] ukRow = {0,1,2,3};
		//Matrix uk = u.getMatrix(ukRow, vCol);
		//uk.print(4, 4);
		//Matrix sigmakInverse = sigmak.inverse();
		//sigmakInverse.print(4, 4);
		//Matrix vkUnknown2D = uk.times(sigmakInverse);
		//vkUnknown2D.print(4, 4);
		//Matrix vkItems = itemUnknown.transpose().times(vkUnknown2D);
		//vkItems.print(4, 4);
		//ukAlice.print(4, 4);
		//sigmak.print(4, 4);
		//double r = rAvg(0) + (ukAlice.times(sigmak).times(vkItems.transpose())).get(0, 0);
		double r = average(0) + (ukAlice.times(sigmak).times(vkEPLtranspose)).get(0, 0);
		System.out.println("r:"+r);
		return 0;
	}
	
	
	public double average(int user){
		double avg = 0;
		int count = 0;
		
		for(int i = 0; i< ratings[user].length; i++){
			if(ratings[user][i] != -1){
				avg += ratings[user][i];
				count++;
			}
		}
		
		
		avg = avg/count;
		System.out.println("avg: " + avg);
		return avg;
	}
	
	//main
	public static void main(String[] args) throws FileNotFoundException {
		PartyTimeSVD svd = new PartyTimeSVD(new File("test.txt"));
		svd.input();
		System.out.println(svd);
		svd.svd();
		System.out.println("=====================================");
		
		svd = new PartyTimeSVD(new File("test2.txt"));
		svd.input();
		System.out.println(svd);
	
		
		System.out.println("=====================================");
		
		svd = new PartyTimeSVD(new File("test3.txt"));
		svd.input();
		System.out.println(svd);
		
		System.out.println("=====================================");
		
	}
	
	

}
