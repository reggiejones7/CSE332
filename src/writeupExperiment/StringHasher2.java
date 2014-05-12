package writeupExperiment;
import providedCode.Hasher;

/**
 * 
 * @author Reggie Jones
 * CSE 332
 * TA: Hye In Kim
 * 
 * StringHasher2 class is used for hashing Strings, while implementing Hasher<String>
 * --For experimental purposes. hash function is intentionally very basic--
 *
 */
public class StringHasher2 implements Hasher<String> {
	
	/**
	 * hash returns a positive integer of the hash of a given String
	 * @param s is a String to be hashed
	 * @return an int that represents the hash of the String
	 */
	@Override
	public int hash(String s) {
		int hash = 0;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0) {
				hash += 10 * ((int) s.charAt(i));
			} else {
				hash += (int) s.charAt(i);
			}
		}
		return Math.abs(hash);
	}
}
