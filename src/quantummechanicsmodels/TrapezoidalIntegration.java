package quantummechanicsmodels;

import java.util.function.Function;

public class TrapezoidalIntegration {

    public static double integrate(int[] x, double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("x and y arrays must have the same length.");
        }

        double sum = 0.0;

        for (int i = 1; i < x.length; i++) {
            int width = x[i] - x[i - 1];
            double area = 0.5 * (y[i] + y[i - 1]) * width;
            sum += area;
        }

        return sum;
    }

    public static double integrate(double[] x, double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("x and y arrays must have the same length.");
        }

        double sum = 0.0;

        for (int i = 1; i < x.length; i++) {
            double width = x[i] - x[i - 1];
            double area = 0.5 * (y[i] + y[i - 1]) * width;
            sum += area;
        }

        return sum;
    }

    public static double integrate(int[] x, Function<Double, Double> f) {

        double sum = 0.0;
        for (int i = 0; i < x.length - 1; i++) {
            double x0 = x[i];
            double x1 = x[i + 1];
            sum += 0.5 * (f.apply(x0) + f.apply(x1)) * (x1 - x0);
        }
        return sum;
    }

    public static double integrate(double[] x, Function<Double, Double> f) {
        double sum = 0.0;
        for (int i = 0; i < x.length - 1; i++) {
            double x0 = x[i];
            double x1 = x[i + 1];
            sum += 0.5 * (f.apply(x0) + f.apply(x1)) * (x1 - x0);
        }
        return sum;
    }
}