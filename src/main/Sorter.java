package main;
import phaseA.FourHeap;
import providedCode.Comparator;
import providedCode.Heap;


/** 
 *  The Sorter class implements a number of different sorting algorithms 
 *  to be used by wordcount to sort a given array of data from largest to smallest count.
 *  specifically: insertion, heap, topK (implemented via a heap), and quicksort. 
 *  */
public class Sorter {
	
	/**
     * Sort the count array in descending order of count. If two elements have
     * the same count, they should be ordered according to the comparator.
     * This code uses insertion sort. The code is generic, but in this project
     * we use it with DataCount<String> and DataCountStringComparator.
     * @param counts array to be sorted.
     * @param comparator for comparing elements.
     */
    public static <E> void insertionSort(E[] array, Comparator<E> comparator) {
        for (int i = 1; i < array.length; i++) {
            E x = array[i];
            int j;
            for (j=i-1; j>=0; j--) {
                if (comparator.compare(x,array[j]) >= 0) { break; }
                array[j + 1] = array[j];
            }
            array[j + 1] = x;
        }
    }
    
    /**
     * Will sort a given generic array using the Heapsort algorithm in
     * descending order of count. If two element have equal counts than
     * the elements will be sorted according to the given comparator
     * @param array array thats going to get sorted
     * @param comparator for comparing the elements in array
     */
    public static <E> void heapSort(E[] array, Comparator<E> comparator) {
    	Heap<E> heap = new FourHeap<E>(comparator);
    	
    	for (int i = 0; i < array.length; i++) {
    		heap.insert(array[i]);
    	}
    	
    	for (int i = 0; i < array.length; i++) {
    		array[i] = heap.deleteMin();
    	}
    }
    
	/**
     * Sorts k elements with top count in the array  using heap sort
     * in ascending order of count. If two elements have
     * the same count, they should be ordered according to the comparator.
     * @param counts array to be sorted.
     * @param comparator for comparing elements.
     * @param k number of top count elements to print
     */
    public static <E> void topKSort(E[] array, Comparator<E> comparator, int k) {
    	if (k >= array.length) {
    		k = array.length;
    	}
    	Heap<E> heap = new FourHeap<E>(comparator);
    	for (int i = 0; i < k; i++) {
    		heap.insert(array[i]);
    	}
    	
    	for (int i = k; i < array.length; i++) {
    		if (comparator.compare(array[i], heap.findMin()) > 0) {
    			heap.deleteMin();
    			heap.insert(array[i]);
    		}
    	}
    	
    	for (int i = k-1; i >= 0; i--) {
    		array[i] = heap.deleteMin();
    	}	
    }    
    
	/**
     * Sort the count array in descending order of count. using quicksort.
     * elements of the same count are ordered according to the comparator.
     * @param array array thats going to get sorted
     * @param comparator for comparing elements.
     */
    public static <E> void otherSort(E[] array, Comparator<E> comparator) {
    	quickSort(array, comparator, 0, array.length - 1);
    }
    
    //simple private function used to swap two elements in an array.
    private static <E> void swap(E[] array, int x, int y) {
    	E temp = array[x];
    	array[x] = array[y];
    	array[y] = temp;
    }
    
    //recursive quicksort function called by othersort.
    private static <E> void quickSort(E[] array, Comparator<E> comparator, int lo, int hi) {
    	if (lo >= hi) {
    		return;
    	}
    	int pivotIndex = partition(array, comparator, lo, hi);
    	quickSort(array, comparator, lo, pivotIndex - 1);
    	quickSort(array, comparator, pivotIndex + 1, hi);
    }	
    
    //helper function to quicksort, finds a pivot and partitions a given section of an array
    private static <E> int partition(E[] array, Comparator<E> comparator, int lo, int hi) {
    	int pivot = (hi+lo)/2;
    	swap(array, lo, pivot);
    	int i = lo + 1;
    	int j = hi;
    	
    	while (i < j) {
	    	if ((comparator.compare(array[j], array[lo]) > 0 )) {
	    		j--;
	    	} else if((comparator.compare(array[i], array[lo]) < 0 )) {
	    		i++;
	    	} else {
	    		swap(array, i, j);
	    		i++;
	    		j--;
	    	}
    	}
    	if ((comparator.compare(array[i], array[lo]) > 0 )) {
    		swap (array, i - 1, lo);
    		return i -1;
    	} else {
    	swap(array, i, lo);
    	}
    	return i;
    }
}
