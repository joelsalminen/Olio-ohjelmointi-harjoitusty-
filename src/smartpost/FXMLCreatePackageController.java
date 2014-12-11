
package smartpost;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private Button cancelButton;
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
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup group = new ToggleGroup();
        firstClassBox.setToggleGroup(group);
        secondClassBox.setToggleGroup(group);
        thirdClassBox.setToggleGroup(group);
        firstClassBox.setSelected(true);
        
        
            
    }    

    @FXML
    private void infoAction(ActionEvent event) {
    }

    @FXML
    private void createPackageAction(ActionEvent event) {
        Item item;
        Package pack;
        String name = nameInputField.getText();
        double mass = Double.parseDouble(massInputField.getText());
        boolean breakable = breakableBox.isSelected();
        
        double[] size = new double[3];
        String[] temp = sizeInputField.getText().split("\\*");
        size[0] = Double.parseDouble(temp[0]);
        size[1] = Double.parseDouble(temp[1]);
        size[2] = Double.parseDouble(temp[2]);
        Arrays.sort(size);
        
        String coordinates[] = new String[4];
        coordinates[0] = startBox.valueProperty().getValue().lat;
        coordinates[1] = startBox.valueProperty().getValue().lng;
        coordinates[2] = destinationBox.valueProperty().getValue().lat;
        coordinates[3] = destinationBox.valueProperty().getValue().lng;
        // miten selvitän että onko comboboxissa valittuna mitään
        item = new Item(size[0], size[1], size[2], mass, name, breakable);
        
        
        if (firstClassBox.isSelected())
            pack = new FirstClassPackage(coordinates[0], coordinates[1], coordinates[2], coordinates[3], item);
        else if (secondClassBox.isSelected())
            pack = new SecondClassPackage(coordinates[0], coordinates[1], coordinates[2], coordinates[3], item);
        else if (thirdClassBox.isSelected())
            pack = new ThirdClassPackage(coordinates[0], coordinates[1], coordinates[2], coordinates[3], item);
    }

    @FXML
    private void cancelAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


}
