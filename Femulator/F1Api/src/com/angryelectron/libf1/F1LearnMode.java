package com.angryelectron.libf1;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Generate F1Entry objects using actual MIDI input.  By creating a property
 * change listener in your class you can be notified whenever a new F1Entry
 * object is generated in response to MIDI input.
 */
public class F1LearnMode {                
    private F1Midi midi;
    private volatile F1Entry entry = new F1Entry();
    private PropertyChangeSupport propChange = new PropertyChangeSupport(this);
    private F1MidiCallback midiCallback = new F1MidiCallback() {

        /**
         * When MIDI is received, update the F1Entry and fire a property
         * change.
         */
        @Override
        public void onMidi(MidiMessage mm) {
            try {
                byte[] message = mm.getMessage();
                ShortMessage sm = new ShortMessage();
                sm.setMessage(mm.getStatus(), message[1], message[2]);
                F1Entry oldEntry = entry;
                entry.setChannel(sm.getChannel());
                entry.setMidiCommand(sm.getCommand());
                entry.setNote(sm.getData1());
                propChange.firePropertyChange("f1Entry", oldEntry, entry);
            } catch (InvalidMidiDataException ex) {
                Logger.getLogger(F1LearnMode.class.getName()).log(Level.ERROR, null, ex);
            }
        }
        
    };
    
    /**
     * Constructor.
     */
    public void F1LearnMode() {        
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
     * Add a property change listener that will fire whenever a new MIDI
     * message is available.
     * @param listener 
     */
    public void addMidiListener(PropertyChangeListener listener) {
        propChange.addPropertyChangeListener(listener);
    }
    
    /**
     * Remove a property change listener.  No notification will be send when 
     * new MIDI messages arrive.
     * @param listener 
     */
    public void removeMidiListener(PropertyChangeListener listener) {
        propChange.removePropertyChangeListener(listener);
    }
    
    /**
     * Get an F1Entry object.  Call this from within a PropertyChangeListener
     * to obtain the latest MIDI message.
     * @return 
     */
    public F1Entry getF1Entry() {
        return entry;
    }
    
}
