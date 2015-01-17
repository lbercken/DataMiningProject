package dataminingproject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
  
public class InstanceReader {
    private final FileReader reader;
 
    public InstanceReader(String filename) throws FileNotFoundException{
        this.reader = new FileReader(filename);
    }
  
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
            i.setClassification(c[c.length - 1]);
            instances.add(i);
        }
        return instances;
    }
      
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