/*
 * Femulator Firmware - USB Device
 * Copyright 2013 Andrew Bythell, abythell@ieee.org
 * http://angryelectron.com/femulator
 *
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

#include "usb.h"
#include "f1.h"

/*
 * When true, the HID Input report will be sent at the next Endpoint interrupt
 */
bool b_inputUpdated = false;

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
				.PrevReportINBuffer           = NULL, 
				.PrevReportINBufferSize       = 64,
			},
	};

/*
 *  Main entry point.
 *
 *  Currently this contains some code to assist with debugging.  When the
 *  Arduino is loaded with the femulator sketch, toggling Pin 13 will toggle
 *  all the F1 pads and send an Input report. 
 */
int main(void)
{
	char prevRxByte;
	SetupHardware();
	
	LEDs_SetAllLEDs(LEDMASK_USB_NOTREADY);
	sei();
	
	for (;;)
	{
		HID_Device_USBTask(&Generic_HID_Interface);
		USB_USBTask();

		char state = Serial_RxByte();
		if (state == prevRxByte) {	
			b_inputUpdated = false;
		} else {
			if (state == '1') {
				F1InputData.pad_state = 0xFFFF;
				LEDs_SetAllLEDs(LEDS_TX);
			} else if (state == '0') {
				F1InputData.pad_state = 0x0000;
				LEDs_SetAllLEDs(LEDS_RX);
			} else {
				LEDs_SetAllLEDs(LEDS_NONE);
			}
			b_inputUpdated = true;
			prevRxByte = state;
		}	
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

/*
 *  Create a report, which the LUFA framework will send to the Host. 
 *  Used to update Traktor with the current state of the controls. 
 */
bool CALLBACK_HID_Device_CreateHIDReport(USB_ClassInfo_HID_Device_t* const HIDInterfaceInfo,
                                         uint8_t* const ReportID,
                                         const uint8_t ReportType,
                                         void* ReportData,
                                         uint16_t* const ReportSize)
{
	/* 
 	 * TODO: currently this only sends data back to Traktor.
 	 * How can this also send F1OutputData back to Software/MIDI?
	 */
	*ReportID = F1_INPUT_REPORT_ID; 
	memcpy(ReportData, &F1InputData, sizeof(F1InputData));
	*ReportSize = sizeof(F1InputData);

	/*
	 * Device-initiated input reports are only sent when true.  By
	 * managing b_inputUpdated, the device will only issue Input reports
	 * when the control state has changed.
	 */
	return b_inputUpdated; 
}

/* 
 *  Get a report from the Host.
 */
void CALLBACK_HID_Device_ProcessHIDReport(USB_ClassInfo_HID_Device_t* const HIDInterfaceInfo,
                                          const uint8_t ReportID,
                                          const uint8_t ReportType,
                                          const void* ReportData,
                                          const uint16_t ReportSize)
{
	switch(ReportID) {
		case F1_INPUT_REPORT_ID:
			/* receive input data from software/MIDI device */
			/* TODO: confirm that LUFA will automatically send F1InputData
			 * to Traktor at the next interrupt interval 
			 */
			memcpy(&F1InputData, ReportData, ReportSize);
			break;
		case F1_OUTPUT_REPORT_ID:
			/* receive data from Traktor */
			/* TODO: pass the data to Software/MIDI */ 
			memcpy(&F1OutputData, ReportData, ReportSize);
			break; 
		case F1_DFU_REPORT_ID1:
		case F1_DFU_REPORT_ID2:
		case F1_DFU_REPORT_ID3:
			/* DFU is used to update the firmware on the F1.
 			 * Femulator shouldn't support this, unless DFU is used
			 * for other functions.
			 */ 
			break;
	}

}
