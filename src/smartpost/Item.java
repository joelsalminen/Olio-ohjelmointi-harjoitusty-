
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
    
    public Item(double s1, double s2, double s3, double m, String n, boolean b){
        
        size[0] = s1;
        size[1] = s2;
        size[2] = s3;
        mass = m;
        name = n;
        canBreak = b;
    }
    

    
}
/*
class Ass extends Item{
    Ass(double s1, double s2, double s3, double m, String n, boolean b){
        super(s1,s2,s3,m,n,b);
    }
}

class Butt extends Item{
    Butt(double s1, double s2, double s3, double m, String n, boolean b){
        super(s1,s2,s3,m,n,b);
    }
}

class Nerd extends Item{
    Nerd(double s1, double s2, double s3, double m, String n, boolean b){
        super(s1,s2,s3,m,n,b);
    }
}*/