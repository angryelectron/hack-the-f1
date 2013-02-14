package com.angryelectron.libf1;

import java.util.ArrayList;
import java.util.List;

public class F1Group {

    private String name;
    private ArrayList<F1Entry> entries = new ArrayList<F1Entry>();

    public F1Group() {
        
    }
    
    public F1Group(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void addEntry(F1Entry entry) {
        entries.add(entry);
    }
    
    public void removeEntry(F1Entry entry) {
        entries.remove(entry);
    }
    
    public List<F1Entry> listEntries() {
        return entries;
    }
}
