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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.angryelectron.femulator.mapviewer;

import com.angryelectron.femulator.f1api.F1Service;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

@ActionID(
    category = "File",
id = "com.angryelectron.femulator.mapviewer.SaveAction")
@ActionRegistration(
    displayName = "#CTL_SaveAction")
@ActionReference(path = "Menu/File", position = 1450)
@Messages("CTL_SaveAction=Save")
public final class SaveAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {      
        F1Service f1 = Lookup.getDefault().lookup(F1Service.class);                
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("F1 Map Files", "f1");
        fc.setFileFilter(filter);
        if (f1.getMapFile() != null) {
            fc.setSelectedFile(f1.getMapFile());
        }
        switch(fc.showSaveDialog(WindowManager.getDefault().getMainWindow())) {
            case JFileChooser.APPROVE_OPTION:
                File file = fc.getSelectedFile().getAbsoluteFile();                                
                f1.setMapFile(file);
                f1.save();              
        }        
    }
}
