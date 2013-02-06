/**
 * F1MapEntry
 * @author ZIPTREK\abythell
 * (C) 2012 Ziptrek Ecotours
 */ 

package com.angryelectron.f1api;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class F1MapEntry {
    private Integer message;
    private Integer channel;
    private Integer note;
    private F1Control f1Control; 
    private String name;        
    private List<PropertyChangeListener> listeners = Collections.synchronizedList(new LinkedList<PropertyChangeListener>());        
    
    public enum F1Control {
      PAD1, PAD2, PAD3, PAD4,
      PAD5, PAD6, PAD7, PAD8,
      PAD9, PAD10, PAD11, PAD12,
      PAD13, PAD14, PAD15, PAD16,
      SHIFT, REVERSE, TYPE, SIZE, BROWSE,
      STOP1, STOP2, STOP3, STOP4,
      QUANT, CAPTURE, KNOB, FILTER1,
      FILTER2, FILTER3, FILTER4,
      VOLUME1, VOLUME2, VOLUME3, VOLUME4      
    };
    
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        listeners.add(pcl);          
    }
    
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        listeners.remove(pcl);
    }
    
    private void fire(String propertyName, Object old, Object nue) {
        PropertyChangeListener[] pcls = listeners.toArray(new PropertyChangeListener[0]);
        for (int i=0; i< pcls.length; i++) {
            pcls[i].propertyChange(new PropertyChangeEvent(this, propertyName, old, nue));
        }
    }

    public Integer getMessage() {        
        return message;
    }

    public void setMessage(Integer message) {
        Integer old = this.message;
        this.message = message;
        fire("message", old, message);
    }
    
    public Integer getChannel() {
        return channel;
    }
    
    public void setChannel(Integer channel) {
        Integer old = this.channel;
        this.channel = channel;
        fire("channel", old, channel);
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        Integer old = this.note;
        this.note = note;
        fire("note", old, note);
    }

    public F1Control getControl() {
        return f1Control;
    }

    public void setControl(F1Control f1Control) {
        F1Control old = this.f1Control;
        this.f1Control = f1Control;
        fire("control", old, f1Control);
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        String old = this.name;
        this.name = name;
        fire("name", old, name);
    }
}
