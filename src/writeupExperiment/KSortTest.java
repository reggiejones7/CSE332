package writeupExperiment;

public class KSortTest {

	private static final int NUM_TESTS = 30;
	private static final int NUM_WARMUP = 10;
	private static final int K_VAL = 20;
	
	public static void main(String[] args) {

		
		for (int i = 1; i < 253; i++) {
			int currentK = K_VAL * i;
			String K = Integer.toString(currentK);
			String[] arguments = {"-h", "-k", K, "hamlet.txt"};
			String[] args2 = {"-h", "-os", "hamlet.txt"};
			
			double time1 = getAverageRuntime(arguments);
			double time2 = getAverageRuntime(args2);
		//	System.out.println(time1);   //topksort time
			System.out.println(time2);   //quicksort time
		}
	}
	

	
	
	//return average runtime in miliseconds.
	private static double getAverageRuntime(String[] args) {
        double totalTime = 0;
        for(int i=0; i<NUM_TESTS; i++) {
          long startTime = System.currentTimeMillis();
          WordCountK.main(args);
          long endTime = System.currentTimeMillis();
          if(NUM_WARMUP <= i) {                    // Throw away first NUM_WARMUP runs to encounter JVM warmup
            totalTime += (endTime - startTime);
          }
        }
        return totalTime / (NUM_TESTS-NUM_WARMUP);  
      }
}
