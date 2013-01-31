package f1test;

import com.codeminders.hidapi.ClassPathLibraryLoader;
import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDDeviceInfo;
import com.codeminders.hidapi.HIDDeviceNotFoundException;
import com.codeminders.hidapi.HIDManager;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class demonstrates enumeration, reading and getting notifications when a
 * HID device is connected/disconnected.
 */
public class demo {

    private static final long READ_UPDATE_DELAY_MS = 50L;

    static {    
        ClassPathLibraryLoader.loadNativeHIDLibrary();
    }
    
    /*
     * Femulator Specific    
     */
    static final int VENDOR_ID = 0x17cc; //0x03EB;
    static final int PRODUCT_ID = 0x1120; //0x204F;
    static final String SERIAL = "FA52C4D0";    
    private static final int BUFSIZE = 64;

    /**
     * @param args input strings value.
     */
    public static void main(String[] args) throws IOException {        
        /*
         *  Enable this to test device enumeration
         */
        //listDevices();
        
        /*
         * Enable this to test writing
         */
        writeDevice();
        
        /*
         * Enable this to test reading.  Also useful for displaying F1 output 
         * reports
         */ 
        //readDevice();
    }

    /**
     * Static function to read an input report to a HID device.
     */
    private static void readDevice() {
        HIDDevice dev;
        try {
            HIDManager hid_mgr = HIDManager.getInstance();
            try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        //Ignore
                        e.printStackTrace();
                    }
            dev = hid_mgr.openById(VENDOR_ID, PRODUCT_ID, null);
            
            System.err.print("Manufacturer: " + dev.getManufacturerString() + "\n");
            System.err.print("Product: " + dev.getProductString() + "\n");
            System.err.print("Serial Number: " + dev.getSerialNumberString() + "\n");
            try {
                byte[] buf = new byte[BUFSIZE];
                dev.enableBlocking();
                while (true) {
                    int n = dev.read(buf);
                    for (int i = 0; i < n; i++) {
                        int v = buf[i];
                        if (v < 0) {
                            v = v + 256;
                        }
                        String hs = Integer.toHexString(v);
                        if (v < 16) {
                            System.err.print("0");
                        }
                        System.err.print(hs + " ");
                    }
                    System.err.println("");

                    try {
                        Thread.sleep(READ_UPDATE_DELAY_MS);
                    } catch (InterruptedException e) {
                        //Ignore
                        e.printStackTrace();
                    }
                }
            } finally {
                dev.close();
                hid_mgr.release();
                System.gc();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Static function to find the list of all the HID devices attached to the
     * system.
     */
    private static void listDevices() {        
        try {
            HIDManager manager = HIDManager.getInstance();
            HIDDeviceInfo[] devs = manager.listDevices();
            System.err.println("Devices:\n\n");
            for (int i = 0; i < devs.length; i++) {
                System.err.println("" + i + ".\t" + devs[i]);
                System.err.println("---------------------------------------------\n");
            }            
            System.gc();        
        } catch (HIDDeviceNotFoundException ex) {
            System.out.println("No HID devices found.");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void writeDevice() throws IOException {
        HIDManager manager = HIDManager.getInstance();
        HIDDevice device = null;
        try {
            device = manager.openById(VENDOR_ID, PRODUCT_ID, null);            
        } catch (HIDDeviceNotFoundException | NullPointerException ex) {
            System.out.println("HID device with VID=" +VENDOR_ID + " and PID="+ PRODUCT_ID + " not found.");
            System.exit(-1);
        }
        if (device == null) {
                System.out.println("Can't connect to device. Check device and/or VID and PID");
                System.exit(-1);
        }
        
        
        //allocate a buffer the same size as the Femulator output report
        ByteBuffer buffer = ByteBuffer.allocate(22);
        
        //the Femulator output report ID is 0x03
        buffer.put(0, (byte)0x03);
        
        /*
         * Fire clips 1-4
         */
        System.out.println("Starting clips 1-4");
        buffer.put(1, (byte)0xF0);                                  
        device.write(buffer.array());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            //ignore
        }
        System.out.println("Starting clips 13-16 (stop, if empty)");
        buffer.put(1, (byte)0x0F);
        device.write(buffer.array());
                
        device.close();
        manager.release();                
    }
}
