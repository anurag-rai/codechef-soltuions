package core.codechef.medium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Numfact {
	
	public static void main(String[] args)throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine().trim());
		
		String[] arr = new String[10];
		int[] val = new int[10];
		
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
