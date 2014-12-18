
package smartpost;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;


/*
 * @author Joel Salminen 0401495
 */
public class ReadAndWrite {
    //An object for reading and writing files
    
    Storage storage = Storage.getInstance();

    public void saveHistory(String history){
        String fileName = "historyLog.txt";
        BufferedWriter writeHistory;
        try {
            writeHistory = new BufferedWriter(new FileWriter (fileName));
            writeHistory.append(history);
            writeHistory.close();
        } catch (IOException ex) {
            System.err.println("asasdd");
        }
    }

    public String loadHistory(){
        String fileName = "historyLog.txt";
        String line;
        String historyLog = "";
        BufferedReader readHistory;
        try {
            readHistory = new BufferedReader(new FileReader(fileName));
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

    

    //These methods are for storing and loading Storage-objects from files

    public void saveStorage() throws FileNotFoundException, IOException{
        //saves the current storage
        
        String fileNamePackage = "storage.ser";
        ObjectOutputStream out;
        
            out = new ObjectOutputStream(new FileOutputStream(fileNamePackage));
            out.writeObject(storage);
            out.flush();
            out.close();
    }
    
    public Storage loadStorage() throws IOException, ClassNotFoundException{
        //Loads the storage from a file
        
        String fileNamePackage = "storage.ser";
        ObjectInputStream in;
        in = new ObjectInputStream(new FileInputStream(fileNamePackage));
        storage = (Storage)in.readObject();
        in.close();
        return storage;
    }

    public void clearStorageFile() throws IOException{
        //clears the storage after it has been loaded
        
        String fileNamePackage = "storage.ser";
        ObjectOutputStream out;
            BufferedWriter writeHistory = new BufferedWriter(new FileWriter (fileNamePackage));
            writeHistory.write("");
            writeHistory.close();
    }
    
}