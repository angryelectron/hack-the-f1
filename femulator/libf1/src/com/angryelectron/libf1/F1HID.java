/**
 * Femulator
 * Copyright 2013 Andrew Bythell <abythell@ieee.org>
 * http://www.angryelectron.com/femulator
 */ 

package com.angryelectron.libf1;

import com.codeminders.hidapi.ClassPathLibraryLoader;
import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDDeviceNotFoundException;
import com.codeminders.hidapi.HIDManager;
import java.io.IOException;
import java.nio.ByteBuffer;

public class F1HID {

     static final int VENDOR_ID = 0x17CC;
     static final int PRODUCT_ID = 0x1120; 
     static final String SERIAL_NUMBER = "FA52C4D0";
    
    static {
        //System.loadLibrary("hidapi-jni");
        ClassPathLibraryLoader.loadNativeHIDLibrary();
    }
    
    private HIDManager hidManager;
    private HIDDevice hidDevice;
    private F1OutputReport output;
    
    public F1HID() {
        output = new F1OutputReport();
    } 

    public void open() throws IOException {
        hidManager = HIDManager.getInstance();
        try {
            hidDevice = hidManager.openById(VENDOR_ID, PRODUCT_ID, null);
            hidDevice.getManufacturerString();
        } catch (HIDDeviceNotFoundException ex) {
            hidManager.release();
            throw new IOException("Can't open Femulator device.");
        }
    }
    
    public void close() throws IOException {
        hidDevice.close();
        hidManager.release();
    }
    
    public void setPad(int pad, boolean value) throws IOException {
        short pads = output.getPads();
        if (value) {
            pads |= (1 << pad);
        } else {
            pads &= ~(1 << pad);
        }
        output.putPads(pads);
        
        ByteBuffer test = ByteBuffer.allocate(22);
        test.put(0, (byte)3);
        test.put(1, (byte)0x80);
        hidDevice.write(test.array());
    }
    
}
