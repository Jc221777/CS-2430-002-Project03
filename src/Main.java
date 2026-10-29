import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public record Experiment(String name, short weight, short rating) {
        final static Experiment CLOUD_PATTERNS            = new Experiment("Cloud Patterns",            (short)36,  (short)5);
        final static Experiment SOLAR_FLARES              = new Experiment("Solar Flares",              (short)264, (short)9);
        final static Experiment SOLAR_POWER               = new Experiment("Solar Power",               (short)188, (short)6);
        final static Experiment BINARY_STARS              = new Experiment("Binary Stars",              (short)203, (short)8);
        final static Experiment RELATIVITY                = new Experiment("Relativity",                (short)104, (short)8);
        final static Experiment SEED_VIABILITY            = new Experiment("Seed Viability",            (short)7,   (short)4);
        final static Experiment SUN_SPOTS                 = new Experiment("Sun Spots",                 (short)90,  (short)2);
        final static Experiment MICE_TUMORS               = new Experiment("Mice Tumors",               (short)65,  (short)8);
        final static Experiment MICROGRAVITY_PLANT_GROWTH = new Experiment("Microgravity Plant Growth", (short)75,  (short)5);
        final static Experiment MICROMETEORITES           = new Experiment("Micrometeorites",           (short)170, (short)9);
        final static Experiment COSMIC_RAYS               = new Experiment("Cosmic Rays",               (short)80,  (short)7);
        final static Experiment YEAST_FERMENTATION        = new Experiment("Yeast Fermentation",        (short)27,  (short)4);

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
    static public Experiment[][] bruteForce(final Experiment[] experiments, final short maxWeight, final short bestSetCount) {
        final short subsetCount = (short)Math.powExact(2, experiments.length);
        Experiment[][] bestExperimentSets = new Experiment[bestSetCount][];
        int[] bestRatings = new int[bestSetCount];

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
        printResult(bruteForce(EXPERIMENTS, (short)700, (short)1)[0]);
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