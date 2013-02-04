/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.angryelectron.libf1;

import com.angryelectron.libf1.F1HID.F1;
import java.io.IOException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author abythell
 */
public class F1Api {
    
    private F1HID hid;
    private F1Mapper mapper;
    private F1Midi midi;
    private enum F1Mode {PLAY, LEARN};
    private F1Mode mode = F1Mode.PLAY;
    
    /*
     * Stores the last received MidiMessage when in MAP mode, which can be used
     * to build a MIDI Learn function.
     */
    private MidiMessage lastMidiMessage;
    
    private F1MidiCallback playCallback = new F1MidiCallback() {
        @Override
        public void onMidi(MidiMessage mm) {
            F1 control = mapper.getControl(mm);
            int value = mapper.getValue(control, mm);
            hid.set(control, value);
            try {
                hid.send();
            } catch (IOException ex) {
                Logger.getLogger(F1Api.class.getName()).log(Level.ERROR, null, ex);
            }
        }
    };
    
    private F1MidiCallback mapCallback = new F1MidiCallback() {
        @Override
        public void onMidi(MidiMessage mm) {
            lastMidiMessage = mm;
        }   
    };
    
    public void setMapper(F1Mapper mapper) {
        this.mapper = mapper;
    }
    
    public F1Mapper getMapper() {
        return mapper;
    }
    
    public void play() throws IOException, MidiUnavailableException {
       hid = new F1HID();
       hid.open();
       mode = F1Mode.PLAY;
       midi = new F1Midi(playCallback);
       midi.open(mapper.getDevice());
    }
    
    public void learn() throws MidiUnavailableException {
        mode = F1Mode.LEARN;
        midi = new F1Midi(mapCallback);
        midi.open(mapper.getDevice());
    }
    
    public MidiMessage getLearnedMidi() {
        if (mode != F1Mode.LEARN) {
            throw new IllegalArgumentException("getLastMidiMessage() is only valid in MAP mode");
        }
        return lastMidiMessage;
    }
      
    public void stop() {
        if (hid != null) {
            hid.close();
        }
        midi.close();
    }
    
}
