
package smartpost;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/*
 * @author Joel Salminen 0401495
 */

public class XMLParser {
    private Document doc;
    SmartPostList smartpostlist = SmartPostList.getInstance();
    
    
    public void parse(String content){
        //parses XML-documents, parsed data is used to create SmartPost-objects
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbuilder = dbf.newDocumentBuilder();
            doc = dbuilder.parse(new InputSource(new StringReader(content)));
            doc.getDocumentElement().normalize();;
            parseCurrentData();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.err.println("Parsiminen epäonnistui.");
        }
    
    
    }
    
    
    private void parseCurrentData(){
        //Creates SmartPost objects which are stored in SmartPostList object for later usage

        String name;
        String city;
        String availability;
        String code;
        String address;
        String lat;
        String lng;
        NodeList nodes = doc.getElementsByTagName("place");
        
        for (int i = 0; i<nodes.getLength();i++){
            
            Node node = nodes.item(i);
            Element e = (Element)node;
            
            name = getValue("postoffice", e);
            city = getValue("city",e);
            availability = getValue("availability", e);
            code = getValue("code", e);
            address = getValue("address", e);
            lat = getValue("lat", e);
            lng = getValue("lng", e);
            smartpostlist.SmartPosts().add(new SmartPost(name, city, availability, code, address, lat, lng));
            
        }
        
    }
    
    private String getValue(String tag, Element e){
        //
        return((Element)e.getElementsByTagName(tag).item(0)).getTextContent();
    }
    

}