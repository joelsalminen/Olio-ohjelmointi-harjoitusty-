
package smartpost;


/*
 * @author Joel Salminen 0401495
 */

public class SmartPost {
    String name;
    String city;
    String availability;
    String code;
    String address;
    float lat;
    float lng;
    
    public SmartPost(String n, String cy, String av, String cd, String add, String coord1, String coord2){
        name = n.substring(19);
        city = cy;
        availability = av;
        code = cd;
        address = add;
        lat = Float.parseFloat(coord1);
        lng = Float.parseFloat(coord2);
    }
    
    @Override
    public String toString(){
        return(city + " " + address);
    }
}