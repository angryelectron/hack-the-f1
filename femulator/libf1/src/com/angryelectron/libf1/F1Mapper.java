/**
 * Femulator MIDI Mapper
 * Copyright 2013 Andrew Bythell <abythell@ieee.org>
 * http://www.angryelectron.com/femulator
 */ 
package com.angryelectron.libf1;

import com.angryelectron.libf1.F1HID.F1;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import javax.sound.midi.MidiMessage;

/**
 * Make, load, save and query mappings between MIDI and F1 controls.
 * @author abythell
 */
public class F1Mapper {

    /**
     * HashMap to hold the MIDI/F1 mappings.
     */
    private HashMap<byte[], F1> map = new HashMap<>();
    
    /**
     * The MIDI device that uses this mapping.  
     */
    private String midiDevice;
    
    /**
     * Scaling value for Analog controls.  Define here so it doesn't have to
     * be recalculated each time.
     */
    private final int analogScale = 0xFFF / 127;
    
    /**
     * Create a new mapping
     * @param mm A MIDI message.  Command, channel, and note will be used.  Velocity is ignored.
     * @param control The F1 control to map to this MIDI message.
     */
    public void setControl(MidiMessage mm, F1 control) {
        /*
         *  Store the MIDI status byte (which holds the command and channel)
         *  and the note byte, but not the value byte, which is variable
         */
        byte message[] = mm.getMessage();
        byte key[] = {message[0], message[1]};
        map.put(key, control);
    }
    
    /**
     * Get the F1 control that matches this MIDI message.  Command, channel, and note are matched.
     * Velocity is ignored.
     * @param mm The incoming MIDI message.
     * @return The corresponding F1 control.
     */
    public F1 getControl(MidiMessage mm) {
        byte message[] = mm.getMessage();
        byte key[] = {message[0], message[1]};
        return map.get(key);
        
    }
    
    /**
     * Midi Velocities are 0 - 127.  Map these to appropriate F1 values.
     * @param control
     * @param velocity
     * @return 
     */
    public int getValue(F1 control, MidiMessage mm) {
        byte velocity = mm.getMessage()[2];
        switch (control.type()) {
            case ANALOG:
                /*
                 * Scale velocity to a 12-bit value.
                 */
                return analogScale * velocity;
            case BINARY:
                /*
                 * Binary controls are either ON or OFF
                 */
                if (velocity == 127) {
                    return 1;
                }
                return 0;
            case ENCODER:
                /*
                 * Midi is 0-127, Encoder values are 0-254.
                 */
                return velocity * 2;
        }
        return 0;
    }

    /**
     * Get the name of the MIDI device used with this mapping.  Use to open the appropriate
     * midi receiver.
     * @return The name of the MIDI device.
     */
    public String getDevice() {
        return midiDevice;
    }
    
    public void setDevice(String device) {
        this.midiDevice = device;
    }
    
    /**
     * Save this map to a file.
     * @param file Name of the map file.
     */
    public void save(File file) throws FileNotFoundException {
        XStream xstream = new XStream();
        FileOutputStream fs = new FileOutputStream(file);
        xstream.toXML(this, fs);
    }
    
    /**
     * Load this map from a file.
     * @param file Name of the map file.
     */
    public void load(File file) throws FileNotFoundException {
        XStream xstream = new XStream();
        FileInputStream fis = new FileInputStream(file);
        F1Mapper newMapper = (F1Mapper) xstream.fromXML(fis, this);
        this.map = newMapper.map;
        this.midiDevice = newMapper.midiDevice;
    }

    
    
}
