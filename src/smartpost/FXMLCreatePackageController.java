
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Joel Salminen 0401495
 */

public class FXMLCreatePackageController implements Initializable {
    @FXML
    private ComboBox<Item> itemBox;
    @FXML
    private ComboBox<SmartPost> startBox;
    @FXML
    private ComboBox<SmartPost> destinationBox;
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
        ToggleGroup group = new ToggleGroup();
        firstClassBox.setToggleGroup(group);
        secondClassBox.setToggleGroup(group);
        thirdClassBox.setToggleGroup(group);
        firstClassBox.setSelected(true);
        for (int i = 0;i<smartpostlist.drawnPostOffices.size();i++){
            startBox.getItems().add(smartpostlist.drawnPostOffices.get(i));
            destinationBox.getItems().add(smartpostlist.drawnPostOffices.get(i));
        }
        
        itemBox.setValue(null);
        startBox.setValue(null);
        destinationBox.setValue(null);
    }    

    @FXML
    private void infoAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent page = FXMLLoader.load(getClass().getResource("FXMLInfoWindow.fxml"));
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println("Paketti-infon avaaminen epäonnistui");
        }
        
    }

    @FXML
    private void createPackageAction(ActionEvent event) {
        Item item;
        Package pack;
        float mass;
        String name;
        boolean breakable;
        try{
            //tää pitää sisentää
            
            
        if (itemBox.valueProperty().getValue() == null){
            
            if (nameInputField.getText().length()<2 | massInputField.getText().length()<1 |
                    widthInputField.getText().length()<1 | lengthInputField.getText().length()<1
                    | heightInputField.getText().length() <1)
                throw new InputException();
            
            name = nameInputField.getText();
            mass = Float.parseFloat(massInputField.getText());
            breakable = breakableBox.isSelected();
            
            float[] size = new float[3];
            size[0] = Float.parseFloat(widthInputField.getText());
            size[1] = Float.parseFloat(lengthInputField.getText());
            size[2] = Float.parseFloat(heightInputField.getText());
            Arrays.sort(size);

            item = new Item(size[0], size[1], size[2], mass, name, breakable);
            
        }else {
            item = itemBox.valueProperty().getValue();
        }
       
        
        
        float coordinates[] = new float[4];
        if (startBox.valueProperty().getValue() == null | destinationBox.valueProperty().getValue() == null)
            throw new EmptyComboBoxException();
        if (startBox.valueProperty().getValue() == destinationBox.valueProperty().getValue())
            throw new InvalidLocationException();
        
        coordinates[0] = startBox.valueProperty().getValue().lat;
        coordinates[1] = startBox.valueProperty().getValue().lng;
        coordinates[2] = destinationBox.valueProperty().getValue().lat;
        coordinates[3] = destinationBox.valueProperty().getValue().lng;

        
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
        
        Stage stage = (Stage) createPackageButton.getScene().getWindow();
        stage.close();
        }
        catch(EmptyComboBoxException ex){
            System.out.println("Valitse lähtöpaikka ja kohde");
        }
        catch(NumberFormatException | InputException ie){
            System.out.println("Virheellinen syöte.");
        }
        catch(InvalidLocationException ile){
            System.out.println("lähtö ja saapumispaikka on sama.");
        }
        
    }

    @FXML
    private void closeAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    //testaa että voiko pakettia tehdä

    

}
