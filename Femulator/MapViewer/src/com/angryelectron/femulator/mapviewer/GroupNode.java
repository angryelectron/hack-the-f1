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
import com.angryelectron.libf1.F1Group;
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
 * GroupNodes represent F1Group objects. 
 */
public class GroupNode extends AbstractNode {

    /**
     * Constructor.
     * @param group The F1Group used to create this node.
     */
    public GroupNode(F1Group group) {
        super(Children.create(new GroupChildFactory(), true), Lookups.singleton(group));
        setDisplayName(group.getName());
        this.setChildren(Children.create(new EntryChildFactory(group), true));
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
            new AddEntryAction(),
            new RenameAction(),
            new DeleteAction()                
        };        
    }       

    private class AddEntryAction extends AbstractAction {

        public AddEntryAction() {
            putValue(NAME, "Add Map Entry");
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            NamePanel panel = new NamePanel();
            NotifyDescriptor nd = new NotifyDescriptor(panel,
                    "Name New Entry",
                    NotifyDescriptor.OK_CANCEL_OPTION,
                    NotifyDescriptor.PLAIN_MESSAGE,
                    null,
                    NotifyDescriptor.OK_OPTION);
            if (DialogDisplayer.getDefault().notify(nd) == NotifyDescriptor.OK_OPTION) {
                String name = panel.getValue();
                if ((name != null) && (!name.isEmpty())) {                                                                                
                    F1Entry entry = new F1Entry();                    
                    entry.setName(name);                              
                    
                    F1Group group = getLookup().lookup(F1Group.class);
                    group.addEntry(entry);
                    
                    F1Service f1 = F1Utils.getF1Service();
                    f1.refresh();
                }
            }
        }
    }

    private class DeleteAction extends AbstractAction {

        public DeleteAction() {
            putValue(NAME, "Delete Group");
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            F1Group group = getLookup().lookup(F1Group.class);
            F1Service f1 = F1Utils.getF1Service();
            f1.getDevice().removeGroup(group);
            f1.refresh();
        }
        
    }

    private class RenameAction extends AbstractAction {       

        public RenameAction() {
            putValue(NAME, "Rename Group");
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {                        
            F1Group group = getLookup().lookup(F1Group.class);                    
            NamePanel panel = new NamePanel();
            panel.setValue(group.getName());
            NotifyDescriptor nd = new NotifyDescriptor(panel,
                    "Rename Group",
                    NotifyDescriptor.OK_CANCEL_OPTION,
                    NotifyDescriptor.PLAIN_MESSAGE,
                    null,
                    NotifyDescriptor.OK_OPTION);
            if (DialogDisplayer.getDefault().notify(nd) == NotifyDescriptor.OK_OPTION) {
                String name = panel.getValue();
                if ((name != null) && (!name.isEmpty())) {                    
                    group.setName(name);
                    setDisplayName(name);
                }
            }
        }
    }
    
    
}
