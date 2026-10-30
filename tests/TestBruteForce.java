import org.junit.jupiter.api.*;

public class TestBruteForce {
    @Test
    void hasCorrectOutput() {
        Main.Experiment[] expected = new Main.Experiment[]{
                Main.Experiment.CLOUD_PATTERNS,
                Main.Experiment.SOLAR_POWER
        };
        Main.Experiment[] input = new Main.Experiment[]{
                Main.Experiment.CLOUD_PATTERNS,
                Main.Experiment.SOLAR_FLARES,
                Main.Experiment.SOLAR_POWER
        };
        Assertions.assertArrayEquals(expected, Main.bruteForce(input, 250, 1)[0]);
    }

    @Test
    void doesNotExceedWeightLimit() {
        for (Main.Experiment[] experimentSet : Main.bruteForce(Main.EXPERIMENTS, 700, 4096)) {
            int totalWeight = 0;
            if (experimentSet == null) continue;
            for (Main.Experiment experiment : experimentSet)
                totalWeight += experiment.weight();
            Assertions.assertFalse(totalWeight > 700);
        }


        for (Main.Experiment[] experimentSet : Main.bruteForce(Main.EXPERIMENTS, 234, 4096)) {
            int totalWeight = 0;
            if (experimentSet == null) continue;
            for (Main.Experiment experiment : experimentSet)
                totalWeight += experiment.weight();
            Assertions.assertFalse(totalWeight > 234);
        }
    }

    @Test
    void canMatchWeightLimit() {

    }

    @Test
    void checksAllSubsets() {
        int expected = 4096;
        Assertions.assertEquals(expected, Main.bruteForce(Main.EXPERIMENTS, Integer.MAX_VALUE, expected).length);
    }
}
