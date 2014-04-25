package phaseA;
import providedCode.*;
/**
 * @author Tristan Riddell
 * CSE 332
 * TA: HyeIn Kim
 * Project 2
 * 
 * MoveToFrontList extends DataCounter class using a linked list.
 * MoveToFrontList is a simple unsorted single linked list. when a node 
 * is accessed or added, it is moved to the front of the list.
 * This uses the concept of temporal locality to maximize efficiency.
 */
/** */
public class MoveToFrontList<E> extends DataCounter<E> {
	//number of nodes in list
	protected int size;
    // Function object to compare elements of type E, passed in constructor.
    protected Comparator<? super E> comparator; 
    //pointer to first node in list
    protected listNode listFront;
	
	/**
	 * constructs an empty linked list
	 * @param c is a 'function object' in order for elements of type E
	 * 	      to be compared.
	 */
	public MoveToFrontList(Comparator<? super E> c) {
		listFront = new listNode(null, null);
        size = 0;
        comparator = c;
	}
	
	/**
	 * incCount increments the count of a node if it exists,
	 * then moves the nde to the list front.
	 *  otherwise creates a node with the data and sets its count to 1.
	 *  and places that at the list front.
	 * @param data is a generic piece of data that's being stored in 
	 *        the nodes of the tree.
	 * Post: Linked list will have new node containing data at front of list.
	 */
	@Override
	public void incCount(E data) {
		
		if (listFront.next == null) {
			//list is empty, creates first element
			listFront.next = new listNode(data, null);
			return;
		}
		
		listNode currentNode = listFront;
		while(currentNode.next != null) {
			if ((comparator.compare(data, currentNode.next.data)) == 0) {
				//node found, count incremented.
				currentNode.next.count++;
				listNode tempNode = currentNode.next;
				currentNode.next = currentNode.next.next;
				tempNode.next = listFront.next;
				listFront.next = tempNode;
				return; 
			}
			currentNode = currentNode.next;
		}
		//node not found, create new node and put it at front of list
		listNode newNode = new listNode(data, listFront.next);
		listFront.next = newNode;	
	}
	
    /**
     * The number of unique data elements in the structure.
     * @return the number of unique data elements in the structure.
     */
	@Override
	public int getSize() {
		return size;
	}

    /**
     * The current count for the data, 0 if it is not in the counter.
     * @param data is a generic piece of data that may be in the list
	 * @return the count for the data. 0 if it is not present in list
     */
	@Override
	public int getCount(E data) {
		listNode currentNode = listFront;
		while (currentNode.next != null) {
			if ((comparator.compare(data, currentNode.next.data)) == 0) {
			//	node found, count returned
				listNode tempNode = currentNode.next;
				currentNode.next = currentNode.next.next;
				tempNode.next = listFront.next;
				listFront.next = tempNode;
				return listFront.next.count;
			}
			currentNode = currentNode.next;
		}
		//node not found, return 0
		return 0;
	}
	
    /**
     * Clients must not increment counts between an iterator's creation and its
     * final use. Data structures need not check this.
     * @return an iterator for the elements.
     */
	@Override
	public SimpleIterator<DataCount<E>> getIterator() {
    	return new SimpleIterator<DataCount<E>>() {  
    		listNode nextNode = listFront.next;
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
	
    /**
     * Inner class to represent a node in the list. Each node includes
     * a data of type E and an integer count. 
     */    protected class listNode {
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
     	//returns data of front node, used for junit testing
     	public E getFront() {
     		return listFront.next.data;
     	}

}
