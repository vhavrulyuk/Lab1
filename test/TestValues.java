import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import sample.Calculations;

import java.io.IOException;

public class TestValues extends ApplicationTest {
    ListView<Double> discreteData;
    ObservableList<Double> variants;

    @Override
    public void start(Stage stage) throws IOException {
        discreteData = new ListView<>();
        variants = FXCollections.observableArrayList(0.35, 0.38, 0.42, 0.47, 0.60, 0.91, 0.89, 0.59, 0.38, 0.27, 0.19, 0.02);
        discreteData.setItems(variants);
    }

    @Test
    public void testAvarageEmpirical() {
        Assert.assertEquals(0.455833333333333, Calculations.empiricalStartingPoint(1, discreteData), 0.000000000000001);
    }

    @Test
    public void testprepareDataForCumulata() {
        Calculations.prepareDataForRelativeCumulata(discreteData);
        Assert.assertEquals(1, 1);
    }

    @Test
    public void testEmpiricalCentralPoint3dOrder() {
        Assert.assertEquals(0.00590341782407408, Calculations.empiricalCentralPoint(3, discreteData), 0.00000000000000001);
    }

    @Test
    public void testAsymmetry() {
        Assert.assertEquals(0.375644172594603, Calculations.empiricalCentralPoint(3, discreteData) / Math.pow(Math.sqrt(Calculations.empiricalVariance(discreteData)), 3), 0.000000000000001);
    }

    @Test
    public void testExcess() {
        Assert.assertEquals(-0.43219583480124, Calculations.empiricalCentralPoint(4, discreteData) / Math.pow(Math.sqrt(Calculations.empiricalVariance(discreteData)), 4) - 3, 0.000000000000001);
    }

    @Test
    public void testEmpriricalStartingPoint() {
        Assert.assertEquals(0.186416583333333, Calculations.empiricalStartingPoint(3, discreteData), 0.000000000000001);
        Assert.assertEquals(0.1422655125, Calculations.empiricalStartingPoint(4, discreteData), 0.0000000001);
    }
}