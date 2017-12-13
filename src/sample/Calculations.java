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
        Collections.sort(dList);
        if (elementsQuantityIsOdd(list)) {
            return dList.get(Math.floorDiv(dList.size(), 2));
        } else {
            int index = dList.size() / 2;
            return (dList.get(index - 1) + dList.get(index)) / 2;
        }
    }

    static Double scope(ListView<Double> list) {
        ArrayList<Double> dList = new ArrayList<>(list.getItems());
        return Collections.max(dList) - Collections.min(dList);
    }

    public static Double empiricalVariance(ListView<Double> list) {
        double sum = 0;
        ArrayList<Double> dList = new ArrayList<>(list.getItems());
        for (Double temp : dList) {
            sum += Math.pow((temp - averageEmpirical(list)), 2);
        }
        return sum / dList.size();
    }

    static Double correctedEmpiricalVariance(ListView<Double> list){
        double sum = 0;
        ArrayList<Double> dList = new ArrayList<>(list.getItems());

        for (Double temp : dList) {
            sum += Math.pow((temp - averageEmpirical(list)), 2);
        }
        return sum/(dList.size() - 1);
    }
    public static Double empiricalCentralPoint(int order, ListView<Double> list){
        double sum = 0;
        ArrayList<Double> dList = new ArrayList<>(list.getItems());

        for (Double temp : dList) {
            sum += Math.pow((temp - averageEmpirical(list)), order);
        }
        return sum/dList.size();
    }

    public static Double asymmetry(ListView<Double> list){
        return empiricalCentralPoint(3, list) / Math.pow(Math.sqrt(empiricalVariance(list)),3);
    }

    public static Double excess(ListView<Double> list){
        return empiricalCentralPoint(4, list) / Math.pow(Math.sqrt(empiricalVariance(list)), 4)-3;
    }

    private static boolean elementsQuantityIsOdd(ListView<Double> list) {
        return list.getItems().size() % 2 > 0;
    }

}
