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
