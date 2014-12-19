package smartpost;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/*
 * @author Joel Salminen 0401495
 */

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private WebView webWindow;
    @FXML
    private ComboBox<SmartPost> automatonComboBox;
    @FXML
    private Button addMarkerButton;
    @FXML
    private Button saveStorageButton;
    @FXML
    private Button loadStorageButton;
    @FXML
    private ComboBox<Package> packageComboBox;
    @FXML
    private Button sendButton;
    @FXML
    private Button deleteButton;
    @FXML
    private ComboBox<Item> itemComboBox;
    @FXML
    private TextField nameInputField;
    @FXML
    private TextField lengthInputField;
    @FXML
    private TextField widthInputField;
    @FXML
    private TextField heightInputField;
    @FXML
    private TextField massInputField;
    @FXML
    private CheckBox breakableBox;
    @FXML
    private RadioButton firstClassBox;
    @FXML
    private RadioButton secondClassBox;
    @FXML
    private RadioButton thirdClassBox;
    @FXML
    private Button infoButton;
    @FXML
    private ComboBox<SmartPost> startComboBox;
    @FXML
    private ComboBox<SmartPost> destinationComboBox;
    @FXML
    private Button cancelButton;
    @FXML
    private Button createPackageButton1;
    @FXML
    private TextArea historyTextArea;
    @FXML
    private Label amountOfPackages;
    @FXML
    private Button resetTextAreaButton;
    @FXML
    private Label titleText;
    @FXML
    private Label historyTitle;
    @FXML
    private Label createPackageTitle;

    private SmartPostList smartpostlist;
    private Storage storage = Storage.getInstance();
    private ReadAndWrite raw = new ReadAndWrite();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        historyTextArea.setText(raw.loadHistory());
        
        //creates a Radio button group, sets firstClassBox on as default:
        ToggleGroup group = new ToggleGroup();
        firstClassBox.setToggleGroup(group);
        secondClassBox.setToggleGroup(group);
        thirdClassBox.setToggleGroup(group);
        firstClassBox.setSelected(true);
        
        //adds items to itemComboBox
        itemComboBox.getItems().add(new DVDBox());
        itemComboBox.getItems().add(new Stones());
        itemComboBox.getItems().add(new Cake());
        itemComboBox.getItems().add(new GlassTableWare());
        
        //Titles need IDs in order to customise them in the css file
        titleText.setId("title-text"); 
        historyTitle.setId("history-title");
        createPackageTitle.setId("package-title");
        
        //textArea is now uneditable because it's only meant for reading
        historyTextArea.setEditable(false);
        historyTextArea.setWrapText(true); //hides horizontal scrollbars

        
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
            DataBuilder databuilder = new DataBuilder();
            databuilder.parse(content);
            
            //Parsed SmartPost objects from SmartPostList are be put in the combo box:
            for (int i = 0; i<smartpostlist.getSize();i++){
                automatonComboBox.getItems().add(smartpostlist.getSmartPost(i));
            }
            
            /*  sets the current value of combo boxes to null.
                This is so that the program would not throw an error if a user tries
                to access an object from a combo box before an object is selected*/
            automatonComboBox.setValue(null);
            packageComboBox.setValue(null);
            itemComboBox.setValue(null);
            startComboBox.setValue(null);
            destinationComboBox.setValue(null);
            
        } catch (MalformedURLException ex) {
            System.err.println("Virheellinen URL");
        } catch (IOException ex) {
            System.err.println("BufferedReaderin avaaminen epäonnistui");
        }
    }
    
    
    @FXML
    private void addMarkerAction(ActionEvent event) {
        //adds markers on the map using coordinates from SmartPost objects
        
        //value is null if no objects have been selected from the combo box
        if (automatonComboBox.valueProperty().getValue() != null){ 
            SmartPost smartpost = automatonComboBox.valueProperty().getValue();
            
            //adding a marker:
            webWindow.getEngine().executeScript("document.goToLocation('"+ smartpost.getAddress() +", "
                    + smartpost.getCode()+ " " + smartpost.getCity() + "', '"+ smartpost.getName() +" "
                    +smartpost.getAvailability() +  "', 'pink')");
            //drawn SmartPost object is added on the start and destination
            //ComboBoxes if it has not already been added before
            if (startComboBox.getItems().contains(smartpost) == false){
                startComboBox.getItems().add(smartpost);
                destinationComboBox.getItems().add(smartpost);
            }
            smartpostlist.removeSmartPost(automatonComboBox.valueProperty().getValue());
            automatonComboBox.getItems().remove(automatonComboBox.valueProperty().getValue());
            automatonComboBox.setValue(null);
        }
    }
    
    
    @FXML
    private void sendAction(ActionEvent event) {
        //sends a package, draws a route on the map and updates the delivery history
        
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Calendar cal = Calendar.getInstance();
        
        if (packageComboBox.valueProperty().getValue() != null){ //value is null if no objects have been selected from the combo box
            double distance; //distance between start and destination 
            
            //coordinates are added into an arraylist because the script command wants
            //the coordinates in that form
            ArrayList <Double> al = new ArrayList();
            al.add(packageComboBox.valueProperty().getValue().getStartSmartPost().getLat());
            al.add(packageComboBox.valueProperty().getValue().getStartSmartPost().getLng());
            al.add(packageComboBox.valueProperty().getValue().getDestinationSmartPost().getLat());
            al.add(packageComboBox.valueProperty().getValue().getDestinationSmartPost().getLng());
            
            //draws a route on the map: 
                distance = (double)webWindow.getEngine().executeScript("document.createPath("+al+", 'red', " 
                        + packageComboBox.valueProperty().getValue().getPackageClass() +")");
                
            try {
                
                //checks if the distance between start and finish is longer than 150km
                //when delivering first class packages
                if (packageComboBox.valueProperty().getValue().getPackageClass() == 1 & distance >150){
                    throw new DistanceException();
                }
            
                //tests if items were broken during delivery:
                packageComboBox.valueProperty().getValue().breakTest();
                if(packageComboBox.valueProperty().getValue().getItem().broken == true){
                    throw new BrokenItemException();
                }
                
                //updates historyTextArea
                historyTextArea.setText(historyTextArea.getText()+date.format(cal.getTime())+"   Lähetettiin ");
                historyTextArea.setText(historyTextArea.getText()+packageComboBox.valueProperty().getValue().getItem().getName());
                historyTextArea.setText(historyTextArea.getText()+ " " +packageComboBox.valueProperty().getValue().getPackageClass());
                historyTextArea.setText(historyTextArea.getText()+ ".luokassa, " + packageComboBox.valueProperty().getValue().startSmartPost.getName());
                historyTextArea.setText(historyTextArea.getText()+" -> " + packageComboBox.valueProperty().getValue().getDestinationSmartPost().getName());
                historyTextArea.setText(historyTextArea.getText()+", etäisyys " + distance +"km. Paketti saapui perille ehjänä.\n");
            
            }
            //exceptions, in case something went wrong during delivery
            catch(DistanceException de){
                openMessageWindow("Paketti jäi matkan varrelle!", "Liian pitkä välimatka 1.luokan paketeille");
                webWindow.getEngine().executeScript("document.deletePaths()");
                
                historyTextArea.setText(historyTextArea.getText()+date.format(cal.getTime())+"   Lähetettiin ");
                historyTextArea.setText(historyTextArea.getText()+packageComboBox.valueProperty().getValue().getItem().getName());
                historyTextArea.setText(historyTextArea.getText()+ " " +packageComboBox.valueProperty().getValue().getPackageClass());
                historyTextArea.setText(historyTextArea.getText()+ ".luokassa, " + packageComboBox.valueProperty().getValue().getStartSmartPost().getName());
                historyTextArea.setText(historyTextArea.getText()+" -> " + packageComboBox.valueProperty().getValue().getDestinationSmartPost().getName());
                historyTextArea.setText(historyTextArea.getText()+" Paketti ei saapunut perille.\n");
                
            }
            catch(BrokenItemException bie){
                openMessageWindow("Tavara hajosi kuljetuksen", "aikana!");
                
                webWindow.getEngine().executeScript("document.deletePaths()");
                historyTextArea.setText(historyTextArea.getText()+date.format(cal.getTime())+"   Lähetettiin ");
                historyTextArea.setText(historyTextArea.getText()+packageComboBox.valueProperty().getValue().getItem().getName());
                historyTextArea.setText(historyTextArea.getText()+ " " +packageComboBox.valueProperty().getValue().getPackageClass());
                historyTextArea.setText(historyTextArea.getText()+ ".luokassa, " + packageComboBox.valueProperty().getValue().getStartSmartPost().getName());
                historyTextArea.setText(historyTextArea.getText()+" -> " + packageComboBox.valueProperty().getValue().getDestinationSmartPost().getName());
                historyTextArea.setText(historyTextArea.getText()+", etäisyys " + distance +"km. Tavara hajosi matkan aikana.\n");
                
            }
            //amountOfPackages counter and historyLog.txt are updated
            amountOfPackages.setText(Integer.toString(Integer.parseInt(amountOfPackages.getText())-1));
            raw.saveHistory(historyTextArea.getText());
            
            //removes delivered package from Storage and then refreshes the packageBox
            storage.removePackage(packageComboBox.valueProperty().getValue());
            packageComboBox.getItems().clear();
            packageComboBox.setValue(null); //value is set to null to avoid NullPointerException
            for (int i = 0; i< storage.getSize();i++){
                packageComboBox.getItems().add(storage.getPackage(i));
            }
        }
    }
    
    
    @FXML
    private void deleteAction(ActionEvent event) {
        //deletes all routes on the map
        
        webWindow.getEngine().executeScript("document.deletePaths()");
    }
    
    
    @FXML
    private void infoAction(ActionEvent event) {
        //opens an info box which contains information about mailing classes
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInfoWindow.fxml"));
            
            Scene scene = new Scene((Parent)loader.load());
            stage.setScene(scene);
            stage.setTitle("TIMO-järjestelmä");
            stage.setResizable(false);
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
        Package pack = null;
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
                itemComboBox.getItems().add(item);
            }else {
                //this value is used to create the item if an item is selected from itemBox
                item = itemComboBox.valueProperty().getValue();
            }
            
            //throws EmptyComboBoxException if no SmartPost objects have been selected from combo boxes:
            if (startComboBox.valueProperty().getValue() == null | destinationComboBox.valueProperty().getValue() == null)
                throw new EmptyComboBoxException();
            
            // throws InvalidLocationException if start and destination are in the same location:
            if (startComboBox.valueProperty().getValue() == destinationComboBox.valueProperty().getValue())
                throw new InvalidLocationException();
            
            SmartPost startSmartPost = startComboBox.valueProperty().getValue();
            SmartPost destinationSmartPost = destinationComboBox.valueProperty().getValue();
            
            //choosing what kind of mailing class to use
            if (firstClassBox.isSelected()){
                pack = new FirstClassPackage(startSmartPost,destinationSmartPost, item);
            }
            else if (secondClassBox.isSelected()){
                pack = new SecondClassPackage(startSmartPost,destinationSmartPost, item);
            }
            else if (thirdClassBox.isSelected()){
                pack = new ThirdClassPackage(startSmartPost,destinationSmartPost, item);
            }
            
            //new package is added to Storage and packageComboBox only if no error are thrown
            storage.addPackage(pack);
            packageComboBox.getItems().clear();
            for (int i=0;i<storage.getSize();i++)
                packageComboBox.getItems().add(storage.getPackage(i));
            resetPackageTab();
            
            //updates the amoutOfPackages counter
            amountOfPackages.setText(Integer.toString(storage.getSize()));
            openMessageWindow("Paketin luominen onnistui.", "");
        }
        
        //In case something went wrong an error message window will pop up:
        catch(EmptyComboBoxException ex){
            openMessageWindow("Valitse lähtö- ja saapumisautomaatti.", "");
        }
        catch(NumberFormatException | InputException ie){
            openMessageWindow("Tarkista syötteet tai valitse" ,"valmis esine pudotusvalikosta.");
        }
        catch(InvalidLocationException ile){
            openMessageWindow("Lähtö- ja saapumisautomaatit", "eivät saa olla samoja.");
        }
        catch(PackagingException pe){
            openMessageWindow("Tavara ei mahdu pakettiin.", "Valitse toinen lähetysluokka");
            }
    }

    
    @FXML
    private void cancelAction(ActionEvent event) {
        //resets the package creation tab
        resetPackageTab();
    }


    private void resetPackageTab(){
        //is used when either a new package is created or when
        //cancelButton is pressed
        itemComboBox.valueProperty().setValue(null);
        startComboBox.valueProperty().setValue(null);
        destinationComboBox.valueProperty().setValue(null);
        firstClassBox.setSelected(true);
        breakableBox.setSelected(false);
        nameInputField.setText("");
        lengthInputField.setText("");
        widthInputField.setText("");
        heightInputField.setText("");
        massInputField.setText("");
    }
    
    
    @FXML
    private void resetTextAreaAction(ActionEvent event) {
        
        historyTextArea.setText("");
        raw.saveHistory("");
    }
    
    
    private void openMessageWindow(String message, String message2){
        //Opens a message window which can be used to tell user about things like incorrect usage
        //of this program. The message shown can be changed depending on the situation.
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMessageWindow.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene((Pane)loader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("TIMO-järjestelmä");
            
            //Passes messages to FXMLMessageWindowController
            //http://stackoverflow.com/questions/14187963/passing-parameters-javafx-fxml
            FXMLMessageWindowController controller = loader.<FXMLMessageWindowController>getController();
            controller.setMessage(message, message2);
            
            scene.getStylesheets().add(FXMLMessageWindowController.class.getResource("SmartPost.css").toExternalForm());
            stage.show();
        } catch (IOException ex) {
            System.out.println("Viesti-ikkunan avaaminen epäonnistui.");
        }
    }

    @FXML
    private void loadStorageAction(ActionEvent event) {
        //loads a Storage object from a file
        
        try{
            SmartPost startSmartPost;
            SmartPost destinationSmartPost;
            storage = raw.loadStorage();
            
            //current packageComboBox is cleared
            //this is because if the user decides to save storage and immidiately after
            //load it, the storage would have 2 pieces of every package.
            packageComboBox.getItems().clear();
            
            //new packages are put into the packageComboBox and SmartPost objects
            //are added to the map:
            for (int i = 0; i<storage.getSize();i++){
                packageComboBox.getItems().add(storage.getPackage(i));
                
                startSmartPost = storage.getPackage(i).getStartSmartPost();
                destinationSmartPost = storage.getPackage(i).getDestinationSmartPost();
                
                webWindow.getEngine().executeScript("document.goToLocation('"
                        + startSmartPost.getAddress() +", "+ startSmartPost.getCode()+
                        " " + startSmartPost.getCity() + "', '"+ startSmartPost.getName() +
                        " "+startSmartPost.getAvailability() +  "', 'pink')");
            
                webWindow.getEngine().executeScript("document.goToLocation('"
                        + destinationSmartPost.getAddress()+", "+ destinationSmartPost.getCode()
                        +" " + destinationSmartPost.getCity() + "', '"+ destinationSmartPost.getName() 
                        +" "+destinationSmartPost.getAvailability()+  "', 'pink')");
                
        }
            raw.clearStorageFile();
            openMessageWindow("Varasto ladattu.","");
            
            //updates the combo boxes and SmartPostList:
            for (int i=0;i<storage.getSize();i++){
                if(startComboBox.getItems().contains(storage.getPackage(i).getStartSmartPost()) == false){
                    startComboBox.getItems().add(storage.getPackage(i).getStartSmartPost());
                    destinationComboBox.getItems().add(storage.getPackage(i).getStartSmartPost());
                }
                if(startComboBox.getItems().contains(storage.getPackage(i).getDestinationSmartPost()) == false){
                    startComboBox.getItems().add(storage.getPackage(i).getDestinationSmartPost());
                    destinationComboBox.getItems().add(storage.getPackage(i).getDestinationSmartPost());
                }
            }
            
    }   catch (IOException | ClassNotFoundException ex) {
            openMessageWindow("Varaston lataaminen epäonnistui!","");
        }
    }
    @FXML
    private void saveStorageAction(ActionEvent event) {
        //writes the current storage into the file.
        //previous file will be lost
        try {
            raw.saveStorage();
            openMessageWindow("Varasto tallennettu.","");
        } catch (IOException ex) {
            openMessageWindow("Varaston tallentaminen ei onnistunut!","");
        }
        
    }

}
