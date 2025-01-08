package com.datalogic.codiscan.listeners

import com.datalogic.codiscan.dataclasses.BatteryData

/** Interface to receive battery status callbacks. */
interface BatteryStatusListener {
    /**
     * Callback function triggered after CodiscanSDK.DeviceManager.triggerBatteryStatus is called,
     * when battery data is received from the connected CODiScan device.
     * @param batteryData Retrieved battery data from the connected CODiScan device.
     */
    fun onBatteryStatus(batteryData: BatteryData)
}