
package smartpost;

/**
 * @author Joel Salminen 0401495
 */
public abstract class Package implements java.io.Serializable{
    //abstract class. SmartPost objects, mailing class, size, and an item
    protected Item item;
    protected SmartPost startSmartPost;
    protected SmartPost destinationSmartPost;
    protected int packageClass;
    protected double massLimit;
    protected double size[] = new double[3];
    
    public Package(){}
    
    @Override
    public String toString(){
        //changes the name of Package objects when displayed in a ComboBox
        return(item.toString() + ", " + packageClass +".luokka" );
    }
    
    public void breakTest(){
        //tests if an item is going to break during delivery
        if ((packageClass == 3|packageClass == 1) & item.degradable == true){
            item.breakItem();
        }

    }
    
    public SmartPost getStartSmartPost(){
        return startSmartPost;
    }
    
    public SmartPost getDestinationSmartPost(){
        return destinationSmartPost;
    }
    
    public int getPackageClass(){
        return packageClass;
    }
    
    public Item getItem(){
        return item;
    }
    
}

