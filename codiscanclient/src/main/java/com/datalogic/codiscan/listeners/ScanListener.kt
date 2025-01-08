package com.datalogic.codiscan.listeners

import com.datalogic.codiscan.dataclasses.ScanData

/** Interface to receive scan callbacks. */
interface ScanListener {
    /**
     * Callback function triggered after a barcode is scanned from the connected CODiScan device.
     * @param scanData Retrieved scan data from the connected CODiScan device.
     */
    fun onScan(scanData: ScanData)
}