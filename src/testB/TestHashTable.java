package testB;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import phaseB.HashTable;
import phaseB.StringHasher;
import providedCode.Comparator;
import providedCode.DataCount;
import providedCode.DataCounter;
import providedCode.SimpleIterator;
import test.TestDataCounter;

/**
 * 
 * @author Reggie Jones
 * CSE 332
 *
 * Tests the HashTable class with JUnit tests
 */
public class TestHashTable extends TestDataCounter<String> {
	private static final int TIMEOUT = 3000;
	
	//Creates AVLTree before each test case
	@Override
	public DataCounter<String> getDataCounter() {
		return new HashTable<String>(new Comparator<String>() {
			public int compare(String e1, String e2) { return e1.compareTo(e2); }
		}, new StringHasher());
	}
	
	
	
	/** test size ===========================================================*/
	@Test(timeout = TIMEOUT)
	public void test_size_empty() {
		assertEquals("No elements added", dc.getSize(), 0);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_size_1() {
		dc.incCount(new String("heyo"));
		assertEquals("Added 'heyo'", dc.getSize(), 1);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_size_when_duplicate_words() {
		addWords("duplicate words duplicate times times");
		assertEquals("Added duplicate words", dc.getSize(), 3);
	}
	
	
	/** test getCount ===========================================================*/
	
	@Test(timeout = TIMEOUT)
	public void test_count_when_empty() {
		assertEquals("Nothing added", dc.getCount(new String("noooo")), 0);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_count_when_element_is_not_in_table() {
		addWords("Adding numerous words but not the one we are looking for");
		assertEquals("Nothing added", dc.getCount(new String("noooo")), 0);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_count_when_multiple_elements() {
		addWords("Adding numerous numerous words into the hash table");
		assertEquals(dc.getCount(new String("Adding")), 1);
		assertEquals(dc.getCount(new String("numerous")), 2);
		assertEquals(dc.getCount(new String("words")), 1);
		assertEquals(dc.getCount(new String("into")), 1);
		assertEquals(dc.getCount(new String("the")), 1);
		assertEquals(dc.getCount(new String("hash")), 1);
		assertEquals(dc.getCount(new String("table")), 1);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_when_element_duplicated_many_times() {
		addWords("repeat int the repeat word that's repeat idly repeat ed");
		assertEquals(dc.getCount(new String("repeat")), 4);
	}
	
	/** test StringHasher ===========================================================*/
	
	@Test(timeout = TIMEOUT)
	public void test_same_hash_for_same_value() {
		StringHasher h = new StringHasher();
		int hash1 = h.hash("happy hashing");
		int hash2 = h.hash("happy hashing");
		assertEquals("Same values should always have same hash", hash1, hash2);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_diff_hash_for_similar_values() {
		StringHasher h = new StringHasher();
		int hash1 = h.hash("happyhashing");
		int hash2 = h.hash("happyhashingg");
		assertNotEquals("Similar values should generally not have same hash", hash1, hash2);
	}
	
	
	/** test internal rehashing ===========================================================*/
	
	@Test(timeout = TIMEOUT)
	public void test_dont_rehash_when_under_load_factor_max() {
		int before = tblLength();
		String s = "I'm going to add a couple of words to the hash table";
		addWords(s);
		int after = tblLength();
		assertEquals("No rehashing should occur", before, after);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_rehash_when_over_load_factor_max() {
		int before = tblLength();
		// +2 is for rounded down, and hashtable rehashes the first
		//time you call incCount once it's already > LOADFACTORMAX
		int words = (int) (HashTable.LOADFACTORMAX * tblLength()) + 2;
		addWords(words);
		int after = tblLength();
		assertNotEquals("Rehashing should occur", before, after);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_rehash_when_over_load_factor_max_twice() {
		int words1 = (int) (HashTable.LOADFACTORMAX * tblLength()) + 2;
		addWords(words1);
		int firstRehash = tblLength();
		int words2 = (int) (HashTable.LOADFACTORMAX * tblLength()) + 2;
		addWords(words2 - words1);
		int secondRehash = tblLength();
		assertNotEquals("Secon Rehashing should occur", firstRehash, secondRehash);
	}
	
	
	/** test hashing distribution ===========================================================*/
	
	@Test(timeout = TIMEOUT)
	public void test_hashing_dist_on_small_set_of_inputs() {
		testHashingDistribution(30);
	}
	
	@Test(timeout = 10000)
	public void test_hashing_dist_on_large_set_of_inputs() {
		testHashingDistribution(10000);
	}
	
	private void testHashingDistribution(int words) {
		addWords(words);
		//rough percentages using my intuition of distribution w/o
		//any formal math of choosing these percentages
		int bucketsWithAtleast10PercentOfElements = 0;
		int bucketsWithAtleast50PercentOfElements = 0;
		int[] counts = ((HashTable<String>) dc).getBucketsCount();
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] > dc.getSize() * .50) {
				bucketsWithAtleast50PercentOfElements++;
			}
			if (counts[i] > dc.getSize() * .10) {
				bucketsWithAtleast10PercentOfElements++;
			}
		}
		assertEquals("Some bucket has atleast 50% of all words", bucketsWithAtleast50PercentOfElements, 0);
		assertTrue("More than one bucket has atleast 10% of all words", bucketsWithAtleast10PercentOfElements <= 1);
	}
	
	
	/** test iterator ===========================================================*/
	@Test(timeout = TIMEOUT)
	public void test_iterator_has_next_when_nothing_added() {
		SimpleIterator<DataCount<String>> itr = dc.getIterator();
		assertFalse(itr.hasNext());
	}
	
	@Test(timeout = TIMEOUT)
	public void test_iterator_has_next_when_something_added() {
		addWords("word");
		SimpleIterator<DataCount<String>> itr = dc.getIterator();
		assertTrue(itr.hasNext());
		itr.next();
		assertFalse(itr.hasNext());
	}
	
	@Test(timeout = TIMEOUT)
	public void test_iterator_next_give_correct_value() {
		addWords("word");
		SimpleIterator<DataCount<String>> itr = dc.getIterator();
		assertTrue(itr.hasNext());
		DataCount<String> next = itr.next();
		assertEquals("should be 'word'", "word", next.data);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void test_iterator_barf_when_next_but_nothing_added() {
		SimpleIterator<DataCount<String>> itr = dc.getIterator();
		itr.next();
	}
	
	@Test(timeout = TIMEOUT)
	public void test_iterator_gives_correct_number_of_values() {
		addWords("There are seven words in this sentence");
		SimpleIterator<DataCount<String>> itr = dc.getIterator();
		int count = 0;
		while(itr.hasNext()) {
			itr.next();
			count++;
		}
		assertEquals("seven words were added", 7, count);
	}
	

	/** private helpers ===========================================================*/
	
	//add all words in a given string to the dc datacounter
	private void addWords(String input) {
		Scanner s = new Scanner(input);
		while (s.hasNext()) {
			dc.incCount(s.next());
		}
		s.close();
	}
	
	//so you don't have to cast datacounter to hashtable
	private int tblLength() {
		return ((HashTable<String>) dc).getTableLength();
	}
		
	//generate random word and add to hashtable a given number of times
	// good use to make table rehash
	private void addWords(int words) {
		String s = "";
		for (int i = 0; i < words; i++) {
			//randomize =~ assuming no repeated words will occur
			Random r = new Random();
			String word = "";
			for (int j = 0; j < 5; j++) {
				word += (char) (r.nextInt(26) + 65); 
			}
			s += " " + word;
		}
		addWords(s);
	}
}
