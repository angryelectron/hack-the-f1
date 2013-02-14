/**
 * Femulator - MIDI Mapper and F1 Emulator control for Traktor
 * Copyright 2013, Andrew Bythell <abythell@ieee.org>
 * http://angryelectron.com/femulator
 *
 * Femulator is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * Femulator is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Femulator.  If not, see <http://www.gnu.org/licenses/>.
 */
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

    /**
     * VENDOR_ID of the USB Hardware
     */
    private static final int VENDOR_ID = 0x17CC;
    
    /**
     * PRODUCT_ID of the USB Hardware
     */
    private static final int PRODUCT_ID = 0x1120;
    
    /**
     * ID of the Femulator Output Report.  All reports sent to the Femulator using 
     * {@link com.angryelectron.libf1.F1HID#setAll(java.nio.ByteBuffer) setAll()} 
     * must have this value set as the first byte, which must match the Femulator
     * firmware.
     */
    public static final byte OUTPUT_ID = 0x03;
    
    /**
     * Size of the Femulator Output Report.  All reports sent to the Femulator using
     * {@link com.angryelectron.libf1.F1HID#setAll(java.nio.ByteBuffer) setAll()} 
     * must have this size, which must match the Femulator firmware.
     */
    public static final int OUTPUT_SIZE = 22;
    
    /**
     * Scaling value for Analog controls.  Define here so it doesn't have to
     * be recalculated each time.
     */
    private final int analogScale = 0xFFF / 127;
       
    /*
     * Load HID native libraries
     */
    static {
        ClassPathLibraryLoader.loadNativeHIDLibrary();
    }
        
    /*
     * HID Access
     */
    private HIDManager hidManager;
    private HIDDevice hidDevice;
    
    /*
     * This buffer holds a copy of the HID output report and maintains
     * the state of the controls.
     */
    private ByteBuffer outputReport;

    /**
     * F1 Control Types
     */
     enum CT {
        BINARY,     // control is on or off, represented by a single bit
        ENCODER,    // a single byte with values 0 - 0xFF
        ANALOG,     // 8-bit A/D value.  bit[2] = {LSB, MSB}, ie. 0x0FA0 -> {0xA0, 0x0F}
    }
    
    /**
     * F1 Control Names
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
        FILTER1(6,0,CT.ANALOG),
        FILTER2(8,0,CT.ANALOG),
        FILTER3(10,0,CT.ANALOG),
        FILTER4(12,0,CT.ANALOG),
        VOLUME1(14,0,CT.ANALOG),
        VOLUME2(16,0,CT.ANALOG),
        VOLUME3(18,0,CT.ANALOG),
        VOLUME4(20,0,CT.ANALOG);        
        
        private final byte BYTE;
        private final byte BIT;
        private CT type;
        
       /**
        * Construct a new F1 control.
        * @param BYTE the byte in the report that contains this control
        * @param BIT the bit in the byte that represents the control's state
        * @param type type of control.
        */
        F1(int BYTE, int BIT, CT type) {
            this.BYTE = (byte) BYTE;
            this.BIT = (byte) BIT;
            this.type = type;
        }
        
        /**
         * Get control's index in outputReport buffer
         * @return zero-based index of the byte that contains this control.
         */
        byte pos() {
            return BYTE;
        }
        
        /**
         * Get mask.  For Binary controls only.
         * @return A bit mask for the value of this control.
         */
        byte mask() {
            return (byte)(1 << BIT);
        }
        
        /**
         * Get control type.  Used to determine how to write control value 
         * into the outputReport buffer.
         * @return CT type.
         */
        CT type() {
            return type;
        }
    }
    
    
    /**
     * Constructor.
     */
    public F1HID() {
        outputReport = ByteBuffer.allocate(OUTPUT_SIZE);
        outputReport.put((byte)0, OUTPUT_ID);
    }

    /**
     * Open the Femulator hardware.
     * @throws IOException If device not found or cannot be opened.
     */
    public void open() throws IOException {
        hidManager = HIDManager.getInstance();
        try {
            hidDevice = hidManager.openById(VENDOR_ID, PRODUCT_ID, null);            
        } catch (HIDDeviceNotFoundException ex) {
            //TODO: find out why this doesn't shut down the thread like it should.
            hidManager.release();
            throw new IOException("Can't open Femulator device.");
        } catch (NullPointerException ex) {
            hidManager.release();
            throw new IOException("Can't open Femulator device.");
        }
    }
    
    /**
     * Close the Femulator hardware.     
     */
    public void close() {
        try {
            hidDevice.close();
        } catch (Exception ex) {
            /*
             * Don't throw exceptions on close so the program can shut down
             * easily in the event of an error.
             */
            Logger.getLogger(F1HID.class.getName()).log(Level.INFO, null, ex);
        }
        hidManager.release();
    }
    
    /**
     * Set the values of all controls at once.  Useful for loading saved states.
     * @param report byte array of size OUTPUT_SIZE and byte[0] = OUTPUT_ID
     * @throws IOException If buffer format is incorrect or buffer cannot be written.
     */
    public void setAll(ByteBuffer report) {
        if (report.capacity() != OUTPUT_SIZE) {
            throw new IllegalArgumentException("Can't write report:  buffer size must be " + OUTPUT_SIZE);
        }
        if (report.get(0) != OUTPUT_ID) {
            throw new IllegalArgumentException("Can't write report:  report Id (byte zero) must be " + OUTPUT_ID);
        }
        outputReport = report;
    }
    
    /**
     * Read the values of all controls at once.  Useful for saving the current control states.
     * @return Current value of all controls in the F1 Protocol format.
     */
    public ByteBuffer getAll() {
        return outputReport;
    }
    
    
    /**
     * Set the value of an individual F1 Control.  Traktor is not updated until 
     * {@link com.angryelectron.libf1.F1HID#send() send()} is called.
     * This allows multiple controls to be updated at the same time.
     * @param control The F1 control to set.
     * @param value A MIDI velocity (0-127).  This value will be translated into
     * a value based on the F1 control type.
     */
    public void set(F1 control, byte midiValue) {
        
        int value = convertMidiValue(control, midiValue);
        
        /*
         * Call the apropriate method to update the outputReport
         */
        switch(control.type()) {
            case ANALOG:
                if (value > 0xFFF) {
                    throw new IllegalArgumentException("Value too large (12-bit resolution max).");
                }
                outputReport.putShort(control.pos(), Short.reverseBytes((short)value));
                break;
            case ENCODER:
                if (value > 0xFF) {
                    throw new IllegalArgumentException("Value too large (8-bit resolution max).");
                }
                outputReport.put(control.pos(), (byte) value);
                break;
            case BINARY:
                if ((value != 0) && (value != 1)) {
                    throw new IllegalArgumentException("Value must be 1 or 0.");
                }
                byte b = outputReport.get(control.pos());
                switch (value) {
                    case 0:
                        b &= ~control.mask();
                        break;
                    case 1:
                        b |= control.mask();
                        break;
                }
                outputReport.put(control.pos(), b);
                break;
        }
    }
    
    /**
     * Send an Output Report to the Femulator.  This sends the current state of all
     * controls to Traktor.
     * @throws IOException If report cannot be sent.
     */
    public void send() throws IOException {
        hidDevice.write(outputReport.array());
    }
    
    /**
     * Midi Velocities are 0 - 127.  Map these to appropriate F1 values.
     * @param control Different control types have different conversions.
     * @param midiValue The MIDI velocity byte (0-127).
     * @return Converted value.
     */
    private int convertMidiValue(F1 control, byte midiValue) {        
        switch (control.type()) {
            case ANALOG:
                /*
                 * Scale velocity to a 12-bit value.
                 */
                return analogScale * midiValue;
            case BINARY:
                /*
                 * Binary controls are either ON or OFF
                 */
                if (midiValue == 127) {
                    return 1;
                }
                return 0;
            case ENCODER:
                /*
                 * Midi is 0-127, Encoder values are 0-254.
                 */
                return midiValue * 2;
        }
        return 0;
    }
       
}
