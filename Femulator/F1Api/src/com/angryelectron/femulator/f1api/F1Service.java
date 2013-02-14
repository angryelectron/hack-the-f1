package com.angryelectron.femulator.f1api;

import com.angryelectron.libf1.F1Device;
import java.io.File;
import org.openide.util.Lookup;

/**
 * F1Service Interface.  Providers of this service are added to the Global 
 * Lookup for use in all other modules.
 */
public interface F1Service {    
    public void setMapFile(File file);
    public File getMapFile();
    public void load();    
    public void save();                            
    public Lookup getLookup();
    public void refresh();
    public F1Device getDevice();    
}
