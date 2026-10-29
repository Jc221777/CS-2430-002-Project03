import java.util.Arrays;
import java.util.Comparator;

public class Main {
	
	public record Experiment(String name, int weight, int rating) {
		public double ratio() {
			return (double) rating / weight;
		}
	};
	
    private Experiment[] Experiments = {
        new Experiment("Cloud Patterns", 36, 5),
        new Experiment("Solar Flares", 264, 9),
        new Experiment("Solar Power", 188, 6),
        new Experiment("Binary Stars", 203, 8),
        new Experiment("Relativity", 104, 8),
        new Experiment("Seed Viability", 7, 4),
        new Experiment("Sun Spots", 90, 2),
        new Experiment("Mice Tumors", 65, 8),
        new Experiment("Microgravity Plant Growth",  75, 5),
        new Experiment("Micrometeorites", 170, 9),
        new Experiment("Cosmic Rays", 80, 7),
        new Experiment("Yeast Fermentation", 27, 4),
    };

    
    

    /**
 	* Selects experiments using a greedy strategy.
 	* Sorts experiments by the provided comparator and
 	* selects each experiment if it fits within 700 kg.
 	*
 	* @param comparator Determines experiment priority.
 	* @return Array of selected experiments.
 	*/
    private Experiment[] greedy(Comparator<Experiment> comparator) {

    	Experiment[] sorted = Experiments.clone();

    	Arrays.sort(sorted, comparator);

    	Experiment[] selected = new Experiment[sorted.length];

    	int totalWeight = 0;
    	int selectedCount = 0;

    	for (Experiment experiment : sorted) {

    		int weight = experiment.weight;

    		if (totalWeight + weight <= 700) {
    			selected[selectedCount++] = experiment;
            	totalWeight += weight;
    		}
    }

    	return Arrays.copyOf(selected, selectedCount);
}



    /**
     * Finds the best subset of experiments using brute force.
     * Checks every possible combination of experiments and chooses the best.
     *
     * @return The optimal set of experiments.
     */
    private Experiment[] bruteForce() {
        final Experiment[] experiments = Experiments;
        final short subsetCount = (short)Math.powExact(2, experiments.length);
        short bestRating = 0;
        Experiment[] bestSubset = null;

        // Iterate over all subsets
        short tracker = 0;
        while (++tracker < subsetCount) {
            // Gather tracked subset
            Experiment[] subset = new Experiment[Long.bitCount(tracker)];
            for (short i = 1, j = 0, maxSubsetIndex = -1; i < subsetCount; i <<= 1, ++j) {
                if ((tracker & i) == 0) continue;
                subset[++maxSubsetIndex] = experiments[j];
            }
            // Evaluate this subset
            short weight = 0;
            short rating = 0;
            for (Experiment experiment : subset) {
                weight += experiment.weight;
                rating += experiment.rating;
            }
            // Update bestSubset if this subset is better
            if (weight <= 700 && rating > bestRating) {
                bestSubset = subset;
                bestRating = rating;
            }
        }
        return bestSubset;
    }


    /**
     * Prints an array.
     * @param array The array to print.
     * @param <T> The type of values in the array.
     */
    private void printResult(Experiment[] array) {
    	int total_weight = 0;
    	int total_rating = 0;
        for (Experiment t : array) {
        	System.out.print(t.name);
        	System.out.print(" (Weight: " + t.weight);
        	System.out.println(" | Rating: " + t.rating + ")");
        	total_weight += t.weight;
        	total_rating += t.rating;
        }
        System.out.println();
        System.out.println("Total Weight: " + total_weight);
        System.out.println("Total Rating: " + total_rating);
    }

    void main() {
        System.out.println("Brute Force");
        System.out.println("------------------------------------------");
        printResult(bruteForce());
        System.out.println();
        System.out.println("Weight");
        System.out.println("------------------------------------------");
        printResult(greedy(Comparator.comparingInt(Experiment::weight)));
        System.out.println();
        System.out.println("Rating");
        System.out.println("------------------------------------------");
        printResult(greedy(Comparator.comparingInt(Experiment::rating).reversed()));
        System.out.println();
        System.out.println("Ratio");
        System.out.println("------------------------------------------");
        printResult(greedy(Comparator.comparingDouble(Experiment::ratio).reversed()));
        System.out.println();
    }
}