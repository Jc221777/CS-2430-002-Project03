public class Main {
    public record Experiment(String name, int weight, int rating) {
        final static Experiment CLOUD_PATTERNS            = new Experiment("CLOUD_PATTERNS",            36,  5);
        final static Experiment SOLAR_FLARES              = new Experiment("SOLAR_FLARES",              264, 9);
        final static Experiment SOLAR_POWER               = new Experiment("SOLAR_POWER",               188, 6);
        final static Experiment BINARY_STARS              = new Experiment("BINARY_STARS",              203, 8);
        final static Experiment RELATIVITY                = new Experiment("RELATIVITY",                104, 8);
        final static Experiment SEED_VIABILITY            = new Experiment("SEED_VIABILITY",            7,   4);
        final static Experiment SUN_SPOTS                 = new Experiment("SUN_SPOTS",                 90,  2);
        final static Experiment MICE_TUMORS               = new Experiment("MICE_TUMORS",               65,  8);
        final static Experiment MICROGRAVITY_PLANT_GROWTH = new Experiment("MICROGRAVITY_PLANT_GROWTH", 75,  5);
        final static Experiment MICROMETEORITES           = new Experiment("MICROMETEORITES",           170, 9);
        final static Experiment COSMIC_RAYS               = new Experiment("COSMIC_RAYS",               80,  7);
        final static Experiment YEAST_FERMENTATION        = new Experiment("YEAST_FERMENTATION",        27,  4);

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
     * Prints an array.
     *
     * @param array The array to print.
     * @param <T> The type of values in the array.
     */
    static private <T> void printArray(T[] array) {
        StringBuilder sb = new StringBuilder();
        for (T t : array)
            if (t != null) sb.append(t).append(" ");
        System.out.println(sb);
    }

    static void main() {
        printArray(bruteForce(EXPERIMENTS, 700, 1)[0]);
    }
}