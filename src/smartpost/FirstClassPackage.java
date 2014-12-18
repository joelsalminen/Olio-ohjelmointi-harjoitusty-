
package smartpost;

/**
 *
 * @author Joel Salminen 0401495
 */
public class FirstClassPackage extends Package implements java.io.Serializable{ 
    
    public FirstClassPackage(SmartPost sp1, SmartPost sp2, Item it) throws PackagingException{
        
        startSmartPost = sp1;
        destinationSmartPost = sp2;
        item = it;
        packageClass = 1;
        size[0] = 90;
        size[1] = 90;
        size[2] = 120;
        massLimit = 50;
        
        //checks if an item can fit into a package. Throws PackagingException if it's too small or too heavy
        if (it.size[0]>size[0] | it.size[1]>size[1] | it.size[2]>size[2]| it.mass>massLimit){
            throw new PackagingException();
            
        }
    }
}
