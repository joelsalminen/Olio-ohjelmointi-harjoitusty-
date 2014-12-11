
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

public class FXMLInfoWindowController implements Initializable {
    @FXML
    private Label firstClassLabel;
    @FXML
    private Label secondClassLabel;
    @FXML
    private Label thirdClassLabel;
    @FXML
    private Button closeButton;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        firstClassLabel.setText("ekan luokan paketit on hassuja, koko x*x*x");
        secondClassLabel.setText("nii on tokanki  luokan, koko x*x*x");
        thirdClassLabel.setText("kolmansia voi vaan paiskoa, koko x*x*x");
    }

    @FXML
    private void closeAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    
}
