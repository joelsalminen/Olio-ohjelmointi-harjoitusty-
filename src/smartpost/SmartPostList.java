
package smartpost;


import java.util.ArrayList;


/*
 * @author Joel Salminen 0401495
 */
public class SmartPostList {
    ArrayList<SmartPost> PostOffices = new ArrayList();
    static private SmartPostList spl = null;
    
    private SmartPostList(){}
    
    static SmartPostList getInstance(){
        if (spl == null)
            spl = new SmartPostList();
        return spl;
    }
    
    public void addSmartPost(SmartPost sp){
        PostOffices.add(sp);
    }
        
    public SmartPost getSmartPost(int i){
        return (PostOffices.get(i));
    }
    
    public int getSize(){
        return PostOffices.size();
    }
}