package com.datalogic.codiscansdk;

import com.datalogic.codiscansdk.InternalDeviceData;

interface IDeviceDetailsListener {
    void onDeviceDetails(in InternalDeviceData deviceData);
}