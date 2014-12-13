
package smartpost;


/*
 * @author Joel Salminen 0401495
 */

public class SmartPost {
    private String name;
    private String city;
    private String availability;
    private String code;
    private String address;
    private float lat;
    private float lng;
    
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
    
    //Getters:
    public float getLat(){
        return lat;
    }
    
    public float getLng(){
        return lng;
    }
    
    public String getCity(){
        return city;
    }
    
    public String getName(){
        return name;
    }
    
    public String getAvailability(){
        return availability;
    }
    
    public String getAddress(){
        return address;
    }
    public String getCode(){
        return code;
    }
}