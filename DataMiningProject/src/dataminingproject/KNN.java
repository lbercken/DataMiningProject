
package dataminingproject;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * This is the KNN class. This class classifies an instance with his K-nearest neighbors
 * @author Laurens van den Bercken and Jeftha Spunda
 */

public class KNN {
    
    // Note that the paper explicitly says that
    // the they used KNN with Manhattan distance metric,
    // while we use the Euclidean distance.
    
    private final ArrayList<Instance> dataset;
    private final Instance toClassify;
    private final String[] labels;
    private final int k;
    
    /**
     * Constructor for KNN
     * @param dataset This is the dataset where KNN searches for the K-nearest neighbors
     * @param toClassify The instances that is going to be classified
     * @param labels The class labels
     * @param k K for KNN
     */
    public KNN(ArrayList<Instance> dataset, Instance toClassify, String[] labels, int k) {
        this.dataset = dataset;
        this.toClassify = toClassify;
        this.labels = labels;
        this.k = k;
    }
    
    /**
     * Computes the euclidean distance between an instance a and b
     * @param a instance a
     * @param b instance b
     * @return the distance between a and b
     */
    private double euclideanDistance(Instance a, Instance b) {
        double distance = 0;
        for(int i = 0; i < a.getNrOfFeatures(); i ++)
            distance += Math.pow(a.getFeature(i) - b.getFeature(i), 2);
        return Math.sqrt(distance);
    }
    
    /**
     * This function return the k-nearest neighbors of instance toClassify
     * @return K-nearest neighbors
     */
    private ArrayList<Instance> findKNeighbors() {
        ArrayList<Instance> neighbors = new ArrayList<>();
        ArrayList<Instance> copy = new ArrayList<>();
        // We need a copy of the dataset, because we manipulate the copy
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
            // When the kth nearest neighbor is found, remove it
            copy.remove(best);
        }
        return neighbors;
    }
    
    /**
     * This function selects the label that is found most
     * in the K-nearest neighbors
     * @param table This is the table that contains the labels (key) and the
     * number of occurrences (value)
     * @return the label that is found most
     */
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
    
    /**
     * This function classifies the instance toClassify
     * @return the class label
     */
    public String classify() {
        Hashtable<String, Integer> table = new Hashtable<>();
        ArrayList<Instance> neighbors = findKNeighbors();
        String majorityLabel;
        // Initialize all label occurrences with 0
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
