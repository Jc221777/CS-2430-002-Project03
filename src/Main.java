public class Main {
    private enum Experiment {
        CLOUD_PATTERNS,
        SOLAR_FLARES,
        SOLAR_POWER,
        BINARY_STARS,
        RELATIVITY,
        SEED_VIABILITY,
        SUN_SPOTS,
        MICE_TUMORS,
        MICROGRAVITY_PLANT_GROWTH,
        MICROMETEORITES,
        COSMIC_RAYS,
        YEAST_FERMENTATION
    }

    final private short[] WEIGHTS = new short[]{36, 264, 188, 203, 104, 7, 90, 65, 75, 170, 80, 27};
    final private short[] RATINGS = new short[]{5, 9, 6, 8, 8, 4, 2, 8, 5, 9, 7, 4};


    /**
     * Finds the best subset of experiments using brute force.
     * Checks every possible combination of experiments and chooses the best.
     *
     * @return The optimal set of experiments.
     */
    private Experiment[] bruteForce() {
        final Experiment[] experiments = Experiment.values();
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
                weight += WEIGHTS[experiment.ordinal()];
                rating += RATINGS[experiment.ordinal()];
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
    private <T> void printArray(T[] array) {
        StringBuilder sb = new StringBuilder();
        for (T t : array)
            if (t != null) sb.append(t).append(" ");
        System.out.println(sb);
    }

    void run() {
        printArray(bruteForce());
    }
  //public static void main(String[] args) {
	//new Main().run();
//}
}