package com.angryelectron.femulator.f1api;

public class F1RefreshEvent {
    private boolean loadEvent;
    
    public F1RefreshEvent() {
        loadEvent = false;
    }
    
    public F1RefreshEvent(boolean load) {
        loadEvent = load;
    }
    
    public boolean isLoadEvent() {
        return loadEvent;
    }
}
