package com.datalogic.codiscansdk;

import com.datalogic.codiscansdk.InternalScanData;

interface IScanListener {
    void onScan(in InternalScanData scanData);
}