
package smartpost;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Solid
 */
public class FXMLMessageWindowController implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private Label messageText;
    @FXML
    private Label messageText2;
    
    void setMessage(String message1, String message2){
        //receives custom message from other objects
        
        messageText.setText(message1);
        messageText2.setText(message2);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void closeAction(ActionEvent event) {
        Stage stage = (Stage)closeButton.getScene().getWindow();
        stage.close();
    }
    
}
