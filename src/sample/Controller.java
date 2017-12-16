package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.util.Collections;
import java.util.TreeMap;

public class Controller {
    @FXML
    public Button removeData;
    @FXML
    public Button resetAll;
    @FXML
    public Button addData;
    @FXML
    private ListView<Double> discreteData;
    @FXML
    private TextField newVarianta;
    @FXML
    private TextArea results;
    @FXML
    private Button calculate;
    @FXML
    private LineChart cumulata;
    @FXML
    private LineChart relativeCumulata;
    @FXML
    private LineChart polygon;
    @FXML
    private LineChart edf;

    @FXML
    public void buildCumulata() {
        TreeMap<Double, Integer> chartData = Calculations.prepareDataForCumulata(discreteData);
        XYChart.Series series = new XYChart.Series<>();
        chartData.forEach((Double x, Integer y) ->
                series.getData().add(new XYChart.Data(x, y)));
        cumulata.getData().add(series);
    }

    @FXML
    public void buildRelativeCumulata() {
        TreeMap<Double, Double> chartData = Calculations.prepareDataForRelativeCumulata(discreteData);
        XYChart.Series series = new XYChart.Series<>();
        chartData.forEach((Double x, Double y) ->
                series.getData().add(new XYChart.Data(x, y)));
        relativeCumulata.getData().add(series);
    }

    @FXML
    private void buildPolygon() {
        TreeMap<Double, Integer> chartData = new TreeMap<>(Calculations.varaintaFrequency(discreteData));
        XYChart.Series series = new XYChart.Series<>();
        chartData.forEach((Double x, Integer y) ->
                series.getData().add(new XYChart.Data(x, y)));
        polygon.getData().add(series);
    }

    @FXML
    public void addNewData(ActionEvent event) {
        Double varianta;
        try {
            varianta = Double.parseDouble(newVarianta.getText());
            discreteData.getItems().add(varianta);
        } catch (NumberFormatException e) {
            handleNumberFormatException();
        }
    }

    public void removeExistingData() {
        final int selectedIndex = discreteData.getSelectionModel().getSelectedIndex();
        try {
            discreteData.getItems().remove(selectedIndex);
        } catch (Exception e) {
            handleException(e);
        }
    }

    public void resetAllControls() {
        discreteData.getItems().clear();
        results.clear();
        cumulata.getData().clear();
        relativeCumulata.getData().clear();
        polygon.getData().clear();
    }

    private void handleNumberFormatException() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Не число типу Double");
        alert.setContentText("Будь-ласка, введіть число типу Double");
        alert.showAndWait();
    }

    private void handleException(Exception e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(e.getClass().getSimpleName());
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    public void calcultaAll() {
        results.clear();
        buildCumulata();
        buildRelativeCumulata();
        buildPolygon();
        Double aE = Calculations.empiricalStartingPoint(1, discreteData);
        Double moda = Calculations.calculateModa(discreteData);
        Double mediana = Calculations.mediana(discreteData);
        Double scope = Calculations.scope(discreteData);
        Double empiricalVariance = Calculations.empiricalVariance(discreteData);
        Double meanSquareDeviation = Math.sqrt(empiricalVariance);
        Double correctedEmpiricalVariance = Calculations.correctedEmpiricalVariance(discreteData);
        Double correctedMeanSquareDeviation = Math.sqrt(correctedEmpiricalVariance);
        Double variation = meanSquareDeviation / aE;
        Double asymmetry = Calculations.asymmetry(discreteData);
        Double excess = Calculations.excess(discreteData);
        Double v3 = Calculations.empiricalStartingPoint(3, discreteData);
        Double v4 = Calculations.empiricalStartingPoint(4, discreteData);
        Double m3 = Calculations.empiricalCentralPoint(3, discreteData);
        Double m4 = Calculations.empiricalCentralPoint(4, discreteData);
        printResultToTxt(aE, moda, mediana, scope, empiricalVariance, meanSquareDeviation, correctedEmpiricalVariance, correctedMeanSquareDeviation, variation, asymmetry, excess, v3, v4, m3, m4);
    }

    private void printResultToTxt(double aE, double moda, double mediana,
                                  double scope, double empiricalVariance,
                                  double meanSquareDeviation, double correctedEmpiricalVariance,
                                  double correctedMeanSquareDeviation,
                                  double variation,
                                  double asymmetry,
                                  double excess,
                                  double eSP3,
                                  double eSP4,
                                  double cP3,
                                  double cP4
    ) {
        String resultText = String.format("Середнє емпіричне: %f\nМода: %f\n" +
                        "Медіана: %f\n" +
                        "Розмах: %f\n" +
                        "Дисперсія: %f\n" +
                        "Середнє квадратичне відхилення: %f\n" +
                        "Виправлена дисперсія: %f\n" +
                        "Виправлене середнє квадратичне відхилення: %f\n" +
                        "Варіація: %f\n" +
                        "Асиметрія: %f\n" +
                        "Ексцес: %f\n" +
                        "Початковий момент 3-го порядку: %f\n" +
                        "Початковий момент 4-го порядку: %f\n" +
                        "Центральний момент 3-го порядку: %f\n" +
                        "Центральний момент 4-го порядку: %f\n", aE, moda, mediana, scope, empiricalVariance,
                meanSquareDeviation, correctedEmpiricalVariance,
                correctedMeanSquareDeviation, variation, asymmetry,
                excess, eSP3, eSP4, cP3, cP4);
        results.appendText(resultText);
    }

}
