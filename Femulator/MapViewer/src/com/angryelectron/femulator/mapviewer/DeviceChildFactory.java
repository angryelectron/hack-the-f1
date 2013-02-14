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
package com.angryelectron.femulator.mapviewer;

import com.angryelectron.femulator.f1api.F1Service;
import com.angryelectron.femulator.f1api.F1Utils;
import com.angryelectron.libf1.F1Device;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

class DeviceChildFactory extends ChildFactory<F1Device> {
        
    private F1Service f1 = F1Utils.getF1Service();    

    /**
     * Create keys used to populate nodes from the list of entries.  Adds a single
     * node representing the single F1Device object.
     * @param toPopulate Nodes will be created for all items added to this list.
     * @return true
     */
    @Override
    protected boolean createKeys(List<F1Device> toPopulate) {                        
        toPopulate.add(f1.getDevice());
        return true;
    }

    /**
     * Create a new node
     * @param key An object, taken from the toPopulate list created by createKeys().
     * @return A new node.
     */
    @Override
    protected Node createNodeForKey(F1Device key) {        
        return new DeviceNode(key);
    }
    
}
