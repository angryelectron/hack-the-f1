/*
             LUFA Library
     Copyright (C) Dean Camera, 2010.
              
  dean [at] fourwalledcubicle [dot] com
      www.fourwalledcubicle.com
*/

/*
  Copyright 2010  Dean Camera (dean [at] fourwalledcubicle [dot] com)

  Permission to use, copy, modify, distribute, and sell this 
  software and its documentation for any purpose is hereby granted
  without fee, provided that the above copyright notice appear in 
  all copies and that both that the copyright notice and this
  permission notice and warranty disclaimer appear in supporting 
  documentation, and that the name of the author not be used in 
  advertising or publicity pertaining to distribution of the 
  software without specific, written prior permission.

  The author disclaim all warranties with regard to this
  software, including all implied warranties of merchantability
  and fitness.  In no event shall the author be liable for any
  special, indirect or consequential damages or any damages
  whatsoever resulting from loss of use, data or profits, whether
  in an action of contract, negligence or other tortious action,
  arising out of or in connection with the use or performance of
  this software.
*/

/** \file
 *
 *  Main source file for the GenericHID demo. This file contains the main tasks of
 *  the demo and is responsible for the initial application hardware configuration.
 */

#include "f1.h"

/** Buffer to hold the previously generated HID report, for comparison purposes inside the HID class driver. */
uint8_t PrevHIDReportBuffer[F1_REPORT_SIZE];

/** Structure to contain reports from the host, so that they can be echoed back upon request */
struct
{
	uint8_t  ReportID;
	uint16_t ReportSize;
	uint8_t  ReportData[F1_REPORT_SIZE];
} HIDReportEcho;

#define SEGMENT_ON	64
#define SEGMENT_OFF	0
#define BUTTON_LED_ON	0x7F
#define BUTTON_LED_OFF	0
#define F1_ID		80
#define F1_VERSION	1

/*
 * F1 Output Report.  Based on data from
 * https://github.com/fatlimey/hack-the-f1/blob/master/Native%20Instruments%20F1%20HID%20Protocol%20Analysis.html
 */
struct
{
	uint8_t id;			/* F1_ID  */
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
	
} F1OutputReport_t;

/* 
 * F1 Input Report.  Based on data from the same document as the Output Report
 */
struct
{
	uint8_t version;		/* F1_VERSION */ 
	uint16_t pad_state;		/* TODO: document how bits map to pads/keys */
	uint16_t key_state;
	uint8_t  knob_value;		/* values: 0 - 0xFF */ 
	uint8_t  analog_inputs[16];	/* TODO: break this out into separate channels */ 
} F1InputReport_t;

/** LUFA HID Class driver interface configuration and state information. This structure is
 *  passed to all HID Class driver functions, so that multiple instances of the same class
 *  within a device can be differentiated from one another.
 */
USB_ClassInfo_HID_Device_t Generic_HID_Interface =
	{
		.Config =
			{
				.InterfaceNumber              = 0,
				.ReportINEndpointNumber       = F1_EPNUM,
				.ReportINEndpointSize         = F1_EPSIZE,
				.ReportINEndpointDoubleBank   = false,
				.PrevReportINBuffer           = PrevHIDReportBuffer,
				.PrevReportINBufferSize       = sizeof(PrevHIDReportBuffer),
			},
	};

/** Main program entry point. This routine contains the overall program flow, including initial
 *  setup of all components and the main program loop.
 */
int main(void)
{
	SetupHardware();
	
	LEDs_SetAllLEDs(LEDMASK_USB_NOTREADY);
	sei();
	
	for (;;)
	{
		HID_Device_USBTask(&Generic_HID_Interface);
		USB_USBTask();
	}
}

