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
    public boolean play();
    public void stop();
}
