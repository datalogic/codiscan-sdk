package com.datalogic.codiscan.listeners

/** Interface to receive connect callbacks. */
interface ConnectListener {
    /** Callback function triggered when a CODiScan device is connected with the Android device. */
    fun onConnect()
}