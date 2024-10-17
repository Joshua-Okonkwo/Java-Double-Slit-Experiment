package quantummechanicsmodels;

import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Random;
import java.util.function.Function;

/**
 * Models the wave function of a single electron being fired at a screen through a double slit. The units of each
 * parameter are arbitrary, as the model is designed purely for conceptual understanding.
 *
 * @author Joshua Okonkwo
 * @version 2.0
 */

public class WaveFunction {

    private final double distanceBetweenSlits;
    private final double distanceToScreen;
    /* Like in the real double slit experiment, if the slit distance is too small or big relative to the distance to
    screen, the interference patterns are no longer visible */
    private final boolean isObserved;
    private final double[] xPositions;
    private final double[] probabilities; // Probabilities of being found in each interval between the X Positions
    private final double screenWidth;
    private final Function<Double, Double> waveFunction;

    public final int X_RANGE;
    public final double NORMALIZATION_CONSTANT;

    public WaveFunction() {
        this(1.0, 10.0);
    }

    public WaveFunction(double distanceBetweenSlits, double distanceToScreen) {
        this(distanceBetweenSlits, distanceToScreen, false);
    }

    public WaveFunction(double distanceBetweenSlits, double distanceToScreen, boolean isObserved) {
        this(distanceBetweenSlits, distanceToScreen, isObserved, 1000, 20);
    }



    public WaveFunction(double distanceBetweenSlits, double distanceToScreen, boolean isObserved, int xRange,
                        double screenWidth) {
        this.distanceBetweenSlits = distanceBetweenSlits;
        this.distanceToScreen = distanceToScreen;
        this.isObserved = isObserved;
        this.screenWidth = screenWidth;
        /* To Decide on a wave function I just played around until I got a periodic function that generally follows the
        expected relationships in the double-slit experiment because a better model would involve complex numbers and
        more variables.

        I also tried this:
        Math.pow(Math.exp((-x*x)/(1000*1000)) * Math.cos(Math.PI * 0.01 * distanceBetweenSlits * x/ distanceToScreen), 2);

        To create an effect where electrons have a higher chance of being measured toward the center, but it ended up
        making a negligible visual difference.
     */
        waveFunction = x -> Math.pow((Math.cos(Math.PI * 0.1 * distanceBetweenSlits * x / distanceToScreen)), 2);

        if (isObserved) {
            X_RANGE = xRange;
            xPositions = new double[] {-distanceBetweenSlits/2, distanceBetweenSlits/2};
            probabilities = new double[2];
            NORMALIZATION_CONSTANT = 1;
        } else {
            double probabilitySum = 0;
            X_RANGE = xRange;
            xPositions = new double[X_RANGE];
            probabilities = new double[X_RANGE];

            for (int i = 0; i < xPositions.length; i++) {
                xPositions[i] = i;

                if (i == 0) {
                    probabilities[0] = 0; //try the other way around
                    continue;
                }

                double[] subDivisions = new double[100];
                double step = (xPositions[i] - xPositions[i - 1]) / (99);
                for (int j = 0; j < 100; j++) {
                    subDivisions[j] = xPositions[i] + j * step;
                }

                probabilities[i] = TrapezoidalIntegration.integrate(subDivisions, waveFunction);
                probabilitySum += probabilities[i];

            }
            NORMALIZATION_CONSTANT = probabilitySum;
            normalizeProbabilities();
        }


    }

    /**
     * A helper method that normalizes the probability distribution (makes the probabilities add up to one.)
     */

    private void normalizeProbabilities() {
        for (int i = 0; i < probabilities.length; i++) {
            probabilities[i] = probabilities[i] / NORMALIZATION_CONSTANT;
        }
    }

    /**
     * Measures the position of the electron on the screen, accounting for wave particle duality. If the electron was
     * observed as it was fired, it behaves like a particle and the particles just come out roughly around the slits.
     * Otherwise, the electron would act like a wave which would be broken up by the slits and create an interference
     * pattern.
     *
     * @return a scatter plot point representing the electron's measured location.
     */

    public XYChart.Data<Number, Number> measurePosition() {
        Random random = new Random();
        double xPos;

        if (isObserved) {
            xPos = xPositions[random.nextInt(2)];
            xPos += random.nextGaussian(0, screenWidth/50);
        } else {
            xPos = RandomChoice.choose(xPositions, probabilities, random);
            xPos = (xPos*screenWidth/X_RANGE) - screenWidth/2;
        }

        double yPos = random.nextGaussian(0, screenWidth/110) * screenWidth;

        // Modify the appearance of the data points, randomly rotating them for aesthetics
        Rectangle rectangle = new Rectangle(6, 6, Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        rectangle.setRotate(random.nextGaussian(0, 10));
        XYChart.Data<Number, Number> point = new XYChart.Data<>(xPos, yPos);
        point.setNode(rectangle);

        return point;
    }

    public double getDistanceBetweenSlits() {
        return distanceBetweenSlits;
    }

    public double getDistanceToScreen() {
        return distanceToScreen;
    }

    public Function<Double, Double> getWaveFunction() {
        return waveFunction;
    }
}
