/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartpost;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author Joel Salminen 0401495
 */
public class FXMLLogController implements Initializable {
    @FXML
    private Button closeButton;
    @FXML
    private ListView<String> eventWindow;

    private ArrayList<String> eventList = new ArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (int i = 0;i<eventList.size();i++){
            eventWindow.getItems().add(eventList.get(i));
        }
    }    

    @FXML
    private void closeAction(ActionEvent event) {
        
    }
    
    public void addEvent(String S){
        eventList.add(S);
        System.out.println(S);
        
                
        
    }
    
}
