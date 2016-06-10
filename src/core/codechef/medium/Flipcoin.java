package core.codechef.medium;

/**
 * https://www.codechef.com/problems/FLIPCOIN
 * 
 * There are N coins kept on the table, numbered from 0 to N - 1.
 * Initially, each coin is kept tails up. You have to perform two types of operations :
 * 1) Flip all coins numbered between A and B inclusive. This is represented by the command "0 A B"
 * 2) Answer how many coins numbered between A and B inclusive are heads up.
 * This is represented by the command "1 A B".
 *  
 * Input :
 * The first line contains two integers, N and Q. Each of the next Q lines are
 * either of the form "0 A B" or "1 A B" as mentioned above.
 *  
 * Output :
 * Output 1 line for each of the queries of the form "1 A B" containing the
 * required answer for the corresponding query.
 *  
 * Sample Input :
 * 4 7
 * 1 0 3
 * 0 1 2
 * 1 0 1
 * 1 0 0
 * 0 0 3
 * 1 0 3 
 * 1 3 3
 *  
 * Sample Output :
 * 0
 * 1
 * 0
 * 2
 * 1
 *  
 * Constraints :
 * 1 <= N <= 100000
 * 1 <= Q <= 100000
 * 0 <= A <= B <= N - 1
 */

/**
 * https://discuss.codechef.com/questions/50514/flipcoin-the-definite-topic-for-us-to-learn-segtrees
 * 
 * Segment Tree + Lazy Propagation.
 * Take care of update and query methods of the seg tree.
 * 
 */

