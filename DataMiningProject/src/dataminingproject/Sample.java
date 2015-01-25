
package dataminingproject;


import java.util.ArrayList;

/**
 * This is a class for a Sample. A sample consists only of a list of instances
 * This is needed because we use n samples.
 * @author Laurens van den Bercken and Jeftha Spunda
 */
public class Sample {
    private final ArrayList<Instance> instances;
    
    /**
     * Constructor for a sample
     * @param instances the instances of the sample
     */
    public Sample(ArrayList<Instance> instances) {
        this.instances = instances;
    }
    
    /**
     * Getter for the instances of the sample
     * @return the instances
     */
    public ArrayList<Instance> getSample(){
        return instances;
    }
}
