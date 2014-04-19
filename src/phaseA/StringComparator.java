package phaseA;
import providedCode.*;


/**
 * @author Reggie Jones
 * 
 * StringComparator is used for comparing 2 strings to each other
 */

public class StringComparator implements Comparator<String>{

	/**
	 * simply compares 2 strings to each other using standard alphabetic conventions
	 * @param s1 a string that you are comparing
	 * @param s2 another string that you are comparing
	 * @return -1 if s1 comes before s2, 1 if s1 comes after s2, and 0 if they are equal
	 */
	@Override
	public int compare(String s1, String s2) {
		int i = 0;
		while (i < s1.length() && i < s2.length()) {
			if (s1.charAt(i) < s2.charAt(i)) {
				return -1;
			} else if (s1.charAt(i) > s2.charAt(i)) {
				return 1;
			}
			i++;
		}
		//ran out of chars to compare in at least one of the strings
		if (s1.length() == s2.length()) {
			return 0;
		} else if (s1.length() == 0) {
			return -1;
		} else {
			return 1;
		}
	}
}
