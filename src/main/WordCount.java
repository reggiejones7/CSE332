package main;
import java.io.IOException;

import phaseA.*;
import phaseB.HashTable;
import phaseB.StringHasher;
import phaseB.topKComparator;
import providedCode.*;

/**
 * An executable that counts the words in a files and prints out the counts in
 * descending order. You will need to modify this file.
 */
public class WordCount {

	/*
	 * counts the words in a given file and adds them to the given DataCounter
	 * @param file of words you want counted
	 * @param counter, where you want the count of words to be added to
	 */
    private static void countWords(String file, DataCounter<String> counter) {
        try {
            FileWordReader reader = new FileWordReader(file);
            String word = reader.nextWord();
            while (word != null) {
                counter.incCount(word);
                word = reader.nextWord();
            }
        }catch (IOException e) {
            System.err.println("Error processing " + file + " " + e);
            System.exit(1);
        }
    }
    
    /*
     * get an array of all the DataCount objects in a given DataCounter
     * @param counter that contains DataCount objects
     * @return an array of DataCount objects containing each unique word
     */
 	@SuppressWarnings("unchecked")
	private static <E> DataCount<E>[] getCountsArray(DataCounter<E> counter) {
 		DataCount<E>[] dataCount = (DataCount<E>[]) new DataCount[counter.getSize()];
 		SimpleIterator<DataCount<E>> iterator = counter.getIterator();
 		int i = 0;
 		while (iterator.hasNext()) {
 			dataCount[i] = iterator.next();
 			i++;
 		}
 		return dataCount;
 	}
    
 	// Prints data in of a given array of counts in the precise printing
 	// format specified
    private static void printDataCount(DataCount<String>[] counts) {
    	for (DataCount<String> c : counts) {
            System.out.println(c.count + "\t" + c.data);
        }
    }
    
    // Overloaded method, used when it's topK sort 
    private static void printDataCount(DataCount<String>[] counts, int k) {
    	if (k > counts.length) {
    		k = counts.length;
    	}
    	for (int i = 0; i < k; i++) {
            System.out.println(counts[i].count + "\t" + counts[i].data);
    	}
    }
    
    // Sorts a given DataCount counts by a given type of sort
    // sort can be "insertion", "heap", "other", or "topK" where K is an integer
    private static void Sort(String sort, DataCount<String>[] counts) {
    	DataCountStringComparator comparator = new DataCountStringComparator();
    	topKComparator topKComp = new topKComparator();
    	if (sort.equals("insertion")) {
    		Sorter.insertionSort(counts, comparator);
    	} else if (sort.equals("heap")) {
    		Sorter.heapSort(counts, comparator);
    	} else if (sort.equals("other")) {
    		Sorter.otherSort(counts, comparator);
    	} else if (sort.substring(0, 3).equals("top")) {
    		Sorter.topKSort(counts, topKComp, Integer.parseInt(sort.substring(3)));
    	}
    }
    
    // System exits from incorrect command line arguments
    private static void argError(String arg) {
    	System.err.println("Incorrect arg \"" + arg + "\"");
    	System.err.println("Usage: [ -b | -a | -m | -h ] [ -is | -hs | -os | -k <number>] <filename>");
		System.exit(1);
    }
    
    
    /** 
     *  Outputs the frequency of each word in a file in descending order.
     *  If two words occur the same amount of times, they will be output
     *  according to alphabetical order.
     *  @param args in the form of 
     *  		[ -b | -a | -m | -h ] [ -is | -hs | -os | -k <number>] <filename>
 	 */
    public static void main(String[] args) {
        if (args.length != 3 && args.length != 4) {
            argError("length");
        }
        
    	DataCounter<String> counter = null;
    	String arg0 = args[0];
    	switch (arg0) {
    		case "-b": counter = new BinarySearchTree<String>(new StringComparator());
    				   break;
    		case "-a": counter = new AVLTree<String>(new StringComparator());
    				   break;
    		case "-m": counter = new MoveToFrontList<String>(new StringComparator());
    			  	   break;
    		case "-h": counter = new HashTable<String>(new StringComparator(), new StringHasher());
    		 		   break;
    		default: argError(arg0);
    	}

    	String arg1 = args[1];
    	String sort = "";
    	String file = args[2];
    	switch(arg1) {
    		case "-is": sort = "insertion";
    					break;
    		case "-hs": sort = "heap";
    					break;
    		case "-os": sort = "other";
    					break;  
    		case "-k":  sort = "top" + Integer.parseInt(args[2]);
    		
    				   file = args[3];	
    				   break;
    		default: argError(arg1);   
    	}
    	
        countWords(file, counter); 
        DataCount<String>[] counts = getCountsArray(counter);
        Sort(sort, counts);
        if (sort.substring(0, 3).equals("top")) {
        	printDataCount(counts, Integer.parseInt(sort.substring(3)));
        } else  {
        	printDataCount(counts);
        }
    }
}
