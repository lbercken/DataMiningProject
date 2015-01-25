package dataminingproject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class reads instances from a file
 * @author Laurens van den Bercken and Jeftha Spunda
 */
public class InstanceReader {
    
    private final FileReader reader;
 
    /**
     * Constructor for the reader
     * @param filename the file that is going to be read
     * @throws FileNotFoundException if the file is not found, throw FileNotFoundException 
     */
    public InstanceReader(String filename) throws FileNotFoundException{
        this.reader = new FileReader(filename);
    }
  
    /**
     * This function reads instances from file
     * @return a list of instances
     * @throws IOException throws an exception when there is no file to read
     */
    public ArrayList<Instance> read() throws IOException{
        ArrayList<Instance> instances;
        instances = new ArrayList<>();
        String s;
        String[] c;
        Instance i;
        while(reader.ready()){
            s = getInstanceFromFile();
            i = new Instance();
            c = s.split(",");
            for(int j = 0; j < c.length - 1; j++)
                i.addFeature(Double.parseDouble(c[j]));
            // The last part of a line is the class label
            // See dataset that is used
            i.setClassification(c[c.length - 1]);
            instances.add(i);
        }
        return instances;
    }
    
    /**
     * This function reads a single instance, and thus a single line, from file
     * @return the instance
     * @throws IOException throws an exception when there was no file to read
     */
    private String getInstanceFromFile() throws IOException{
        StringBuilder instance = new StringBuilder();
        boolean over = false;
        while(!over){
            char c = (char) reader.read();
            if(c == '\n')
                over = true;
            else
                instance.append(c);
        }
        return instance.toString();
    }
}