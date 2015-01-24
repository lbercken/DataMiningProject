
package dataminingproject;

import java.util.ArrayList;

public class Sample {
    private final ArrayList<Instance> instances;
    
    public Sample(ArrayList<Instance> instances) {
        this.instances = instances;
    }
    
    public ArrayList<Instance> getSample(){
        return instances;
    }
}
