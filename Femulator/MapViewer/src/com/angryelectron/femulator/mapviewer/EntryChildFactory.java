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

import com.angryelectron.libf1.F1Entry;
import com.angryelectron.libf1.F1Group;
import com.angryelectron.femulator.f1api.F1RefreshEvent;
import com.angryelectron.femulator.f1api.F1Service;
import com.angryelectron.femulator.f1api.F1Utils;
import java.util.Collection;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

class EntryChildFactory extends ChildFactory<F1Entry> {
        
    private F1Service f1;   
    private F1Group group;
    private Lookup.Result<F1RefreshEvent> result;    
    
    /**
     * This allows the listener to be attached in the constructor.  If the class
     * implements LookupListener, the constructor leaks.  When a new F1RefreshEvent
     * is added to the F1Service Lookup, refresh the list of nodes.  This will
     * keep the Node List in the TopComponent up-to-date with the F1Service model.
     */
    private LookupListener listener = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent ev) {
            Collection<? extends F1RefreshEvent> allInstances = result.allInstances();            
            if (!allInstances.isEmpty()) {            
                refresh(true);            
            }
        }        
    };    
    
    /**
     * Constructor.  
     * @param group The F1Group that contains the entries to be populated.
     */
    protected EntryChildFactory(F1Group group) {
        f1 = F1Utils.getF1Service();
        result = f1.getLookup().lookupResult(F1RefreshEvent.class);
        result.addLookupListener(listener);  
        this.group = group;
    }

    /**
     * Create keys used to populate nodes from the list of entries.
     * @param toPopulate Nodes will be created for all items added to this list.
     * @return true
     */
    @Override
    protected boolean createKeys(List<F1Entry> toPopulate) {                        
        /*
         * Create keys/nodes for all entries in the current group.
         */
        toPopulate.addAll(group.listEntries());
        return true;
    }

    /**
     * Create a new node
     * @param key An object, taken from the toPopulate list created by createKeys().
     * @return A new node.
     */
    @Override
    protected Node createNodeForKey(F1Entry key) {        
        return new EntryNode(key);
    }
    
}
