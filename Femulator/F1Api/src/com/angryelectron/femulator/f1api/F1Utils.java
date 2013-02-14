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
