
package smartpost;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/*
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
*/

/*
 * @author Joel Salminen 0401495
 */
public class ReadAndWrite {
    //An object for reading and writing files
    
    private final String fileNameHistory = "historyLog.txt";
    /*
    private String fileNamePackage = "storage.ser";
    private Storage storage = Storage.getInstance();
    */

    public void saveHistory(String history){
        BufferedWriter writeHistory;
        try {
            writeHistory = new BufferedWriter(new FileWriter (fileNameHistory));
            writeHistory.append(history);
            writeHistory.close();
        } catch (IOException ex) {
            System.err.println("asasdd");
        }
    }

    public String loadHistory(){
        String line;
        String historyLog = "";
        BufferedReader readHistory;
        try {
            readHistory = new BufferedReader(new FileReader(fileNameHistory));
                    while((line = readHistory.readLine())!=null){
           historyLog = historyLog + line +"\n";
        }
        readHistory.close();
        return historyLog;
        } catch (FileNotFoundException ex) {
            saveHistory("");
        } catch (IOException ex) {
            System.err.println("IOException");
        }
        return historyLog;
    }

    
/*
    //These methods are for storing and loading Storage-objects from files
    //
    public Storage loadStorage(){
        ObjectInputStream in;
        try {
            in = new ObjectInputStream(new FileInputStream(fileNamePackage));
            storage = (Storage)in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("Luokkaa ei löytynyt.");
        }
        
        in.close();
        return storage;
    }
    
    public void saveStorage(){
        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(new FileOutputStream(fileNamePackage));
            out.writeObject(storage);
            out.close();
        } catch (IOException ex) {
            System.out.err("IOException):
        }

    }
*/    
}