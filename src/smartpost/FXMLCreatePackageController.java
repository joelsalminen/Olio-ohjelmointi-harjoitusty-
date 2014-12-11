
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
    private TextField sizeInputField;
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
    
    Storage storage = Storage.getInstance();
    SmartPostList smartpostlist = SmartPostList.getInstance();
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup group = new ToggleGroup();
        firstClassBox.setToggleGroup(group);
        secondClassBox.setToggleGroup(group);
        thirdClassBox.setToggleGroup(group);
        firstClassBox.setSelected(true);
        for (int i = 0;i<smartpostlist.getSize();i++){
            startBox.getItems().add(smartpostlist.getSmartPost(i));
            destinationBox.getItems().add(smartpostlist.getSmartPost(i));
        }

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
        String name = nameInputField.getText();
        float mass = Float.parseFloat(massInputField.getText());
        boolean breakable = breakableBox.isSelected();
        
        float[] size = new float[3];
        String[] temp = sizeInputField.getText().split("\\*");
        size[0] = Float.parseFloat(temp[0]);
        size[1] = Float.parseFloat(temp[1]);
        size[2] = Float.parseFloat(temp[2]);
        Arrays.sort(size);
        
        float coordinates[] = new float[4];
        coordinates[0] = startBox.valueProperty().getValue().lat;
        coordinates[1] = startBox.valueProperty().getValue().lng;
        coordinates[2] = destinationBox.valueProperty().getValue().lat;
        coordinates[3] = destinationBox.valueProperty().getValue().lng;
        
        // miten selvitän että onko comboboxissa valittuna mitään
        
        item = new Item(size[0], size[1], size[2], mass, name, breakable);
        

        
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

    @FXML
    private void closeAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


}
