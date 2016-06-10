package core.codechef.medium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Mixtures {
	public static void main(String[] args)throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String[] arr = new String[100];
		int[] colors = new int[100];
		int N = -1;
		while ( true ) {
			for ( int i = 0; i < 100; i++ ) {
				colors[i] = 0;
			}
			try {
				N = Integer.parseInt(br.readLine().trim());
			}
			catch (Exception e) {
				System.exit(0);
			}
			arr = br.readLine().trim().split(" ");
			for ( int i = 0; i < N; i++ ) {
				colors[i] = Integer.parseInt(arr[i]);
			}			
			System.out.println(calculate(colors, N));
		}		
	}
	
	public static int calculate(int[] colors, int length) {
		
		//for 1 based indexing
		int[][] mixture = new int[length + 1][length + 1];
		int[][] smoke 	= new int[length + 1][length + 1];
		
		for ( int i = 0; i < length; i++ ) {
			mixture[i+1][i+1] = colors[i];
		}
		
		int j = 0, q = 0;
		for (int L = 2; L <= length; L++ ) {
	        for ( int i = 1; i <= length - L + 1; i++) {
	        	j = i + L - 1;
	        	smoke[i][j] = Integer.MAX_VALUE; 
	        	for (int k = i; k <= j - 1; k++) {
	        		q =smoke[i][k] + smoke[k+1][j] + (mixture[i][k] * mixture[k+1][j]);
	        		if ( q < smoke[i][j] ) {
	        			smoke[i][j] = q;
	        			mixture[i][j] = (mixture[i][k] + mixture[k+1][j]) % 100;
	        		}		
	        	}
	        }
		}
		return smoke[1][length];
	}
}
