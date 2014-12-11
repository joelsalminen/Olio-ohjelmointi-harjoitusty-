
package smartpost;

/**
 *
 * @author Joel Salminen 0401495
 */
public class FirstClassPackage extends Package{
    double size[] = new double[3];
    
    public FirstClassPackage(float c1, float c2, float c3, float c4, Item it){
        super(c1, c2, c3, c4, it);
        size[0] = 30;
        size[1] = 40;
        size[2] = 50;
    }
    

}
