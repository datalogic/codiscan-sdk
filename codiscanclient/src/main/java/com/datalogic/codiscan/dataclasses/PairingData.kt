package com.datalogic.codiscan.dataclasses

import android.graphics.Bitmap

/**
 * Contains a pairing code formatted both as a string, and a Bitmap of a Data Matrix code.
 * @property pairingCode A pairing code to connect a CODiScan to a mobile device.
 * @property bitmap The pairing code formatted into a Bitmap of a Data Matrix code.
 */
data class PairingData(
    val pairingCode: String,
    val bitmap: Bitmap
)