
package smartpost;

/**
 *
 * @author Joel Salminen 0401495
 */
public class FirstClassPackage extends Package{
    
    public FirstClassPackage(double c1, double c2, double c3, double c4, Item it) throws PackagingException{
        
        coordinates[0] = c1;
        coordinates[1] = c2;
        coordinates[2] = c3;
        coordinates[3] = c4;
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
