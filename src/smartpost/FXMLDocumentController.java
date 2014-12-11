package smartpost;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


/*
 * @author Joel Salminen 0401495
 */

public class FXMLDocumentController implements Initializable {
    @FXML
    private Button sendAction;
    @FXML
    private Button createPackageButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addToMapButton;
    @FXML
    private ComboBox<SmartPost> automatonBox;
    @FXML
    private WebView webWindow;
    URL url;
    SmartPostList smartpostlist;
    XMLParser xmlparser;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        smartpostlist = SmartPostList.getInstance();
        webWindow.getEngine().load(getClass().getResource("index.html").toExternalForm());
        try {
            url = new URL("http://smartpost.ee/fi_apt.xml");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String content = "";
            String line;
            while ((line = br.readLine()) != null){
                content += line + "\n";
            }
            xmlparser = new XMLParser(content);
            for (int i = 0; i<smartpostlist.getSize();i++){
                automatonBox.getItems().add(smartpostlist.getSmartPost(i));
            }
            
            
            
            
        } catch (MalformedURLException ex) {
            System.err.println("error");
        } catch (IOException ex) {
            System.err.println("error");
        }
    }    

    @FXML
    private void createPackageAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent page = FXMLLoader.load(getClass().getResource("FXMLCreatePackage.fxml"));
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println("Paketin luominen epäonnistui.");
        }
    }

    @FXML
    private void deleteActioin(ActionEvent event) {
        
    }

    @FXML
    private void addToMapAction(ActionEvent event) {
        SmartPost smartpost = automatonBox.valueProperty().getValue();
        webWindow.getEngine().executeScript("document.goToLocation('"+ smartpost.address +", "+ smartpost.code
                + " " + smartpost.city + "', '"+ smartpost.name +" "+smartpost.availability +  "', 'blue')");
    }
    
}
