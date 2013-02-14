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
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

public class F1Entry {
    private Integer command;
    private Integer channel;
    private Integer note;
    private F1 f1Control;
    private Direction direction;
    private String name;        
    
    public enum Direction {MIDI_F1, F1_MIDI};    
    
    public F1Entry(Integer message, Integer channel, Integer note, F1 f1Control, String name) {
        this.command = message;
        this.channel = channel;
        this.note = note;
        this.f1Control = f1Control;
        this.name = name;    
    }
    
    public F1Entry() {
        //use some defaults
        command = ShortMessage.NOTE_ON;
        channel = 1;
        note = 0;
        f1Control = F1.SHIFT;
        direction = Direction.MIDI_F1;
        this.name = "[unnamed]";
    }
   

    public Integer getMidiCommand() {        
        return command;
    }

    public void setMidiCommand(Integer command) {        
        this.command = command;        
    }
    
    public String getMidiCommandName() {
        Class<?> c = ShortMessage.class;
        for (Field f : c.getFields()) {
            try {
                if (command == f.getInt(null)) {
                    return f.getName();                    
                }
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(F1Entry.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(F1Entry.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public void setMidiCommandName(String command) {
        Class<?> c = ShortMessage.class;        
        try {
            Field f = c.getField(command);
            this.command = f.getInt(null);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(F1Entry.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(F1Entry.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(F1Entry.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(F1Entry.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Integer getChannel() {
        return channel;
    }
    
    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public F1 getControl() {
        return f1Control;
    }
    
    public String getControlName() {
        return f1Control.toString();
    }

    public void setControl(F1 f1Control) {
        this.f1Control = f1Control;
    }
    
    public void setControlName(String control) {        
        this.f1Control = F1.valueOf(control);
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    
    public MidiMessage getMidiMessage() throws InvalidMidiDataException {
        ShortMessage message = new ShortMessage();
        message.setMessage(command, channel, note, 0);
        return message;
    }
    
}
