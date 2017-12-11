package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class Controller {
    @FXML
    private Button addData;
    @FXML
    private ListView<Double> discreteData;
    @FXML
    private TextField newVarianta;
    @FXML
    private Button resetAll;


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
}
