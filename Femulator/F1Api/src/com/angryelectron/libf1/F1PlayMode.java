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
package com.angryelectron.libf1;

import com.angryelectron.libf1.F1HID.F1;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Translate incoming MIDI messages according to a user-defined map and send
 * F1 commands to Traktor.
 */
public class F1PlayMode {
        
    private F1Device device;
    private F1Mapper mapper = new F1Mapper();
    private F1HID hid = new F1HID();
    private F1Midi midi;
    private F1MidiCallback midiCallback = new F1MidiCallback() {

        @Override
        public void onMidi(MidiMessage mm) {
            byte[] midi = mm.getMessage();
            ShortMessage sm = new ShortMessage();
            try {
                sm.setMessage(mm.getStatus(), midi[1], 0);
                Collection<F1> lookupF1 = mapper.lookupF1(mm);
                for (F1 f1 : lookupF1) {
                    hid.set(f1, midi[2]);
                }
                hid.send();
            } catch (Exception ex) {              
                Logger.getLogger(F1Midi.class.getName()).log(Level.ERROR, null, ex);
            }
        }
        
    };
    
    public void F1PlayMode(File mapFile) {
        device = F1Mapper.loadMapFile(mapFile);
        midi = new F1Midi(midiCallback);
    }
    
    public void F1PlayMode(F1Device device) {
        this.device = device;
        midi = new F1Midi(midiCallback);
    }
    
    public void start() throws IOException, InvalidMidiDataException, MidiUnavailableException {
        mapper.createMap(device);
        hid.open();
        midi.open(device.getDeviceName());
    }
    
    public void stop() {
        hid.close();
        midi.close();
    }
}
