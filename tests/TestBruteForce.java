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
        Main.Experiment[] expected = new Main.Experiment[]{
                Main.Experiment.CLOUD_PATTERNS,
                Main.Experiment.SOLAR_POWER
        };
        int expectedWeight = Main.Experiment.CLOUD_PATTERNS.weight() + Main.Experiment.SOLAR_POWER.weight();
        Main.Experiment[] input = new Main.Experiment[]{
                Main.Experiment.CLOUD_PATTERNS,
                Main.Experiment.SOLAR_FLARES,
                Main.Experiment.SOLAR_POWER
        };
        Assertions.assertArrayEquals(expected, Main.bruteForce(input, expectedWeight, 1)[0]);
    }
}
