package testA;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

import providedCode.Comparator;
import providedCode.DataCounter;
import test.TestDataCounter;
import phaseA.AVLTree;


public class TestAVLTree extends TestDataCounter<String> {
	private static final int TIMEOUT = 3000; //3 seconds

	//Creates AVLTree before each test case
	@Override
	public DataCounter<String> getDataCounter() {
		return new AVLTree<String>(new Comparator<String>() {
			public int compare(String e1, String e2) { return e1.compareTo(e2); }
		});
	}
	
		
	/** Test Height ===========================================================**/

	@Test(timeout = TIMEOUT)
	public void test_height_empty() {
		assertEquals("Height of null is -1 in context of AVL tree", -1, 
				((AVLTree<String>) dc).getHeight());
	}
	
	@Test(timeout = TIMEOUT)
	public void test_height_singe_node() {
		addAndGetHeight("Height of single node is 0 in context of AVL tree", 0, 
				new String[]{"root"});
	}
	
	@Test(timeout = TIMEOUT)
	public void test_height_two_nodes() {
		String[] words = {"first", "second"};
		addAndGetHeight("Added " + Arrays.toString(words), 1, words);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_height_after_left_left_imbalance_on_root() {
		String[] words = {"c", "b", "a"};
		addAndGetHeight("Added " + Arrays.toString(words), 1, words);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_height_after_left_left_imbalance_on_child_of_root() {
		String[] words = {"d", "e", "c", "b", "a"};
		addAndGetHeight("Added " + Arrays.toString(words), 3, words);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_height_after_left_right_imbalance_on_root() {
		String[] words = {"c", "a", "b"};
		addAndGetHeight("Added " + Arrays.toString(words), 1, words);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_height_after_left_right_imbalance_on_child_of_root() {
		String[] words = {"d", "c", "e", "a", "b"};
		addAndGetHeight("Added " + Arrays.toString(words), 3, words);
	}

	@Test(timeout = TIMEOUT)
	public void test_height_after_right_left_imbalance_on_root() {
		String[] words = {"a", "c", "b"};
		addAndGetHeight("Added " + Arrays.toString(words), 1, words);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_height_after_right_left_imbalance_on_child_of_root() {
		String[] words = {"b", "a", "c", "e", "d"};
		addAndGetHeight("Added " + Arrays.toString(words), 3, words);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_height_after_right_right_imbalance_on_root() {
		String[] words = {"a", "b", "c"};
		addAndGetHeight("Added " + Arrays.toString(words), 1, words);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_height_after_right_right_imbalance_on_child_of_root() {
		String[] words = {"b", "a", "c", "d", "e"};
		addAndGetHeight("Added " + Arrays.toString(words), 3, words);
	}
	
	
	/** Test count ===========================================================**/
	
	@Test(timeout = TIMEOUT)
	public void test_count_single_node() {
		String[] words = {"five", "five", "five", "five", "five"};
		addAndCheckCount("Added \"five\" 5 times", "five", 5, words);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_count_multiple_nodes() {
		String[] words = {"abc", "abcd", "ab", "a", "abc", "a", "abcd", "a", "a"};
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("a", 4);
		map.put("ab", 1);
		map.put("abc", 2);
		map.put("abcd", 2);
		
		addAndCheckCounts(map, words);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_count_multiple_nodes_with_lots_of_increments() {
		String[] words = {"the", "pretty", "brown", "dog", "dog", "pretty", "brown", "brown",
						  "the", "the", "the", "brown", "dog", "pretty", "a", "b", "pretty", 
						  "brown", "a", "b", "c", "d", "e", "c", "d", "pretty", "brown", "dog"};
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("the", 4);
		map.put("pretty", 5);
		map.put("brown", 6);
		map.put("dog", 4);
		map.put("a", 2);
		map.put("b", 2);
		map.put("c", 2);
		map.put("d", 2);
		map.put("e", 1);
		
		addAndCheckCounts(map, words);
	}
	
	
	
	/** Test balance ===========================================================**/
	
	@Test(timeout = TIMEOUT)
	public void test_balance_single_node() {
		addAndCheckBalance("Added \"first\" 1 time", new String[]{"first"});
	}
	
	@Test(timeout = TIMEOUT)
	public void test_balance_on_large_input() {
		String[] words = {"first", "second", "a", "a", "b", "first", "last", "second",
							"first", "a", "a", "a", "a", "b", "first", "last", "second",
							"new", "old", "yes", "no", "maybe", "so"};
		addAndCheckBalance("Added " + Arrays.toString(words), words);
	}
	
	
	/** Test when add a new node vs. just incrementing the count for existing node===**/
	
	@Test(timeout = TIMEOUT)
	public void test_add_new_node() {
		dc.incCount("first");
		assertEquals("Added a new node with data \"first\"", 1, dc.getSize());
	}
	
	@Test(timeout = TIMEOUT)
	public void test_add_only_one_node_then_increment_it() {
		addStringsToTree(new String[] {"first", "first", "first", "first", "first"});
		assertEquals("Added a new node with data \"first\" 5 times", 1, dc.getSize());
	}
	
	@Test(timeout = TIMEOUT)
	public void test_add_two_nodes_then_increment_them() {
		addStringsToTree(new String[] {"first", "first", "second", "second", "second",
										"first", "first", "first", "second", "first"});
		assertEquals("Added \"first\" 5 times & \"second\" 6 times", 2, dc.getSize());
	}
	
	
	/** Test overallRoot ===========================================================**/
	
	@Test(expected = NoSuchElementException.class)
	public void test_overallRoot_when_empty() {
		getRootString();
	}
	
	@Test(timeout = TIMEOUT)
	public void test_overallRoot_single_node() {
		dc.incCount("woooo");
		assertEquals("Added \"woooo\"", "woooo", getRootString());
	}
	
	@Test(timeout = TIMEOUT)
	public void test_overallRoot_after_left_left_inbalance_case() {
		String[] words = {"zzz", "yyy", "xxx"};
		addAndCheckRoot("Added " + Arrays.toString(words), "yyy", words);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_overallRoot_after_left_right_inbalance_case() {
		String[] words = {"ccc", "aaa", "bbb"};
		addAndCheckRoot("Added " + Arrays.toString(words), "bbb", words);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_overallRoot_after_right_left_inbalance_case() {
		String[] words = {"aaa", "ccc", "bbb"};
		addAndCheckRoot("Added " + Arrays.toString(words), "bbb", words);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_overallRoot_after_right_right_inbalance_case() {
		String[] words = {"xxx", "yyy", "zzz"};
		addAndCheckRoot("Added " + Arrays.toString(words), "yyy", words);
	}
	
	
	
	/** Private methods ===========================================================**/
	
	private void addAndGetHeight(String message, int expectedHeight, String[] input) {
		for (String s : input) {
			dc.incCount(s);
		}
		assertEquals(message, expectedHeight, ((AVLTree<String>) dc).getHeight());
	}
	
	
	private void addAndCheckBalance(String message, String[] input) {
		addStringsToTree(input);
		assertTrue(message, ((AVLTree<String>) dc).verifyBalance());
	}
	private void addAndCheckCount(String message, String key, int expectedCount, String[] input) {
		addStringsToTree(input);
		assertEquals(message, expectedCount, dc.getCount(key));
	}
	
	private void addAndCheckCounts(Map<String, Integer> map, String[] input) {
		addStringsToTree(input);
		for (String k : map.keySet()) {
			int occurs = (int) map.get(k);
			assertEquals("Added \"" + k + "\" " + occurs + " times", occurs, dc.getCount(k));
		}
	}
	
	private void addStringsToTree(String[] input) {
		for (String s : input) {
			dc.incCount(s);
		}
	}
	
	private void addAndCheckRoot(String message, String expectedData, String[] input) {
		addStringsToTree(input);
		assertEquals(message, expectedData, getRootString());
	}
	
	private String getRootString() {
		return ((AVLTree<String>) dc).getRootData();
	}
}
