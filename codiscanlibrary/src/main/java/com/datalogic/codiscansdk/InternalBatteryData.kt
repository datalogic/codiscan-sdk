package com.datalogic.codiscansdk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * BatteryData is data class to fetch battery data from CODiScan.
 */
@Parcelize
data class InternalBatteryData(
    var batteryCharge: Int,
    var batteryProfile: BatteryProfile,
    var batteryStatus: BatteryStatus,
    var batteryTemperature: String,
    var batteryCycleCount: Int,
    var batteryHealth: Int
) : Parcelable