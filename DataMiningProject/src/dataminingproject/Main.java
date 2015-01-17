package dataminingproject;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
  
    public static void main(String[] args) throws IOException {
        System.out.println("Reading file...");
        InstanceReader reader = new InstanceReader("iris_data.txt");
        ArrayList<Instance> instances = reader.read();
        System.out.println("Done reading file");
        for(Instance instance : instances) {
            System.out.println(instance);
        }
        System.out.println("Done printing all instances from file");
    }
        
}
