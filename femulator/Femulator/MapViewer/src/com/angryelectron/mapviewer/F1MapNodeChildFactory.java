package com.angryelectron.mapviewer;

import com.angryelectron.f1api.F1MapEntry;
import java.util.Arrays;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

public class F1MapNodeChildFactory extends ChildFactory<F1MapEntry> {

    @Override
    protected boolean createKeys(List<F1MapEntry> toPopulate) {
        F1MapEntry[] entries = new F1MapEntry[5];
        for (int i=0; i< entries.length; i++) {
            entries[i] = new F1MapEntry();
            entries[i].setName(Integer.valueOf(i).toString());
        }
        toPopulate.addAll(Arrays.asList(entries));
        return true;
    }
    
    @Override
    protected Node createNodeForKey(F1MapEntry key) {
        return new F1MapNode(key);
    }

    
    

}
