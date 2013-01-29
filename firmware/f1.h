/*
 * Femulator Firmware - USB Report Data Structures
 * Copyright 2013, Andrew Bythell, abythell@ieee.org
 * http://angryelectron.com/femulator
 */

#ifndef F1_H
#define F1_H

/*
 * USB HID Report IDs
 */
#define F1_OUTPUT_REPORT_ID	128
#define F1_INPUT_REPORT_ID	1
#define F1_DFU_REPORT_ID1	240
#define F1_DFU_REPORT_ID2	208
#define F1_DFU_REPORT_ID3	241
#define FEMULATOR_OUTPUT_REPORT_ID 3

/*
 * F1 Output Data Report, ID 128.  Based on data from
 * https://github.com/fatlimey/hack-the-f1/blob/master/
 *
 * Report ID 128
 * 	Size 8, Count 80 -> 7-seg displays, function keys, rgb pads, stop keys
 */
struct {
	uint8_t reportID;
	uint8_t rightDigit[8];		/* Each byte in the array corresponds to segment: DP, G, C, B, A, F, E, D */
	uint8_t leftDigit[8];		/* Value = SEGMENT_ON or SEGMENT_OFF */ 
	uint8_t browse;			/* Illuminated button state : BUTTON_LED_ON or BUTTON_LED_OFF */ 
	uint8_t size;
	uint8_t type;
	uint8_t reverse;
	uint8_t shift;
	uint8_t capture;
	uint8_t quant;
	uint8_t sync;
	uint8_t pad_1[3];		/* Each byte in the array corresponds to color: Blue, Red, Green */
	uint8_t pad_2[3];		/* Min = 0, Max = 0x7D */
	uint8_t pad_3[3];		/* eg. White = {73, 73, 73} */
	uint8_t pad_4[3];
	uint8_t pad_5[3];
	uint8_t pad_6[3];
	uint8_t pad_7[3];
	uint8_t pad_8[3];
	uint8_t pad_9[3];
	uint8_t pad_10[3];
	uint8_t pad_11[3];
	uint8_t pad_12[3];
	uint8_t pad_13[3];
	uint8_t pad_14[3];
	uint8_t pad_15[3];
	uint8_t pad_16[3];
	uint8_t stop_4[2];		/* Each byte in array corresponds to one of two LEDS */ 
	uint8_t stop_3[2];		/* TODO: find out what values to use */ 
	uint8_t stop_2[2];
	uint8_t stop_1[2];
} F1OutputData;

/* 
 * F1 Input Data Report, ID 128.  Based on data from
 * https://github.com/fatlimey/hack-the-f1/blob/master/
 *
 * Report ID 1
 * 	Size 1, Count 32 -> Pads and Keys, 1 per bit
 *	Size 8, Count 1 -> Encoder
 *	Size 16, Count 4 -> Analog Data?
 *	Size 16, Count 4 -> Analog Data?
 * 
 */
struct
{
	uint8_t reportID;
	uint16_t pad_state;		/* TODO: define macros to mask pads/keys */
	uint16_t key_state;		/* TODO: define macros to mask pads/keys */
	uint8_t  knob_value;		/* values: 0 - 0xFF */ 
	uint16_t  analog1[4];		/* TODO: determine the data format */ 
	uint16_t  analog2[4];		/* TODO: determine the data format */ 
} F1InputData;

#endif /* F1_H */
