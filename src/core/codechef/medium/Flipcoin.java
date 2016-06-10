package core.codechef.medium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Flipcoin {
	public static void main(String[] args)throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] arr = br.readLine().trim().split(" ");
		int N = Integer.parseInt(arr[0]);
		int Q = Integer.parseInt(arr[1]);
		
		int[] coins = new int[N];
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
		int max_size = 3 * n;
		tree = new int[max_size]; // Memory allocation
		lazy = new int[max_size];
		constructSTUtil(arr, 0, n - 1, 0);
	}
	
	/**
	 *  A recursive function that constructs Segment Tree for array[ss..se].
	 *  
	 * @param arr
	 * 			the array of values
	 * @param ss
	 * 			starting index of arr
	 * @param se
	 * 			last index of arr
	 * @param si
	 * 			index of current node in segment tree st
	 * @return
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
	     tree[node] = constructSTUtil(arr, start, mid, node * 2 + 1) + constructSTUtil(arr, mid + 1, end, node * 2 + 2);
	     return tree[node];
	 }
	
	//Lazy update
	void updateLazy(int start, int end, int qs, int qe, int node) {
		
		if ( start > end  || qs > qe )
			return;
		if ( lazy[node] == 1 ) {	//Update curr node if lazy has been set previously
			tree[node] = (end - start + 1) - tree[node];
			if(start != end){   	//i.e if not a leaf then mark children
	            lazy[2 * node + 1] = 1 - lazy[2 * node + 1];
	            lazy[2 * node + 2] = 1 - lazy[2 * node + 2];
	        }
			lazy[node] = 0;
		}	
		if ( qe < start || end < qs ) 
			return;
		if( qs <= start && end <= qe ) {	//segment inside range hence update
			tree[node] = (end - start + 1) - tree[node];
			if( start != end ) {   			//if not leaf then mark children
	            lazy[2 * node + 1] = 1 - lazy[2 * node + 1];
	            lazy[2 * node + 2] = 1 - lazy[2 * node + 2];
	        }
			return;					//we do not go down the tree and update every node
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
        if( qe < start || end < qs)
        	return 0;
        if ( lazy[node] == 1 ) {		//update curr node if lazy has been set previously
        	tree[node] = (end - start + 1) - tree[node];     //updating seg tree node
        	if( start != end ){   		//if not a leaf then mark children
        		lazy[2 * node + 1] = 1 - lazy[2 * node + 1];
	            lazy[2 * node + 2] = 1 - lazy[2 * node + 2];
            }
            lazy[node] = 0;
        }
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
