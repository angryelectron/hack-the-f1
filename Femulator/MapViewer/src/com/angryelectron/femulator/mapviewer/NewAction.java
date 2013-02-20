/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.angryelectron.femulator.mapviewer;

import com.angryelectron.femulator.f1api.F1Service;
import com.angryelectron.femulator.f1api.F1Utils;
import com.angryelectron.libf1.F1Device;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
    category = "File",
id = "com.angryelectron.femulator.mapviewer.NewAction")
@ActionRegistration(
    displayName = "#CTL_NewAction")
@ActionReference(path = "Menu/File", position = 1100)
@Messages("CTL_NewAction=New")
public final class NewAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        F1Service f1 = F1Utils.getF1Service();
        f1.getDevice().clear();
        f1.refresh();    
    }
}
