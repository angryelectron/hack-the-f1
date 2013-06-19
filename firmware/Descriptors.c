/*
             LUFA Library
     Copyright (C) Dean Camera, 2013.

  dean [at] fourwalledcubicle [dot] com
           www.lufa-lib.org
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

/** \file
 *
 *  USB Device Descriptors, for library use when in USB device mode. Descriptors are special
 *  computer-readable structures which the host requests upon device enumeration, to determine
 *  the device's capabilities and functions.
 */

#include "Descriptors.h"

/** HID class report descriptor. This is a special descriptor constructed with values from the
 *  USBIF HID class specification to describe the reports and capabilities of the HID device. This
 *  descriptor is parsed by the host and its contents used to determine what data (and in what encoding)
 *  the device will send, and what it may be sent back from the host. Refer to the HID specification for
 *  more details on HID report descriptors.
 */
const USB_Descriptor_HIDReport_Datatype_t PROGMEM GenericReport[] =
{
	HID_RI_USAGE_PAGE(16, 0xFF01),
	HID_RI_USAGE(8, 0x00),
	HID_RI_COLLECTION(8, 0x01),

	  HID_RI_USAGE(8, 0x01),
	  HID_RI_COLLECTION(8, 0x02),

	    HID_RI_REPORT_ID(8, 0x01),
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_LOGICAL_MINIMUM(8, 0x00),
	    HID_RI_LOGICAL_MAXIMUM(8, 0x01),
	    HID_RI_REPORT_SIZE(8, 0x01),
	    HID_RI_REPORT_COUNT(8, 0x20),
	    HID_RI_INPUT(8, HID_IOF_DATA | HID_IOF_VARIABLE | HID_IOF_ABSOLUTE),

	    HID_RI_USAGE(8, 0x03),
	    HID_RI_LOGICAL_MINIMUM(8, 0x00),
	    HID_RI_LOGICAL_MAXIMUM(16, 0x00FF),
	    HID_RI_REPORT_SIZE(8, 0x08),
	    HID_RI_REPORT_COUNT(8, 0x01),
	    HID_RI_INPUT(8, HID_IOF_DATA | HID_IOF_VARIABLE | HID_IOF_ABSOLUTE),

	    HID_RI_USAGE(8, 0x04),
	    HID_RI_USAGE(8, 0x04),
	    HID_RI_USAGE(8, 0x04),
	    HID_RI_USAGE(8, 0x04),
	    HID_RI_LOGICAL_MINIMUM(8, 0x00),
	    HID_RI_LOGICAL_MAXIMUM(16, 0x0FFF),
	    HID_RI_REPORT_SIZE(8, 0x10),
	    HID_RI_REPORT_COUNT(8, 0x04),
	    HID_RI_INPUT(8, HID_IOF_DATA | HID_IOF_VARIABLE | HID_IOF_ABSOLUTE),

	    HID_RI_USAGE(8, 0x05),
	    HID_RI_USAGE(8, 0x05),
	    HID_RI_USAGE(8, 0x05),
	    HID_RI_USAGE(8, 0x05),
	    HID_RI_LOGICAL_MINIMUM(8, 0x00),
	    HID_RI_LOGICAL_MAXIMUM(16, 0x0FFF),
	    HID_RI_REPORT_SIZE(8, 0x10),
	    HID_RI_REPORT_COUNT(8, 0x04),
	    HID_RI_INPUT(8, HID_IOF_DATA | HID_IOF_VARIABLE | HID_IOF_ABSOLUTE),

	  HID_RI_END_COLLECTION(0),
	  HID_RI_USAGE(8, 0x02),
	  HID_RI_COLLECTION(8, 0x02),
		
	    /* custom Femulator Output Report */
	    HID_RI_REPORT_ID(8, 0x02),
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_USAGE(8, 0x02), /* Vendor Usage 2 */
	    HID_RI_LOGICAL_MINIMUM(8, 0x00),
	    HID_RI_LOGICAL_MAXIMUM(8, 0x01),
	    HID_RI_REPORT_SIZE(8, 0x01),
	    HID_RI_REPORT_COUNT(8, 0x20),
	    HID_RI_OUTPUT(8, 0x02), 

	    HID_RI_USAGE(8, 0x03),
	    HID_RI_LOGICAL_MINIMUM(8, 0x00),
	    HID_RI_LOGICAL_MAXIMUM(16, 0x00FF),
	    HID_RI_REPORT_SIZE(8, 0x08),
	    HID_RI_REPORT_COUNT(8, 0x01),
	    HID_RI_OUTPUT(8, 0x02), 

	    HID_RI_USAGE(8, 0x04),
	    HID_RI_USAGE(8, 0x04),
	    HID_RI_USAGE(8, 0x04),
	    HID_RI_USAGE(8, 0x04),
	    HID_RI_LOGICAL_MINIMUM(8, 0x00),
	    HID_RI_LOGICAL_MAXIMUM(16, 0x0FFF),
	    HID_RI_REPORT_SIZE(8, 0x10),
	    HID_RI_REPORT_COUNT(8, 0x04),
	    HID_RI_OUTPUT(8, 0x02),

	    HID_RI_USAGE(8, 0x05),
	    HID_RI_USAGE(8, 0x05),
	    HID_RI_USAGE(8, 0x05),
	    HID_RI_USAGE(8, 0x05),
	    HID_RI_LOGICAL_MINIMUM(8, 0x00),
	    HID_RI_LOGICAL_MAXIMUM(16, 0x0FFF),
	    HID_RI_REPORT_SIZE(8, 0x10),
	    HID_RI_REPORT_COUNT(8, 0x04),
	    HID_RI_OUTPUT(8, 0x02), 
	    /* end custom output report */

	  HID_RI_END_COLLECTION(0),
	  HID_RI_USAGE(8, 0x80),
	  HID_RI_COLLECTION(8, 0x02),

	    HID_RI_REPORT_ID(8, 0x80),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_USAGE(8, 0x81),
	    HID_RI_LOGICAL_MINIMUM(8, 0x00),
	    HID_RI_LOGICAL_MAXIMUM(8, 0x7F),
	    HID_RI_REPORT_SIZE(8, 0x08),
	    HID_RI_REPORT_COUNT(8, 0x50),
	    HID_RI_OUTPUT(8, 0x02),

	  HID_RI_END_COLLECTION(0),

	HID_RI_END_COLLECTION(0),
};

