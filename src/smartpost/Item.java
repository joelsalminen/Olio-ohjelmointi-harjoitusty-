
package smartpost;

/**
 *
 * @author Joel Salminen 0401495
 */
public class Item {
    String name;
    double[] size = new double[3]; 
    Boolean broken = false;
    double mass;
    Boolean canBreak;
    
    public Item(float s1, float s2, float s3, float m, String n, boolean b){
        
        size[0] = s1;
        size[1] = s2;
        size[2] = s3;
        mass = m;
        name = n;
        canBreak = b;
    }
    

    
}

class Ass extends Item{
    Ass(float s1, float s2, float s3, float m, String n, boolean b){
        super(s1,s2,s3,m,n,b);
    }
}

class Butt extends Item{
    Butt(float s1, float s2, float s3, float m, String n, boolean b){
        super(s1,s2,s3,m,n,b);
    }
}

class Nerd extends Item{
    Nerd(float s1, float s2, float s3, float m, String n, boolean b){
        super(s1,s2,s3,m,n,b);
    }
}

class Glass extends Item{

    Glass (float s1, float s2, float s3, float m, String n, boolean b){
        super(s1,s2,s3,m,n,b);
    }
}