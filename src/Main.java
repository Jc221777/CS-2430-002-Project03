public class Main {
    public record Experiment(String name, short weight, short rating) {
        final static Experiment CLOUD_PATTERNS            = new Experiment("CLOUD_PATTERNS",            (short)36,  (short)5);
        final static Experiment SOLAR_FLARES              = new Experiment("SOLAR_FLARES",              (short)264, (short)9);
        final static Experiment SOLAR_POWER               = new Experiment("SOLAR_POWER",               (short)188, (short)6);
        final static Experiment BINARY_STARS              = new Experiment("BINARY_STARS",              (short)203, (short)8);
        final static Experiment RELATIVITY                = new Experiment("RELATIVITY",                (short)104, (short)8);
        final static Experiment SEED_VIABILITY            = new Experiment("SEED_VIABILITY",            (short)7,   (short)4);
        final static Experiment SUN_SPOTS                 = new Experiment("SUN_SPOTS",                 (short)90,  (short)2);
        final static Experiment MICE_TUMORS               = new Experiment("MICE_TUMORS",               (short)65,  (short)8);
        final static Experiment MICROGRAVITY_PLANT_GROWTH = new Experiment("MICROGRAVITY_PLANT_GROWTH", (short)75,  (short)5);
        final static Experiment MICROMETEORITES           = new Experiment("MICROMETEORITES",           (short)170, (short)9);
        final static Experiment COSMIC_RAYS               = new Experiment("COSMIC_RAYS",               (short)80,  (short)7);
        final static Experiment YEAST_FERMENTATION        = new Experiment("YEAST_FERMENTATION",        (short)27,  (short)4);

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
     * Finds the best subset of experiments using brute force.
     * Checks every possible combination of experiments and chooses the best.
     *
     * @return The optimal set of experiments.
     */
    static public Experiment[][] bruteForce(final Experiment[] experiments, final short bestSetCount) {
        final short subsetCount = (short)Math.powExact(2, experiments.length);
        Experiment[][] bestExperimentSet = new Experiment[bestSetCount][];
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
            if (weight > 700) continue;
            // Find the worst of the best experiments
            int worstIndex = 0;
            for (int i = 0; i < bestExperimentSet.length; ++i) {
                if (bestRatings[worstIndex] <= bestRatings[i]) continue;
                worstIndex = i;
            }
            // If this experiment is better than the worst, replace the worst
            if (rating <= bestRatings[worstIndex]) continue;
            bestRatings[worstIndex] = rating;
            bestExperimentSet[worstIndex] = subset;
        }

        return bestExperimentSet;
    }

    /**
     * Prints an array.
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
        printArray(bruteForce(EXPERIMENTS, (short)1)[0]);
    }
}