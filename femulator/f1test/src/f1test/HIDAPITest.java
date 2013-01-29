package f1test;

import com.angryelectron.libf1.F1HID;
import com.codeminders.hidapi.ClassPathLibraryLoader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class demonstrates enumeration, reading and getting notifications when a
 * HID device is connected/disconnected.
 */
public class HIDAPITest {

    static {
        ClassPathLibraryLoader.loadNativeHIDLibrary();
    }
    
    /**
     * @param args input strings value.
     */
    public static void main(String[] args) throws IOException {
        try {
            test();
        } catch (Exception ex) {
            Logger.getLogger(HIDAPITest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.exit(0);
        }
    }
    
    private static void test() throws IOException {
        F1HID f1 = new F1HID();
        f1.open();
        f1.setPad(1, true);
        f1.close();
    }
    
    

    
}
