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
 * Femulator MIDI Receiver
 * Copyright 2013 Andrew Bythell <abythell@ieee.org>
 * http://www.angryelectron.com/femulator
 */ 
package com.angryelectron.libf1;

import java.util.ArrayList;
import java.util.List;
import javax.sound.midi.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class F1Midi implements Receiver {
    
    private MidiDevice receiver;
    private F1MidiCallback callback;

    public F1Midi(F1MidiCallback callback) {
        this.callback = callback;
    }
    
    /**
     * List available MIDI devices.  Only lists devices that are available
     * as receivers
     * @return 
     */
    public static List<String> list() {
        ArrayList<String> list = new ArrayList<String>();
        MidiDevice.Info[] device = MidiSystem.getMidiDeviceInfo();
        for (int i=0; i<device.length; i++) {
            try {
                MidiDevice midi = MidiSystem.getMidiDevice(device[i]);
                if (midi.getMaxTransmitters() != 0) {
                    list.add(device[i].getName());
                }
            } catch (MidiUnavailableException ex) {
                Logger.getLogger(F1Midi.class.getName()).log(Level.WARN, ex.getMessage());
            }
        }
        return list;
    }
    
    /**
     * Open a MIDI device and receive MIDI messages.
     * @param deviceName The name of the Midi device
     * @throws MidiUnavailableException If the device cannot be opened.
     */
    public void open(String deviceName) throws MidiUnavailableException {
        MidiDevice.Info[] device = MidiSystem.getMidiDeviceInfo();
        for (int i=0; i< device.length; i++) {
            if (device[i].getName().equals(deviceName)) {
                MidiDevice midi = MidiSystem.getMidiDevice(device[i]);
                if (midi.getMaxTransmitters() != 0) {
                    receiver = midi;
                    receiver.getTransmitter().setReceiver(this);
                }
            } 
        }
        if (receiver == null) {
            throw new MidiUnavailableException("Could not open " + deviceName + " MIDI device ");
        }
        receiver.open();
    }
    
    /**
     * This is called in response to incoming MIDI messages and is for internal
     * use only.
     * @param mm A Midi Message
     * @param l Not Used
     */
    @Override
     public void send(MidiMessage mm, long l) {        
        callback.onMidi(mm);        
    }
        

    @Override
    public void close() {
        if (receiver != null) {
                receiver.close();  //shoud close all receivers and transmitters
                receiver = null;
        }
    }
    
}
