
package smartpost;

/**
 *
 * @author Joel Salminen 0401495
 */
public class SecondClassPackage extends Package{

    
    public SecondClassPackage(float c1, float c2, float c3, float c4, Item it) throws PackagingException, EmptySpacePackagingException{

        coordinates[0] = c1;
        coordinates[1] = c2;
        coordinates[2] = c3;
        coordinates[3] = c4;
        item = it;
        packageClass = 2;
        size[0] = 35;
        size[1] = 35;
        size[2] = 40;
        massLimit = 30;
        float volume = (float) (size[0]*size[1]*size[2]);
        
        //checks if an item can fit into a package. Throws PackagingException if it's too small or too heavy
        if (item.size[0]>size[0] | item.size[1]>size[1] | item.size[2]>size[2]| it.mass>massLimit){
            throw new PackagingException();
        }
        
        //checks if an item is not big enough to be in the package without wiggling
        if (volume > item.size[0]*item.size[1]*item.size[2]+5){
            throw new EmptySpacePackagingException();
        }
    }
}

