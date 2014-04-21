package phaseA;
import providedCode.*;

/**
 * @author Reggie Jones
 * CSE 332
 * TA: HyeIn Kim
 * Project 2
 * 
 * AVLTree extends BinarySearchTree class using an AVLTree. The AVLTree
 * is self balancing and holds the properties of an AVLTree- namely that
 * the nodes ordering holds BST standards, but also that the absolute 
 * value of the difference in the heights of every nodes two children is
 * at most 1. 
 *
 *
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
	
	/**
	 * incCount increments the count of a node if it exists, otherwise creates
	 * a node with the data and sets its count to 1.
	 * @param data is a generic piece of data that's being stored in 
	 *        the nodes of the tree.
	 * Post: AVLTree will be balanced and hold BST ordering properties
	 */
	@Override
	public void incCount(E data) {
		if (overallRoot == null) {
			overallRoot = new AVLNode(data); 
		} else if(!incNodeIfExists(data, overallRoot)) {
			incCountHelper(data, overallRoot);         //add a new node
		}
	}
	
	
	 // incExistingNode will increment the count of a Node if it already
	 // exists in the AVLTree and return true, or else it will only return false.
	 // @param data the corresponding key that needs incrementing in the AVLTree
	 // @param currentNode the current node we are checking
	 // @return true if data is in the AVLTree, false otherwise
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
		} else {
			return incNodeIfExists(data, currentNode.right);
		}
	}
	
	// incCountHelper adds a new node with the given data to the tree
	// @param data data to be added to tree
	// @param root the root of the tree that data's getting added to
	// pre: data cannot already be in the tree
	private BSTNode incCountHelper(E data, BSTNode root) {
		if (root == null) {
			return new AVLNode(data);
		} else {
			int cmp = comparator.compare(data, root.data);
			if (cmp < 0) {
				root.left = balance(incCountHelper(data, root.left));
			} else {  
				root.right = balance(incCountHelper(data, root.right));
			}
		}
		return balance(root);
	}
	
	
	 // balances the tree from a given root, returns the same tree if already
	 // balanced.
	 // Case 1 = left-left case
	 // Case 2 = left-right case
	 // Case 3 = right-left case
	 // Case 4 = right-right case
	 // @param root root is the root of the AVLTree that's getting balanced
	 // @return a BSTNode that holds the properties of a AVLTree
	 // pre: the tree must be only unbalanced from the previous insert
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
			//root was balanced
			return root;
		}
	}
	
	// rotates a given root to the right and returns the new root of that tree
	// checks if overallroot is the one thats getting rotated because you have
	// to set overall root manually if it is.
	private BSTNode rotateRight(BSTNode root) {
		BSTNode temp = root.left;
		root.left = temp.right;
		temp.right = root;
		updateHeight(root);  //update root first because its child of temp now
		updateHeight(temp);
		
		if (root == overallRoot) {
			overallRoot = temp;
		}
		root = temp;
		return root;
	}
	
	//rotates a given root to the left and returns the new root of that tree
	// if overallRoot is the one getting rotated on then it gets set correctly
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
	
	// returns an int of the case of imbalance on a given root, 
	// returns -1 if the tree is balanced
	// case 1 = left-left
	// case 2 = left-right
	// case 3 = right-left
	// case 4 = right-right
	private int getImbalanceCase(BSTNode root) {
		//no root with height under 2 can be unbalanced
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
			return -1;
		}
	}
	
	//returns the height of a given node
	//height of a null node is -1
	private int height(BSTNode node) {
		if (node == null) {
			return -1;
		}
		@SuppressWarnings("unchecked")
		AVLNode n = (AVLNode) node;
		return n.height;
	}
	
	//updates the height of a given node
	private void updateHeight(BSTNode node) {
		@SuppressWarnings("unchecked")
		AVLNode n = (AVLNode) node;
		n.height = Math.max(height(node.left), height(node.right)) + 1;
	}
	
	
	/**
	 * prints an in-order traversal of the AVLTree to check tree has BST ordering
	 */
	public void print() {
		print2(overallRoot);
	}
	
	// does the printing of the AVLTree in in-order traversal
	private void print2(BSTNode root) {
		if (root == null) {
			return;
		}
		print2(root.left);
		System.out.println(root.data + ", ");
		print2(root.right);
	}

    
     // private inner class that extends BSTNode to represent a node 
     // in the AVLTree. Each node includes data of type E, an int count of the data,
     // left and right children, as well as the nodes height in the tree.
	private class AVLNode extends BSTNode {
		public int height;   //the height of this node in the tree
		
		// constructs an AVLNode with a given d as the nodes data
		public AVLNode(E d) {
			super(d);
			height = 0;
		}
	}
}
