
package smartpost;


import java.util.ArrayList;


/*
 * @author Joel Salminen 0401495
 */
public class SmartPostList {
    ArrayList<SmartPost> PostOffices = new ArrayList();
    ArrayList<SmartPost> drawnPostOffices = new ArrayList();
    static private SmartPostList spl = null;
    
    private SmartPostList(){}
    
    static SmartPostList getInstance(){
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