
package dataminingproject;

import java.util.ArrayList;
import java.util.Hashtable;

public class KNN {
    
    // Note that the paper explicitly says that
    // the they used KNN with Manhattan distance metric,
    // while we use the Euclidean distance.
    
    private final ArrayList<Instance> dataset;
    private final Instance toClassify;
    private final String[] labels;
    private final int k;
    
    public KNN(ArrayList<Instance> dataset, Instance toClassify, String[] labels, int k) {
        this.dataset = dataset;
        this.toClassify = toClassify;
        this.labels = labels;
        this.k = k;
    }
    
    private double euclideanDistance(Instance a, Instance b) {
        double distance = 0;
        for(int i = 0; i < a.getNrOfFeatures(); i ++)
            distance += Math.pow(a.getFeature(i) - b.getFeature(i), 2);
        return Math.sqrt(distance);
    }
    
    private ArrayList<Instance> findKNeighbors() {
        ArrayList<Instance> neighbors = new ArrayList<>();
        ArrayList<Instance> copy = new ArrayList<>();
        for(Instance instance : dataset) {
            copy.add(new Instance(instance));
        }
        for(int p = 0; p < k; p++){
            Instance best = copy.get(0);
            for(int i = 1; i < copy.size(); i++) {
                Instance temp = copy.get(i);
                if(euclideanDistance(toClassify, temp) < euclideanDistance(toClassify, best))
                    best =  temp;
            }
            neighbors.add(best);
            copy.remove(best);
        }
        return neighbors;
    }
    
    private String selectBestLabel(Hashtable<String, Integer> table) {
        int highest = table.get(labels[0]);
        String bestLabel = labels[0];
        for (int i = 1; i < labels.length; i++) {
            int temp = table.get(labels[i]);
            if(highest < temp) {
                highest = temp;
                bestLabel = labels[i];
            }
        }
        return bestLabel;
    }
    
    public String classify() {
        Hashtable<String, Integer> table = new Hashtable<>();
        ArrayList<Instance> neighbors = findKNeighbors();
        String majorityLabel;
        for (String label : labels) {
            table.put(label, 0);
        }
        for (Instance neighbor : neighbors) {
            String label = neighbor.getLabel();
            table.put(label, 1 + table.get(label));
        }
        return selectBestLabel(table);
    }
}
