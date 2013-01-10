/*
 * Femulator Firmware - USB Descriptors
 * Copyright 2013 Andrew Bythell, abythell@ieee.org
 * http://angryelectron.com/femulator
 *
 * This descriptor will make an Arduino UNO appear as a Traktor Kontrol F1
 * according to the "USBlyzer Report" at https://github.com/fatlimey/hack-the-f1
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

#include "Descriptors.h"
#include "HIDDescriptors.h"


/** Device descriptor structure. This descriptor, located in FLASH memory, describes the overall
 *  device characteristics, including the supported USB version, control endpoint size and the
 *  number of device configurations. The descriptor is read out by the USB host when the enumeration
 *  process begins.
 */
USB_Descriptor_Device_t PROGMEM DeviceDescriptor =
{
	.Header                 = {.Size = sizeof(USB_Descriptor_Device_t), .Type = DTYPE_Device},
	.USBSpecification       = VERSION_BCD(02.00),
	.Class                  = 0x00,
	.SubClass               = 0x00,
	.Protocol               = 0x00,
			
	/* The Endpoint0Size is defined in the Makefile:  
	 *
	 * 	LUFA_OPTS += -D FIXED_CONTROL_ENDPOINT_SIZE=64
	 *
	 * The device fails to enumerate if the size is explicity set here. 
	 */
	.Endpoint0Size          = FIXED_CONTROL_ENDPOINT_SIZE,
		
	.VendorID               = 0x17CC, 

	.ProductID          	= 0x1120, 
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
USB_Descriptor_Configuration_t PROGMEM ConfigurationDescriptor =
{
	.Config = 
		{
			.Header                 = {.Size = sizeof(USB_Descriptor_Configuration_Header_t), .Type = DTYPE_Configuration},
			.TotalConfigurationSize = sizeof(USB_Descriptor_Configuration_t),
			.TotalInterfaces        = 2,
				
			.ConfigurationNumber    = 1,
			.ConfigurationStrIndex  = 0x04,
			.ConfigAttributes 	= 0x80,
			
			.MaxPowerConsumption    = USB_CONFIG_POWER_MA(480)
		},
		
	.HID_Interface = 
		{
			.Header                 = {.Size = sizeof(USB_Descriptor_Interface_t), .Type = DTYPE_Interface},

			.InterfaceNumber        = 0,
			.AlternateSetting       = 0,
		
			.TotalEndpoints         = 2,
				
			.Class                  = 0x03,
			.SubClass               = 0x00,
			.Protocol               = 0x00,
				
			.InterfaceStrIndex      = NO_DESCRIPTOR
		},

	.HID_F1HID = 
		{
			.Header 		= {.Size = sizeof(USB_HID_Descriptor_t), .Type = DTYPE_HID},
			.HIDSpec                = VERSION_BCD(01.10),
                        .CountryCode            = 0x00,
                        .TotalReportDescriptors = 1,
                        .HIDReportType          = DTYPE_Report,
                        .HIDReportLength        = sizeof(F1Report)

		},
	
	.HID_ReportINEndpoint =
                {
                        .Header                 = {.Size = sizeof(USB_Descriptor_Endpoint_t), .Type = DTYPE_Endpoint},
                        .EndpointAddress        = (ENDPOINT_DESCRIPTOR_DIR_IN | F1_EPNUM),
                        .Attributes             = (EP_TYPE_INTERRUPT | ENDPOINT_ATTR_NO_SYNC | ENDPOINT_USAGE_DATA),
                        .EndpointSize           = F1_EPSIZE,
                        .PollingIntervalMS      = 0x04
                },

	.HID_ReportOUTEndpoint =
                {
                        .Header                 = {.Size = sizeof(USB_Descriptor_Endpoint_t), .Type = DTYPE_Endpoint},
                        .EndpointAddress        = (ENDPOINT_DESCRIPTOR_DIR_OUT | F1_EPNUM),
                        .Attributes             = (EP_TYPE_INTERRUPT | ENDPOINT_ATTR_NO_SYNC | ENDPOINT_USAGE_DATA),
                        .EndpointSize           = F1_EPSIZE,
                        .PollingIntervalMS      = 0x04
                },

	.DFU_Interface = 
		{
			.Header = {.Size = sizeof(USB_Descriptor_Interface_t), .Type = DTYPE_Interface},
			.InterfaceNumber = 1,
			.AlternateSetting = 0,
			.TotalEndpoints = 0,
			.Class = 0xFE,
			.SubClass = 0x01,
			.Protocol = 0x01,
			.InterfaceStrIndex = 0x05 
		},
	.DFU_Functional =
		{
			.Header = {.Size = sizeof(USB_Descriptor_DFU_Functional_t), .Type = DTYPE_DFUFunctional},
			.Attributes = 0x07,	/* CanDnload, CanUpload, ManifestationTolerant */ 
			.DetachTimeout = 0x00FA,
			.TransferSize = 0x0040,
			/* .DFUSpecification = VERSION_BCD(01.10) */
		}	

};

/** 
 *  Descriptor Strings 
 */
USB_Descriptor_String_t PROGMEM LanguageString =
{
	.Header                 = {.Size = USB_STRING_LEN(1), .Type = DTYPE_String},
		
	.UnicodeString          = {LANGUAGE_ID_ENG}
};

USB_Descriptor_String_t PROGMEM ManufacturerString =
{
	.Header                 = {.Size = USB_STRING_LEN(18), .Type = DTYPE_String},
	.UnicodeString          = L"Native Instruments"
};

USB_Descriptor_String_t PROGMEM ProductString =
{
	.Header = {.Size = USB_STRING_LEN(18), .Type = DTYPE_String},
	.UnicodeString = L"Traktor Kontrol F1"
};

USB_Descriptor_String_t PROGMEM SerialString = 
{
	.Header = {.Size = USB_STRING_LEN(8), .Type = DTYPE_String},
	.UnicodeString = L"FA52C4D0"
};

USB_Descriptor_String_t PROGMEM ConfigurationString = 
{
	.Header = {.Size = USB_STRING_LEN(22), .Type = DTYPE_String},
	.UnicodeString = L"Traktor Kontrol F1 HID"
};

USB_Descriptor_String_t PROGMEM DFUString = 
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
                                    void** const DescriptorAddress)
{
	const uint8_t  DescriptorType   = (wValue >> 8);
	const uint8_t  DescriptorNumber = (wValue & 0xFF);

	void*    Address = NULL;
	uint16_t Size    = NO_DESCRIPTOR;

	switch (DescriptorType)
	{
		case DTYPE_Device: 
			Address = (void*)&DeviceDescriptor;
			Size    = sizeof(USB_Descriptor_Device_t);
			break;
		case DTYPE_Configuration: 
			Address = (void*)&ConfigurationDescriptor;
			Size    = sizeof(USB_Descriptor_Configuration_t);
			break;
		case DTYPE_String: 
			switch (DescriptorNumber)
			{
				case 0x00: 
					Address = (void*)&LanguageString;
					Size    = pgm_read_byte(&LanguageString.Header.Size);
					break;
				case 0x01: 
					Address = (void*)&ManufacturerString;
					Size    = pgm_read_byte(&ManufacturerString.Header.Size);
					break;
				case 0x02: 
					Address = (void*)&ProductString;
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
		case DTYPE_HID:
                        Address = (void*)&ConfigurationDescriptor.HID_F1HID;
                        Size    = sizeof(USB_HID_Descriptor_t);
                        break;
                case DTYPE_Report:
                        Address = (void*)&F1Report;
                        Size    = sizeof(F1Report);
                        break;
	
	}
	
	*DescriptorAddress = Address;
	return Size;
}
