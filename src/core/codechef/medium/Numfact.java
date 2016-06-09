package core.codechef.medium;

/**
 * https://www.codechef.com/problems/NUMFACT
 * 
 * Alice has learnt factorization recently. Bob doesn't think she has learnt
 * it properly and hence he has decided to quiz her. Bob gives Alice a very
 * large number and asks her to find out the number of factors of that number.
 * To make it a little easier for her, he represents the number as a product
 * of N numbers. Alice is frightened of big numbers and hence is asking you
 * for help. Your task is simple. Given N numbers, you need to tell the number
 * of distinct factors of the product of these N numbers.
 * 
 * Input:
 * First line of input contains a single integer T, the number of test cases.
 * Each test starts with a line containing a single integer N.
 * The next line consists of N space separated integers (Ai).
 * 
 * Output:
 * For each test case, output on a separate line the total number of factors of the
 * product of given numbers.
 * 
 * Constraints:
 * 1 ≤ T ≤ 100
 * 1 ≤ N ≤ 10
 * 2 ≤ Ai ≤ 1000000
 * 
 * Example:
 * 
 * Input:
 * 3
 * 3
 * 3 5 7
 * 3
 * 2 4 6
 * 2
 * 5 5
 * 
 * Output:
 * 8
 * 10
 * 3
 */

/**
 * 
 * http://stackoverflow.com/questions/2844703/algorithm-to-find-the-factors-of-a-given-number-shortest-method
 * http://www.geeksforgeeks.org/print-all-prime-factors-of-a-given-number/
 * 
 * Here I use a HashMap to save the power(exponent) of the prime factor.
 * 
 * 
 * Editorial [https://discuss.codechef.com/questions/15943/numfact-editorial]
 * Quick Explanation:
 * 
 * We can factorize each one of the N given numbers into its prime factors.
 * Then we find the number of occurrences of each prime factor, say they are
 * a1, a2,...aK, if we have K distinct prime factors. Our answer is simply: (a1+1)(a2+1)(...)*(aK+1).
 * 
 * Detailed Explanation:
 * 
 * This problem relies on some knowledge of divisor function. Divisor functions returns the number
 * of positive and distinct divisors of a number. Let's call it d(x).
 * 
 * Some properties of the divisor function:
 * 
 * We now look into some important properties of the divisor function:
 * 
 * For a prime number p, we have d(p) = 2, as there are only two numbers which divide a prime number:1 and itself.
 * 
 * Now, it's a known fact that this function is multiplicative but not completely multiplicative.
 * This means that if two numbers, say, a and b are there such that gcd(a, b) = 1, then the following 
 * holds: d(a*b) = d(a)*d(b).
 * 
 * This allows us to deduce the important relationship, that is the key of solving this problem:
 * 
 * For a prime number, p, we have: d(p^n) = n+1.
 * 
 * Now, it's easy to understand that all we need to do is to factorize all the N given numbers into
 * its prime factors, and, for each prime factor we also need to count how many times it appears
 * (that is, we need to know the exponent of each prime factor).
 * 
 * Once we have this count with us (which can be done using integer factorization and for example,
 * the set and map data structures, one to guarantee uniqueness of the factors and the other to save
 * the number of occurences for each unique prime factor), all we need to do is to multiply all these
 * numbers plus one together and we will obtain our answer.
 * 
 * As an example, consider the number:
 * 504 = 2^3 * 3^2 * 7^1
 * The number of distinct divisors of 504 is then (3+1) * (2+1) * (1+1) = 24.
 */

/**
 * @author Anurag Rai
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Numfact {
	public static void main(String[] args)throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine().trim());
		
		String[] arr = new String[10];	//10 is the max size of input
		int[] val = new int[10];
		
		//for every test case
		for ( int t = 0; t < T; t++ ) {
			
			int N = Integer.parseInt(br.readLine().trim());
			arr = br.readLine().trim().split(" ");
			
			HashMap<Integer, Integer> primeExponent = new HashMap<>();
			
			for ( int i = 0; i < N; i++ ) {
				val[i] = Integer.parseInt(arr[i]);
				findPrimeFactors(primeExponent, val[i]);
			}
			
			long answer = primeExponent.values().stream().mapToLong(Number::longValue).map(i -> i + 1).reduce(1, (a,b) -> a * b);
				
			System.out.println(answer);
		}
	}
	
	// A function to print all prime factors of a given number n
	public static void findPrimeFactors(HashMap<Integer, Integer> primeExponent, int n) {
	    // Print the number of 2s that divide n
	    while ( n % 2 == 0) {
	    	if ( primeExponent.get(2) == null) {
	    		primeExponent.put(2, 1);
	    	} else {
	    		primeExponent.put(2, primeExponent.get(2) + 1);
	    	}
	        n = n/2;
	    }
	 
	    // n must be odd at this point.  So we can skip one element (Note i = i +2)
	    for ( int i = 3; i <= Math.sqrt(n); i = i+2 ) {
	        // While i divides n, print i and divide n
	        while ( n % i == 0) {
	        	if ( primeExponent.get(i) == null) {
		    		primeExponent.put(i, 1);
		    	} else {
		    		primeExponent.put(i, primeExponent.get(i) + 1);
		    	}
	            n = n/i;
	        }
	    }
	 
	    // This condition is to handle the case when n is a prime number
	    // greater than 2
	    if (n > 2) {
	    	if ( primeExponent.get(n) == null) {
	    		primeExponent.put(n, 1);
	    	} else {
	    		primeExponent.put(n, primeExponent.get(n) + 1);
	    	}
	    }
	    	
	}
}
