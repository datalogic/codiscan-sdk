package com.datalogic.codiscansdk;

import com.datalogic.codiscansdk.InternalPairingData;

interface IPairingCodeListener {
    void onPairingCode(in InternalPairingData pairingData);
}