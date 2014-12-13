
package smartpost;

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
class Ass extends Item{
    Ass(){
        super(20,30,20,5,"Ass",false);
    }

}

class Butt extends Item{
    Butt(){
        super(20,30,20,5,"Butt",false);
    }

}

class Nerd extends Item{
    Nerd(){
        super(20,30,20,5,"Nerd",true);
    }

}

class Glass extends Item{

    Glass (){
        super(20,30,20,5,"Glass",true);
    }

}