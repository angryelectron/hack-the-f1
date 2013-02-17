/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.angryelectron.femulator.mapviewer;

import com.angryelectron.femulator.f1api.F1Service;
import com.angryelectron.femulator.f1api.F1Utils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
    category = "File",
id = "com.angryelectron.femulator.mapviewer.PlayAction")
@ActionRegistration(
    iconBase = "com/angryelectron/femulator/mapviewer/player_play.png",
displayName = "#CTL_PlayAction")
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1200),
    @ActionReference(path = "Toolbars/File", position = 300)
})
@Messages("CTL_PlayAction=Play")
public final class PlayAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        F1Service f1 = F1Utils.getF1Service();
        f1.play();
    }
}
