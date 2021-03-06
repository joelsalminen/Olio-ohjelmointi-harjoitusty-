
package smartpost;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Joel Salminen 0401495
 */
public class Mainclass extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        //sets the minimum size of this window
        stage.setMinHeight(640);
        stage.setMinWidth(880);
        
        //sets title for this window
        stage.setTitle("TIMO-järjestelmä");
        
        //choosing a .css-file
        scene.getStylesheets().add(Mainclass.class.getResource("SmartPost.css").toExternalForm());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
