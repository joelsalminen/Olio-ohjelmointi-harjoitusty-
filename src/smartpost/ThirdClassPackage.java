
package smartpost;

/**
 *
 * @author Joel Salminen 0401495
 */
public class ThirdClassPackage extends Package implements java.io.Serializable{
        public ThirdClassPackage(SmartPost sp1, SmartPost sp2, Item it) throws PackagingException{

        startSmartPost = sp1;
        destinationSmartPost = sp2;
        item = it;
        packageClass = 3;
        size[0] = 120;
        size[1] = 120;
        size[2] = 200;
        massLimit = 100;
        
        //checks if an item can fit into a package. Throws PackagingException if it's too small or too heavy
        if (item.getSize()[0]>size[0] | item.getSize()[1]>size[1] | item.getSize()[2]>size[2]| item.getMass()>massLimit)
            throw new PackagingException();
        }
}