/** Configures the Arduino UNO hardware and chip peripherals */
void SetupHardware(void)
{
	/* Disable watchdog if enabled by bootloader/fuses */
	MCUSR &= ~(1 << WDRF);
	wdt_disable();

	/* Hardware Initialization */
	Serial_Init(9600, false);
	LEDs_Init();
	USB_Init();

	/* Start the flush timer so that overflows occur rapidly to push received bytes to the USB interface */
	TCCR0B = (1 << CS02);

	/* Pull target /RESET line high */
	AVR_RESET_LINE_PORT |= AVR_RESET_LINE_MASK;
	AVR_RESET_LINE_DDR |= AVR_RESET_LINE_MASK;
}

/** Event handler for the library USB Connection event. */
void EVENT_USB_Device_Connect(void)
{
	LEDs_SetAllLEDs(LEDMASK_USB_ENUMERATING);	
}

/** Event handler for the library USB Disconnection event. */
void EVENT_USB_Device_Disconnect(void)
{
	LEDs_SetAllLEDs(LEDMASK_USB_NOTREADY);
}

/** Event handler for the library USB Configuration Changed event. */
void EVENT_USB_Device_ConfigurationChanged(void)
{
	LEDs_SetAllLEDs(LEDMASK_USB_READY);

	if (!(HID_Device_ConfigureEndpoints(&Generic_HID_Interface)))
	  LEDs_SetAllLEDs(LEDMASK_USB_ERROR);

	USB_Device_EnableSOFEvents();
}

/** Event handler for the library USB Unhandled Control Request event. */
void EVENT_USB_Device_UnhandledControlRequest(void)
{
	HID_Device_ProcessControlRequest(&Generic_HID_Interface);
}

/** Event handler for the USB device Start Of Frame event. */
void EVENT_USB_Device_StartOfFrame(void)
{
	HID_Device_MillisecondElapsed(&Generic_HID_Interface);
}

/** HID class driver callback function for the creation of HID reports to the host.
 *
 *  \param[in]     HIDInterfaceInfo  Pointer to the HID class interface configuration structure being referenced
 *  \param[in,out] ReportID    Report ID requested by the host if non-zero, otherwise callback should set to the generated report ID
 *  \param[in]     ReportType  Type of the report to create, either REPORT_ITEM_TYPE_In or REPORT_ITEM_TYPE_Feature
 *  \param[out]    ReportData  Pointer to a buffer where the created report should be stored
 *  \param[out]    ReportSize  Number of bytes written in the report (or zero if no report is to be sent
 *
 *  \return Boolean true to force the sending of the report, false to let the library determine if it needs to be sent
 */
bool CALLBACK_HID_Device_CreateHIDReport(USB_ClassInfo_HID_Device_t* const HIDInterfaceInfo,
                                         uint8_t* const ReportID,
                                         const uint8_t ReportType,
                                         void* ReportData,
                                         uint16_t* const ReportSize)
{
	if (HIDReportEcho.ReportID)
	  *ReportID = HIDReportEcho.ReportID;

	memcpy(ReportData, HIDReportEcho.ReportData, HIDReportEcho.ReportSize);
	
	*ReportSize = HIDReportEcho.ReportSize;
	return true;
}

/** HID class driver callback function for the processing of HID reports from the host.
 *
 *  \param[in] HIDInterfaceInfo  Pointer to the HID class interface configuration structure being referenced
 *  \param[in] ReportID    Report ID of the received report from the host
 *  \param[in] ReportType  The type of report that the host has sent, either REPORT_ITEM_TYPE_Out or REPORT_ITEM_TYPE_Feature
 *  \param[in] ReportData  Pointer to a buffer where the created report has been stored
 *  \param[in] ReportSize  Size in bytes of the received HID report
 */
void CALLBACK_HID_Device_ProcessHIDReport(USB_ClassInfo_HID_Device_t* const HIDInterfaceInfo,
                                          const uint8_t ReportID,
                                          const uint8_t ReportType,
                                          const void* ReportData,
                                          const uint16_t ReportSize)
{
	HIDReportEcho.ReportID   = ReportID;
	HIDReportEcho.ReportSize = ReportSize;
	memcpy(HIDReportEcho.ReportData, ReportData, ReportSize);
}
