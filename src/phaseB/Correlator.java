package phaseB;

import java.io.IOException;

import phaseA.AVLTree;
import phaseA.MoveToFrontList;
import phaseA.StringComparator;
import providedCode.BinarySearchTree;
import providedCode.DataCount;
import providedCode.DataCounter;
import providedCode.FileWordReader;
import providedCode.SimpleIterator;



/**
 * 
 * @author Reggie Jones
 * CSE 332 Proj. 2
 * TA: Hye In Kim
 *
 * The Correlator class purpose is for give a quantity to the similarity between
 * to different text files.
 * The equation for quantifying the relationship is squaring the difference between frequencies 
 * of each word that show up in both files, then adding them all together. The outliars (words 
 * used very frequently or very sparingly) are not taken into consideration.
 * Prints out the value that represents this similarity relationship.
 */

public class Correlator {
	
	//Min and Max frequency for tossing out outliars
	private static final double MINFREQ = .0001;
	private static final double MAXFREQ = .01;
	
	//total words for each file, respectively
    private static int totalWords1;
    private static int totalWords2;

	public static void main(String[] args) {
    	if (args.length != 3) {
            argError("length");
        }
    	totalWords1 = 0;
    	totalWords2 = 0;
    	DataCounter<String> counter1 = null;
    	DataCounter<String> counter2 = null;
    	String arg0 = args[0];
    	switch (arg0) {
    		case "-b": counter1 = new BinarySearchTree<String>(new StringComparator());
    				   counter2 = new BinarySearchTree<String>(new StringComparator());
    				   break;
    		case "-a": counter1 = new AVLTree<String>(new StringComparator());
    				   counter2 = new AVLTree<String>(new StringComparator());
    				   break;
    		case "-m": counter1 = new MoveToFrontList<String>(new StringComparator());
    				   counter2 = new MoveToFrontList<String>(new StringComparator());
    			  	   break;
    		case "-h": counter1 = new HashTable<String>(new StringComparator(), new StringHasher());
    				   counter2 = new HashTable<String>(new StringComparator(), new StringHasher());
    		 		   break;
    		default: argError(arg0);
    	}
    	String file1 = args[1];
    	String file2 = args[2];
    	countWords(file1, counter1, 1);
    	countWords(file2, counter2, 2);
    	
    	SimpleIterator<DataCount<String>> iterator = counter1.getIterator();
    	double variance = 0.0;
    	while (iterator.hasNext()) {
    		String word = iterator.next().data;
    		int occur1 = counter1.getCount(word);
    		int occur2 = counter2.getCount(word);
    		if (occur1 > 0 && occur2 > 0) {
	    		double freq1 = (double) occur1 / totalWords1;
	    		double freq2 = (double) occur2 / totalWords2;
	    		//ignore words with too high or low of frequency
	    		if (freq1 <= MAXFREQ  && freq2 <= MAXFREQ && freq1 >= MINFREQ && freq2 >= MINFREQ) {
	    			variance += Math.pow((freq1 - freq2), 2);
	    		}
    		}
    	}
    	
    	System.out.println(variance);  // IMPORTANT: Do not change printing format. Just print the double.
    }
    
	// countWords goes through a given file and 
	// @param file name of file
	// @param counter a DataCounter to count the data
	// @param fileNum to increment the correct totalWords counter
    private static void countWords(String file, DataCounter<String> counter, int fileNum) {
        try {
            FileWordReader reader = new FileWordReader(file);
            String word = reader.nextWord();
            while (word != null) {
                counter.incCount(word);
                word = reader.nextWord();
                if (fileNum == 1) {
                	totalWords1++;
                } else {
                	totalWords2++;
                }
            }
        }catch (IOException e) {
            System.err.println("Error processing " + file + " " + e);
            System.exit(1);
        }
    }
    
    // System exits from incorrect arguments
    private static void argError(String arg) {
    	System.err.println("Incorrect arg \"" + arg + "\"");
    	System.err.println("Usage: [ -b | -a | -m | -h ] <filename> <filename>");
		System.exit(1);
    }
}