/** Device descriptor structure. This descriptor, located in FLASH memory, describes the overall
 *  device characteristics, including the supported USB version, control endpoint size and the
 *  number of device configurations. The descriptor is read out by the USB host when the enumeration
 *  process begins.
 */
const USB_Descriptor_Device_t PROGMEM DeviceDescriptor =
{
	.Header                 = {.Size = sizeof(USB_Descriptor_Device_t), .Type = DTYPE_Device},

	.USBSpecification       = VERSION_BCD(02.00),
	.Class                  = USB_CSCP_NoDeviceClass,
	.SubClass               = USB_CSCP_NoDeviceSubclass,
	.Protocol               = USB_CSCP_NoDeviceProtocol,

	.Endpoint0Size          = FIXED_CONTROL_ENDPOINT_SIZE,

	.VendorID               = 0x17CC,
	.ProductID              = 0x1120,
	.ReleaseNumber          = 0x0009, 

	.ManufacturerStrIndex   = 0x01, 
	.ProductStrIndex        = 0x02, 
	.SerialNumStrIndex      = 0x06, 

	.NumberOfConfigurations = FIXED_NUM_CONFIGURATIONS
};

/** Configuration descriptor structure. This descriptor, located in FLASH memory, describes the usage
 *  of the device in one of its supported configurations, including information about any device interfaces
 *  and endpoints. The descriptor is read out by the USB host during the enumeration process when selecting
 *  a configuration so that the host may correctly communicate with the USB device.
 */
const USB_Descriptor_Configuration_t PROGMEM ConfigurationDescriptor =
{
	.Config =
		{
			.Header                 = {.Size = sizeof(USB_Descriptor_Configuration_Header_t), .Type = DTYPE_Configuration},

			.TotalConfigurationSize = sizeof(USB_Descriptor_Configuration_t),
			.TotalInterfaces        = 1,

			.ConfigurationNumber    = 1,
			.ConfigurationStrIndex  = 0x04, 

			.ConfigAttributes       = 0x80,

			.MaxPowerConsumption    = USB_CONFIG_POWER_MA(480)
		},

	.HID_Interface =
		{
			.Header                 = {.Size = sizeof(USB_Descriptor_Interface_t), .Type = DTYPE_Interface},

			.InterfaceNumber        = 0x00,
			.AlternateSetting       = 0x00,

			.TotalEndpoints         = 2,

			.Class                  = HID_CSCP_HIDClass,
			.SubClass               = HID_CSCP_NonBootSubclass,
			.Protocol               = HID_CSCP_NonBootProtocol,

			.InterfaceStrIndex      = NO_DESCRIPTOR
		},

	.HID_GenericHID =
		{
			.Header                 = {.Size = sizeof(USB_HID_Descriptor_HID_t), .Type = HID_DTYPE_HID},

			.HIDSpec                = VERSION_BCD(01.10),
			.CountryCode            = 0x00,
			.TotalReportDescriptors = 1,
			.HIDReportType          = HID_DTYPE_Report,
			.HIDReportLength        = sizeof(GenericReport)
		},

	.HID_ReportINEndpoint =
		{
			.Header                 = {.Size = sizeof(USB_Descriptor_Endpoint_t), .Type = DTYPE_Endpoint},

			.EndpointAddress        = GENERIC_IN_EPADDR,
			.Attributes             = (EP_TYPE_INTERRUPT | ENDPOINT_ATTR_NO_SYNC | ENDPOINT_USAGE_DATA),
			.EndpointSize           = GENERIC_EPSIZE,
			.PollingIntervalMS      = 0x04
		},

	.HID_ReportOUTEndpoint =
		{
			.Header                 = {.Size = sizeof(USB_Descriptor_Endpoint_t), .Type = DTYPE_Endpoint},

			.EndpointAddress        = GENERIC_OUT_EPADDR,
			.Attributes             = (EP_TYPE_INTERRUPT | ENDPOINT_ATTR_NO_SYNC | ENDPOINT_USAGE_DATA),
			.EndpointSize           = GENERIC_EPSIZE,
			.PollingIntervalMS      = 0x04
		}
};

