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
