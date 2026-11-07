import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public record Experiment(String name, int weight, int rating) {
        final static Experiment CLOUD_PATTERNS             = new Experiment("Cloud Patterns",               36,  5);
        final static Experiment SOLAR_FLARES               = new Experiment("Solar Flares",                 264, 9);
        final static Experiment SOLAR_POWER                = new Experiment("Solar Power",                  188, 6);
        final static Experiment BINARY_STARS               = new Experiment("Binary Stars",                 203, 8);
        final static Experiment RELATIVITY                 = new Experiment("Relativity",                   104, 8);
        final static Experiment SEED_VIABILITY             = new Experiment("Seed Viability",               7,   4);
        final static Experiment SUN_SPOTS                  = new Experiment("Sun Spots",                    90,  2);
        final static Experiment MICE_TUMORS                = new Experiment("Mice Tumors",                  65,  8);
        final static Experiment MICROGRAVITY_PLANT_GROWTH  = new Experiment("Microgravity Plant Growth",    75,  5);
        final static Experiment MICROMETEORITES            = new Experiment("Micrometeorites",              170, 9);
        final static Experiment COSMIC_RAYS_PRIMARY_BAND   = new Experiment("Cosmic Rays (Primary Band)",   80,  7);
        final static Experiment COSMIC_RAYS_SECONDARY_BAND = new Experiment("Cosmic Rays (Secondary Band)", 10,  5);
        final static Experiment YEAST_FERMENTATION         = new Experiment("Yeast Fermentation",           27,  4);

        public boolean isSolar() {
            return this == SOLAR_FLARES ||
                   this == SOLAR_POWER ||
                   this == SUN_SPOTS;
        }

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
            Experiment.COSMIC_RAYS_PRIMARY_BAND,
            Experiment.COSMIC_RAYS_SECONDARY_BAND,
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
    	int solarCount = 0;

    	boolean hasSeed = false;
    	boolean hasYeast = false;
    	boolean hasPrimaryBand = false;

    	for (Experiment experiment : sorted) {

    		if (experiment == Experiment.COSMIC_RAYS_SECONDARY_BAND) {
    			continue;
    		}

    		int weight = experiment.weight;

    		if ((experiment == Experiment.YEAST_FERMENTATION && hasSeed) || (experiment == Experiment.SEED_VIABILITY && hasYeast)) {
    			weight -= 7;
    		}

    		if (totalWeight + weight <= 700 && (!experiment.isSolar() || solarCount < 2)) {

    			selected[selectedCount++] = experiment;
            	totalWeight += weight;

    			if (experiment == Experiment.YEAST_FERMENTATION) {
    				hasYeast = true;
    			}

    			if (experiment == Experiment.SEED_VIABILITY) {
    				hasSeed = true;
    			}

    			if (experiment == Experiment.COSMIC_RAYS_PRIMARY_BAND) {
    				hasPrimaryBand = true;
    			}

    			if (experiment.isSolar()) {
    				solarCount++;
    			}
    	}

    }
    	if (hasPrimaryBand && totalWeight + 10 <= 700) {

    		selected[selectedCount++] = Experiment.COSMIC_RAYS_SECONDARY_BAND;
    		totalWeight += Experiment.COSMIC_RAYS_SECONDARY_BAND.weight;
    		}

    	return Arrays.copyOf(selected, selectedCount);
}

    /**
     * Finds the best subsets of experiments using brute force.
     * Checks every valid subset of experiments and chooses the {@code bestSetCount} best.
     *
     * @param experiments The set of experiments to choose from.
     * @param maxWeight The maximum weight of a subset of experiments.
     * @param bestSetCount The number of subsets to return.
     * @return The {@code bestSetCount} subsets of experiments with the highest total ratings.
     */
    static public Experiment[][] bruteForce(final Experiment[] experiments,
                                            final int maxWeight,
                                            final int bestSetCount
    ) {
        // Build the bitsets needed for fast checks later
        int dependentBitset = 0;
        int invalidBitset = 0;
        int conditionBitset = 0;
        for (int i = 0; i < experiments.length; ++i) {
            if (experiments[i] == Experiment.COSMIC_RAYS_PRIMARY_BAND
                    || experiments[i] == Experiment.COSMIC_RAYS_SECONDARY_BAND)
                dependentBitset |= 1 << i;
            if (experiments[i] == Experiment.SOLAR_FLARES
                    || experiments[i] == Experiment.SOLAR_POWER
                    || experiments[i] == Experiment.SUN_SPOTS)
                invalidBitset |= 1 << i;
            if (experiments[i] == Experiment.YEAST_FERMENTATION || experiments[i] == Experiment.SEED_VIABILITY)
                conditionBitset |= 1 << i;
        }
        final boolean useDependentCheck = Long.bitCount(dependentBitset) == 2;
        final boolean useInvalidCheck = Long.bitCount(invalidBitset) == 3;
        final boolean useConditionCheck = Long.bitCount(conditionBitset) == 2;

        final int subsetCount = Math.powExact(2, experiments.length);
        Experiment[][] bestExperimentSets = new Experiment[bestSetCount][];
        int[] bestRatings = new int[bestSetCount];
        int[] bestWeights = new int[bestSetCount];

        // Iterate over all subsets
        int subsetBitset = 0;
        while (++subsetBitset < subsetCount) {
            if (useDependentCheck) {
                final int checkSet = subsetBitset & dependentBitset;
                if (checkSet != 0 && checkSet != dependentBitset) continue;
            }
            if (useInvalidCheck && (subsetBitset & invalidBitset) == invalidBitset) continue;

            // Gather tracked subset
            Experiment[] subset = new Experiment[Long.bitCount(subsetBitset)];
            for (int i = 1, j = 0, maxSubsetIndex = -1; i < subsetCount; i <<= 1, ++j) {
                if ((subsetBitset & i) == 0) continue;
                subset[++maxSubsetIndex] = experiments[j];
            }

            // Modify experiments based on combinations
            if (useConditionCheck && (subsetBitset & conditionBitset) == conditionBitset)
                for (int i = 0; i < subset.length; ++i)
                    if (subset[i] == Experiment.YEAST_FERMENTATION)
                        subset[i] = new Experiment(Experiment.YEAST_FERMENTATION.name, 20, Experiment.YEAST_FERMENTATION.rating);

            // Evaluate this subset
            int weight = 0;
            int rating = 0;
            for (Experiment experiment : subset) {
                weight += experiment.weight;
                rating += experiment.rating;
            }
            if (weight > maxWeight) continue;

            // Find the worst rating recorded
            int worstRating = Integer.MAX_VALUE;
            for (int bestRating : bestRatings)
                worstRating = Math.min(worstRating, bestRating);
            if (rating <= worstRating) continue;

            // Find the heaviest of the worst rated subsets recorded
            int worstIndex = 0;
            int worstWeight = 0;
            for (int i = 0; i < bestWeights.length; ++i)
                if (bestRatings[i] == worstRating && bestWeights[i] > worstWeight)
                    worstWeight = bestWeights[worstIndex = i];

            // If this subset is better than the worst, replace the worst
            bestRatings[worstIndex] = rating;
            bestWeights[worstIndex] = weight;
            bestExperimentSets[worstIndex] = subset;
        }
        return bestExperimentSets;
    }

    /**
     * Finds the best subsets of experiments.
     * Checks a subset of the possible subsets of experiments and chooses the {@code bestSetCount} best.
     * This implementation does not check subsets which it already knows to be over the weight limit.
     *
     * @param experiments The set of experiments to choose from.
     * @param maxWeight The maximum weight of a subset of experiments.
     * @param bestSetCount The number of subsets to return.
     * @return The {@code bestSetCount} subsets of experiments with the highest total ratings.
     */
    static public Experiment[][] dynamic(final Experiment[] experiments, final int maxWeight, final int bestSetCount) {
        // Build the bitsets needed for fast checks later
        int dependentBitset = 0;
        int invalidBitset = 0;
        int conditionBitset = 0;
        for (int i = 0; i < experiments.length; ++i) {
            if (experiments[i] == Experiment.COSMIC_RAYS_PRIMARY_BAND
                    || experiments[i] == Experiment.COSMIC_RAYS_SECONDARY_BAND)
                dependentBitset |= 1 << i;
            if (experiments[i] == Experiment.SOLAR_FLARES
                    || experiments[i] == Experiment.SOLAR_POWER
                    || experiments[i] == Experiment.SUN_SPOTS)
                invalidBitset |= 1 << i;
            if (experiments[i] == Experiment.YEAST_FERMENTATION || experiments[i] == Experiment.SEED_VIABILITY)
                conditionBitset |= 1 << i;
        }
        final boolean useDependentCheck = Long.bitCount(dependentBitset) == 2;
        final boolean useInvalidCheck = Long.bitCount(invalidBitset) == 3;
        final boolean useConditionCheck = Long.bitCount(conditionBitset) == 2;

        final int subsetCount = Math.powExact(2, experiments.length);
        Experiment[][] bestExperimentSets = new Experiment[bestSetCount][];
        int[] bestRatings = new int[bestSetCount];
        int[] bestWeights = new int[bestSetCount];
        SuperMask subsets = new SuperMask(subsetCount);

        // Iterate over all subsets
        int subsetBitset = 0;
        while (++subsetBitset < subsetCount) {
            if (subsets.match(subsetBitset)) continue;
            if (useDependentCheck) {
                final int checkSet = subsetBitset & dependentBitset;
                if (checkSet != 0 && checkSet != dependentBitset) continue;
            }
            if (useInvalidCheck && (subsetBitset & invalidBitset) == invalidBitset) continue;

            // Gather tracked subset
            Experiment[] subset = new Experiment[Long.bitCount(subsetBitset)];
            for (int i = 1, j = 0, maxSubsetIndex = -1; i < subsetCount; i <<= 1, ++j) {
                if ((subsetBitset & i) == 0) continue;
                subset[++maxSubsetIndex] = experiments[j];
            }

            // Modify experiments based on combinations
            if (useConditionCheck && (subsetBitset & conditionBitset) == conditionBitset)
                for (int i = 0; i < subset.length; ++i)
                    if (subset[i] == Experiment.YEAST_FERMENTATION)
                        subset[i] = new Experiment(Experiment.YEAST_FERMENTATION.name, 20, Experiment.YEAST_FERMENTATION.rating);

            // Evaluate this subset
            int weight = 0;
            int rating = 0;
            for (Experiment experiment : subset) {
                weight += experiment.weight;
                rating += experiment.rating;
            }
            if (weight > maxWeight) {
                subsets.add(subsetBitset);
                continue;
            }

            // Find the worst rating recorded
            int worstRating = Integer.MAX_VALUE;
            for (int bestRating : bestRatings)
                worstRating = Math.min(worstRating, bestRating);
            if (rating <= worstRating) continue;

            // Find the heaviest of the worst rated subsets recorded
            int worstIndex = 0;
            int worstWeight = 0;
            for (int i = 0; i < bestWeights.length; ++i)
                if (bestRatings[i] == worstRating && bestWeights[i] > worstWeight)
                    worstWeight = bestWeights[worstIndex = i];

            // If this subset is better than the worst, replace the worst
            bestRatings[worstIndex] = rating;
            bestWeights[worstIndex] = weight;
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

    	boolean hasSeed = Arrays.asList(array).contains(Experiment.SEED_VIABILITY);

        for (Experiment t : array) {

        	int weight = t.weight;

        	if (t == Experiment.YEAST_FERMENTATION && hasSeed) {
        		weight -= 7;
        	}

        	System.out.print(t.name);
        	System.out.print(" (Weight: " + weight);
        	System.out.println(" | Rating: " + t.rating + ")");
        	total_weight += weight;
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
        System.out.println("Dynamic");
        System.out.println("------------------------------------------");
        printResult(dynamic(EXPERIMENTS, 700, 1)[0]);
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