package dataminingproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main {
  
    private static final int totalInstances = 150;
    private static final String[] labels = {"Iris-setosa", "Iris-virginica", "Iris-versicolor"};
    private static final int k = 1; // k used for KNN
    private static final int n = 100; // Number of samples
    private static final int m = 3; // Sample size
    
    public static void main(String[] args) throws IOException {
        InstanceReader reader = new InstanceReader("iris_data.txt");
        ArrayList<Instance> instances = reader.read();
        Collections.shuffle(instances); // the data set was sorted on label, so that is why we shuffle!
        ArrayList<Instance> trainSet = new ArrayList<>();
        ArrayList<Instance> testSet = new ArrayList<>();
        ArrayList<Sample> samples = new ArrayList<>();
        Random random = new Random(); // needed for selecting random samples
        
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
            for(Instance instance : trainSet) {
                copyOfTrainSet.add(new Instance(instance));
            }
            for(int i = 0; i < trainSet.size(); i++) {
                KNN knn = new KNN(samples.get(j).getSample(), trainSet.get(i), labels, k);
                String label = knn.classify();
                copyOfTrainSet.get(i).setClassification(label);
            }
            int countAccuracy = 0;
            for(int i = 0; i < trainSet.size(); i++) {
                if(trainSet.get(i).getLabel().compareTo(copyOfTrainSet.get(i).getLabel()) == 0) {
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
            if(testSet.get(i).getLabel().compareTo(copyOfTestSet.get(i).getLabel()) == 0) {
                countAccuracy++;
            }
        }
        int size = bestSample.getSample().size();
        System.out.println("Accuracy = " + (countAccuracy / 50.0) * 100.0 + "%"
            + " with sample size = " + size + ".");
        System.out.println("Storage = " + size / (double) trainSet.size() * 100.0 + "%");
    }
        
}
