package com.datalogic.codiscan.dataclasses

/**
 * Contains information regarding a barcode scanned by the connected CODiScan device.
 * @property rawData The scanned barcode as an array of bytes.
 * @property barcodeData The scanned barcode as a string.
 * @property barcodeID The symbology of the scanned barcode as a human readable string. E.g. "UPCA"
 */
data class ScanData(
    val rawData: ByteArray,
    val barcodeData: String,
    val barcodeID: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScanData

        if (!rawData.contentEquals(other.rawData)) return false
        if (barcodeData != other.barcodeData) return false
        if (barcodeID != other.barcodeID) return false

        return true
    }

    override fun hashCode(): Int {
        var result = rawData.contentHashCode()
        result = 31 * result + barcodeData.hashCode()
        result = 31 * result + barcodeID.hashCode()
        return result
    }
}