/** Language descriptor structure. This descriptor, located in FLASH memory, is returned when the host requests
 *  the string descriptor with index 0 (the first index). It is actually an array of 16-bit integers, which indicate
 *  via the language ID table available at USB.org what languages the device supports for its string descriptors.
 */
const USB_Descriptor_String_t PROGMEM LanguageString =
{
	.Header                 = {.Size = USB_STRING_LEN(1), .Type = DTYPE_String},

	.UnicodeString          = {LANGUAGE_ID_ENG}
};

const USB_Descriptor_String_t PROGMEM ManufacturerString =
{
	.Header                 = {.Size = USB_STRING_LEN(18), .Type = DTYPE_String},
	.UnicodeString          = L"Native Instruments"
};

const USB_Descriptor_String_t PROGMEM ProductString =
{
	.Header = {.Size = USB_STRING_LEN(18), .Type = DTYPE_String},
	.UnicodeString = L"Traktor Kontrol F1"
};

const USB_Descriptor_String_t PROGMEM SerialString = 
{
	.Header = {.Size = USB_STRING_LEN(8), .Type = DTYPE_String},
	.UnicodeString = L"FA52C4D0"
};

const USB_Descriptor_String_t PROGMEM ConfigurationString = 
{
	.Header = {.Size = USB_STRING_LEN(22), .Type = DTYPE_String},
	.UnicodeString = L"Traktor Kontrol F1 HID"
};

const USB_Descriptor_String_t PROGMEM DFUString = 
{
	.Header = {.Size = USB_STRING_LEN(22), .Type = DTYPE_String},
	.UnicodeString = L"Traktor Kontrol F1 DFU"
};


/** This function is called by the library when in device mode, and must be overridden (see library "USB Descriptors"
 *  documentation) by the application code so that the address and size of a requested descriptor can be given
 *  to the USB library. When the device receives a Get Descriptor request on the control endpoint, this function
 *  is called so that the descriptor details can be passed back and the appropriate descriptor sent back to the
 *  USB host.
 */
uint16_t CALLBACK_USB_GetDescriptor(const uint16_t wValue,
                                    const uint8_t wIndex,
                                    const void** const DescriptorAddress)
{
	const uint8_t  DescriptorType   = (wValue >> 8);
	const uint8_t  DescriptorNumber = (wValue & 0xFF);

	const void* Address = NULL;
	uint16_t    Size    = NO_DESCRIPTOR;

	switch (DescriptorType)
	{
		case DTYPE_Device:
			Address = &DeviceDescriptor;
			Size    = sizeof(USB_Descriptor_Device_t);
			break;
		case DTYPE_Configuration:
			Address = &ConfigurationDescriptor;
			Size    = sizeof(USB_Descriptor_Configuration_t);
			break;
		case DTYPE_String:
			switch (DescriptorNumber)
			{
				case 0x00:
					Address = &LanguageString;
					Size    = pgm_read_byte(&LanguageString.Header.Size);
					break;
				case 0x01:
					Address = &ManufacturerString;
					Size    = pgm_read_byte(&ManufacturerString.Header.Size);
					break;
				case 0x02:
					Address = &ProductString;
					Size    = pgm_read_byte(&ProductString.Header.Size);
					break;
				case 0x06: 
					Address = (void*)&SerialString;
					Size    = pgm_read_byte(&SerialString.Header.Size);
					break;
				case 0x04: 
					Address = (void*)&ConfigurationString;
					Size    = pgm_read_byte(&ConfigurationString.Header.Size);
					break;
				case 0x05: 
					Address = (void*)&DFUString;
					Size    = pgm_read_byte(&DFUString.Header.Size);
					break;
			}

			break;
		case HID_DTYPE_HID:
			Address = &ConfigurationDescriptor.HID_GenericHID;
			Size    = sizeof(USB_HID_Descriptor_HID_t);
			break;
		case HID_DTYPE_Report:
			Address = &GenericReport;
			Size    = sizeof(GenericReport);
			break;
	}

	*DescriptorAddress = Address;
	return Size;
}

