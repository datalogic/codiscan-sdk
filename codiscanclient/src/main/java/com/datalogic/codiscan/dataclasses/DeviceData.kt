package com.datalogic.codiscan.dataclasses

/**
 * Contains identifying information and firmware version regarding the connected CODiScan.
 * @property deviceType The human readable model name of the handheld device.
 * @property fwVersion The firmware version of the device.
 * @property deviceId The ID of the device.
 */
data class DeviceData(
    val deviceType: String,
    val fwVersion: String,
    val deviceId: String
)