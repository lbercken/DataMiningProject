package dataminingproject;

import java.util.ArrayList;

/**
 * This is the instance class
 * @author Laurens van den Bercken and Jeftha Spunda
 */
public class Instance {
      
    private final ArrayList<Double> features;
    private String label;
 
    /**
     * Standard constructor for an instance
     */
    public Instance() {
        label = "";
        features = new ArrayList<>();
    }
    
    /**
     * Copy constructor for an instance
     * @param instance the instance that is copied
     */
    public Instance(Instance instance) {
        this.features = instance.features;
        this.label = instance.label;
    }
 
    /**
     * Getter for label of instance
     * @return the label
     */
    public String getLabel() {
        return label;
    }
 
    /**
     * Set label for an instance
     * @param classification the new string
     */
    public void setClassification(String classification) {
        this.label = classification.trim();
    }
 
    /**
     * Getter for getting a feature i from an instance
     * @param i for getting the ith feature
     * @return the ith feature
     */
    public double getFeature(int i) {
        return features.get(i);
    }
 
    /**
     * Function for adding a feature
     * @param f the value of the feature
     */
    public void addFeature(double f) {
        features.add(f);
    }
    
    /**
     * Getter for getting the number of features
     * @return the number of features
     */
    public int getNrOfFeatures() {
        return features.size();
    }
      
    /**
     * This is the toString for an instance
     * @return the String representation of an instance
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Double feature : features) {
            s.append(feature).append(" ");
        }
        s.append("\t").append(label);
        return s.toString();
    }
}