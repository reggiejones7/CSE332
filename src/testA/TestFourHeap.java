package testA;
import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import phaseA.FourHeap;
import providedCode.Comparator;


public class TestFourHeap {
	private static final int TIMEOUT = 3000;
	FourHeap<Integer> testHeap;
	
	@Before
	public void startup() {
		testHeap = new FourHeap<Integer>(new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) {
				return e1-e2;
			}
			
		});
	}
	
	@Test(timeout = TIMEOUT)
	public void test_empty() {
		assertTrue("empty heap", testHeap.isEmpty());
	}
	@Test(expected = NoSuchElementException.class)
	public void find_empty() {
		testHeap.findMin();
	}
	@Test(expected = NoSuchElementException.class)
	public void delete_empty() {
		testHeap.deleteMin();
	}
	@Test(timeout = TIMEOUT)
	public void insert__and_remove_one() {
		testHeap.insert(1);
		assertEquals("size is 1", 1, testHeap.getSize());
		assertEquals("min is 1", 1, (long)testHeap.findMin());
		assertEquals("deleted Min is 1", 1, (long)testHeap.deleteMin());
		assertEquals("size is now 0", 0, testHeap.getSize());
		
	}
	@Test(timeout = TIMEOUT)
	public void hundredinsert() {
		for (int i = 0; i < 100; i++) {
			testHeap.insert(i);
		}
		assertEquals("min is 0", 0, (long)testHeap.deleteMin());
		assertEquals("min is 1", 1, (long)testHeap.deleteMin());
		assertEquals("min is 2", 2, (long)testHeap.deleteMin());

	}
	@Test(timeout = TIMEOUT)
	public void hundredinsertreverse() {
		for (int i = 99; i >= 0; i--) {
			testHeap.insert(i);
		}
		assertEquals("min is 0", 0, (long)testHeap.deleteMin());
		assertEquals("min is 1", 1, (long)testHeap.deleteMin());
		assertEquals("min is 2", 2, (long)testHeap.deleteMin());

	}
	@Test(timeout = TIMEOUT)
	public void insert_then_delete() {
		for (int i = 100; i > 0; i--) {
			testHeap.insert(i);
			testHeap.deleteMin();
		}
		assertTrue("heap is empty", testHeap.isEmpty());
	}
	@Test(timeout = TIMEOUT)
	public void multiple_insert() {
		for (int i = 10; i > 0; i--) {
			testHeap.insert(5);
		}
		assertEquals("heap is empty", 5, (long)testHeap.deleteMin());
	}
	
	
	




}
