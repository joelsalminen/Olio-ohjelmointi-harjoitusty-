
package smartpost;

/**
 *
 * @author Joel Salminen 0401495
 */
public class SecondClassPackage extends Package{

    public SecondClassPackage(double c1, double c2, double c3, double c4, Item it) throws PackagingException{

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
        
        //checks if an item can fit into a package. Throws PackagingException if it's too small or too heavy
        if (item.size[0]>size[0] | item.size[1]>size[1] | item.size[2]>size[2]| it.mass>massLimit){
            throw new PackagingException();
        }
        
    }
}

