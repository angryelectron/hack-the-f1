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
package com.angryelectron.femulator.f1api;

import com.angryelectron.libf1.F1Entry;
import com.angryelectron.libf1.F1Midi;
import com.angryelectron.libf1.F1MidiCallback;
import java.io.IOException;
import java.util.Arrays;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 * Generate F1Entry objects using actual MIDI input.  An F1Entry object will
 * be put on the Lookup in response to incoming MIDI messages.
 */
public class F1LearnMode implements Lookup.Provider {                
    private F1Midi midi;
    private InstanceContent content;
    private Lookup lookup;
    private F1MidiCallback midiCallback = new F1MidiCallback() {

        /**
         * When MIDI is received, create an F1Entry and put it on the Lookup.
         */
        @Override
        public void onMidi(MidiMessage mm) {
            try {
                byte[] message = mm.getMessage();
                ShortMessage sm = new ShortMessage();
                sm.setMessage(mm.getStatus(), message[1], message[2]);
                F1Entry entry = new F1Entry();
                entry.setChannel(sm.getChannel());
                entry.setMidiCommand(sm.getCommand());
                entry.setNote(sm.getData1());
                content.set(Arrays.asList(entry), null);
                
            } catch (InvalidMidiDataException ex) {
                Logger.getLogger(F1LearnMode.class.getName()).log(Level.ERROR, null, ex);
            }
        }
        
    };
    
    /**
     * Constructor.
     */
    public F1LearnMode() {
        content = new InstanceContent();
        lookup = new AbstractLookup(content);
        midi = new F1Midi(midiCallback);
    }
        
    /**
     * Start listening for MIDI messages.
     * @param midiDevice Name of MIDI device to listen to.
     * @throws IOException
     * @throws InvalidMidiDataException
     * @throws MidiUnavailableException 
     */
    public void start(String midiDevice) throws IOException, InvalidMidiDataException, MidiUnavailableException {    
        midi.open(midiDevice);
    }
    
    /**
     * Stop listening for MIDI messages.
     */
    public void stop() {        
        midi.close();
    }

    /**
     * Return the Lookup for this object.
     * @return Lookup
     */
    @Override
    public Lookup getLookup() {
        return lookup;
    }
    
}
