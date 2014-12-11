
package smartpost;

/**
 * @author Joel Salminen 0401495
 */
public class Package{
    Item item;
    String startCoordinates_lat;
    String startCoordinates_lon;
    String endCoordinates_lat;
    String endCoordinates_lon;
    
    public Package(String c1, String c2, String c3, String c4, Item it){

        startCoordinates_lat = c1;
        startCoordinates_lon = c2;
        endCoordinates_lat = c3;
        endCoordinates_lon = c4;
        item = it;
    }
    
   
}

