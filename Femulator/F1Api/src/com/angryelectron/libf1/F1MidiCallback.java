/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.angryelectron.libf1;

import javax.sound.midi.MidiMessage;

/**
 *
 * @author abythell
 */
public interface F1MidiCallback {

    public void onMidi(MidiMessage mm);
    
}
