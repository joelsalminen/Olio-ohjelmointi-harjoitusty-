
package smartpost;

import java.util.ArrayList;

/**
 *
 * @author Joel Salminen 0401495
 */
public class Storage {
    ArrayList<Package> packages = new ArrayList();
    static private Storage storage = null;
    private Storage(){
        
    }
    
    public Storage getInstance(){
        if (storage == null)
            storage = new Storage();
        return storage;
    }
    
    public void addPackage(Package p){
        packages.add(p);
    }
    
}
