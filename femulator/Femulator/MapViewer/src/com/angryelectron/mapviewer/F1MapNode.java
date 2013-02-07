package com.angryelectron.mapviewer;

import com.angryelectron.f1api.F1MapEntry;
import com.angryelectron.f1api.F1MapEntry.F1Control;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.WeakListeners;
import org.openide.util.lookup.Lookups;

public class F1MapNode extends AbstractNode implements PropertyChangeListener {

    public F1MapNode(F1MapEntry obj) {
        super(Children.create(new F1MapNodeChildFactory(), true), Lookups.singleton(obj));
        obj.addPropertyChangeListener(WeakListeners.propertyChange(this, obj));
        setDisplayName(obj.getName());
    }
    
    public F1MapNode() {
        super(Children.create(new F1MapNodeChildFactory(), true));
        setDisplayName("Root (Change to something else)");
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        F1MapEntry entry = getLookup().lookup(F1MapEntry.class);
        try {
            Property<String> nameProperty = new PropertySupport.Reflection<String>(entry, String.class, "name");
            Property<Integer> messageProperty = new PropertySupport.Reflection<Integer>(entry, Integer.class, "message");
            Property<Integer> channelProperty = new PropertySupport.Reflection<Integer>(entry, Integer.class, "channel");
            Property<Integer> noteProperty = new PropertySupport.Reflection<Integer>(entry, Integer.class, "note");
            Property<F1Control> controlProperty = new PropertySupport.Reflection<F1Control>(entry, F1Control.class, "control");                        
            
            nameProperty.setName("name");
            messageProperty.setName("message");
            channelProperty.setName("channel");
            noteProperty.setName("note");
            controlProperty.setName("control");
            
            set.put(nameProperty);
            set.put(messageProperty);
            set.put(channelProperty);
            set.put(noteProperty);
            set.put(controlProperty);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(F1MapNode.class.getName()).log(Level.SEVERE, null, ex);
        }
        sheet.put(set);
        return sheet;
    }
    
    
    
    
    @Override
    public Action[] getActions(boolean popup) {
        return new Action[] {new MyAction() };
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        if ("name".equals(pce.getPropertyName())) {
            this.fireDisplayNameChange(null, getDisplayName());
        }
    }
    
    private class MyAction extends AbstractAction {

        public MyAction() {
            putValue(NAME, "Do something");
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            F1MapEntry entry = getLookup().lookup(F1MapEntry.class);
            JOptionPane.showMessageDialog(null, "Hello from " + entry);
        }
        
    }
    
}
