/*
 * Femulator Firmware - USB Descriptor Header
 * Copyright 2013 Andrew Bythell, abythell@ieee.org
 * http://angryelectron.com/femulator
 */

/*
  Copyright 2013  Dean Camera (dean [at] fourwalledcubicle [dot] com)

  Permission to use, copy, modify, distribute, and sell this
  software and its documentation for any purpose is hereby granted
  without fee, provided that the above copyright notice appear in
  all copies and that both that the copyright notice and this
  permission notice and warranty disclaimer appear in supporting
  documentation, and that the name of the author not be used in
  advertising or publicity pertaining to distribution of the
  software without specific, written prior permission.

  The author disclaims all warranties with regard to this
  software, including all implied warranties of merchantability
  and fitness.  In no event shall the author be liable for any
  special, indirect or consequential damages or any damages
  whatsoever resulting from loss of use, data or profits, whether
  in an action of contract, negligence or other tortious action,
  arising out of or in connection with the use or performance of
  this software.
*/

#ifndef _DESCRIPTORS_H_
#define _DESCRIPTORS_H_

	/* Includes: */
		#include <avr/pgmspace.h>

		#include <LUFA/Drivers/USB/USB.h>
		
		#include "Config/AppConfig.h"

	/* Type Defines: */

		typedef struct
		{
			USB_Descriptor_Header_t Header;
			uint8_t Attributes;
			uint16_t DetachTimeout;
			uint16_t TransferSize;
		} USB_Descriptor_DFU_Functional_t;

		/** Type define for the device configuration descriptor structure. This must be defined in the
		 *  application code, as the configuration descriptor contains several sub-descriptors which
		 *  vary between devices, and which describe the device's usage to the host.
		 */

		typedef struct
		{
			USB_Descriptor_Configuration_Header_t Config;
			USB_Descriptor_Interface_t            HID_Interface;
			USB_HID_Descriptor_HID_t              HID_F1HID;
		        USB_Descriptor_Endpoint_t             HID_ReportINEndpoint;
		        USB_Descriptor_Endpoint_t             HID_ReportOUTEndpoint;
			USB_Descriptor_Interface_t	DFU_Interface;	
			USB_Descriptor_DFU_Functional_t	DFU_Functional;
		} USB_Descriptor_Configuration_t;

	/* Macros: */
		#define GENERIC_IN_EPADDR         (ENDPOINT_DIR_IN | 1)

		/* 
 		 * The Controller Descriptor Dump says the OUT Endpoint Address is 1, 
		 * which is the same as in IN Endpoint Address.  When configured this way,
		 * the USB Hub constantly resets and hidapi cannot write output reports.
		 * Let's use 2 for now, but this might cause some issues later
		 * getting output from Traktor.  Currently, Traktor seems to be communicating
		 * successfully. 
		 */
		#define GENERIC_OUT_EPADDR         (ENDPOINT_DIR_OUT | 2)

		/** Size in bytes of the endpoint. */
		#define GENERIC_EPSIZE            64

		#define DTYPE_DFUFunctional	0x21

	/* Function Prototypes: */
		uint16_t CALLBACK_USB_GetDescriptor(const uint16_t wValue,
		                                    const uint8_t wIndex,
		                                    const void** const DescriptorAddress)
		                                    ATTR_WARN_UNUSED_RESULT ATTR_NON_NULL_PTR_ARG(3);

#endif

