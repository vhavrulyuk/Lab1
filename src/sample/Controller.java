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
        printResultToTxt(aE);
    }

    private void printResultToTxt(double calculatedValue) {
        String resultText = "";
        resultText += Calculations.calculatedValue + ": " + calculatedValue + "\n";
        results.appendText(resultText);
    }

}
