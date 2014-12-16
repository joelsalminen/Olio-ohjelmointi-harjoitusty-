
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
 * @author Joel Salminen 0401495
 */

public class FXMLErrorWindowController implements Initializable {
    
    @FXML
    private Label errorText;
    @FXML
    private Label errorText2;
    @FXML
    private Button closeButton;
    
    //void initialize(){}
    void setErrorMessage(String error1, String error2){
        //recieves custom error message from other objects
        
        errorText.setText(error1);
        errorText2.setText(error2);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void closeAction(ActionEvent event) {
        //closes the error window
        
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    
}