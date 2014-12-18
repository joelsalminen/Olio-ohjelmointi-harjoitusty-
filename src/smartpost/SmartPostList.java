
package smartpost;


import java.util.ArrayList;

/*
 * @author Joel Salminen 0401495
 */
public class SmartPostList {
    //Stores of SmartPost objects
    
    private ArrayList<SmartPost> smartPosts = new ArrayList();
    
    static private SmartPostList spl = null;
    private SmartPostList(){}
    
    static public SmartPostList getInstance(){
        //Singleton pattern
        if (spl == null)
            spl = new SmartPostList();
        return spl;
    }

    public int getSize(){
        return smartPosts.size();
    }
    public SmartPost getSmartPost(int i){
        return smartPosts.get(i);
    }
    
    public void addSmartPost(SmartPost sp){
        smartPosts.add(sp);
    }
    
}