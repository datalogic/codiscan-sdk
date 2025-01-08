package com.datalogic.codiscan.listeners

import com.datalogic.codiscan.dataclasses.DeviceData

/** Interface to receive device details callbacks. */
interface DeviceDetailsListener {
    /**
     * Callback function triggered after CodiscanSDK.DeviceManager.triggerDeviceDetails is called,
     * when device data is received from the connected CODiScan device.
     * @param deviceData Retrieved device data from the connected CODiScan device.
     */
    fun onDeviceDetails(deviceData: DeviceData)
}