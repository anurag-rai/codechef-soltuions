package core.codechef.medium;

/**
 *  https://www.codechef.com/problems/PRIME1
 * 
 *  Shridhar wants to generate some prime numbers for his cryptosystem. Help him! 
 *  Your task is to generate all prime numbers between two given numbers.
 *  
 *  Input
 *  The first line contains t, the number of test cases (less then or equal to 10).
 *  Followed by t lines which contain two numbers m and n (1 <= m <= n <= 1000000000, n-m<=100000) 
 *  separated by a space.
 *  
 *  Output
 *  For every test case print all prime numbers p such that m <= p <= n, one number per line. 
 *  Separate the answers for each test case by an empty line.
 *  
 *  Example
 *  Input:
 *  2
 *  1 10
 *  3 5
 *  
 *  Output:
 *  2
 *  3
 *  5
 *  7
 *  
 *  3
 *  5
 *  Warning: large Input/Output data, be careful with certain languages 
 *  (though most should be OK if the algorithm is well designed)
 *  
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Prime1 {
	
	public static int[] prime_numbers;
	public static int index;
	
	public static void main(String[] args)throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine().trim());
		
		// here 1000000000 is the largest number to test
		// so generate all prime numbers below sqrt(1000000000)
		//
		prime_numbers = new int[(int) Math.round(Math.sqrt(1000000000))];
		index = 0;
		
		seive( (int) Math.round(Math.sqrt(1000000000)) );
		/*
		 * print the prime numbers below sqrt(1000000000)
		 * for ( int i = 0; i < index; i++ )  {
		 * System.out.println(prime_numbers[i]);
		 * }
		 * 
		 */
		
		for ( int t = 0; t < T; t++ ) {
			String[] arr = br.readLine().trim().split(" ");
			long m = Long.parseLong(arr[0]);
			long n = Long.parseLong(arr[1]);		
			generatePrime(m,n);
			System.out.println();
		}
	}
	
	public static void generatePrime(long m, long n) {
		for ( long i = m; i <= n; i++ ) {
			//System.out.println("Checking: " + i);
			if ( check(i) )
				System.out.println(i);
		}
	}
	
	public static boolean check(long number) {
		if ( number <= 1) {
			return false;
		}
		for ( int i = 0; i < index; i++ ) {
			if ( prime_numbers[i] > Math.sqrt(number) )
				break;
			if ( number % prime_numbers[i] == 0 )
				return false;
		}
		return true;
	}
	
	public static void seive(int n) {
	    boolean[] prime = new boolean[n+1];
	    for ( int i = 0; i < n+1; i++ ) {
	    	prime[i] = true;
	    }
	    for (int p = 2; p * p <= n; p++) {
	        if (prime[p] == true)
	        {
	            for (int i = p*2; i<=n; i += p)
	                prime[i] = false;
	        }
	    }
	    for (int p = 2; p <= n; p++) {
	       if (prime[p]) {
	    	   prime_numbers[index] = p;
	    	   index++;
	       }
	    }
	}
}
