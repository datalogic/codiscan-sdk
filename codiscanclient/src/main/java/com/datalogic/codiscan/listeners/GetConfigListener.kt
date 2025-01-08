package com.datalogic.codiscan.listeners

/** Interface to receive get configuration values callback. */
interface GetConfigListener {
    /**
     * Callback function triggered after CodiscanSDK.ConfigurationManager.get is called,
     * when configuration parameter values are received from the connected CODiScan device.
     * @param ints map of configuration parameters values of type integer.
     * @param strings map of configuration parameters values of type string.
     */
    fun onGetConfig(ints: HashMap<Int, Int>, strings: HashMap<Int, String>)
}