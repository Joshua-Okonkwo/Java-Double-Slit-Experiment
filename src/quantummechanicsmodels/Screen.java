package quantummechanicsmodels;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.ScatterChart;

public class Screen extends ScatterChart<Number, Number> {

    private final XYChart.Series<Number, Number> series;
    private final WaveFunction experiment;

    public Screen () {
        this(1, 10.0, false, 1000, 20.0);
    }
    public Screen(double distanceBetweenSlits, double distanceToScreen, boolean isObserved, int xRange, double screenWidth) {
        super(new NumberAxis(-screenWidth/2, screenWidth/2, 1), new NumberAxis(-10, 10, 1));
        experiment = new WaveFunction(distanceBetweenSlits, distanceToScreen, isObserved, xRange, screenWidth);

        setTitle("Double-Slit Experiment");

        series = new XYChart.Series<>();
        getData().add(series);
        setLegendVisible(false);
        configureAxes();
    }

    private void configureAxes() {
        NumberAxis xAxis = (NumberAxis) getXAxis();
        NumberAxis yAxis = (NumberAxis) getYAxis();

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(-10);
        yAxis.setUpperBound(10);

        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(-10);
        xAxis.setUpperBound(10);
    }

    public void fireElectron () {
        series.getData().add(experiment.measurePosition());
    }

    public void fireNElectrons(int num) {
        for (int i = 0; i < num; i++) {
            series.getData().add(experiment.measurePosition());
        }
    }

}
