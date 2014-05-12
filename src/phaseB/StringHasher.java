package phaseB;
import providedCode.Hasher;

/**
 * 
 * @author Reggie Jones
 * CSE 332
 * TA: Hye In Kim
 * 
 * StringHasher class is used for hashing Strings, while implementing Hasher<String>
 *
 */
public class StringHasher implements Hasher<String> {
	
	/**
	 * hash returns a (positive) integer of the hash of a given String
	 * @param s is a String to be hashed
	 * @return an int that represents the hash of the String
	 */
	@Override
	public int hash(String s) {
		int hash = 0;
		for (int i = 0; i < s.length(); i++) {
			hash += (int) (s.charAt(i) * Math.pow(37, i));
		}
		return Math.abs(hash);
	}
}
