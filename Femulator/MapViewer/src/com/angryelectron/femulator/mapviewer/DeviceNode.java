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
 * DeviceNodes represent F1Device objects in the MapTopComponent ExplorerManager. 
 */
public class DeviceNode extends AbstractNode {

    /**
     * Constructor.  Create a new device node to represent an F1Device object.
     * @param device 
     */
    public DeviceNode(F1Device device) {
        super(Children.create(new DeviceChildFactory(), true), Lookups.singleton(device));
        setDisplayName(device.getDeviceName());
        this.setChildren(Children.create(new GroupChildFactory(), true));
    }    
    
    public DeviceNode() {
        super(Children.create(new DeviceChildFactory(), true));
        setDisplayName("[no device]");
        this.setChildren(Children.create(new GroupChildFactory(), true));
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
                new SetDeviceAction(),
                new AddGroupAction()
            };        
}

    private static class AddGroupAction extends AbstractAction {

        public AddGroupAction() {
            putValue(NAME, "Add Group");
        }

        @Override
        public void actionPerformed(ActionEvent ae) {            
            NamePanel panel = new NamePanel();
            NotifyDescriptor nd = new NotifyDescriptor(panel,
                    "Add New Group",
                    NotifyDescriptor.OK_CANCEL_OPTION,
                    NotifyDescriptor.PLAIN_MESSAGE,
                    null,
                    NotifyDescriptor.OK_OPTION);
            if (DialogDisplayer.getDefault().notify(nd) == NotifyDescriptor.OK_OPTION) {
                String name = panel.getValue();
                if ((name != null) && (!name.isEmpty())) {                                                            
                    F1Group group = new F1Group(name);
                    F1Service f1 = F1Utils.getF1Service();
                    f1.getDevice().addGroup(group);
                    f1.refresh();
                }
            }
        }

    }
    
    private class SetDeviceAction extends AbstractAction {

        public SetDeviceAction() {
            putValue(NAME, "Set MIDI Device");
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            MidiPanel panel = new MidiPanel();
            NotifyDescriptor nd = new NotifyDescriptor(panel,
                    "Select MIDI Device",
                    NotifyDescriptor.OK_CANCEL_OPTION,
                    NotifyDescriptor.PLAIN_MESSAGE,
                    null,
                    NotifyDescriptor.OK_OPTION);
            if (DialogDisplayer.getDefault().notify(nd) == NotifyDescriptor.OK_OPTION) {                
                /*
                 * DeviceNodes don't add the F1Device object to the Node's lookup.
                 * Use F1Service instead.
                 */
                F1Service f1 = F1Utils.getF1Service();
                f1.getDevice().setDeviceName(panel.getSelectedMidiDevice());                
                setDisplayName(panel.getSelectedMidiDevice());                
            }
        }
        
    }

    
}
