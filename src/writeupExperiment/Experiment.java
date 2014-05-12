package writeupExperiment;

import java.io.*;
import java.util.*;

/**
 * 
 * @author Reggie Jones
 * CSE 332
 * TA: Hye In Kim
 * 
 * Experiment is for experimenting and testing the run times of combinations
 * of the data structures and sorting algos we implemented in phases A & B.
 * All combinations are tested against 4 different files- the-new-atlantis, 
 * hamlet, quest which are twice as many words as the file listed before it.
 * Also, same.txt (all the same word) which is approximately same size as hamlet 
 * to test not only how word count affects the runtime, but also how count of 
 * unique words affects it.
 * 
 */
public class Experiment {

	private static final int NUM_TESTS = 30;
	private static final int NUM_WARMUP = 10;
	
	private static Map<String, String[]> combinations;

	public static void main(String[] args) {
	    combinations = new TreeMap<String, String[]>();
	    
	    putCombinations("the-new-atlantis.txt"); //17k words
	    putCombinations("same.txt");  //34k of the word 'same'
	    putCombinations("hamlet.txt"); //34k words
	    putCombinations("quest.txt"); //68k words
	    
	    
	    for (String descript: combinations.keySet()) {
	    	double aveTime = getAverageRuntime(combinations.get(descript));
	    	System.out.println(descript + " " + (int)aveTime);
	    }
	    
	    
	    //Experimentation part 2- Hashing
	    String[] insertion = new String[]{"-h", "-is", "hamlet.txt"};
	    String[] other = new String[]{"-h", "-os", "hamlet.txt"};
	    String[] heap = new String[]{"-h", "-hs", "hamlet.txt"};
	    double insertionTime = getAverageRuntime(insertion);
	    double otherTime = getAverageRuntime(other);
	    double heapTime = getAverageRuntime(heap);
	    
	    try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("hashOutput.txt", true)));
		    out.println("\n==================");
		    out.println("Insertion " + insertionTime);
		    out.println("Other " + otherTime);
		    out.println("Heap " + heapTime);
		    out.close();
	    } catch (IOException e) {
	    	System.out.println("couldn't find hashOutput.txt");
	    }
	}
	
	//puts all combos of structures/sorts and a given filename
	//into the combinations map
	private static void putCombinations(String fileName) {
		String name = fileName.substring(0, fileName.lastIndexOf("."));
		//keys are formatted datastructureSorterFilename
		combinations.put("hashInsertion" + name , new String[]{"-h", "-is", fileName});
	    combinations.put("hashHeap" + name, new String[]{"-h", "-hs", fileName});
	    combinations.put("hashOther" + name, new String[]{"-h", "-os", fileName});
	    
	    combinations.put("bstInsertion" + name, new String[]{"-b", "-is", fileName});
	    combinations.put("bstHeap" + name, new String[]{"-b", "-hs", fileName});
	    combinations.put("bstOther" + name, new String[]{"-b", "-os", fileName});
	    
	    combinations.put("avlInsertion" + name, new String[]{"-a", "-is", fileName});
	    combinations.put("avlHeap" + name, new String[]{"-a", "-hs", fileName});
	    combinations.put("avlOther" + name, new String[]{"-a", "-os", fileName});
	    
	    combinations.put("moveInsertion" + name, new String[]{"-m", "-is", fileName});
	    combinations.put("moveHeap" + name, new String[]{"-m", "-hs", fileName});
	    combinations.put("moveOther" + name, new String[]{"-m", "-os", fileName});
	}
	
	//return average runtime in miliseconds.
	private static double getAverageRuntime(String[] args) {
        double totalTime = 0;
        for(int i=0; i<NUM_TESTS; i++) {
          long startTime = System.currentTimeMillis();
          WordCount.main(args);
          long endTime = System.currentTimeMillis();
          if(NUM_WARMUP <= i) {                    // Throw away first NUM_WARMUP runs to encounter JVM warmup
            totalTime += (endTime - startTime);
          }
        }
        return totalTime / (NUM_TESTS-NUM_WARMUP);  
      }

}
