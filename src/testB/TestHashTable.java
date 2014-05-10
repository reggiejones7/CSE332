package testB;
import static org.junit.Assert.*;

import org.junit.Test;

import phaseB.HashTable.HashBucket;

/**
 * 
 * @author Reggie Jones
 * CSE 332
 *
 * Tests the HashTable class with JUnit tests
 */
public class TestHashTable {

	@Test
	public void test() {
		HashBucket[] h = new HashBucket[10];
		h[0] = new HashBucket(new String("a"));
		System.out.println(h[0].data + h[0].next);
	}

	public class HashBucket {
		public HashBucket next;
		public String data;
		public int count;
		
		public HashBucket(String d) {
			this(d, null);
		}
		
		public HashBucket(String d, HashBucket n) {
			count = 1;
			data = d;
			next = n;
		}
	}
}
