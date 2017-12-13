package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
        Double aE = Calculations.averageEmpirical(discreteData);
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
        printResultToTxt(aE, moda, mediana, scope, empiricalVariance, meanSquareDeviation, correctedEmpiricalVariance, correctedMeanSquareDeviation, variation, asymmetry, excess);
    }

    private void printResultToTxt(double aE, double moda, double mediana,
                                  double scope, double empiricalVariance,
                                  double meanSquareDeviation, double correctedEmpiricalVariance,
                                  double correctedMeanSquareDeviation,
                                  double variation,
                                  double asymmetry,
                                  double excess) {
        String resultText = String.format("Середнє емпіричне: %f\nМода: %f\n" +
                        "Медіана: %f\n" +
                        "Розмах: %f\n" +
                        "Дисперсія: %f\n" +
                        "Середнє квадратичне відхилення: %f\n" +
                        "Виправлена дисперсія: %f\n" +
                        "Виправлене середнє квадратичне відхилення: %f\n" +
                        "Варіація: %f\n" +
                        "Асиметрія: %f\n" +
                        "Ексцес: %f\n", aE, moda, mediana, scope, empiricalVariance,
                meanSquareDeviation, correctedEmpiricalVariance,
                correctedMeanSquareDeviation, variation, asymmetry,
                excess);
        results.appendText(resultText);
    }

}
