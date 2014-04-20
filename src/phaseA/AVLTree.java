package phaseA;
import providedCode.*;

/**
 * @author Reggie Jones
 * CSE 332
 * TA: HyeIn Kim
 * Project 2
 * 
 * AVLTree extends BinarySearchTree class using an AVLTree. [give properties that avltrees hold]
 */
  
//finish commenting class header and all function headers

/**
 * TODO: REPLACE this comment with your own as appropriate.
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * X1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override incCount method such that it creates AVLNode instances
 *    instead of BSTNode instances.
 * X3. Do NOT "replace" the left and right fields in BSTNode with new
 *    left and right fields in AVLNode.  This will instead mask the
 *    super-class fields (i.e., the resulting node would actually have
 *    four node fields, with code accessing one pair or the other
 *    depending on the type of the references used to access the
 *    instance).  Such masking will lead to highly perplexing and
 *    erroneous behavior. Instead, continue using the existing BSTNode
 *    left and right fields.
 * 4. Cast left and right fields to AVLNode whenever necessary in your
 *    AVLTree. This will result a lot of casts, so you can also follow
 *    the hints given during section to reduce the number of casts.
 * 5. Do NOT override the dump method of BinarySearchTree & the toString
 *    method of DataCounter. They are used for grading.
 * TODO: Develop appropriate JUnit tests for your AVLTree (TestAVLTree
 * in testA package).
 */
public class AVLTree<E> extends BinarySearchTree<E> {

	/**
	 * constructs an empty AVLTree
	 * @param c is a 'function object' in order for elements of type E
	 * 	      to be compared.
	 */
	public AVLTree(Comparator<? super E> c) {
		super(c);
	}
	
	//1. check if data is already in the tree, if so just ++. no need for balancing or updating height.
	//2. go until you hit the bottom. insert node there. then balance and update heights.
	@Override
	public void incCount(E data) {
		if (overallRoot == null) {
			overallRoot = new AVLNode(data); 
		} else if(!incNodeIfExists(data, overallRoot)) {
			incCountHelper(data, overallRoot);         //add a new node
		}
	}
	
	/**
	 * incExistingNode will increment the count of a Node if it already
	 * exists in the AVLTree and return true, or else it will only return false.
	 * @param data the corresponding key AVLTree that needs incrementing in the AVLTree
	 * @param currentNode the current node we are checking
	 * @return true if data is in the AVLTree, false otherwise
	 */
	private boolean incNodeIfExists(E data, BSTNode currentNode) {
		if (currentNode == null) {
			return false;
		}
		
		int cmp = comparator.compare(data, currentNode.data);
		if (cmp == 0) {
			currentNode.count++;
			return true;
		} else if (cmp < 0) {
			return incNodeIfExists(data, currentNode.left);
		} else {  //cmp > 0
			return incNodeIfExists(data, currentNode.right);
		}
	}
	
	//pre: data cannot already be in tree
	private BSTNode incCountHelper(E data, BSTNode root) {
		if (root == null) {
			return new AVLNode(data);
		} else {
			int cmp = comparator.compare(data, root.data);
			if (cmp < 0) {
				root.left = balance(incCountHelper(data, root.left));
			} else {  //cmp > 0 
				root.right = balance(incCountHelper(data, root.right));
			}
		}
		return root;
	}
	
	/**
	 * 
	 * Case 1 = left-left case
	 * Case 2 = left-right case
	 * Case 3 = right-left case
	 * Case 4 = right-right case
	 * @param root root is the root of the AVLTree that's getting balanced
	 * @return a BSTNode that holds the properties of a AVLTree
	 */
	//make sure i'm supposed to return BSTNode and not AVLNode
	private BSTNode balance(BSTNode root) {
		//updateHeight first because we just inserted a node from incCount
		updateHeight(root);
		int imbalanceCase = getImbalanceCase(root);
		
		if (imbalanceCase == 1) {
			return rotateRight(root);
		} else if (imbalanceCase == 2) { 
			root.left = rotateLeft(root.left);
			return rotateRight(root);
		} else if (imbalanceCase == 3) { 
			root.right = rotateRight(root.right);
			return rotateLeft(root);
		} else if (imbalanceCase == 4) { 
			return rotateLeft(root);
		} else {
			//root wasn't unbalanced
			return root;
		}
	}
	
	//check if overallroot is the one thats getting rotated because you have to reset overall root manually
	private BSTNode rotateRight(BSTNode root) {
		BSTNode temp = root.left;
		root.left = temp.right;
		temp.right = root;
		updateHeight(root);  //update root first because its below temp now
		updateHeight(temp);
		
		if (root == overallRoot) {
			overallRoot = temp;
		}
		root = temp;
		return root;
	}
	
	private BSTNode rotateLeft(BSTNode root) {
		BSTNode temp = root.right;
		root.right = temp.left;
		temp.left = root;
		updateHeight(root);
		updateHeight(temp);

		if (root == overallRoot) {
			overallRoot = temp;
		}
		root = temp;
		return root;
	}
	
	private int getImbalanceCase(BSTNode root) {
		//no root with height under 2 can be imbalanced so don't bother checking
		//prob get rid of first case
		if (height(root) < 2) {
			return -1;
		}
		//positive means go left, negative means go right
		if (height(root.left) - height(root.right) > 1) {
			if (height(root.left.left) - height(root.left.right) > 0) {
				return 1;
			} else {
				return 2;
			}
		} else if (height(root.right) - height(root.right) < 1) {
			if (height(root.right.left) - height(root.right.right) > 0) {
				return 3;
			} else {
				return 4;
			}
		} else {
			//root wasn't unbalanced
			return -1;
		}
	}
	
	//height of a null node is -1
	private int height(BSTNode node) {
		if (node == null) {
			return -1;
		}
		@SuppressWarnings("unchecked")
		AVLNode n = (AVLNode) node;
		return n.height;
	}
	
	private void updateHeight(BSTNode node) {
		@SuppressWarnings("unchecked")
		AVLNode n = (AVLNode) node;
		n.height = Math.max(height(node.left), height(node.right)) + 1;
	}
	

    /**
     * private inner class that extends BSTNode to represent a node 
     * in the AVLTree. Each node includes data of type E, an int count of the data,
     * left and right children, as well as the nodes height in the tree.
     */
	private class AVLNode extends BSTNode {
		public int height;   //the height of this node in the tree
		
		public AVLNode(E d) {
			super(d);
			height = 0;
		}
	}

}
