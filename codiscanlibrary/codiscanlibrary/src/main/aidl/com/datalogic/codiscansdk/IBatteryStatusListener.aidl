package com.datalogic.codiscansdk;

import com.datalogic.codiscansdk.InternalBatteryData;

interface IBatteryStatusListener {
    void onBatteryStatus(in InternalBatteryData batteryData);
}