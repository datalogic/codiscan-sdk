package com.datalogic.codiscansdk

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * PairingData is data class containing information about the CODiScan pairing code.
 */
@Parcelize
data class InternalPairingData(
    val pairingCode: String, val bitmap: Bitmap
) : Parcelable