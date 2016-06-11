package core.codechef.medium;

/**
 * https://www.codechef.com/problems/MIXTURES
 *  Harry Potter has n mixtures in front of him, arranged in a row.Each mixture
 *  has one of 100 different colors (colors have numbers from 0 to 99).
 *  He wants to mix all these mixtures together. At each step, he is going to
 *  take two mixtures that stand next to each other and mix them together, and
 *  put the resulting mixture in their place.
 *  
 *  When mixing two mixtures of colors a and b, the resulting mixture will have
 *  the color (a+b) mod 100.
 *  Also, there will be some smoke in the process. The amount of smoke generated
 *  when mixing two mixtures of colors a and b is a*b.
 *  Find out what is the minimum amount of smoke that Harry can get when mixing
 *  all the mixtures together.
 *  
 *  Input
 *  There will be a number of test cases in the input.
 *  The first line of each test case will contain n, the number of mixtures, 1 <= n <= 100.
 *  The second line will contain n integers between 0 and 99 -
 *  the initial colors of the mixtures.
 *  
 *  Output
 *  For each test case, output the minimum amount of smoke.
 *  
 *  Example
 *  
 *  Input:
 *  2
 *  18 19
 *  3
 *  40 60 20
 *  
 *  Output:
 *  342
 *  2400
 *  
 *  In the second test case, there are two possibilities:
 *  first mix 40 and 60 (smoke: 2400), getting 0, then mix 0 and 20 (smoke: 0);
 *  total amount of smoke is 2400
 *  first mix 60 and 20 (smoke: 1200), getting 80, then mix 40 and 80 (smoke: 3200);
 *  total amount of smoke is 4400 
 *  
 *  The first scenario is the correct approach since it minimizes the amount of smoke produced. 
 */

/**
 * Variation of Matrix Multiplication problem.
 * 
 * Keep track of not only the smoke, but the mixtures as well.
 * This is because the value of the nth mixture will change if you use it.
 * (Becomes (a + b) % 100.
 * So for every kth split, keep track of the value of the mixture if split at 
 * kth index.
 * (here kth split refers to the 'k' in the method of matrix multiplication)
 */

/**
 * @author Anurag Rai
 */
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
