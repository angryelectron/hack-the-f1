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

public class F1Device {

    private String device;
    private ArrayList<F1Group> groups = new ArrayList<F1Group>();
    
    public F1Device(String device) {
        this.device = device;
    }   
    
    public void setDeviceName(String device) {
        this.device = device;
    }
    
    public String getDeviceName() {
        return device;
    }
    
    public void addGroup(F1Group group) {
        groups.add(group);
    }
    
    public void removeGroup(F1Group group) {
        groups.remove(group);
    }
    
    public List<F1Group> listGroups() {
        return groups;        
    }
    
}
