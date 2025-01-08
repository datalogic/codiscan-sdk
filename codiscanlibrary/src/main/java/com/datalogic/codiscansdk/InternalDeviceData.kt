package com.datalogic.codiscansdk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * DeviceData is data class to fetch device details from CODiScan.
 */
@Parcelize
data class InternalDeviceData(
    val deviceType: String, val fwVersion: String, val deviceId: String
) : Parcelable