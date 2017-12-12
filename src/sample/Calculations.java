package sample;

import javafx.scene.control.ListView;

import java.util.*;

public class Calculations {
    public static double averageEmpirical(ListView<Double> list) {
        int amount = list.getItems().size();

        double sum = 0;
        for (Double variant : list.getItems()) {
            sum += variant;
        }
        return sum / amount;
    }

    public static Double calculateModa(ListView<Double> list) {
        ArrayList<Double> dList = new ArrayList<>(list.getItems());
        Set<Double> uniqueSet = new HashSet<>(list.getItems());
        HashMap<Double, Integer> potentialModa = new HashMap<>();
        for (Double temp : uniqueSet) {
            potentialModa.put(temp,Collections.frequency(dList,temp));
            System.out.println(temp+": "+ Collections.frequency(dList,temp));
        }
        return Collections.max(potentialModa.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }
}
