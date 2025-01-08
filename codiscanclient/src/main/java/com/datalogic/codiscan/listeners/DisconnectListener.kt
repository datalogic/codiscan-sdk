package com.datalogic.codiscan.listeners

/** Interface to receive disconnect callbacks. */
interface DisconnectListener {
    /** Callback function triggered when a CODiScan device is disconnected from the Android device. */
    fun onDisconnect()
}