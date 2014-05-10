package phaseB;
import phaseA.GArrayStack;
import providedCode.*;


/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. You may implement any kind of HashTable discussed in class; the
 *    only restriction is that it should not restrict the size of the
 *    input domain (i.e., it must accept any key) or the number of
 *    inputs (i.e., it must grow as necessary).
 * 2. You should use this implementation with the -h option.
 * 3. Your HashTable should rehash as appropriate (use load factor as
 *    shown in the class).
 * 4. To use your HashTable for WordCount, you will need to be able to
 *    hash strings. Implement your own hashing strategy using charAt
 *    and length. Do NOT use Java's hashCode method.
 * 5. HashTable should be able to grow at least up to 200,000. We are
 *    not going to test input size over 200,000 so you can stop
 *    resizing there (of course, you can make it grow even larger but
 *    it is not necessary).
 * 6. We suggest you to hard code the prime numbers. You can use this
 *    list: http://primes.utm.edu/lists/small/100000.txt NOTE: Make
 *    sure you only hard code the prime numbers that are going to be
 *    used. Do NOT copy the whole list!
 * TODO: Develop appropriate JUnit tests for your HashTable.
 */

/***
 * 
 * @author Reggie Jones
 * @param <E>
 * CSE 332
 * TA: Hye In Kim
 * --fill in class description--
 * using seperate chaining, resize when .75 load factor
 */
public class HashTable<E> extends DataCounter<E> {
	private static final int[] PRIMES = {47, 101, 211, 431, 863, 1733, 3467, 6947, 13913, 27961, 55927, 111949, 223087, 447779};
	private static final int PRIMEINDEXMAX = 13; //will allow atleast 200,000 entries in hashtable
	private static final double LOADFACTORMAX = .75;
	
	private int primeIndex;
	private HashBucket[] table;
	private int size;
	private Comparator<? super E> comparator; 
	private Hasher<E> hasher;
	
	@SuppressWarnings("unchecked")
	public HashTable(Comparator<? super E> c, Hasher<E> h) {
		comparator = c;
		hasher = h;
		primeIndex = 0;
		table = (HashBucket[]) new HashTable.HashBucket[PRIMES[primeIndex]];
		size = 0;
	}
	
	@Override
	public void incCount(E data) {
		double loadFactor = (double) getSize() / table.length;
		//check if table needs to be rehashed
		if (loadFactor > LOADFACTORMAX && primeIndex < PRIMEINDEXMAX) {
			//reHash();
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
	}
	
	private void reHash() {
		int tblL = table.length;
		@SuppressWarnings("unchecked")
		HashBucket[] auxTable = (HashBucket[]) new HashTable.HashBucket[PRIMES[++primeIndex]];
		for (int i = 0; i < tblL; i++) {
			HashBucket bucket = table[i];
			while (bucket != null) {
				int newHash = hasher.hash(bucket.data) % auxTable.length;
				auxTable[newHash] = new HashBucket(bucket.data, auxTable[newHash]);
				bucket = bucket.next;
			}
		}
		table = auxTable;
		//debugging
		/*for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				System.out.println(table[i].data);
			}
		}*/
	}
	@Override
	public int getSize() {
		return size;
	}

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
		//bucket is null, data is not in the hash table-maybe throw error instead?
		return -1;
	}	

	/** {@inhericDoc} */ //??is this aight?
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
	
	public class HashBucket {
		public HashBucket next;
		public E data;
		public int count;
		
		public HashBucket(E d) {
			this(d, null);
		}
		
		public HashBucket(E d, HashBucket n) {
			size++;
			count = 1;
			data = d;
			next = n;
		}
	}
}
