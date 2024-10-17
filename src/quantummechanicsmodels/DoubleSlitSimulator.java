package quantummechanicsmodels;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DoubleSlitSimulator extends Application {

    private Screen screen;
    private BorderPane pane;

    @Override
    public void start(Stage stage) {
        pane = new BorderPane();

        //Create Input Screen
        FlowPane paramPane = new FlowPane();
        paramPane.setHgap(10);
        paramPane.setVgap(10);
        paramPane.setAlignment(Pos.CENTER);
        paramPane.setPadding(new Insets(20, 20, 20, 20));
        TextField slitDistanceInput = new TextField();
        TextField screenDistanceInput = new TextField();
        TextField observeElectronsInput = new TextField();

        paramPane.getChildren().addAll(
                createLabeledField("Distance Between Slits", slitDistanceInput),
                createLabeledField("Distance To Screen", screenDistanceInput),
                createLabeledField("Observe Electrons (true/false)", observeElectronsInput)
        );

        pane.setCenter(paramPane);

        Button createExperimentBtn = new Button("Create Experiment");
        Button createDefaultExperimentBtn = new Button("Create Experiment with Default Parameters");

        HBox buttonBox = new HBox(10, createExperimentBtn, createDefaultExperimentBtn);
        buttonBox.setAlignment(Pos.CENTER);
        pane.setBottom(buttonBox);

        Button fireElectronBtn = new Button("Fire Electron");
        Button fireMultipleBtn = new Button("Fire Multiple Electrons");
        TextField numElectronsInput = new TextField();
        numElectronsInput.setPromptText("Number of electrons");
        Button resetBtn = new Button("Reset");
        HBox controls = new HBox(10, fireElectronBtn, fireMultipleBtn, numElectronsInput, resetBtn);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(20, 20, 20, 20));

        fireElectronBtn.setOnAction(_ -> screen.fireElectron());

        fireMultipleBtn.setOnAction(_ -> {
            try {
                int num = Integer.parseInt(numElectronsInput.getText());
                screen.fireNElectrons(num);
            } catch (NumberFormatException e) {
                showAlert("Invalid input", "Please enter a valid number of electrons.");
            }
        });

        resetBtn.setOnAction(_ -> {
            pane.setCenter(paramPane);
            pane.setBottom(buttonBox);
        });

        createExperimentBtn.setOnAction(_ -> {
            try {
                double slitDistance = Double.parseDouble(slitDistanceInput.getText());
                double screenDistance = Double.parseDouble(screenDistanceInput.getText());
                boolean observeElectrons = Boolean.parseBoolean(observeElectronsInput.getText().toLowerCase());

                screen = new Screen(slitDistance, screenDistance, observeElectrons, 1000, 20);
                pane.setCenter(screen);
                pane.setBottom(controls);
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter valid numbers");
            }
        });

        createDefaultExperimentBtn.setOnAction(_ -> {
            screen = new Screen();
            pane.setCenter(screen);
            pane.setBottom(controls);
        });


        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Double Slit Experiment Simulator");
        stage.show();
    }

    private VBox createLabeledField(String labelText, TextField textField) {
        Label label = new Label(labelText);
        VBox vbox = new VBox(5, label, textField);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}