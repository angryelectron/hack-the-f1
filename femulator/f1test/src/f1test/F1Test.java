/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package f1test;

import com.angryelectron.libf1.F1Api;
import com.angryelectron.libf1.F1HID;
import com.angryelectron.libf1.F1HID.F1;
import com.angryelectron.libf1.F1Mapper;
import com.angryelectron.libf1.F1Midi;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.sound.midi.MidiUnavailableException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author abythell
 */
public class F1Test {
    
    public static void main(String[] args) throws IOException, FileNotFoundException, MidiUnavailableException {  
        testPlay();
        //testHID();
    }
    
    private static void testPlay() throws FileNotFoundException, IOException, MidiUnavailableException {
        F1Mapper mapper = new F1Mapper();
        //File mapFile = new File("test.map");
        //mapper.load(mapFile);

        F1Api f1 = new F1Api();
        f1.setMapper(mapper);
        
        f1.play();
    }
    
    private static void testLearn() throws MidiUnavailableException {
        
        List<String> devices = F1Midi.list();
        
        F1Mapper mapper = new F1Mapper();
        mapper.setDevice("Launchpad");
        
        F1Api f1 = new F1Api();
        f1.setMapper(mapper);
        f1.learn();
    }
    
    private static void testHID() {
        /*
         * Open the hardware
         */
        F1HID f1 = new F1HID();
        try {
            f1.open();
        } catch (IOException ex) {
            Logger.getLogger(F1Test.class.getName()).log(Level.ERROR, ex);
            f1.close();
            System.exit(-1);
        }
        
        /*
         * Send some commands
         */
        F1 controls[] = {
           F1.PAD1, F1.STOP1, F1.PAD1
        };
        try {
            for (F1 c : controls) {
                f1.set(c, 1);
                f1.send();
                f1.set(c,0);
                f1.send();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    java.util.logging.Logger.getLogger(F1Test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
            
        } catch (IOException | IllegalArgumentException ex) {
            Logger.getLogger(F1Test.class.getName()).log(Level.ERROR, ex);
        }
        
        /*
         * Shut down
         */
        f1.close();
        System.exit(1);
    }
    
}
