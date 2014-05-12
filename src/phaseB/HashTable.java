package phaseB;
import phaseA.GArrayStack;
import providedCode.*;

/***
 * 
 * @author Reggie Jones
 * @param <E> for generics
 * CSE 332
 * TA: Hye In Kim
 * 
 * HashTable is an implementation of a dictionary that extends DataCounter class. 
 * On a high level implementation note, the hash table uses seperate chaining 
 * to resolve collisions. when HashTable reaches a load factor of 
 * .75 (number of elements / table size), it automatically rehashes for performance 
 * benefits. The HashTable will grow to support atleast an input size of 200,000 
 * elements while still being efficient.
 * 
 */
public class HashTable<E> extends DataCounter<E> {
	//PRIMES allows atleast 200,000 entries
	private static final int[] PRIMES = {47, 101, 211, 431, 863, 1733, 3467, 6947, 13913, 
										27961, 55927, 111949, 223087, 447779};
	public static final double LOADFACTORMAX = .75;
	
	//index of which prime being used for table length 
	private int primeIndex;
	private HashBucket[] table;
	private int size;
	private Comparator<? super E> comparator; 
	private Hasher<E> hasher;
	
	/**
	 * Constructs an empty HashTable
	 * @param c is a 'function object' in order for elements of type E
	 * 	      to be compared.
	 * @param h is a hasher that will be used to do the hashing
	 * 		  for each of the elements in the hashtable
	 */
	@SuppressWarnings("unchecked")
	public HashTable(Comparator<? super E> c, Hasher<E> h) {
		comparator = c;
		hasher = h;
		primeIndex = 0;
		table = (HashBucket[]) new HashTable.HashBucket[PRIMES[primeIndex]];
		size = 0;
	}
	
	/**
	 * incCount increments the count of a bucket if it exists, otherwise
	 * it creates a new bucket and adds it to the HashTable. Rehashes the
	 * table if the load factor exceeds .75
	 * @param data is the element that the count will be incremented on
	 */
	@Override
	public void incCount(E data) {
		double loadFactor = (double) getSize() / table.length;
		if (loadFactor > LOADFACTORMAX && primeIndex < PRIMES.length) {
			reHash();
		}
		
		int hash = hasher.hash(data) % table.length;
		HashBucket bucket = table[hash];
		//simply increment count if already in table
		while (bucket != null) {
			if (comparator.compare(bucket.data, data) == 0) {
				bucket.count++;
				return;
			}
			bucket = bucket.next;
		}
		
		//not in table, so create new hashbucket
		table[hash] = new HashBucket(data, table[hash]);
		size++;
	}
	
	// rehashes the table after approximately doubling the length of
	// table (new table length will always be prime)
	private void reHash() {
		int tblL = table.length;
		@SuppressWarnings("unchecked")
		HashBucket[] auxTable = (HashBucket[]) new HashTable.HashBucket[PRIMES[++primeIndex]];
		for (int i = 0; i < tblL; i++) {
			HashBucket bucket = table[i];
			while (bucket != null) {
				int newHash = hasher.hash(bucket.data) % auxTable.length;
				auxTable[newHash] = new HashBucket(bucket.data, 
													auxTable[newHash], bucket.count);
				bucket = bucket.next;
			}
		}
		table = auxTable;
	}
	
	/**
	 * returns the number of elements in the HashTable
	 * @return int of number of elements
	 */
	@Override
	public int getSize() {
		return size;
	}
	
	/**
	 * returns the count for a given data element (how many times it 
	 * has been incremented in the hashtable) 
	 * @param data is the element of which you want the count of
	 * @return int of how many times the element has been incremented
	 * 			if it's in the hashtable, return 0 if not in hashtable
	 */
	@Override
	public int getCount(E data) {
		int hash = hasher.hash(data) % table.length;
		HashBucket bucket = table[hash];
		while (bucket != null) {
			int cmp = comparator.compare(bucket.data, data);
			if (cmp == 0) {
				return bucket.count;
			}
			bucket = bucket.next;
		}
		return 0;
	}	

	 /**
     * Clients must not increment counts between an iterator's creation and its
     * final use. Data structures need not check this.
     * @return an iterator for the elements.
     */
	@Override
	public SimpleIterator<DataCount<E>> getIterator() {
		// Anonymous inner class that keeps a stack of yet-to-be-processed buckets
    	return new SimpleIterator<DataCount<E>>() {  
    		GStack<HashBucket> stack = new GArrayStack<HashBucket>(); 
    		{
    			for (int i = 0; i < table.length; i++) {
    				if (table[i] != null) {
    					stack.push(table[i]);
    				}
    			}
    		}
    		public boolean hasNext() {
        		return !stack.isEmpty();
        	}
        	public DataCount<E> next() {
        		if(!hasNext()) {
        			throw new java.util.NoSuchElementException();
        		}
        		HashBucket bucket = stack.pop();
        		if(bucket.next != null) {
        			stack.push(bucket.next);
        		}
        		return new DataCount<E>(bucket.data, bucket.count);
        	}
    	};
	}
	
	/**
	 * get the length of the array thats the implementation of hash table. For testing purposes
	 * @return length of hash table
	 */
	public int getTableLength() {
		return table.length;
	}
	
	/**
	 * get an array of number of items in each bucket corresponding to the index
	 * of the internal table array.
	 * @return array of how many elements in each bucket of the hash table
	 */
	public int[] getBucketsCount() {
		int[] counts = new int[getSize()];
		for (int i = 0; i < getSize(); i++) {
			int count = 0;
			HashBucket bucket = table[i];
			while (bucket != null) {
				count++;
				bucket = bucket.next;
			}
			counts[i] = count;
		}
		return counts;
	}
	
	// HashBucket is a private inner class that represents the individual
	// buckets in the HashTable. Each Bucket is implemented as a linked list
	// for seperate chaining
	private class HashBucket {
		public HashBucket next;	//pointer to next HashBucket
		public E data;
		public int count;
		
		// Constructs HashBucket from a given data element with
		// a given next HashBucket
		public HashBucket(E d, HashBucket n) {
			this(d, n, 1);
		}
		
		// Constructs HashBucket from a given data element, HashBucket, and count
		// supplying a count is useful for when rehashing the table
		public HashBucket(E d, HashBucket n, int c) {
			data = d;
			next = n;
			count = c;
		}
	}
}
