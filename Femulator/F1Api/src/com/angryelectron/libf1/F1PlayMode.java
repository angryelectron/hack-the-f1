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
