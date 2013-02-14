/**
 * Femulator MIDI Mapper
 * Copyright 2013 Andrew Bythell <abythell@ieee.org>
 * http://www.angryelectron.com/femulator
 */ 
package com.angryelectron.libf1;

import com.angryelectron.libf1.F1HID.F1;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collection;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;

/**
 * Load, save and query mappings between MIDI and F1 controls.
 * @author abythell
 */
public class F1Mapper {    
        
    private Multimap<MidiMessage, F1> midiMap = HashMultimap.create();
    private Multimap<F1, MidiMessage> f1Map = HashMultimap.create();
    
    /**
     * Configure XStream XML reader/writer.  Be careful making changes as it
     * could prevent older map files from being read.
     * @return XStream object that can be used for reading and writing map files.
     */
    private static XStream getXStream() {
        XStream xstream = new XStream(new StaxDriver());
        xstream.alias("devicemap", F1Device.class);
        xstream.alias("group", F1Group.class);
        xstream.alias("map", F1Entry.class);                 
        xstream.addImplicitCollection(F1Device.class, "groups");
        xstream.addImplicitCollection(F1Group.class, "entries");        
        xstream.useAttributeFor(F1Device.class, "device");
        xstream.useAttributeFor(F1Group.class, "name");
        xstream.useAttributeFor(F1Entry.class, "name");
        return xstream;
    }
    
    /**
     * Load all F1Entry objects into HashMaps for quick access.  Needed for playback
     * mode, but not for mapping mode.     
     * @param device The F1Device that holds the mappings.
     * @throws InvalidMidiDataException 
     */
    public void createMap(F1Device device) throws InvalidMidiDataException {
        midiMap.clear();
        f1Map.clear();
        for (F1Group group : device.listGroups()) {
            for (F1Entry entry : group.listEntries()) {
                switch(entry.getDirection()) {
                    case MIDI_F1:
                        midiMap.put(entry.getMidiMessage(), entry.getControl());
                        break;
                    case F1_MIDI:
                        f1Map.put(entry.getControl(), entry.getMidiMessage());
                        break;
                }
            }
        }
    }
    
    /**
     * Lookup all F1 controls mapped to this MidiMessage.
     * @param mm The incoming MidiMessage (with velocity set to zero).
     * @return A collection of F1 controls.
     */
    public Collection<F1> lookupF1(MidiMessage mm) {
        return midiMap.get(mm);        
    }
    
    /**
     * Lookup all MidiMessages (with velocity set to zero) mapped to this F1
     * command.
     * @param control The incoming F1 command.
     * @return A collection of MidiMessages (without velocities).
     */
    public Collection<MidiMessage> lookupMidi(F1 control) {
        return f1Map.get(control);
    }
    
    /**
     * Load an F1 Map file from disk.
     * @param file
     * @return 
     */
    public static F1Device loadMapFile(File file) {                               
        XStream xstream = getXStream();
        F1Device device = new F1Device("default");
        device = (F1Device) xstream.fromXML(file, device);                
        return device;
    }
        
    /**
     * Save an F1 Map file to disk.
     * @param device
     * @param file
     * @throws FileNotFoundException 
     */
    public static void saveMapFile(F1Device device, File file) throws FileNotFoundException {        
        FileOutputStream fs = new FileOutputStream(file);          
        XStream xstream = getXStream();
        xstream.toXML(device, fs);
    }
    
    
}
