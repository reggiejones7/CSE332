package writeupExperiment;

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
	    putCombinations("same.txt");  //34k of the word 'the'
	    putCombinations("hamlet.txt"); //34k words
	    putCombinations("quest.txt"); //68k words
	    
	    
	    for (String descript: combinations.keySet()) {
	    	double aveTime = getAverageRuntime(combinations.get(descript));
	    	System.out.println(descript + " " + (int)aveTime);
	    }
	
	}
	
	//puts the combos of structures/sorts and a given filename
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
	    
	    combinations.put("moveInsertion" + name, new String[]{"-h", "-is", fileName});
	    combinations.put("moveHeap" + name, new String[]{"-h", "-hs", fileName});
	    combinations.put("hashOther" + name, new String[]{"-h", "-os", fileName});
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
