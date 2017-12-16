package sample;

import javafx.scene.control.ListView;

import java.util.*;

public class Calculations {

    static public HashMap<Double, Integer> varaintaFrequency(ListView<Double> list) {
        Set<Double> uniqueSet = new HashSet<>(list.getItems());
        HashMap<Double, Integer> frequency = new HashMap<>();
        for (Double temp : uniqueSet) {
            frequency.put(temp, Collections.frequency(list.getItems(), temp));
        }
        return frequency;
    }

    static public HashMap<Double, Double> relativeVaraintaFrequency(ListView<Double> list) {
        Set<Double> uniqueSet = new HashSet<>(list.getItems());
        HashMap<Double, Double> relativeFrequency = new HashMap<>();
        for (Double temp : uniqueSet) {
            relativeFrequency.put(temp, (double) Collections.frequency(list.getItems(), temp)/list.getItems().size());
        }
        return relativeFrequency;
    }

    static public TreeMap<Double, Integer> prepareDataForCumulata(ListView<Double> list) {
        HashMap<Double, Integer> variantaFrequency = varaintaFrequency(list);
        TreeMap<Double, Integer> data = new TreeMap<>(variantaFrequency);
        int sum = 0;
        int currentValue = 0;
        for (Map.Entry<Double, Integer> entry : data.entrySet()           /*Integer v:data.values()*/) {
            currentValue = entry.getValue();
            sum+=currentValue;
            entry.setValue(sum);
        }
        return data;//new TreeMap<>(variantaFrequency);
    }

    static public TreeMap<Double, Double> prepareDataForRelativeCumulata(ListView<Double> list) {
        HashMap<Double, Double> variantaFrequency = relativeVaraintaFrequency(list);
        TreeMap<Double, Double> data = new TreeMap<>(variantaFrequency);
        double sum = 0;
        double currentValue = 0;
        for (Map.Entry<Double, Double> entry : data.entrySet()           /*Integer v:data.values()*/) {
            currentValue = entry.getValue();
            sum+=currentValue;
            entry.setValue(sum);
        }
        return data;
    }

    public static Double calculateModa(ListView<Double> list) {
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
            sum += Math.pow((temp - empiricalStartingPoint(1, list)), 2);
        }
        return sum / dList.size();
    }

    static Double correctedEmpiricalVariance(ListView<Double> list) {
        double sum = 0;
        ArrayList<Double> dList = new ArrayList<>(list.getItems());

        for (Double temp : dList) {
            sum += Math.pow((temp - empiricalStartingPoint(1, list)), 2);
        }
        return sum / (dList.size() - 1);
    }

    public static Double empiricalCentralPoint(int order, ListView<Double> list) {
        double sum = 0;
        ArrayList<Double> dList = new ArrayList<>(list.getItems());

        for (Double temp : dList) {
            sum += Math.pow((temp - empiricalStartingPoint(1, list)), order);
        }
        return sum / dList.size();
    }

    public static Double empiricalStartingPoint(int order, ListView<Double> list) {
        double sum = 0;
        ArrayList<Double> dList = new ArrayList<>(list.getItems());

        for (Double temp : dList) {
            sum += Math.pow(temp, order);
        }
        return sum / dList.size();
    }


    public static Double asymmetry(ListView<Double> list) {
        return empiricalCentralPoint(3, list) / Math.pow(Math.sqrt(empiricalVariance(list)), 3);
    }

    public static Double excess(ListView<Double> list) {
        return empiricalCentralPoint(4, list) / Math.pow(Math.sqrt(empiricalVariance(list)), 4) - 3;
    }

    private static boolean elementsQuantityIsOdd(ListView<Double> list) {
        return list.getItems().size() % 2 > 0;
    }

}
