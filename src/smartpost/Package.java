
package smartpost;

/**
 * @author Joel Salminen 0401495
 */
public class Package{
    Item item;
    float []coordinates = new float[4];

    
    public Package(float c1, float c2, float c3, float c4, Item it){

        coordinates[0] = c1;
        coordinates[1] = c2;
        coordinates[2] = c3;
        coordinates[3] = c4;
        item = it;
    }
    
   
}

