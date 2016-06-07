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

/**
 * here 1000000000 is the largest number to test
 * so generate all prime numbers below sqrt(1000000000)
 * Idea:
 * To check if a number 'x' is prime, only divide it by prime numbers
 * less than sqrt(x)
 * There's no point in checking for divisibility against non-primes, 
 * since if x divisible by non-prime y, then there exists a prime p < y 
 * such that x divisible by p, since we can write y as a product of primes. 
 * For example, 12 is divisible by 6, but 6 = 2 * 3, which means that 12 is also divisible by 2 or 3.
 * 
 * http://stackoverflow.com/questions/3220907/efficient-algorithm-to-get-primes-between-two-large-numbers
 * 
 * @author Anurag Rai
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
		
		//store all prime numbers less than sqrt(1000000000). Size unknown but less than sqrt(1000000000).
		prime_numbers = new int[(int) Math.round(Math.sqrt(1000000000))];
		//'index' stores the number of prime numbers in less than sqrt(1000000000)
		index = 0;
		//generate all prime numbers less than sqrt(1000000000)
		seive( (int) Math.round(Math.sqrt(1000000000)) );
		
		// print the prime numbers below sqrt(1000000000)
		// for ( int i = 0; i < index; i++ )  {
		// System.out.println(prime_numbers[i]);
		// }
		
		//for every test case
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
	
	/**
	 * @param number
	 * 			the number to check if it is a prime
	 * 
	 * @return true if the number is prime else false		
	 */
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
	
	/**
	 * Sieve of Eratosthenes
	 * 
	 * @param n
	 * 		generate all prime-numbers till this limit.
	 */
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
