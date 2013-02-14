package com.angryelectron.femulator.f1api;

import com.angryelectron.libf1.F1HID;
import com.angryelectron.libf1.F1Midi;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.sound.midi.ShortMessage;
import org.openide.util.Lookup;

public class F1Utils {

    /**
     * List available F1 Controls.
     * @return 
     */
    static public String[] listF1Controls() {
        ArrayList<String> controls = new ArrayList<String>();
        for (F1HID.F1 value : F1HID.F1.values()) {
            controls.add(value.toString());
        }
        return controls.toArray(new String[controls.size()]);
    }
    
    /**
     * List available MIDI Devices
     */    
    static public String[] listMidiDevices() {        
        List<String> devices = F1Midi.list();
        return devices.toArray(new String[devices.size()]);        
    }
    
    /**
     * List MIDI Command Types, as defined by the ShortMessage class.
     * @return Array of MIDI commands.
     */
    static public String[] listMidiCommands() {
        Class<?> c = ShortMessage.class;
        ArrayList<String> names = new ArrayList<String>();
        for (Field f : c.getFields()) {
            names.add(f.getName());
        }
        return names.toArray(new String[names.size()]);
    }
    
    static public F1Service getF1Service() {
        return Lookup.getDefault().lookup(F1Service.class);
    }
}
