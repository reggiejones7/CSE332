package main;
import phaseA.FourHeap;
import providedCode.Comparator;
import providedCode.Heap;


/** 
 *  TODO: REPLACE this comment with your own as appropriate.
 *  Implement the sorting methods below. Do not change the provided
 *  method signature, but you may add as many other methods as you
 *  want.
 */
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
    
    private static <E> void swap(E[] array, int x, int y) {
    	E temp = array[x];
    	array[x] = array[y];
    	array[y] = temp;
    }
    
    private static <E> void quickSort(E[] array, Comparator<E> comparator, int lo, int hi) {
    	if (lo >= hi) {
    		return;
    	}
    	int pivotIndex = partition(array, comparator, lo, hi);
    	quickSort(array, comparator, lo, pivotIndex - 1);
    	quickSort(array, comparator, pivotIndex + 1, hi);
    }	
    
    private static <E> int partition(E[] array, Comparator<E> comparator, int lo, int hi) {
    	int pivot = (hi+lo)/2;
    	/*if (comparator.compare(array[med],array[lo]) < 0) {
    		if (comparator.compare(array[med],array[hi]) < 0) {
    			if (comparator.compare(array[hi],array[lo]) < 0) {
    				pivot = hi;
    			} else {
    				pivot = lo;
    			}
    		} else {
    			pivot = med;
    		}
    	} else if (comparator.compare(array[med],array[hi]) < 0) {
    		pivot = med;
    	} else if (comparator.compare(array[hi],array[lo]) < 0) {
    		pivot = lo;
    	} else {
    		pivot = hi;
    	}*/
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
    
 /*   private static <E> boolean mergeSortHelper(E[] array, E[] auxArray, Comparator<E> comparator, int lo, int hi) {
    	if (hi - lo == 1) {
            if (comparator.compare(array[lo],array[hi]) > 0) {
            	auxArray[hi] = array[lo];
            	auxArray[lo] = array[hi];
            }
            else {
            	auxArray[hi] = array[hi];
            	auxArray[lo] = array[lo];
            }
            return true;
    	} else if(hi == lo) {
    		auxArray[hi] = array[hi];
    		return true;
    	} else {
    		int lowerHi = ((hi - lo)/2) + lo;
    		int higherLo = lowerHi + 1;
    		mergeSortHelper(array, auxArray, comparator, lo, lowerHi);
    		boolean mergeToMain = mergeSortHelper(array, auxArray, comparator, higherLo, hi);

    		int pointerLo = lo;
    		int pointerHi = higherLo;
    		int auxIndex = lo;
			E[] mergeArray;
			E[] auxSpace;
    		if (mergeToMain) {
    			mergeArray = array;
    			auxSpace = auxArray;
    		} else {
    			mergeArray = auxArray;
    			auxSpace = array;
    		}
	    	while ((pointerLo != higherLo) && (pointerHi != hi + 1)) {
	            if (comparator.compare(auxSpace[pointerLo],auxSpace[pointerHi]) < 0) {
	            	mergeArray[auxIndex] = auxSpace[pointerLo];
	            	pointerLo++;
	            } else {
	            	mergeArray[auxIndex] = auxSpace[pointerHi];
	            	pointerHi++;
	            }
	        	auxIndex++;
	    	}
	    	while (pointerLo != higherLo) {
	        	mergeArray[auxIndex] = auxSpace[pointerLo];
	        	pointerLo++;
	        	auxIndex++;
	    	}
	    	while (pointerHi != hi + 1) {
	        	mergeArray[auxIndex] = auxSpace[pointerHi];
	        	pointerHi++;
	        	auxIndex++;
	    	}	    	
	    	return !(mergeToMain);
	    }
    }*/
}
