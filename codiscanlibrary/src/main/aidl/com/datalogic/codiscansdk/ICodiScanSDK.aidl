package com.datalogic.codiscansdk;

import com.datalogic.codiscansdk.IDeviceManager;
import com.datalogic.codiscansdk.IConfigurationManager;

interface ICodiScanSDK {
    IDeviceManager deviceManager();

    IConfigurationManager configurationManager();

    int getVersion();
}