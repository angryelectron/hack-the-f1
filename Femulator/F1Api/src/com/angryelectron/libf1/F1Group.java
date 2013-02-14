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

import java.util.ArrayList;
import java.util.List;

public class F1Group {

    private String name;
    private ArrayList<F1Entry> entries = new ArrayList<F1Entry>();

    public F1Group() {
        
    }
    
    public F1Group(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void addEntry(F1Entry entry) {
        entries.add(entry);
    }
    
    public void removeEntry(F1Entry entry) {
        entries.remove(entry);
    }
    
    public List<F1Entry> listEntries() {
        return entries;
    }
}
