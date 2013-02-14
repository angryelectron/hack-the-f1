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
import com.angryelectron.libf1.F1Entry;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.Lookups;

/**
 * EntryNodes represent F1Entry objects. 
 */
public class EntryNode extends AbstractNode {

    /**
     * Constructor.  
     * @param entry The F1Entry data object used to create this node.
     */
    public EntryNode(F1Entry entry) {
        super(Children.create(new GroupChildFactory(), true), Lookups.singleton(entry));
        setDisplayName(entry.getName());
        this.setChildren(Children.LEAF);
    }               

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage("com/angryelectron/femulator/mapviewer/node.png");
    }
    
    @Override
    public Image getOpenedIcon(int i) {
        return getIcon(i);
    }
    
    @Override
    public Action[] getActions (boolean popup) {        
            return new Action[] { 
                new RenameAction(),
                new DeleteAction()
            };        
}

    private class DeleteAction extends AbstractAction {

        public DeleteAction() {
            putValue(NAME, "Delete Entry");
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
                        
        }
    }
       
    private class RenameAction extends AbstractAction {       

        public RenameAction() {
            putValue(NAME, "Rename Entry");
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {                        
            F1Entry entry = getLookup().lookup(F1Entry.class);                    
            NamePanel panel = new NamePanel();
            panel.setValue(entry.getName());
            NotifyDescriptor nd = new NotifyDescriptor(panel,
                    "Rename Entry",
                    NotifyDescriptor.OK_CANCEL_OPTION,
                    NotifyDescriptor.PLAIN_MESSAGE,
                    null,
                    NotifyDescriptor.OK_OPTION);
            if (DialogDisplayer.getDefault().notify(nd) == NotifyDescriptor.OK_OPTION) {
                String name = panel.getValue();
                if ((name != null) && (!name.isEmpty())) {                    
                    F1Service f1 = F1Utils.getF1Service();
                    entry.setName(name);
                    setDisplayName(name);                    
                    f1.refresh();
                }
            }
        }
    }
    
    
}
