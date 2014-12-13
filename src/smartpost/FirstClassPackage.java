
package smartpost;

/**
 *
 * @author Joel Salminen 0401495
 */
public class FirstClassPackage extends Package{
    
    public FirstClassPackage(float c1, float c2, float c3, float c4, Item it) throws PackagingException{
        
        coordinates[0] = c1;
        coordinates[1] = c2;
        coordinates[2] = c3;
        coordinates[3] = c4;
        item = it;
        packageClass = 1;
        size[0] = 30;
        size[1] = 40;
        size[2] = 50;
        massLimit = 20;
        
        //checks if an item can fit into a package. Throws PackagingException if it's too small or too heavy
        if (it.size[0]>size[0] | it.size[1]>size[1] | it.size[2]>size[2]| it.mass>massLimit){
            throw new PackagingException();
            
        }
        
    }
    

}
