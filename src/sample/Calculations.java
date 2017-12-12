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

     static Double calculateModa(ListView<Double> list) {
        ArrayList<Double> dList = new ArrayList<>(list.getItems());
        Set<Double> uniqueSet = new HashSet<>(list.getItems());
        HashMap<Double, Integer> potentialModa = new HashMap<>();
        for (Double temp : uniqueSet) {
            potentialModa.put(temp, Collections.frequency(dList, temp));
        }
        return Collections.max(potentialModa.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

     static Double mediana(ListView<Double> list) {
        ArrayList<Double> dList = new ArrayList<>(list.getItems());
        //ArrayList<Double> testList = new ArrayList<Double>(Arrays.asList(0.35,0.38,0.42,0.47,0.6,0.91,0.89,0.59,0.38,0.27,0.19));
        Collections.sort(dList);
        if (elementsQuantityisOdd(list)) {
            return dList.get(Math.floorDiv(dList.size(), 2));
        } else {
            int index = dList.size() / 2;
            return (dList.get(index - 1) + dList.get(index)) / 2;
        }
    }

    private static boolean elementsQuantityisOdd(ListView<Double> list) {
        return list.getItems().size() % 2 > 0;
    }
}
