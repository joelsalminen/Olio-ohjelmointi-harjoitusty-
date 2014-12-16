
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
    private double lat;
    private double lng;
    
    public SmartPost(String n, String cy, String av, String cd, String add, String coord1, String coord2){
        name = n.substring(19);
        city = cy;
        availability = av;
        code = cd;
        address = add;
        lat = Double.parseDouble(coord1);
        lng = Double.parseDouble(coord2);
    }
    
    @Override
    public String toString(){
        return(city + " " + address);
    }
    
    //Getters:
    public double getLat(){
        return lat;
    }
    
    public double getLng(){
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