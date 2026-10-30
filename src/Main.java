import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public record Experiment(String name, int weight, int rating) {
        final static Experiment CLOUD_PATTERNS            = new Experiment("Cloud Patterns",            36,  5);
        final static Experiment SOLAR_FLARES              = new Experiment("Solar Flares",              264, 9);
        final static Experiment SOLAR_POWER               = new Experiment("Solar Power",               188, 6);
        final static Experiment BINARY_STARS              = new Experiment("Binary Stars",              203, 8);
        final static Experiment RELATIVITY                = new Experiment("Relativity",                104, 8);
        final static Experiment SEED_VIABILITY            = new Experiment("Seed Viability",            7,   4);
        final static Experiment SUN_SPOTS                 = new Experiment("Sun Spots",                 90,  2);
        final static Experiment MICE_TUMORS               = new Experiment("Mice Tumors",               65,  8);
        final static Experiment MICROGRAVITY_PLANT_GROWTH = new Experiment("Microgravity Plant Growth", 75,  5);
        final static Experiment MICROMETEORITES           = new Experiment("Micrometeorites",           170, 9);
        final static Experiment COSMIC_RAYS               = new Experiment("Cosmic Rays",               80,  7);
        final static Experiment YEAST_FERMENTATION        = new Experiment("Yeast Fermentation",        27,  4);

        public double ratio() {
            return (double) rating / weight;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    final static public Experiment[] EXPERIMENTS = new Experiment[]{
            Experiment.CLOUD_PATTERNS,
            Experiment.SOLAR_FLARES,
            Experiment.SOLAR_POWER,
            Experiment.BINARY_STARS,
            Experiment.RELATIVITY,
            Experiment.SEED_VIABILITY,
            Experiment.SUN_SPOTS,
            Experiment.MICE_TUMORS,
            Experiment.MICROGRAVITY_PLANT_GROWTH,
            Experiment.MICROMETEORITES,
            Experiment.COSMIC_RAYS,
            Experiment.YEAST_FERMENTATION
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

    	Experiment[] sorted = EXPERIMENTS.clone();

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
     * Finds the best subsets of experiments using brute force.
     * Checks every possible subset of experiments and chooses the {@code bestSetCount} best.
     *
     * @param experiments The set of experiments to choose from.
     * @param maxWeight The maximum weight of a subset of experiments.
     * @param bestSetCount The number of subsets to return.
     * @return The {@code bestSetCount} subsets of experiments with the highest total ratings.
     */
    static public Experiment[][] bruteForce(final Experiment[] experiments, final int maxWeight, final int bestSetCount) {
        final int subsetCount = Math.powExact(2, experiments.length);
        Experiment[][] bestExperimentSets = new Experiment[bestSetCount][];
        int[] bestRatings = new int[bestSetCount];

        // Iterate over all subsets
        int tracker = 0;
        while (++tracker < subsetCount) {
            // Gather tracked subset
            Experiment[] subset = new Experiment[Long.bitCount(tracker)];
            for (int i = 1, j = 0, maxSubsetIndex = -1; i < subsetCount; i <<= 1, ++j) {
                if ((tracker & i) == 0) continue;
                subset[++maxSubsetIndex] = experiments[j];
            }
            // Evaluate this subset
            int weight = 0;
            int rating = 0;
            for (Experiment experiment : subset) {
                weight += experiment.weight;
                rating += experiment.rating;
            }
            if (weight > maxWeight) continue;
            // Find the worst of the best experiments
            int worstIndex = 0;
            for (int i = 0; i < bestExperimentSets.length; ++i) {
                if (bestRatings[worstIndex] <= bestRatings[i]) continue;
                worstIndex = i;
            }
            // If this experiment is better than the worst, replace the worst
            if (rating <= bestRatings[worstIndex]) continue;
            bestRatings[worstIndex] = rating;
            bestExperimentSets[worstIndex] = subset;
        }

        return bestExperimentSets;
    }

    /**
     * Prints an array of {@code Experiments}.
     *
     * @param array The array to print.
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
        printResult(bruteForce(EXPERIMENTS, 700, 1)[0]);
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