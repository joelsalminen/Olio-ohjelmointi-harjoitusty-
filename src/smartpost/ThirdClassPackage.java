
package smartpost;

/**
 *
 * @author Joel Salminen 0401495
 */
public class ThirdClassPackage extends Package{
    double size[] = new double[3];
        public ThirdClassPackage(String c1, String c2, String c3, String c4, Item it){
        super(c1, c2, c3, c4, it);
        size[0] = 30;
        size[1] = 40;
        size[2] = 50;
        }
}
