/**
 * Femulator
 * Copyright 2013 Andrew Bythell <abythell@ieee.org>
 * http://www.angryelectron.com/femulator
 */ 

package com.angryelectron.libf1;

import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDManager;
import java.io.IOException;

public class F1HID {

    private static final int VENDOR_ID = 0x17CC;
    private static final int PRODUCT_ID = 0x1120; 
    private static final String SERIAL_NUMBER = "FA52C4D0";
    
    private HIDDevice hidDevice;
    
    public F1HID() throws IOException {
        HIDManager hidManager = HIDManager.getInstance();
        hidDevice = hidManager.openById(VENDOR_ID, VENDOR_ID, SERIAL_NUMBER);
        if (hidDevice == null) {
            throw new IOException("Can't open Femulator device.");
        }
    }        
    
}
