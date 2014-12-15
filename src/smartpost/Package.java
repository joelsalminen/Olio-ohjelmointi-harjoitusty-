
package smartpost;

/**
 * @author Joel Salminen 0401495
 */
public abstract class Package{
    //abstract class. Consists of coordinates, class, size, and an item
    protected Item item;
    protected double []coordinates = new double[4];
    protected int packageClass;
    protected double massLimit;
    protected double size[] = new double[3];
    
    public Package(){
    }
    
    @Override
    public String toString(){
        return(item.toString() + ", " + packageClass +".luokka" );
    }
    
    public void breakTest(){
        //tests if an item is going to break during delivery
        if ((packageClass == 3|packageClass == 1) & item.degradable == true){
            item.breakItem();
        }

    }
}

