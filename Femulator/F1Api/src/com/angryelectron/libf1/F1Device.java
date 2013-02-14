package com.angryelectron.libf1;

import java.util.ArrayList;
import java.util.List;

public class F1Device {

    private String device;
    private ArrayList<F1Group> groups = new ArrayList<F1Group>();
    
    public F1Device(String device) {
        this.device = device;
    }   
    
    public void setDeviceName(String device) {
        this.device = device;
    }
    
    public String getDeviceName() {
        return device;
    }
    
    public void addGroup(F1Group group) {
        groups.add(group);
    }
    
    public void removeGroup(F1Group group) {
        groups.remove(group);
    }
    
    public List<F1Group> listGroups() {
        return groups;        
    }
    
}