/**
 * @author Anurag Rai
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Flipcoin {
	public static void main(String[] args)throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] arr = br.readLine().trim().split(" ");
		int N = Integer.parseInt(arr[0]);
		int Q = Integer.parseInt(arr[1]);
		
		//Initially all tails
		//0 = tails 1 = heads
		int[] coins = new int[N];
		//create a seg tree
		SegmentTree seg_tree = new SegmentTree(coins);
		
		//for every query
		for ( int q = 0; q < Q; q++ ) {
			String[] arr1 = br.readLine().trim().split(" ");
			int choice = Integer.parseInt(arr1[0]);
			int x = Integer.parseInt(arr1[1]);
			int y = Integer.parseInt(arr1[2]);
			
			if ( choice == 0 ) {
					seg_tree.updateLazy(0, N-1, x, y, 0);
			}
			if ( choice == 1 ) {
				System.out.println(seg_tree.queryLazy(0, N-1, x, y, 0));
			}
			//System.out.println("TREE==>");
			//seg_tree.printSegTree();
		}
	}
}

class SegmentTree 
{
	public static int tree[]; // The array that stores segment tree nodes
	public static int lazy[];
	
	SegmentTree(int arr[]) {
		int n = arr.length;
		int max_size = 3 * n;		//Max size needed for n leaves
		tree = new int[max_size];
		lazy = new int[max_size];
		constructSTUtil(arr, 0, n - 1, 0);
	}
	
	/**
	 *  A recursive function that constructs Segment Tree for array[start..end].
	 *  
	 * @param arr
	 * 			the array of values
	 * @param start
	 * 			starting index of arr
	 * @param end
	 * 			last index of arr
	 * @param node
	 * 			index of current node in segment tree st
	 * @return value of the node
	 */
	 int constructSTUtil(int arr[], int start, int end, int node)
	 {
	     // If there is one element in array, store it in current node of
	     // segment tree and return
	     if (start == end) {
	         tree[node] = arr[start];
	         return arr[start];
	     }
	     // If there are more than one elements, then recur for left and
	     // right subtrees and store the sum of values in this node
	     int mid = start + (end - start) / 2;
	     tree[node] = 	constructSTUtil(arr, start, mid, node * 2 + 1) + 
	    		 		constructSTUtil(arr, mid + 1, end, node * 2 + 2);
	     return tree[node];
	 }
	
	 /**
	  * Lazy update
	  * 
	  * It can only have two values in this problem
	  * 0 ==> Lazy NOT set
	  * 1 ==> Lazy is set
	  * 
	  */
	 void updateLazy(int start, int end, int qs, int qe, int node) {	
		 //invalid input
		 if ( start > end  || qs > qe )
			 return;
		 //if lazy for the node is set
		 if ( lazy[node] == 1 ) {
			 //update the node
			 /**
			  * new # of heads = (total # of coins in this node) - previous # of heads
			  */
			 tree[node] = (end - start + 1) - tree[node];
			 //if it is not a leaf
			 if( start != end ){
				 //mark children as lazy
				 /**
				  * We needed to flip the coins (marked lazy)
				  * and we are flipping it again.
				  * 
				  * If it was 0, and we flip, we mark it 1.
				  * If it was flipped (1), and we flip again, it becomes 0.
				  * 
				  */
				 lazy[2 * node + 1] = 1 - lazy[2 * node + 1];
				 lazy[2 * node + 2] = 1 - lazy[2 * node + 2];
			 }
			 //since node is updated, make lazy 0
			 lazy[node] = 0;
		 }	
		 //out of range
		 if ( qe < start || end < qs ) 
			 return;
		//if segment inside range
		if( qs <= start && end <= qe ) {
			//update node
			tree[node] = (end - start + 1) - tree[node];
			//if it is not a leaf
			if( start != end ) {
				//mark children as lazy
	            lazy[2 * node + 1] = 1 - lazy[2 * node + 1];
	            lazy[2 * node + 2] = 1 - lazy[2 * node + 2];
	        }
			//we do not go down the tree and update every node
			return;					
		}
		int mid = start + (end - start) / 2;
		updateLazy(start, mid, qs, qe, 2 * node + 1);
		updateLazy(mid + 1, end, qs, qe, 2 * node + 2);
		tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
	}
	
	//lazy query
	public int queryLazy(int start, int end, int qs, int qe, int node ){
		//if( start > end )
        //    return 0;
		//out of range
        if( qe < start || end < qs)
        	return 0;
        //if lazy has been set previously
        if ( lazy[node] == 1 ) {
        	//update the node
        	/**
        	 * new # of heads = (total # of coins in this node) - previous # of heads
        	 */
        	tree[node] = (end - start + 1) - tree[node];
        	//if it is not a leaf
        	if( start != end ){
        		//mark children
        		lazy[2 * node + 1] = 1 - lazy[2 * node + 1];
	            lazy[2 * node + 2] = 1 - lazy[2 * node + 2];
            }
        	//since node is updated, make lazy 0
            lazy[node] = 0;
        }
        //if segment inside range
        if( qs <= start && end <= qe )
        	return tree[node];
        int mid = start + (end - start) / 2;
        int x = queryLazy (start, mid, qs, qe, 2 * node + 1);
        int y = queryLazy (mid + 1, end, qs, qe, 2 * node + 2);
        return x + y;
    }
    /*
   	//Update
   	void update(int start, int end, int qs, int qe, int node) {
   		if ( start > end )
   			return;
   		if ( end < qs || qe < start) 
   			return;
   		if( qs <= start && end <= qe ) {
   			tree[node] = (end - start + 1) - tree[node];
   		}
   		update(start, (start + end) / 2, qs, qe, 2 * node + 1);
   		update((start + end) / 2 + 1, end, qs, qe, 2 * node + 2);
   		tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
   	}
   	*/
    /*
	//normal query
    public int query(int start, int end, int qs, int qe, int node ){
        if( start > end  || qs > qe )
            return 0;
        if( qe < start || end < qs )
        	return 0;
        if( qs <= start && end <= qe )
        	return tree[node];
        int x = query (start, (start + end) / 2, qs, qe, 2 * node + 1);
        int y = query ((start + end) / 2 + 1 , end, qs, qe, 2 * node + 2);
        return x + y;
    }
    */
     /*
    public void printSegTree() {
    	for ( int i = 0; i < tree.length; i ++ ){
    		System.out.println("NODE " + i + " : " + tree[i]);
    	}
    }
    */
}
