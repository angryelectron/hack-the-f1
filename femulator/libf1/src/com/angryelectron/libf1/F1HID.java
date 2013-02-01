/**
 * Femulator API
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
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * An API for controlling Traktor 2.5 Remix Decks using 
 * Femulator hardware.
 * 
 */
public class F1HID {

    /*
     * Femulator hardware settings
     */
    static final int VENDOR_ID = 0x17CC;
    static final int PRODUCT_ID = 0x1120;
    static final byte OUTPUT_ID = 0x03;      //Report ID
    static final int OUTPUT_SIZE = 22;      //Size of Report in bytes
       
    /*
     * Load HID native libraries
     */
    static {
        ClassPathLibraryLoader.loadNativeHIDLibrary();
    }
        
    private HIDManager hidManager;
    private HIDDevice hidDevice;
    private ByteBuffer outputReport;

    /**
     * F1 Control Types
     */
    public enum CT {
        BINARY,     // control is on or off, represented by a single bit
        ENCODER,    // a single byte with values 0 - 0xFF
        ANALOG,     // 8-bit A/D value.  bit[2] = {LSB, MSB}, ie. 0x0FA0 -> {0xA0, 0x0F}
    }
    
    /**
     * Enumerated list of Controls each assigned an index in the outputReport
     * and a bit position for binary controls.
     */
    public enum F1 {        
        PAD1(1,7,CT.BINARY),
        PAD2(1,6,CT.BINARY),
        PAD3(1,5,CT.BINARY),
        PAD4(1,4,CT.BINARY),        
        PAD5(1,3,CT.BINARY),
        PAD6(1,2,CT.BINARY),
        PAD7(1,1,CT.BINARY),
        PAD8(1,0,CT.BINARY),                
        PAD9(2,7,CT.BINARY),
        PAD10(2,6,CT.BINARY),
        PAD11(2,5,CT.BINARY),
        PAD12(2,4,CT.BINARY),
        PAD13(2,3,CT.BINARY),
        PAD14(2,2,CT.BINARY),
        PAD15(2,1,CT.BINARY),
        PAD16(2,0,CT.BINARY),
        SHIFT(3,7,CT.BINARY),
        REVERSE(3,6,CT.BINARY),
        TYPE(3,5,CT.BINARY),
        SIZE(3,4,CT.BINARY),
        BROWSE(3,3,CT.BINARY),
        //not used (3,2),
        //not used (3,1),
        //not used (3,0),        
        STOP1(4,7,CT.BINARY),
        STOP2(4,6,CT.BINARY),
        STOP3(4,5,CT.BINARY),
        STOP4(4,4,CT.BINARY),        
        SYNC(4,3,CT.BINARY),
        QUANT(4,2,CT.BINARY),
        CAPTURE(4,1,CT.BINARY),
        //not used (4,0),
        KNOB(5,0,CT.ENCODER),
        
        /*
         * TODO: verify the mapping of the analog inputs.  Don't know bits/bytes
         * are the knobs and which are the sliders.
         */
        FILTER1(6,0,CT.ANALOG),
        FILTER2(8,0,CT.ANALOG),
        FILTER3(10,0,CT.ANALOG),
        FILTER4(12,0,CT.ANALOG),
        SLIDER1(14,0,CT.ANALOG),
        SLIDER2(16,0,CT.ANALOG),
        SLIDER3(18,0,CT.ANALOG),
        SLIDER4(20,0,CT.ANALOG);        
        
        private final byte BYTE;
        private final byte BIT;
        private CT type;
        
        F1(int BYTE, int BIT, CT type) {
            this.BYTE = (byte) BYTE;
            this.BIT = (byte) BIT;
            this.type = type;
        }
                
        byte pos() {
            return BYTE;
        }
        
        byte mask() {
            return (byte)(1 << BIT);
        }
        
        CT type() {
            return type;
        }
    }
    
    
    /**
     * Constructor
     */
    public F1HID() {
        outputReport = ByteBuffer.allocate(OUTPUT_SIZE);
        outputReport.put((byte)0, OUTPUT_ID);
    }

    /**
     * Open a USB HID connection with the Femulator hardware.
     * @throws IOException 
     */
    public void open() throws IOException {
        hidManager = HIDManager.getInstance();
        try {
            hidDevice = hidManager.openById(VENDOR_ID, PRODUCT_ID, null);            
        } catch (HIDDeviceNotFoundException | NullPointerException ex) {
            hidManager.release();
            throw new IOException("Can't open Femulator device.");
        }
    }
    
    /**
     * Close the USB HID connection with the Femulator hardware.     
     */
    public void close() {
        try {
            hidDevice.close();
            hidManager.release();
        } catch (IOException ex) {
            Logger.getLogger(F1HID.class.getName()).log(Level.INFO, null, ex);
        }
    }
    
    /**
     * Send a command to Traktor
     * @param c The F1 control to set.
     * @param state The value of the control.
     * @throws IOException 
     */
    public void set(F1 c, int state) throws IOException {
        switch(c.type()) {
            case ANALOG:
                setAnalogControl(c, state);
                break;
            case ENCODER:
                outputReport.put(c.pos(), (byte)state);
                break;
            case BINARY:
                setBinaryControl(c, state);
                break;                
        }
        hidDevice.write(outputReport.array());
    }
        
    private void setBinaryControl(F1 c, int state) {
        byte value = outputReport.get(c.pos());
        switch (state) {
            case 0:
                value &= ~c.mask();
                break;
            case 1:
                value |= c.mask();            
                break;
        }
        outputReport.put(c.pos(), value);          
    }
    
    private void setAnalogControl(F1 c, int state) {
        //TODO: split the int into two separate bytes
        //and correct endianess (is that a word?).
    }
       
}
