
package smartpost;

import java.util.ArrayList;

/**
 * @author Joel Salminen 0401495
 */

public class Storage {
    //This class is used to keep track of Package objects
    private ArrayList<Package> packages = new ArrayList();
    static private Storage storage = null;
    private Storage(){
    }
    
    static Storage getInstance(){
        //Sigleton pattern
        if (storage == null)
            storage = new Storage();
        return storage;
    }
    
    public int getSize(){
        return packages.size();
    }
    
    public Package getPackage(int i){
       return packages.get(i);
   } 

    public void removePackage(Package p){
        packages.remove(p);
    }
    
    public void addPackage(Package p){
        packages.add(p);
    }
}
