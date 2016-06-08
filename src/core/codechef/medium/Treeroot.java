package core.codechef.medium;

/**
 * Chef has a binary tree. The binary tree consists of 1 or more nodes.
 * Each node has a unique integer id. Each node has up to 2 children,
 * which are identified by their ids, and each node is the child of at most 1 other node.
 * A node X is considered to be an ancestor of node Y if node Y is a child of node X
 * or if there is some node Z for which X is an ancestor of Z and Y is a child of Z.
 * No node is an ancestor of itself. A special node called the root node is an ancestor of all other nodes.
 * Chef has forgotten which node of his tree is the root, and wants you to help him to figure it out.
 * Unfortunately, Chef's knowledge of the tree is incomplete. He does not remember
 * the ids of the children of each node, but only remembers the sum of the ids of the children of each node.
 * 
 * Input
 * Input begins with an integer T, the number of test cases. Each test case begins with an integer N,
 * the number of nodes in the tree. N lines follow with 2 integers each: the id of a node,
 * and the sum of the ids of its children. The second number will be 0 if the node has no children.
 * 
 * Output
 * For each test case, output on a line a space separated list of all possible values 
 * for the id of the root node in increasing order. It is guaranteed that at least one 
 * such id exists for each test case.
 * 
 * Constraints
 * 
 * 1 ≤ T ≤ 50
 * 1 ≤ N ≤ 30
 * All node ids are between 1 and 1000, inclusive
 * 
 * Sample Input
 * 2
 * 1
 * 4 0
 * 6
 * 1 5
 * 2 0
 * 3 0
 * 4 0
 * 5 5
 * 6 5
 * 
 * Sample Output
 * 4
 * 6
 * 
 * Explanation
 * 
 * In the first sample test case, there is only one node, which is clearly the root. 
 * In the second test case, there are two non-isomorphic trees that satisfy the constraints,
 * as seen in the following picture:
 * 
 *   6           6
 *    \         / \
 *     5       1   4
 *    / \       \
 *   1   4       5
 *  / \         / \
 * 2   3       2   3
 * 
 */

/**
 * From Editorial: [https://discuss.codechef.com/questions/4727/treeroot-editorial]
 * 
 * The possible root node is unique (if it exists). Since we are given that a tree 
 * exists, finding the root node's id is as simple as follows.
 * Denote by "id(node v)" the id of the node v, and "sid(node v)" the 
 * sum of the id's of v's children. Consider x = sum(id(v) - sid(v)) over 
 * all nodes v. If a binary tree exists, then its root node has to have id x.
 * This is because, for each node v other than the root, its id is counted once 
 * in the sum (as id(v)) and it cancels out once in the sum (as -sid(v's parent)).
 * Since the root node doesn't have a parent, its id is left uncanceled in the sum.
 * 
 * The constraints N <= 30 were left to trick some people into trying out brute force solutions.
 * The test-data however does not allow for such solutions to pass.
 * One possible brute force solution requires you to store all possible subtrees 
 * rooted at a particular vertex, and then for each vertex t, and possible children-pairs 
 * u,v test if t with u and v can form a possible subtree (by merging two subtrees rooted at u and v).
 *  The main issue to be handled here, is to ensure that the considered subtrees at u and v 
 *  to be merged are disjoint.
 *  
 *  This can be done by storing vector<int> dp[node u] which stores bitmasks of all possible 
 *  subtrees rooted at u. Finally check if there exists a vertex on which a subtree rooted 
 *  includes all vertices.
 *  
 *  Unfortunately the tests included cases where there were as many as 10^4 possible bitmasks 
 *  for subtrees rooted at a node, and hence such an attempt to merge subtrees would have tried
 *  10^4 * 10^4 possibilities to merge. Such an approach would time out.
 *  
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Treeroot {
	public static void main(String[] args)throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine().trim());
		
		//for every test case
		for ( int t = 0; t < T; t++ ) {
			int N = Integer.parseInt(br.readLine().trim());
			int root = 0;
			for ( int n = 0; n < N; n++ ) {
				String[] arr = br.readLine().trim().split(" ");
				int id = Integer.parseInt(arr[0]);
				int child_sum = Integer.parseInt(arr[1]);
				
				root += id - child_sum;
			}
			System.out.println(root);
		}
	}
}
