package com.datalogic.codiscansdk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * ScanData is data class used to fetch barcode scan data from CODiScan.
 */
@Parcelize
data class InternalScanData(
    val rawData: ByteArray,
    val barcodeData: String,
    val barcodeID: String
) : Parcelable