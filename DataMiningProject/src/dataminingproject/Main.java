package dataminingproject;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
  
    public static final int totalInstances = 150;
    public static final String[] labels = {"Iris-setosa", "Iris-virginica", "Iris-versicolor"};
    
    public static void main(String[] args) throws IOException {
        InstanceReader reader = new InstanceReader("iris_data.txt");
        ArrayList<Instance> instances = reader.read();
        ArrayList<Instance> trainSet = new ArrayList<>();
        ArrayList<Instance> testSet = new ArrayList<>();
        for(int i = 0; i < totalInstances; i++) {
            if(i < 100) {
                trainSet.add(instances.get(i));
            }
            else {
                testSet.add(instances.get(i));
            }
        }
        ArrayList<Instance> copyOfTestSet = new ArrayList<>(testSet);
        for(int i = 0; i < testSet.size(); i++) {
            ArrayList<Instance> copy = new ArrayList<>(trainSet);
            KNN knn = new KNN(copy, testSet.get(i), labels, 15);
            knn.classify();
            testSet.set(i, knn.gettoClassify());
        }
        int countAccuracy = 0;
        for(int i = 0; i < testSet.size(); i++) {
            if(testSet.get(i).getLabel().compareTo(copyOfTestSet.get(i).getLabel()) == 0) {
                countAccuracy++;
            }
        }
        System.out.println("Accuracy = " + countAccuracy / 50 * 100 + "%");
    }
        
}
