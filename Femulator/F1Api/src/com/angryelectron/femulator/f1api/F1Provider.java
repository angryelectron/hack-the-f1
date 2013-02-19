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
package com.angryelectron.femulator.f1api;

import com.angryelectron.libf1.F1Device;
import com.angryelectron.libf1.F1Mapper;
import com.angryelectron.libf1.F1PlayMode;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ServiceProvider;

/**
 * F1Service Provider.  Singleton implementation of the F1Service can be
 * retrieved by any dependent class that needs it using the application's
 * global lookup.  The annotation takes care of initializing it and making
 * it available.
 * 
 * To access this class from elsewhere:
 *      F1Service f1 = Lookup.getDefault().lookup(F1Service.class);                
 * 
 * The class also has its own Lookup which is used to notify UI elements when
 * the 'device' model has changed.
 */
@ServiceProvider(service=F1Service.class)
public class F1Provider implements F1Service, Lookup.Provider {    
        
    private InstanceContent content;
    private Lookup lookup;
    private F1Device device = new F1Device("[no device]");
    private File mapFile;
    private F1PlayMode playMode;
    private boolean isPlaying = false;
           
    /**
     * Constructor.  Initializes the Lookup Provider.
     */
    public F1Provider() {
        content = new InstanceContent();
        lookup = new AbstractLookup(content);
    }
    
    /**
     * Return this class' Lookup.  Required to implement Lookup.Provider.
     * @return Lookup.
     */
    @Override
    public Lookup getLookup() {
        return lookup;
    }
    
    /**
     * Refresh all nodes.  Will update all nodes created by ChildFactories that
     * are listening to the F1Service lookup for F1RefreshEvents().
     */
    @Override
    public void refresh() {
        content.set(Arrays.asList(new F1RefreshEvent()), null);
    }
    
    /**
     * Reload all nodes.  Unlike refresh, this will update existing nodes.
     */
    private void reload() {
        content.set(Arrays.asList(new F1RefreshEvent(true)), null);
    }
    
    /**
     * Get the F1Device.  Used by ChildFactories to populate nodes. 
     * @return 
     */
    @Override
    public F1Device getDevice() {
        return device;
    }
    
    
    /**
     * Read F1MapEntries from disk.  Uses the libf1 library to fetch mapping
     * entries and converts them into F1Entry objects.  These objects are placed in
     * the Lookup for other modules to use.
     * @param file 
     */
    @Override
    public void load() {                       
        device = F1Mapper.loadMapFile(mapFile);
        reload();
    }
    
    /**
     * Save F1MapEntries to disk.  Uses the libf1 library to save all F1Entry
     * objects to disk.
     * @param file 
     */
    @Override
    public void save() {
        try {
            F1Mapper.saveMapFile(device, mapFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(F1Provider.class.getName()).log(Level.WARN, ex);
            StatusDisplayer.getDefault().setStatusText(ex.getMessage());
        }
    }             

    @Override
    public void setMapFile(File file) {
        mapFile = file;        
    }

    @Override
    public File getMapFile() {
        return mapFile;
    }
    
    @Override
    public boolean play() {
        if (!isPlaying) {
            playMode = new F1PlayMode(device);
            try {
                playMode.start();
                isPlaying = true;
                return true;
            } catch (Exception ex) {
                StatusDisplayer.getDefault().setStatusText(ex.getMessage());
                Logger.getLogger(F1Provider.class.getName()).log(Level.WARN, ex);
            }
        }
        return false;
    }
    
    @Override
    public void stop() {
        if (isPlaying) {
            playMode.stop();
            isPlaying = false;
        }
    }
            
}
