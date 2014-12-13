
package smartpost;

import java.util.Arrays;

/**
 *
 * @author Joel Salminen 0401495
 */
public class ThirdClassPackage extends Package{
        public ThirdClassPackage(float c1, float c2, float c3, float c4, Item it) throws PackagingException{
        coordinates[0] = c1;
        coordinates[1] = c2;
        coordinates[2] = c3;
        coordinates[3] = c4;
        item = it;
        packageClass = 3;
        size[0] = 100;
        size[1] = 115;
        size[2] = 120;
        massLimit = 100;
        
        //checks if an item can fit into a package. Throws PackagingException if it's too small or too heavy
        if (item.size[0]>size[0] | item.size[1]>size[1] | item.size[2]>size[2]| item.mass>massLimit)
            throw new PackagingException();
        }
}
