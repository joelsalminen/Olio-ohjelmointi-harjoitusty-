
package smartpost;

import java.util.Arrays;

/**
 *
 * @author Joel Salminen 0401495
 */
public class Item {
    protected String name;
    protected double[] size = new double[3];
    protected double mass;
    
    //defines whether or not an item is broken. false by default
     protected Boolean broken = false; 
     
    //defines whether or not an item can break.
    protected Boolean degradable; 

    
    public Item(float s1, float s2, float s3, float m, String n, boolean b){
        //consists of size, name and degradability
        size[0] = s1;
        size[1] = s2;
        size[2] = s3;
        Arrays.sort(size);
        mass = m;
        name = n;
        degradable = b;
    }
    
    public void breakItem (){
        //breaks an item in case it is breakable
        if (degradable == true);
        broken = true;
    }
    @Override
    public String toString(){
        return(name);
    }

}

//Different kind of items:
class DVDBox extends Item{
    DVDBox(){
        super(15,20,10,5,"DVD-boksi",false);
    }

}

class Stones extends Item{
    Stones(){
        super(100,100,150,80,"Kivikokoelma",false);
    }

}

class Cake extends Item{
    Cake(){
        super(20,30,30,5,"Täytekakku",true);
    }

}

class GlassTableWare extends Item{

    GlassTableWare (){
        super(15,20,25,8,"Lasiastiasto",true);
    }

}