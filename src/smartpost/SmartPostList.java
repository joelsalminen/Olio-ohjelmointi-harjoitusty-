
package smartpost;


import java.util.ArrayList;


/*
 * @author Joel Salminen 0401495
 */
public class SmartPostList {
    //Stores of SmartPost objects
    
    //List SmartPost objects that can be drawn on the map:
    private ArrayList<SmartPost> PostOffices = new ArrayList();
    
    //List of SmartPost objects that have already been drawn on the map:
    private ArrayList<SmartPost> drawnPostOffices = new ArrayList();
    
    static private SmartPostList spl = null;
    private SmartPostList(){}
    
    static SmartPostList getInstance(){
        //Singleton pattern
        if (spl == null)
            spl = new SmartPostList();
        return spl;
    }
    
    
    public ArrayList<SmartPost> SmartPosts (){
        return PostOffices;
    }
    
    public ArrayList<SmartPost> DrawnSmartPosts(){
        return drawnPostOffices;
    }
    
}