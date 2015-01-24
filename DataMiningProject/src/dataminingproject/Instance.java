package dataminingproject;

import java.util.ArrayList;
  
public class Instance {
      
    private final ArrayList<Double> features;
    private String label;
   
    public Instance(String label){
        this.label = label.trim();
        features = new ArrayList<>();
    }
 
    public Instance() {
        label = "";
        features = new ArrayList<>();
    }
    
    public Instance(Instance instance) {
        this.features = instance.features;
        this.label = instance.label;
    }
 
    public String getLabel() {
        return label;
    }
 
    public void setClassification(String classification) {
        this.label = classification.trim();
    }
 
    public double getFeature(int i) {
        return features.get(i);
    }
 
    public void addFeature(double f) {
        features.add(f);
    }
     
    public int getNrOfFeatures() {
        return features.size();
    }
      
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