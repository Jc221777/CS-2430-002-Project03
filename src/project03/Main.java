package project03;
import java.util.Arrays;
import java.util.Comparator;
/**
 * A class that builds a set of records and organizes them using greedy
 * strategies, brute force, and dynamic strategies, then displays
 * the results of each strategy.
 *
 * Team 03
 * Jasey Christensen, Thaddeus Schlep & Tristan Taj
 * CS 2430, section 002
 * Programming Project 3: Optimal Selection
 *
 * @author Thaddeus
 * @author Tristan
 */
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
     * Accounts for additional constraints including a maximum of
     * two solar experiments, the Seed Viability and Yeast
     * Fermentation weight reduction, and the Cosmic Rays dependency.
     *
     * @param comparator Determines experiment priority.
     * @return Array of selected experiments.
     */
     public Experiment[] greedy(Experiment[] experiments, Comparator<Experiment> comparator, int finalWeight) {

    	Arrays.sort(experiments, comparator);

    	Experiment[] selected = new Experiment[experiments.length];

    	int totalWeight = 0;
    	int selectedCount = 0;
    	int solarCount = 0;

    	boolean hasSeed = false;
    	boolean hasYeast = false;
    	boolean hasPrimaryBand = false;

    	for (Experiment experiment : experiments) {

    		// Skip the secondary band until the primary band has been selected.
    		if (experiment == Experiment.COSMIC_RAYS_SECONDARY_BAND) {
    			continue;
    		}

    		int weight = experiment.weight;

    		// Reduce the combined weight by 7 kg if both experiments are selected.
    		if ((experiment == Experiment.YEAST_FERMENTATION && hasSeed) || (experiment == Experiment.SEED_VIABILITY && hasYeast)) {
    			weight -= 7;
    		}

    		// Ensure the payload stays within finalWeight and contains at most two solar experiments.
    		if (totalWeight + weight <= finalWeight && (!experiment.isSolar() || solarCount < 2)) {

    		    if (experiment == Experiment.YEAST_FERMENTATION) {
    		        hasYeast = true;
    		    }

    		    if (experiment == Experiment.SEED_VIABILITY) {
    		        hasSeed = true;
    		    }

    		    if (experiment == Experiment.YEAST_FERMENTATION && hasSeed) {
    		        experiment = new Experiment(experiment.name(), weight, experiment.rating());
    		    }

    			if (experiment == Experiment.SEED_VIABILITY && hasYeast) {
    			    for (int i = 0; i < selectedCount; i++) {
    			        if (selected[i] == Experiment.YEAST_FERMENTATION) {
    			            selected[i] = new Experiment("Yeast Fermentation", 20, 4);
    			            break;
    			        }
    			    }
    			}

    			selected[selectedCount++] = experiment;
    			totalWeight += weight;

    			if (experiment == Experiment.COSMIC_RAYS_PRIMARY_BAND) {
    				hasPrimaryBand = true;
    			}

    			if (experiment.isSolar()) {
    				solarCount++;
    			}
    	}

    }
    	// Include the secondary band if the primary was selected
    	// and there is enough remaining weight capacity.
    	if (hasPrimaryBand && totalWeight + 10 <= finalWeight) {

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
        Arrays.fill(bestWeights, Integer.MAX_VALUE);

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
        Arrays.fill(bestWeights, Integer.MAX_VALUE);
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

        for (Experiment t : array) {

        	int weight = t.weight;

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

    /**
     * Calculates the total rating of a selected payload.
     *
     * @param experiments The selected experiments.
     * @return The combined rating.
     */
    private static int totalRating(Experiment[] experiments) {
        int total = 0;

        for (Experiment experiment : experiments) {
            total += experiment.rating();
        }

        return total;
    }

    /**
     * Prints a single row of the comparison summary.
     *
     * @param name Strategy name.
     * @param experiments Selected experiments.
     * @param optimalRating The maximum rating found by brute force.
     */
    private void printSummary(String name, Experiment[] experiments,
                              int optimalRating) {
        int weight = 0;

        for (Experiment experiment : experiments) {
            weight += experiment.weight();
        }

        int rating = totalRating(experiments);

        System.out.printf("%-30s %10d %10d %12s%n",
                name, weight, rating,
                rating == optimalRating ? "YES" : "NO");
    }

    void main() {
        Main program = new Main();
        final int MAX_WEIGHT = 700;

        System.out.println("Project 3 - Knapsack Optimization");
        System.out.println("Maximum Weight: " + MAX_WEIGHT + " kg");

        // Greedy strategies
        Experiment[] ratingFirst = program.greedy(
        		EXPERIMENTS,
                Comparator.comparingInt(Experiment::rating).reversed(),
                700);

        Experiment[] weightFirst = program.greedy(
        		EXPERIMENTS,
                Comparator.comparingInt(Experiment::weight),
                700);

        Experiment[] ratioFirst = program.greedy(
        		EXPERIMENTS,
                Comparator.comparingDouble(Experiment::ratio).reversed(),
                700);

        System.out.println("\n--- Highest Rating First ---");
        program.printResult(ratingFirst);

        System.out.println("\n--- Lightest Weight First ---");
        program.printResult(weightFirst);

        System.out.println("\n--- Best Ratio First ---");
        program.printResult(ratioFirst);

        // Brute force
        Experiment[][] topThree = bruteForce(EXPERIMENTS, MAX_WEIGHT, 3);

        Arrays.sort(topThree, (a, b) ->
                Integer.compare(totalRating(b), totalRating(a)));

        System.out.println("\n--- Brute Force ---");

        for (int i = 0; i < topThree.length; i++) {
            System.out.println("\nSubset " + (i + 1));

            if (i == 0) {
                System.out.println("Optimal Solution");
            }

            program.printResult(topThree[i]);
        }

        // Dynamic
        System.out.println("\n--- Dynamic ---");

        Experiment[] dynamicResult = dynamic(EXPERIMENTS, MAX_WEIGHT, 1)[0];
        program.printResult(dynamicResult);

        // Comparison
        System.out.println("\n--- Comparison ---");

        int optimalRating = totalRating(topThree[0]);

        System.out.printf("%-25s %10s %10s %10s%n",
                "Strategy", "Weight", "Rating", "Optimal");

        program.printSummary("Highest Rating", ratingFirst, optimalRating);
        program.printSummary("Lightest Weight", weightFirst, optimalRating);
        program.printSummary("Best Ratio", ratioFirst, optimalRating);
        program.printSummary("Brute Force", topThree[0], optimalRating);
        program.printSummary("Dynamic", dynamicResult, optimalRating);
    }
}