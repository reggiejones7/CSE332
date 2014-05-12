package testA;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import phaseA.AVLTree;
import phaseA.MoveToFrontList;
import providedCode.Comparator;
import providedCode.DataCounter;

/**
 * 
 * @author Tristan Riddell
 *
 */

public class TestMoveToFrontList {
	private static final int TIMEOUT = 3000; //3 seconds
	MoveToFrontList<Integer> tester;

	@Before
	public void startup() {
		tester = new MoveToFrontList<Integer>(new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) {
				return e1-e2;
			}
			
		});
	}
	
	@Test(timeout = TIMEOUT)
	public void test_empty_size() {
		assertEquals("empty",0,tester.getSize());
	}

	@Test(timeout = TIMEOUT)
	public void test_oneinsert_size() {
		tester.incCount(0);
		assertEquals("1",1,tester.getSize());
	}
	@Test(timeout = TIMEOUT)
	public void test_threeinsert_size() {
		tester.incCount(0);
		tester.incCount(1);
		tester.incCount(2);
		assertEquals("size should be 3", 3, tester.getSize());
	}

	@Test(timeout = TIMEOUT)
	public void test_count_inc() {
		tester.incCount(0);
		assertEquals("count of element added once is 1",1,tester.getCount(0));
	}
	@Test(timeout = TIMEOUT)
	public void test_300count_and_size() {
		for (int i = 0; i < 300; i++) {
			tester.incCount(0);
		}
		assertEquals("count of element added 300 times is 300",300,tester.getCount(0));
		assertEquals("size is still 1",1,tester.getSize());

	}
	@Test(timeout = TIMEOUT)
	public void test_count_out_of_order() {
		tester.incCount(1);
		tester.incCount(2);
		tester.incCount(3);
		tester.incCount(1);
		tester.incCount(2);
		tester.incCount(3);
		tester.incCount(1);
		tester.incCount(2);
		tester.incCount(3);

		assertEquals("count of element is 3",3,tester.getCount(1));
		assertEquals("count of element is 3",3,tester.getCount(2));
		assertEquals("count of element is 3",3,tester.getCount(3));
	}
	@Test(timeout = TIMEOUT)
	public void test_moved_to_front() {
		tester.incCount(1);
		tester.incCount(2);
		tester.incCount(3);
		tester.incCount(1);
		assertEquals("1 should be put back to the front", 1, (long)tester.getFront());
	}
	@Test(timeout = TIMEOUT)
	public void test_getCount_moved_to_front() {
		tester.incCount(1);
		tester.incCount(2);
		tester.incCount(3);
		tester.incCount(1);
		tester.getCount(3);
		assertEquals("3 should be put back to the front", 3, (long)tester.getFront());
	}
	@Test(timeout = TIMEOUT)
	public void test_dynamic_front_moves() {
		tester.incCount(1);
		tester.incCount(2);
		assertEquals("2 should be put back to the front", 2, (long)tester.getFront());
		tester.incCount(3);
		assertEquals("3 should be put back to the front", 3, (long)tester.getFront());
		tester.incCount(1);
		assertEquals("1 should be put back to the front", 1, (long)tester.getFront());
		tester.getCount(3);
		assertEquals("3 should be put back to the front", 3, (long)tester.getFront());
		tester.incCount(2);
		assertEquals("2 should be put back to the front", 2, (long)tester.getFront());
	}
}
