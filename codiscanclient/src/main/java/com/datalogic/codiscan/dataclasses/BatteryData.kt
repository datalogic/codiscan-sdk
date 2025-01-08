package com.datalogic.codiscan.dataclasses

import com.datalogic.codiscansdk.BatteryProfile
import com.datalogic.codiscansdk.BatteryStatus

/**
 * Contains information regarding the connected CODiScan's battery.
 * @property batteryCharge The charge of the battery as an integer, representing percentage of remaining charge.
 * @property batteryProfile The battery profile, 0 -> Performance, 1 -> Health, 2 -> Save.
 * @property batteryStatus The battery status, 0 -> Charging, 1 -> Discharging.
 * @property batteryTemperature The temperature of the battery by tenths of a degree in Celsius. 101 = 10.1 C
 * @property batteryCycleCount The battery cycle count.
 * @property batteryHealth The health of the battery, given as an integer.
 */
data class BatteryData(
    val batteryCharge: Int,
    val batteryProfile: BatteryProfile,
    val batteryStatus: BatteryStatus,
    val batteryTemperature: String,
    val batteryCycleCount: Int,
    val batteryHealth: Int
)