package phaseA;
import providedCode.*;
/**
 * @author Tristan Riddell
 * CSE 332
 * TA: HyeIn Kim
 * Project 2
 * 
 * MoveToFrontList extends DataCounter class using a linked list.
 */
/**
 * TODO: REPLACE this comment with your own as appropriate.
 * 1. The list is typically not sorted.
 * 2. Add new items (with a count of 1) to the front of the list.
 * 3. Whenever an existing item has its count incremented by incCount
 *    or retrieved by getCount, move it to the front of the list. That
 *    means you remove the node from its current position and make it
 *    the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 *    elements to the front.  The iterator should return elements in
 *    the order they are stored in the list, starting with the first
 *    element in the list.
 * TODO: Develop appropriate JUnit tests for your MoveToFrontList.
 */
public class MoveToFrontList<E> extends DataCounter<E> {
	//number of nodes in list
	protected int size;
    // Function object to compare elements of type E, passed in constructor.
    protected Comparator<? super E> comparator; 
    //node used as pointer to front of linked list.
    protected listNode listFront;
	

	public MoveToFrontList(Comparator<? super E> c) {
		listFront = null;
        size = 0;
        comparator = c;
	}
	
	@Override
	//increments count of node with data data, 
	//if node not found, creates new node and sets it at front of list
	public void incCount(E data) {
		
		if (listFront == null) {
			//list is empty, creates first element
			listFront = new listNode(data, null);
			return;
		}
		
		listNode currentNode = listFront;
		while(currentNode != null) {
			if ((comparator.compare(data, currentNode.data)) == 0) {
				//node found, count incremented.
				currentNode.count++;
				return;
			}
			currentNode = currentNode.next;
		}
		//node not found, create new node and put it at front of list
		listNode newNode = new listNode(data, listFront);
		listFront = newNode;	
	}

	@Override
	//returns number of elements stored in list
	public int getSize() {
		return size;
	}

	@Override
	//returns count of node containing given data, or 0 if node not found
	public int getCount(E data) {
		listNode currentNode = listFront;
		while (currentNode != null) {
			if ((comparator.compare(data, currentNode.data)) == 0) {
				//node found, count returned
				return currentNode.count;
			}
			currentNode = currentNode.next;
		}
		//node not found, return 0
		return 0;
	}

	@Override
	//returns a simple iterator over the elements of the linked list
	public SimpleIterator<DataCount<E>> getIterator() {
    	return new SimpleIterator<DataCount<E>>() {  
    		listNode nextNode = listFront;
    		public boolean hasNext() {
    			if 	(nextNode != null) {
    				return true;
    			} else {
    				return false;
    			}
        	}
        	public DataCount<E> next() {
        		if(!hasNext()) {
        			throw new java.util.NoSuchElementException();
        		}
        		listNode returnNode = nextNode;
        		nextNode = nextNode.next;
        		return new DataCount<E>(returnNode.data, returnNode.count);
        	}
    	};
	}
	
	//creates a node class used in MoveToFrontList's linked list
    protected class listNode {
        public E data;          // The data element stored at this node.
        public int count;       // The count for this data element.
        public listNode next;

        /**
         * Create a new data node and increment the list size.
         */
        public listNode(E d, listNode n) {
            data  = d;
            count = 1;
            size++;
            next = n;
        }
    }

}
