package com.datalogic.codiscansdk;

import com.datalogic.codiscansdk.IConnectListener;
import com.datalogic.codiscansdk.IDisconnectListener;
import com.datalogic.codiscansdk.IPairingCodeListener;
import com.datalogic.codiscansdk.IScanListener;
import com.datalogic.codiscansdk.IBatteryStatusListener;
import com.datalogic.codiscansdk.IDeviceDetailsListener;

interface IDeviceManager {
    int triggerPairingObject();

    int triggerDisconnect();

    int triggerBatteryStatus();

    int triggerDeviceDetails();

    int findMyDevice();

    int registerConnectListener(IConnectListener listener);

    int removeConnectListener(IConnectListener listener);

    int registerDisconnectListener(IDisconnectListener listener);

    int removeDisconnectListener(IDisconnectListener listener);

    int registerPairingCodeListener(IPairingCodeListener listener);

    int removePairingCodeListener(IPairingCodeListener listener);

    int registerScanListener(IScanListener listener);

    int removeScanListener(IScanListener listener);

    int registerBatteryStatusListener(IBatteryStatusListener listener);

    int removeBatteryStatusListener(IBatteryStatusListener listener);

    int registerDeviceDetailsListener(IDeviceDetailsListener listener);

    int removeDeviceDetailsListener(IDeviceDetailsListener listener);
}