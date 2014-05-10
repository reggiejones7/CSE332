package phaseB;
import providedCode.Hasher;

/**
 * 
 * @author Reggie Jones
 * CSE 332
 * TA: Hye In Kim
 * 
 * --class description--
 *
 */
public class StringHasher implements Hasher<String> {
	
	@Override
	public int hash(String s) {
		//can use length and charat
		int hash = 0;
		for (int i = 0; i < s.length(); i++) {
			hash += (int) (s.charAt(i) * Math.pow(37, i));
		}
		return Math.abs(hash);
	}
}
