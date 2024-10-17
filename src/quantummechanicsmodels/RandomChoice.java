package quantummechanicsmodels;

import java.util.Random;

public class RandomChoice {

    public static int choose(int[] values, double[] probs, Random random) {

        if (probs.length != values.length) {
            throw new IllegalArgumentException("probs.length != values.length");
        }


        double[] cumulativeProbs = new double[probs.length];
        cumulativeProbs[0] = probs[0];

        for (int i = 1; i < probs.length; i++) {
            cumulativeProbs[i] = cumulativeProbs[i - 1] + probs[i];
        }

        double rand = random.nextDouble() * cumulativeProbs[cumulativeProbs.length - 1];
        for (int i = 0; i < cumulativeProbs.length; i++) {
            if (rand <= cumulativeProbs[i]) {
                return values[i];
            }
        }

        throw new IllegalStateException("Probabilities are not normalized");
    }

    public static double choose(double[] values, double[] probs, Random random) {

        if (probs.length != values.length) {
            throw new IllegalArgumentException("probs.length != values.length");
        }


        double[] cumulativeProbs = new double[probs.length];
        cumulativeProbs[0] = probs[0];

        for (int i = 1; i < probs.length; i++) {
            cumulativeProbs[i] = cumulativeProbs[i - 1] + probs[i];
        }

        // Meant to be a "weighted" random choice
        double rand = random.nextDouble() * cumulativeProbs[cumulativeProbs.length - 1];
        for (int i = 0; i < cumulativeProbs.length; i++) {
            if (rand <= cumulativeProbs[i]) {
                return values[i];
            }
        }

        throw new IllegalStateException("Probabilities are not normalized");
    }
}
