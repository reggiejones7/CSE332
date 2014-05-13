package phaseA;
import providedCode.*;
import java.util.NoSuchElementException;
/**
 * @author Tristan Riddell
 * CSE 332
 * TA: HyeIn Kim
 * Project 2
 * 
 * FourHeap implements heap interface using a 4-ary heap.
 * An array is used to store and manage the heap's data.
 * Whenever a value is added or removed from the heap,
 * percolate up or down is called to ensure the properties of a heap
 * are maintained. In other words, that : any given node's
 * children are greater than the parent.
 * 
 */

/**

 */
public class FourHeap<E> extends Heap<E> {
	//constant value which determines initial size of heap array
	private static final int INIT_SIZE = 10;
    // Function object to compare elements of type E, passed in constructor.
    protected Comparator<? super E> comparator;
	
    /**
     * 
     * constructs an empty FourHeap
	 * @param c is a 'function object' in order for elements of type E
	 * 	      to be compared.
     */
	public FourHeap(Comparator<? super E> c) {
		super(INIT_SIZE);
		size = 0;
		comparator = c;
	}

	/**
	 * increases array size if necessary, then inserts item at end of array
	 * percolates item upwards to maintain a valid heap
	 * @param item generic data being added to the heap
	 */
	@Override
	public void insert(E item) {
		if (heapArray.length == size) {
			enlargeArray();
		}
		
		heapArray[size] = item;
		size++;
		percolateUp(size-1);

	}

	/**
	 * minimum value according to given comparator is set to tree root. 
	 * @return first element in heapArray.
	 * @throws NoSuchElementException if the heap is empty
	 */
	@Override
	public E findMin() {
		if (size == 0) {
			throw new NoSuchElementException("The heap is empty.");
		}
		return heapArray[0];
	}

	/**
	 * deletes and returns the min value in the heap, then reforms
	 * the heap 
	 * @return first element in heap
	 * @param NoSuchElementException
	 */
	@Override
	public E deleteMin() {
		if (size == 0) {
			throw new NoSuchElementException("The heap is empty.");

		}
		
		E min = heapArray[0];
		heapArray[0] = heapArray[size-1];
		heapArray[size-1] = null;
		size--;
		percolateDown(0);
		return min;
	}

	/**
	 * @return true if heap is empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}
	
	/* 
	 * percolates value at given index upwards to correct location
	 * for a valid heap, larger than parent and smaller than children
	 * @param index location in heapArray which is being percolated
	 */
	private void percolateUp(int index) {
		E temp;
		int currentIndex = index;
		
		while(currentIndex != 0) {
			int parentIndex;
			if (currentIndex % 4 == 0) {
				parentIndex = (currentIndex / 4) -1;
			} else {
				parentIndex = (((currentIndex + (4 - (currentIndex % 4)))) / 4) - 1;
			}
			if (comparator.compare(heapArray[currentIndex], heapArray[parentIndex]) < 0) {
				temp = heapArray[parentIndex];
				heapArray[parentIndex] = heapArray[currentIndex];
				heapArray[currentIndex] = temp;
				currentIndex = parentIndex;
			} else {
				//pre condition: parent is less than currentIndex,
				//index has been percolated to correct spot.
				break;
			}
		}
	}
	/* 
	 * percolates value at given index downwards to correct location
	 * for a valid heap, larger than parent and smaller than children
	 * @param index location in heapArray which is being percolated
	 */
	private void percolateDown(int index) {
		int childIndex = (4 * index) + 1;
		int currentIndex = index;
		while(childIndex < size) {
			int minChild = childIndex;
			//saves index of smallest child
			for (int i = 1; i < 4; i++) {
				if (heapArray[childIndex+i] == null) {
					break;
				}
				if (comparator.compare(heapArray[minChild], heapArray[childIndex+i]) > 0) {
					minChild = childIndex + i;
				}
			}
			//compares smallest child to parent
			if (comparator.compare(heapArray[minChild], heapArray[currentIndex]) < 0) {
				//if smallest child smaller than parent, swap with parent
				E temp = heapArray[currentIndex];
				heapArray[currentIndex] = heapArray[minChild];
				heapArray[minChild] = temp;
				currentIndex = minChild;
				childIndex = (4 * currentIndex) + 1;
			} else {
				//pre: smallest child is greater than node, 
				//node is in correct spot
				break;
			}
		}
	}
	
	/**
	 * gets size of heap, used for junit testing purposes
	 * @return size of heap
	 */
	public int getSize() {
		return size;
	}
	
	// called when array is full to create a new array with double size
	@SuppressWarnings("unchecked")
	private void enlargeArray() {
		E[] old = heapArray;
		heapArray = (E[]) new Object[size*2];
		for (int i = 0; i < size; i++ ) {
			heapArray[i] = old[i];
		}
	}

}
