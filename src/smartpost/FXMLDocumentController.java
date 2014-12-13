package smartpost;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


/*
 * @author Joel Salminen 0401495
 */

public class FXMLDocumentController implements Initializable {
    //Main window of this program. 
    
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
    @FXML
    private Button sendButton;
    @FXML
    private ComboBox<Package> packageBox;
    @FXML
    private Button refreshPackagesButton;



    SmartPostList smartpostlist;

    Storage storage = Storage.getInstance();
    
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //parses an XML-document, loads objects to combo boxes
        
        //loads the map in webWindow
        webWindow.getEngine().load(getClass().getResource("index.html").toExternalForm());
        smartpostlist = SmartPostList.getInstance(); //list of SmartPost objects, currently empty
        
        try {
            URL xmlURL = new URL("http://smartpost.ee/fi_apt.xml");
            BufferedReader br = new BufferedReader(new InputStreamReader(xmlURL.openStream()));
            String content = "";
            String line;
            while ((line = br.readLine()) != null){
                content += line + "\n";
            }
            
            //XML-documet is parsed in XMLParser object
            XMLParser xmlparser = new XMLParser();
            xmlparser.parse(content);
            
            //Parsed SmartPost objects from SmartPostList will be put in the combo box:
            for (int i = 0; i<smartpostlist.SmartPosts().size();i++){
                automatonBox.getItems().add(smartpostlist.SmartPosts().get(i));
            }

            //Packages from PackageList will be put in the combo box:
            for (int i = 0;i<storage.getPackageList().size();i++){
                packageBox.getItems().add(storage.getPackageList().get(i));
            }
            
            /*  sets the current value of combo boxes to null.
                This is so that the program would not throw an error if a user tries
                to access an object from a combo box before an object is selected*/
            automatonBox.setValue(null);
            packageBox.setValue(null);
            
          } catch (MalformedURLException ex) {
            System.err.println("Virheellinen URL");
        } catch (IOException ex) {
            System.err.println("Buffered Readerin avaaminen epäonnistui");
        }
    }    

    @FXML
    private void createPackageAction(ActionEvent event) {
        //opens a package creating window
        
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLCreatePackage.fxml"));
            Parent parent = (Parent)loader.load();
            stage.setScene(new Scene(parent));
            
            stage.show();
        } catch (IOException ex) {
            System.err.println("Paketinluomisikkunan avaaminen epäonnistui.");
        }
    }


    @FXML
    private void deleteAction(ActionEvent event) {
        //deletes all routes on the map
        
        webWindow.getEngine().executeScript("document.deletePaths()");
    }

    @FXML
    private void addToMapAction(ActionEvent event) {
        //adds markers on the map using coordinates from SmartPost objects
        
        
        if (automatonBox.valueProperty().getValue() != null){ //value is null if no objects have been selected from the combo box
            SmartPost smartpost = automatonBox.valueProperty().getValue();
            
            //adds a marker:
            webWindow.getEngine().executeScript("document.goToLocation('"+ smartpost.getAddress() +", "+ smartpost.getCode()
                    + " " + smartpost.getCity() + "', '"+ smartpost.getName() +" "+smartpost.getAvailability() +  "', 'pink')");
            
            
            //drawn SmartPost object is added on the DrawnSmartPostList if it's not added before
            if (smartpostlist.DrawnSmartPosts().contains(smartpost) == false){
                smartpostlist.DrawnSmartPosts().add(smartpost);
            }
        }

    }

    @FXML
    private void sendAction(ActionEvent event) {
        //sends a package, draws a route on the map
        
        if (packageBox.valueProperty().getValue() != null){ //value is null if no objects have been selected from the combo box
            
            //coordinates are added into an arraylist
            ArrayList <Float> al = new ArrayList();
            al.add(packageBox.valueProperty().getValue().coordinates[0]);
            al.add(packageBox.valueProperty().getValue().coordinates[1]);
            al.add(packageBox.valueProperty().getValue().coordinates[2]);
            al.add(packageBox.valueProperty().getValue().coordinates[3]);
            
            //draws a route on the map: 
            webWindow.getEngine().executeScript("document.createPath("+al+", 'red', " + packageBox.valueProperty().getValue().packageClass +")");
            
            //tests if items were broken during delivery:
            packageBox.valueProperty().getValue().breakTest();
            if(packageBox.valueProperty().getValue().item.broken == true){
                openErrorWindow("Tavara hajosi kuljetuksen", "aikana!");
            }
            
            //removes delivered package from Storage and then reloads the packages
            //in packageBox
            storage.getPackageList().remove(packageBox.valueProperty().getValue());
            packageBox.getItems().clear();
            packageBox.setValue(null); //value is set to null to avoid NullPointerException
            for (int i = 0; i< storage.getPackageList().size();i++){
                packageBox.getItems().add(storage.getPackageList().get(i));
            }
            
            
        }
    }

    @FXML
    private void refreshAction(ActionEvent event) {
        //refreshes pakcageBox
        
        packageBox.getItems().clear();
        storage = Storage.getInstance();
        for (int i = 0; i< storage.getPackageList().size();i++){
            packageBox.getItems().add(storage.getPackageList().get(i));
        }
    }

    private void openErrorWindow(String errorMessage, String errorMessage2){
        //Opens an error window which can be used to tell user about incorrect usage
        //of this program. Error message can be changed depending on the situation.
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ErrorWindowFXML.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene((Pane)loader.load()));
            
            //Passes error messages to ErrorWindowFXMLController
            //http://stackoverflow.com/questions/14187963/passing-parameters-javafx-fxml
            ErrorWindowFXMLController controller = loader.<ErrorWindowFXMLController>getController();
            controller.setErrorMessage(errorMessage, errorMessage2);
            
            stage.show();
        } catch (IOException ex) {
            System.out.println("Virheilmoitusikkunan avaaminen epäonnistui.");
        }
    }

    
    
    
}
