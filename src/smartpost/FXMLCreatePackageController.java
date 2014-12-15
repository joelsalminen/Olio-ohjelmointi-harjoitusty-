
package smartpost;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Joel Salminen 0401495
 */

public class FXMLCreatePackageController implements Initializable {
    @FXML
    private ComboBox<Item> itemComboBox;
    @FXML
    private ComboBox<SmartPost> startComboBox;
    @FXML
    private ComboBox<SmartPost> destinationComboBox;
    @FXML
    private TextField nameInputField;
    @FXML
    private TextField massInputField;
    @FXML
    private Button infoButton;
    @FXML
    private Button closeButton;
    @FXML
    private RadioButton firstClassBox;
    @FXML
    private RadioButton secondClassBox;
    @FXML
    private RadioButton thirdClassBox;
    @FXML
    private CheckBox breakableBox;
    @FXML
    private Button createPackageButton;
    @FXML
    private TextField lengthInputField;
    @FXML
    private TextField widthInputField;
    @FXML
    private TextField heightInputField;
    
    Storage storage = Storage.getInstance();
    SmartPostList smartpostlist = SmartPostList.getInstance();

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //creates a Radio button group, adds objects to combo boxes
        
        //creates a Radio button group, sets firstClassBox on as default:
        ToggleGroup group = new ToggleGroup();
        firstClassBox.setToggleGroup(group);
        secondClassBox.setToggleGroup(group);
        thirdClassBox.setToggleGroup(group);
        firstClassBox.setSelected(true);
        
        //adds drawn markers to combo boxes
        for (int i = 0;i<smartpostlist.DrawnSmartPosts().size();i++){
            startComboBox.getItems().add(smartpostlist.DrawnSmartPosts().get(i));
            destinationComboBox.getItems().add(smartpostlist.DrawnSmartPosts().get(i));
        }
        
        //adds item to itemComboBox
        itemComboBox.getItems().add(new DVDBox());
        itemComboBox.getItems().add(new Stones());
        itemComboBox.getItems().add(new Cake());
        itemComboBox.getItems().add(new GlassTableWare());
        
        
        
        /*  sets the initial value of combo boxes to null.
            This is so that the program would not throw an error if a user tries
            to access an object from a combo box before an object is selected*/
        itemComboBox.setValue(null);
        startComboBox.setValue(null);
        destinationComboBox.setValue(null);
    }    

    @FXML
    private void infoAction(ActionEvent event) {
        //opens an info box which contains information about mailing classes
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInfoWindow.fxml"));
            Scene scene = new Scene((Parent)loader.load());
            stage.setScene(scene);
            
            scene.getStylesheets().add(FXMLInfoWindowController.class.getResource("SmartPost.css").toExternalForm());
            stage.show();
        } catch (IOException ex) {
            System.err.println("Paketti-infon avaaminen epäonnistui");
        }
        
    }

    @FXML
    private void createPackageAction(ActionEvent event) {
        //creates Package objects
        
        Item item;
        Package pack;
        float mass;
        String name;
        boolean breakable; //defines items degradability
        
        try{
            //creating an item:
            //if value is null, no items are selected from itemComboBox and the item
            //has to be created using the information gathered from input fields
            if (itemComboBox.valueProperty().getValue() == null){ 
            
                //throws InputException if input is invalid
                if (nameInputField.getText().length()<2 | massInputField.getText().length()<1 |
                        widthInputField.getText().length()<1 | lengthInputField.getText().length()<1
                        | heightInputField.getText().length() <1)
                    throw new InputException();
            
                name = nameInputField.getText();
                mass = Float.parseFloat(massInputField.getText());
                breakable = breakableBox.isSelected();
                
                //size variables are stored in an array so they can be sorted and later compared
                //to package sizes more easily
                float[] size = new float[3];
                size[0] = Float.parseFloat(widthInputField.getText());
                size[1] = Float.parseFloat(lengthInputField.getText());
                size[2] = Float.parseFloat(heightInputField.getText());
                Arrays.sort(size);

                item = new Item(size[0], size[1], size[2], mass, name, breakable);
            
            }else {
                //this value is used to create the item if an item is selected from itemBox
                item = itemComboBox.valueProperty().getValue();
            }

            
            
            float coordinates[] = new float[4];
            
            //if no SmartPost objects have been selected from combo boxes,
            //throws EmptyComboBoxException:
            if (startComboBox.valueProperty().getValue() == null | destinationComboBox.valueProperty().getValue() == null)
                throw new EmptyComboBoxException();
            
            //if start and destination are in the same location, throws
            // InvalidLocationException:
            if (startComboBox.valueProperty().getValue() == destinationComboBox.valueProperty().getValue())
                throw new InvalidLocationException();
            
            coordinates[0] = startComboBox.valueProperty().getValue().getLat();
            coordinates[1] = startComboBox.valueProperty().getValue().getLng();
            coordinates[2] = destinationComboBox.valueProperty().getValue().getLat();
            coordinates[3] = destinationComboBox.valueProperty().getValue().getLng();

            if (firstClassBox.isSelected()){
                pack = new FirstClassPackage(coordinates[0], coordinates[1], coordinates[2], coordinates[3], item);
                storage.addPackage(pack);
            }
            else if (secondClassBox.isSelected()){
                pack = new SecondClassPackage(coordinates[0], coordinates[1], coordinates[2], coordinates[3], item);
                storage.addPackage(pack);
            }
            else if (thirdClassBox.isSelected()){
                pack = new ThirdClassPackage(coordinates[0], coordinates[1], coordinates[2], coordinates[3], item);
                storage.addPackage(pack);
            }
                   
            //closing the package creating window:
            Stage stage = (Stage) createPackageButton.getScene().getWindow();
            stage.close();
            
            
        }
        
        //In case something went wrong an error window will pop up:
        catch(EmptyComboBoxException ex){
            openErrorWindow("Valitse lähtö- ja saapumisautomaatti.", "");
        }
        catch(NumberFormatException | InputException ie){
            openErrorWindow("Tarkista syötteet tai valitse" ,"valmis esine pudotusvalikosta.");
        }
        catch(InvalidLocationException ile){
            openErrorWindow("Lähtö- ja saapumisautomaatit", "eivät saa olla samoja.");
        }
        catch(PackagingException pe){
            openErrorWindow("Tavara ei mahdu pakettiin.", "Valitse toinen lähetysluokka");
        }
        
    }

    @FXML
    private void closeAction(ActionEvent event) {
        //closes the package creating window
        
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.setResizable(false);
        stage.close();
    }

    private void openErrorWindow(String errorMessage, String errorMessage2){
        //Opens an error window which can be used to tell user about incorrect usage
        //of this program. Error message can be changed depending on the situation.
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ErrorWindowFXML.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene((Pane)loader.load());
            stage.setScene(scene);

            ErrorWindowFXMLController controller = loader.<ErrorWindowFXMLController>getController();
            controller.setErrorMessage(errorMessage, errorMessage2);
            
            scene.getStylesheets().add(ErrorWindowFXMLController.class.getResource("SmartPost.css").toExternalForm());
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Virheilmoitusikkunan avaaminen epäonnistui.");
        }
    }

}
