package dataminingproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * 
 * @author Laurens van den Bercken and Jeftha Spunda
 */
public class Main {
  
    private static final int totalInstances = 150;
    private static final String[] labels = {"Iris-setosa", "Iris-virginica", "Iris-versicolor"};
    private static final int k = 1; // k used for KNN
    private static final int n = 100; // Number of samples
    private static final int m = 3; // Sample size
    private static final String fileName = "iris_data.txt";
    
    /**
     * This is the main. This is were the algorithm is in 4 steps.
     * @param args
     * @throws IOException This will throw an IOException when the file is
     * not found
     */
    public static void main(String[] args) throws IOException {
        InstanceReader reader = new InstanceReader(fileName);
        ArrayList<Instance> instances = reader.read();
        // We call the function shuffle so that we have random instances in our
        // train and test set.
        Collections.shuffle(instances);
        ArrayList<Instance> trainSet = new ArrayList<>();
        ArrayList<Instance> testSet = new ArrayList<>();
        ArrayList<Sample> samples = new ArrayList<>();
        // This is needed for selecting random samples
        Random random = new Random(); 
        
        // Split dataset into train and test set
        // train size = 100; test size = 50;
        for(int i = 0; i < totalInstances; i++) {
            if(i < 100) {
                trainSet.add(instances.get(i));
            }
            else {
                testSet.add(instances.get(i));
            }
        }
        
        // Step 1: Build samples
        for(int i = 0; i < n; i++) {
            ArrayList<Instance> sample = new ArrayList<>();
            for(int j = 0; j < m; j++) {
                // Note that we do not remove an instance when we select one randomly
                // In other words, we select instances randomly with replacement
                int randomInstance = random.nextInt(trainSet.size());
                sample.add(trainSet.get(randomInstance));
            }
            samples.add(new Sample(sample));
        }
        
        // Step 2: Classify training set with samples
        ArrayList<Double> accuracy = new ArrayList<>();
        for(int j = 0; j < n; j++) {
            ArrayList<Instance> copyOfTrainSet = new ArrayList<>();
            // We need a copy (for every sample) of the training set to compute the accuracy.
            for(Instance instance : trainSet) {
                copyOfTrainSet.add(new Instance(instance));
            }
            for(int i = 0; i < trainSet.size(); i++) {
                KNN knn = new KNN(samples.get(j).getSample(), trainSet.get(i), labels, k);
                String label = knn.classify();
                copyOfTrainSet.get(i).setClassification(label);
            }
            // Compute accuracy
            int countAccuracy = 0;
            for(int i = 0; i < trainSet.size(); i++) {
                if(trainSet.get(i).getLabel().equals(copyOfTrainSet.get(i).getLabel())) {
                    countAccuracy++;
                }
            }
            double pushAccuracy = countAccuracy / (double) trainSet.size();
            accuracy.add(pushAccuracy);
        }
        
        // Step 3: Select sample with highest accuracy
        double highest = accuracy.get(0);
        int highestIndex = 0;
        for(int i = 1; i < n; i++) {
            double higher = accuracy.get(i);
            if(highest < higher) {
                highest = higher;
                highestIndex = i;
            }
        }
        Sample bestSample = samples.get(highestIndex);
        
        // Step 4: Classify test set with best sample
        ArrayList<Instance> copyOfTestSet = new ArrayList<>();
        // We need a copy again for computing accuracy
        for(Instance instance : testSet) {
            copyOfTestSet.add(new Instance(instance));
        }
        for(int i = 0; i < testSet.size(); i++) {
            KNN knn = new KNN(bestSample.getSample(), testSet.get(i), labels, k);
            String label = knn.classify();
            copyOfTestSet.get(i).setClassification(label);
        }
        int countAccuracy = 0;
        for(int i = 0; i < testSet.size(); i++) {
            if(testSet.get(i).getLabel().equals(copyOfTestSet.get(i).getLabel())) {
                countAccuracy++;
            }
        }
        int size = bestSample.getSample().size();
        System.out.println("Accuracy = " + (countAccuracy / 50.0) * 100.0 + "%"
            + " with sample size = " + size + ".");
        System.out.println("Storage = " + size / (double) trainSet.size() * 100.0 + "%");
    }
        
}
