package com.datalogic.codiscan.listeners

import com.datalogic.codiscan.dataclasses.PairingData

/** Interface to receive pairing code callback. */
interface PairingCodeListener {
    /**
     * Callback function triggered after CodiscanSDK.DeviceManager.triggerPairingObject is called,
     * when a pairing code is received from the CODiScan service.
     * @param pairingData The latest pairing code data received from the CODiScan SDK Service.
     */
    fun onPairingCode(pairingData: PairingData)
}