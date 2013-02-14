package com.angryelectron.femulator.mapviewer;

import com.angryelectron.libf1.F1Group;
import com.angryelectron.femulator.f1api.F1RefreshEvent;
import com.angryelectron.femulator.f1api.F1Service;
import com.angryelectron.femulator.f1api.F1Utils;
import java.util.Collection;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

class GroupChildFactory extends ChildFactory<F1Group> {
        
    private F1Service f1;       
    private Result<F1RefreshEvent> result;    
    
    /**
     * This allows the listener to be attached in the constructor.  If the class
     * implements LookupListener, the constructor leaks.  When new F1MapEntries
     * are added to the F1Service Lookup, refresh the list of nodes.  This will
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
     * Constructor.  Start listening to F1Service's lookup.
     */
    public GroupChildFactory() {
        f1 = F1Utils.getF1Service();
        result = f1.getLookup().lookupResult(F1RefreshEvent.class);
        result.addLookupListener(listener);         
    }
    

    /**
     * Create keys used to populate nodes from the list of entries.
     * @param toPopulate Nodes will be created for all items added to this list.
     * @return true
     */
    @Override
    protected boolean createKeys(List<F1Group> toPopulate) {                
        toPopulate.addAll(f1.getDevice().listGroups());        
        return true;
    }

    /**
     * Create a new node
     * @param key An object, taken from the toPopulate list created by createKeys().
     * @return A new node.
     */
    @Override
    protected Node createNodeForKey(F1Group key) {
        //Node result = new AbstractNode(Children.create(new GroupChildFactory(), true), Lookups.singleton(key));
        return new GroupNode(key);
    }
    
}
