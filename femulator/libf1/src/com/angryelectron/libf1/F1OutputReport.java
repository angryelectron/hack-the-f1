/**
 * Femulator
 * Copyright 2013 Andrew Bythell <abythell@ieee.org>
 * http://www.angryelectron.com/femulator
 */ 

package com.angryelectron.libf1;

import java.nio.ByteBuffer;

class F1OutputReport {
    
    /**
     * A buffer to hold HID reports
     */
    private ByteBuffer outputReport;
    private static final int REPORT_SIZE = 22;
    private static final byte REPORT_ID = 3;
    
    /**
     * Zero-based index of fields within the output report buffer.
     */
    protected static final int HEADER = 0;
    protected static final int PAD = 1;
    protected static final int KEY = 3;
    protected static final int KNOB = 5;
    protected static final int ANALOG1 = 6;
    protected static final int ANALOG2 = 14;

    /**
     * Zero-based index of Keys.
     *
     */
    protected static final int BROWSE_KEY = 3;
    protected static final int SIZE_KEY = 4;
    protected static final int TYPE_KEY = 5;
    protected static final int REVERSE_KEY = 6;
    protected static final int SHIFT_KEY = 7;
    protected static final int CAPTURE_KEY = 9;
    protected static final int QUANT_KEY = 10;
    protected static final int SYNC_KEY = 11;
    protected static final int KILL4_KEY = 12;
    protected static final int KILL3_KEY = 13;
    protected static final int KILL2_KEY = 14;
    protected static final int KILL1_KEY = 15;
    
    protected F1OutputReport() {
        outputReport = ByteBuffer.allocate(REPORT_SIZE);
        outputReport.put(HEADER, REPORT_ID);
    }
    
    protected byte[] getBytes() {
        return outputReport.array();
    }
    
    protected short getPads() {
        return outputReport.getShort(PAD);
    }
    
    protected void putPads(short value) {
        outputReport.putShort(PAD, value);
    }
    
    protected short getKeys() {
        return outputReport.getShort(KEY);
    }
    
    protected void putKeys(short value) {
        outputReport.putShort(KEY, value);
    }
    
}
