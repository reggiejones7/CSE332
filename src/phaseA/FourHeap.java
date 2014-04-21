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
 */

/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. It is exactly like the binary heap we studied, except nodes
 *    should have 4 children instead of 2. Only leaves and at most one
 *    other node will have fewer children.
 * 2. Use an array-based implementation, beginning at index 0 (Root
 *    should be at index 0).  Construct the FourHeap by passing
 *    appropriate argument to superclass constructor.  Hint: Complete
 *    written homework #2 before attempting this.
 * 3. Throw appropriate exceptions in FourHeap whenever needed. For
 *    example, when deleteMin is on an empty heap, you could use
 *    UnderFlowException as is done in the Weiss text, or you could
 *    use NoSuchElementException (in which case it will be fine if you
 *    want to import it).
 * TODO: Develop appropriate JUnit tests for your FourHeap.
 * 
 * IMPORTANT NOTE:
 * 1. You MUST use the fields defined in the superclass!
 * 2. FourHeap should be a MIN-HEAP, which means the "smallest"
 *    element according to the given comparator should be at the root. It
 *    is obvious from the name of methods, deleteMin() & findMin(),
 *    which should return the minimum element determined by the given
 *    comparator. For example, the DataCountStringComparator considers
 *    the element with the highest count to be the "smallest", so when you
 *    call deleteMin() or findMin() your FourHeap should return the
 *    element with highest count.
 * 3. If you accidentally made it as MAX-HEAP instead of MIN-HEAP, it
 *    will return the "largest" element (with
 *    DataCountStringComparator, this will be the one with lowest count) when
 *    deleteMin() is called, and you are going to lose considerable
 *    amount of FourHeap points.
 * 4. For Testing: Given the same comparator, FourHeap's deleteMin()
 *    should return the same element as Java's PriorityQueue's poll().
 */
public class FourHeap<E> extends Heap<E> {
	//constant value which determines initial size of heap array
	private static final int INIT_SIZE = 50;
    // Function object to compare elements of type E, passed in constructor.
    protected Comparator<? super E> comparator;
	
	public FourHeap(Comparator<? super E> c) {
		// TODO: To-be implemented. Replace the dummy parameter to superclass constructor.
		super(INIT_SIZE);
		size = 0;
		comparator = c;
	}

	//increases array size if necessary, then inserts item at end of array
	//percolates item upwards to maintain a valid heap
	@Override
	public void insert(E item) {
		if (heapArray.length == size) {
			enlargeArray();
		}
		
		heapArray[size] = item;
		percolateUp(size);
		size++;
	}

	@Override
	//minimum value according to given comparator is set to tree root. 
	//returns first element in heapArray.
	public E findMin() {
		if (size == 0) {
			throw new NoSuchElementException("The heap is empty.");
		}
		return heapArray[0];
	}

	@Override
	public E deleteMin() {
		if (size == 0) {
			throw new NoSuchElementException("The heap is empty.");

		}
		
		E min = heapArray[0];
		heapArray[0] = heapArray[size-1];
		percolateDown(0);
		size--;
		return min;
	}

	@Override
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * percolates value at given index upwards to correct location
	 * for a valid heap, larger than parent and smaller than children
	 * @param index location in heapArray which is being percolated
	 */
	private void percolateUp(int index) {
		E temp;
		int currentIndex = index;
		
		while(currentIndex != 0) {
			int parentIndex = (currentIndex + (4 - (currentIndex% 4)) / 4) - 1;
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
	/**
	 * 
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
