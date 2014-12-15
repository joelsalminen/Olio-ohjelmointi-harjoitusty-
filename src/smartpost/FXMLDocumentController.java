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
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
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
    private ComboBox<SmartPost> automatonComboBox;
    @FXML
    private WebView webWindow;
    @FXML
    private Button sendButton;
    @FXML
    private ComboBox<Package> packageComboBox;
    @FXML
    private Button refreshPackagesButton;
    @FXML
    private Text titleText;

    private SmartPostList smartpostlist;
    private Storage storage = Storage.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //parses an XML-document, loads objects to combo boxes
        
        titleText.setId("title-text");
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
            
            //Parsed SmartPost objects from SmartPostList are be put in the combo box:
            for (int i = 0; i<smartpostlist.SmartPosts().size();i++){
                automatonComboBox.getItems().add(smartpostlist.SmartPosts().get(i));
            }

            //Packages from PackageList will be put in the combo box:
            for (int i = 0;i<storage.getSize();i++){
                packageComboBox.getItems().add(storage.getPackage(i));
            }
            
            /*  sets the current value of combo boxes to null.
                This is so that the program would not throw an error if a user tries
                to access an object from a combo box before an object is selected*/
            automatonComboBox.setValue(null);
            packageComboBox.setValue(null);
            
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
            Scene scene = new Scene((Parent)loader.load());
            stage.setScene(scene);
            scene.getStylesheets().add(FXMLCreatePackageController.class.getResource("SmartPost.css").toExternalForm());
            stage.setResizable(false);
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
        
        
        if (automatonComboBox.valueProperty().getValue() != null){ //value is null if no objects have been selected from the combo box
            SmartPost smartpost = automatonComboBox.valueProperty().getValue();
            
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
        
        if (packageComboBox.valueProperty().getValue() != null){ //value is null if no objects have been selected from the combo box
            double distance; //distance between start and destination 
            
            //coordinates are added into an arraylist
            ArrayList <Double> al = new ArrayList();
            al.add(packageComboBox.valueProperty().getValue().coordinates[0]);
            al.add(packageComboBox.valueProperty().getValue().coordinates[1]);
            al.add(packageComboBox.valueProperty().getValue().coordinates[2]);
            al.add(packageComboBox.valueProperty().getValue().coordinates[3]);
            
            try {
                //draws a route on the map: 
                distance = (double)webWindow.getEngine().executeScript("document.createPath("+al+", 'red', " + packageComboBox.valueProperty().getValue().packageClass +")");
                
                //checks if the distance between start and finish is longer than 150km
                //when delivering first class packages
                if (packageComboBox.valueProperty().getValue().packageClass == 1 & distance >150){
                    throw new DistanceException();
                }
            
                //tests if items were broken during delivery:
                packageComboBox.valueProperty().getValue().breakTest();
                if(packageComboBox.valueProperty().getValue().item.broken == true){
                    throw new BrokenItemException();
                }
            
            
            }
            catch(DistanceException de){
                openErrorWindow("Paketti jäi matkan varrelle!", "Liian pitkä välimatka 1.luokan paketeille");
                webWindow.getEngine().executeScript("document.deletePaths()");
            }
            catch(BrokenItemException bie){
                openErrorWindow("Tavara hajosi kuljetuksen", "aikana!");
                webWindow.getEngine().executeScript("document.deletePaths()");
            }
            //removes delivered package from Storage and then reloads the packages
            //in packageBox
            storage.removePackage(packageComboBox.valueProperty().getValue());
            packageComboBox.getItems().clear();
            packageComboBox.setValue(null); //value is set to null to avoid NullPointerException
            for (int i = 0; i< storage.getSize();i++){
                packageComboBox.getItems().add(storage.getPackage(i));
            }
        }
    }

    @FXML
    private void refreshAction(ActionEvent event) {
        //refreshes pakcageComboBox
        
        packageComboBox.getItems().clear();
        storage = Storage.getInstance();
        for (int i = 0; i< storage.getSize();i++){
            packageComboBox.getItems().add(storage.getPackage(i));
        }
    }

    private void openErrorWindow(String errorMessage, String errorMessage2){
        //Opens an error window which can be used to tell user about incorrect usage
        //of this program. Error message can be changed depending on the situation.
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ErrorWindowFXML.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene((Pane)loader.load());
            stage.setScene(scene);
            
            //Passes error messages to ErrorWindowFXMLController
            //http://stackoverflow.com/questions/14187963/passing-parameters-javafx-fxml
            ErrorWindowFXMLController controller = loader.<ErrorWindowFXMLController>getController();
            controller.setErrorMessage(errorMessage, errorMessage2);
            
            scene.getStylesheets().add(ErrorWindowFXMLController.class.getResource("SmartPost.css").toExternalForm());
            stage.show();
        } catch (IOException ex) {
            System.out.println("Virheilmoitusikkunan avaaminen epäonnistui.");
        }
    }
    
}
