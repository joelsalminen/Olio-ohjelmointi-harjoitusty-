
package smartpost;

/**
 *
 * @author Joel Salminen 0401495
 */
public class ThirdClassPackage extends Package{
        public ThirdClassPackage(double c1, double c2, double c3, double c4, Item it) throws PackagingException{
        coordinates[0] = c1;
        coordinates[1] = c2;
        coordinates[2] = c3;
        coordinates[3] = c4;
        item = it;
        packageClass = 3;
        size[0] = 120;
        size[1] = 120;
        size[2] = 200;
        massLimit = 100;
        
        //checks if an item can fit into a package. Throws PackagingException if it's too small or too heavy
        if (item.size[0]>size[0] | item.size[1]>size[1] | item.size[2]>size[2]| item.mass>massLimit)
            throw new PackagingException();
        }
}
