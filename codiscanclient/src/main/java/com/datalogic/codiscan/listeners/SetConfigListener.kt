package com.datalogic.codiscan.listeners

/** Interface to receive set configuration values callback. */
interface SetConfigListener {
    /**
     * Callback function triggered after CodiscanSDK.ConfigurationManager.set is called, when
     * status is received, regarding applying new configuration values, from the connected CODiScan device.
     * @param status Status code received for setting configurations:
     *  * 0 -> Success, all configuration values applied.
     *  * 1 -> Some values applied, some failed to apply,
     *  * 2 -> Failed to apply any given configuration values.
     * @param message Success/error message from applying new configuration values.
     */
    fun onSetConfig(status: Int, message: String)
}