package test;
import static org.junit.Assert.*;
import main.Sorter;

import org.junit.Before;
import org.junit.Test;

import providedCode.DataCount;
import providedCode.DataCountStringComparator;


public class TestSorter {
	private static final int TIMEOUT = 2000;
	private DataCountStringComparator cmp;
	
	// Runs before each test
	@Before
	public void setUp() {
		cmp = new DataCountStringComparator();
	}
	
	
	/** test insertion sort ==========================================*/
	@Test(timeout = TIMEOUT)
	public void test_insertion_sort_with_data_count_same_counts() {
		//this case is testing that when the counts are the same it 
		//uses the comparator to order the elements
		@SuppressWarnings("unchecked")
		DataCount<String>[] count = new DataCount[6]; 
		count[0] = new DataCount<String>("string", 1);
		count[1] = new DataCount<String>("strin", 1);
		count[2] = new DataCount<String>("A", 1);
		count[3] = new DataCount<String>("a", 1);
		count[4] = new DataCount<String>("1", 1);
		count[5] = new DataCount<String>("-2", 1);
		
		Sorter.insertionSort(count, cmp);
		assertData(new String[]{"-2", "1", "A", "a", "strin", "string"}, count);
		
	}
	
	@Test(timeout = TIMEOUT)
	public void test_insertion_sort_with_data_count_different_counts() {
		@SuppressWarnings("unchecked")
		DataCount<String>[] count = new DataCount[6]; 
		count[0] = new DataCount<String>("5", 5);
		count[1] = new DataCount<String>("1", 1);
		count[2] = new DataCount<String>("11", 11);
		count[3] = new DataCount<String>("200", 200);
		count[4] = new DataCount<String>("2", 2);
		count[5] = new DataCount<String>("2001", 2001);
		
		Sorter.insertionSort(count, cmp);
		assertData(new String[]{"2001", "200", "11", "5", "2", "1"}, count);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_insertion_sort_with_data_count_different_counts_check_counts() {
		@SuppressWarnings("unchecked")
		DataCount<String>[] count = new DataCount[12]; 
		count[0] = new DataCount<String>("One", 5);
		count[1] = new DataCount<String>("Cries", 1);
		count[2] = new DataCount<String>("because", 11);
		count[3] = new DataCount<String>("they", 10);
		count[4] = new DataCount<String>("are", 2);
		count[5] = new DataCount<String>("sad", 2001);
		count[6] = new DataCount<String>("I cry", 1200);
		count[7] = new DataCount<String>("because", 22);
		count[8] = new DataCount<String>("others are stupid", 222);
		count[9] = new DataCount<String>("and", 2);
		count[10] = new DataCount<String>("that makes me", 2);
		count[11] = new DataCount<String>("sad", 2);
		Sorter.insertionSort(count, cmp);
		assertCounts(new int[]{2001, 1200, 222, 22, 11, 10, 5, 2, 2, 2, 2, 1}, count);
	}
	 
	@SuppressWarnings("unchecked")
	@Test(timeout = TIMEOUT)
	public void test_insertion_sort_on_empty_array() {
		Sorter.insertionSort(new DataCount[]{}, cmp);
		assertTrue("verifying insertionSort doesn't blow up on an empty array", true);
		
	}
	
	/** test heap sort ==========================================*/
	
	@Test(timeout = TIMEOUT)
	public void test_heap_sort_with_data_count_same_counts() {
		//this case is testing that when the counts are the same it 
		//uses the comparator to order the elements
		@SuppressWarnings("unchecked")
		DataCount<String>[] count = new DataCount[6]; 
		count[5] = new DataCount<String>("a", 1);
		count[4] = new DataCount<String>("b", 1);
		count[3] = new DataCount<String>("c", 1);
		count[2] = new DataCount<String>("d", 1);
		count[1] = new DataCount<String>("e", 1);
		count[0] = new DataCount<String>("f", 1);
		
		Sorter.heapSort(count, cmp);
		for (int i = 0; i < 6; i++) {
			System.out.println(" " + count[i].data);
		}
		assertEquals("a", count[0].data);
		assertEquals("b", count[1].data);
		assertEquals("c", count[2].data);
	}
	
	
	
	
	/** private methods ==============================================*/
	private void assertData(String[] expected, DataCount<String>[] count) {
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], count[i].data);
		}
	}
	
	private void assertCounts(int[] expected, DataCount<String>[] count) {
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], count[i].count);
		}
	}	
}
