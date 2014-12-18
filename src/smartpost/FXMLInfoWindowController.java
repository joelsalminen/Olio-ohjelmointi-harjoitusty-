
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
    //this window gives information about the different kinds of mailing classes
    
    @FXML
    private Button closeButton;
    @FXML
    private Label firstClassLabel;
    @FXML
    private Label secondClassLabel;
    @FXML
    private Label thirdClassLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        firstClassLabel.setText("1.lähetysluokka on nopein pakettiluokka, mutta sitä ei kannata käyttää\n");
        firstClassLabel.setText(firstClassLabel.getText()+"yli 150km lähetyksissä tai lähetettäessä helposti särkyviä tavaroita.\n");
        firstClassLabel.setText(firstClassLabel.getText()+"TIMO ei ota vastuuta paketeista, jotka on lähetetty yli 150km päähän tällä\n");
        firstClassLabel.setText(firstClassLabel.getText()+"lähetysluokalla. Paketin maksinmikoko: 90x90x120 cm, painoraja 50kg");
        
        secondClassLabel.setText("2.lähetysluokka on hitain mutta samalla myös kaikista turvallisin tapa\n");
        secondClassLabel.setText(secondClassLabel.getText()+"lähettää paketteja. \n");
        secondClassLabel.setText(secondClassLabel.getText()+"Paketin maksimikoko: 35x35x40 cm, painoraja 30kg");
        
        thirdClassLabel.setText("3.lähetysluokassa kannattaa lähettää suurikokoisia ja painavia\n");
        thirdClassLabel.setText(thirdClassLabel.getText() + "paketteja, jotka eivät mene helposti rikki. \n");
        thirdClassLabel.setText(thirdClassLabel.getText() + "Paketin maksimikoko: 120x120x200 cm, painoraja 100kg");
    }

    @FXML
    private void closeAction(ActionEvent event) {
        //closes the information window when closeButton is pressed.
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
