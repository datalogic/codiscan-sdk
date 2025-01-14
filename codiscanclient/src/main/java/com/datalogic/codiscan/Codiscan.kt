package com.datalogic.codiscan

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import com.datalogic.codiscan.constants.BAD_PROPERTY
import com.datalogic.codiscan.constants.INTERNAL_ERROR
import com.datalogic.codiscan.constants.SDK_NOT_BOUND_ERROR
import com.datalogic.codiscan.constants.SUCCESS
import com.datalogic.codiscan.dataclasses.BatteryData
import com.datalogic.codiscan.dataclasses.DeviceData
import com.datalogic.codiscan.dataclasses.PairingData
import com.datalogic.codiscan.dataclasses.ScanData
import com.datalogic.codiscan.listeners.BatteryStatusListener
import com.datalogic.codiscan.listeners.ConnectListener
import com.datalogic.codiscan.listeners.DeviceDetailsListener
import com.datalogic.codiscan.listeners.DisconnectListener
import com.datalogic.codiscan.listeners.GetConfigListener
import com.datalogic.codiscan.listeners.PairingCodeListener
import com.datalogic.codiscan.listeners.ScanListener
import com.datalogic.codiscan.listeners.SetConfigListener
import com.datalogic.codiscansdk.IBatteryStatusListener
import com.datalogic.codiscansdk.ICodiScanSDK
import com.datalogic.codiscansdk.IConnectListener
import com.datalogic.codiscansdk.IDeviceDetailsListener
import com.datalogic.codiscansdk.IDisconnectListener
import com.datalogic.codiscansdk.IGetConfigListener
import com.datalogic.codiscansdk.IPairingCodeListener
import com.datalogic.codiscansdk.IScanListener
import com.datalogic.codiscansdk.ISetConfigListener
import com.datalogic.codiscansdk.InternalBatteryData
import com.datalogic.codiscansdk.InternalDeviceData
import com.datalogic.codiscansdk.InternalPairingData
import com.datalogic.codiscansdk.InternalScanData
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking
import java.nio.charset.Charset

/** Manages connection to the CodiscanService and as a wrapper to CodiscanService's implementation of the CodiscanSDK. */
class Codiscan {
    /** Tag for logging purposes. */
    private val tag = "CODISCAN_SDK"
    /** CODiScan SDK interface to bind with the CODiScan service. */
    private var codiscanInterface: ICodiScanSDK? = null
    /** List of [BatteryStatusListener]s to receive battery status callbacks from the connected CODiScan. */
    private val batteryStatusListenerList: MutableList<BatteryStatusListener> = mutableListOf()
    /** List of [ConnectListener]s to receive connect callbacks from the CODiScan service. */
    private val connectListenerList: MutableList<ConnectListener> = mutableListOf()
    /** List of [DeviceDetailsListener]s to receive device details callbacks from the connected CODiScan. */
    private val deviceDetailsListenerList: MutableList<DeviceDetailsListener> = mutableListOf()
    /** List of [DisconnectListener]s to receive disconnect callbacks from the CODiScan service. */
    private val disconnectListenerList: MutableList<DisconnectListener> = mutableListOf()
    /** List of [GetConfigListener]s to receive get configuration callbacks from the connected CODiScan. */
    private val getConfigListenerList: MutableList<GetConfigListener> = mutableListOf()
    /** List of [PairingCodeListener]s to receive pairing code callbacks from the CODiScan service. */
    private val pairingCodeListenerList: MutableList<PairingCodeListener> =  mutableListOf()
    /** List of [ScanListener]s to receive scan data callbacks from the connected CODiScan. */
    private val scanListenerList: MutableList<ScanListener> = mutableListOf()
    /** List of [SetConfigListener]s to receive set configuration callbacks from the connected CODiScan. */
    private val setConfigListenerList: MutableList<SetConfigListener> = mutableListOf()
    /** Local copy of callback passed in from [bindService]. Invoked by [mConnection] when CODiScan service connects.  */
    private var sdkBindListener: () -> Unit? = {}
    /** Internal listener to receive battery status callbacks from CODiScan service. */
    private var clientBatteryStatusListener: ClientBatteryStatusListener? = null
    /** Internal listener to receive connect callbacks from CODiScan service. */
    private var clientConnectListener: ClientConnectListener? = null
    /** Internal listener to receive device data callbacks from CODiScan service. */
    private var clientDeviceDetailsListener: ClientDeviceDetailsListener? = null
    /** Internal listener to receive disconnect callbacks from CODiScan service. */
    private var clientDisconnectListener: ClientDisconnectListener? = null
    /** Internal listener to receive get config callbacks from CODiScan service. */
    private var clientGetConfigListener: ClientGetConfigListener? = null
    /** Internal listener to receive pairing code callbacks from CODiScan service. */
    private var clientPairingCodeListener: ClientPairingCodeListener? = null
    /** Internal listener to receive scan callbacks from CODiScan service. */
    private var clientScanListener: ClientScanListener? = null
    /** Internal listener to receive set config callbacks from CODiScan service. */
    private var clientSetConfigListener: ClientSetConfigListener? = null
    /** Instance of the [DeviceManager] class. */
    val deviceManager = DeviceManager()
    /** Instance of the [ConfigurationManager] class. */
    val configurationManager = ConfigurationManager()
    /** Internal listener that handles the binding to the CODiScan service. */
    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            codiscanInterface = ICodiScanSDK.Stub.asInterface(service)
            sdkBindListener.invoke()
        }
        override fun onServiceDisconnected(name: ComponentName) {
            codiscanInterface = null
        }
    }

    /**
     * Binds a given Context to the CODiScan service.
     * NOTE: For the CODiScan SDK to function, it must first bind to the CODiScan service.
     * @param ctx the given Context to which the CODiScan service is to be bound to.
     * @param callback called when the CODiScan service is connected and bound to the given Context.
     */
    fun bindService(ctx: Context, callback: () -> Unit = {}) {
        sdkBindListener = callback
        ctx.bindService(
            Intent("com.datalogic.codiscanservice.service.CodiScanService.BIND")
                .setPackage("com.datalogic.codiscanservice"),
            mConnection,
            ComponentActivity.BIND_AUTO_CREATE
        )
    }

    /**
     * Unbind the CODiScan service from the given Context.
     * @param ctx the given Context to which the CODiScan service is to be unbound from.
     */
    fun unbindService(ctx: Context) {
        ctx.unbindService(mConnection)
        sdkBindListener = {}
    }

    /**
     * Management class for the CODiScan device. Used to pair with a CODiScan device, request
     * CODiScan device data, and trigger the findMe function.
     */
    inner class DeviceManager {

        /**
         * Trigger the CODiScan service to send a pairing code to connect a CODiScan device by
         * invoking any registered [PairingCodeListener]'s onPairingCode callback.
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 5 -> Fail, already connected.
         *  * 6 -> Fail, CODiScan service unbound.
         */
        fun triggerPairingObject(): Int {
            return if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.deviceManager()?.triggerPairingObject() ?: INTERNAL_ERROR
        }

        /**
         * Disconnect the connected CODiScan device. Will invoke any registered [DisconnectListener]s.
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 1 -> Fail, CODiScan device already disconnected.
         *  * 6 -> Fail, CODiScan service unbound.
         */
        fun triggerDisconnect(): Int {
            return if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.deviceManager()?.triggerDisconnect() ?: INTERNAL_ERROR
        }

        /**
         * Trigger the connected CODiScan device to send battery data by invoking
         * any registered [BatteryStatusListener]'s onBatteryStatus callback.
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 1 -> Fail, CODiScan device disconnected.
         *  * 6 -> Fail, CODiScan service unbound.
         */
        fun triggerBatteryStatus(): Int {
            return if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.deviceManager()?.triggerBatteryStatus() ?: INTERNAL_ERROR
        }

        /**
         * Trigger the connected CODiScan device to send device data by invoking
         * any registered [DeviceDetailsListener]'s onDeviceDetails callback.
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 1 -> Fail, CODiScan device disconnected.
         *  * 6 -> Fail, CODiScan service unbound.
         */
        fun triggerDeviceDetails(): Int{
            return if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.deviceManager()?.triggerDeviceDetails() ?: INTERNAL_ERROR
        }

        /**
         * Send the "Find My Device" command to the connected CODiScan. Causes the devices to flash and beep.
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 1 -> Fail, CODiScan device disconnected.
         *  * 6 -> Fail, CODiScan service unbound.
         */
        fun findMyDevice(): Int {
            return if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.deviceManager()?.findMyDevice() ?: INTERNAL_ERROR
        }

        /**
         * Register a given [ConnectListener] to receive onConnect callbacks when a CODiScan device
         * pairs with the mobile device.
         * @param listener the given [ConnectListener].
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 2 -> Fail, unable to register listener.
         *  * 6 -> Fail, CODiScan service unbound.
         */
        fun registerConnectListener(listener: ConnectListener): Int {
            connectListenerList.add(listener)
            if(clientConnectListener == null){
                clientConnectListener = ClientConnectListener()
            }
            return if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.deviceManager()?.registerConnectListener(clientConnectListener) ?: INTERNAL_ERROR
        }

        /**
         * Remove a given [ConnectListener] to stop receiving onConnect callbacks.
         * @param listener the given [ConnectListener].
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 2 -> Fail, unable to register listener.
         *  * 6 -> Fail, CODiScan service unbound.
         */
        fun removeConnectListener(listener: ConnectListener): Int {
            connectListenerList.remove(listener)
            if(connectListenerList.size == 0){
                val result = if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.deviceManager()?.removeConnectListener(clientConnectListener) ?: INTERNAL_ERROR
                clientConnectListener = null
                return result
            }
            return SUCCESS
        }

        /**
         * Register a given [DisconnectListener] to receive onDisconnect callbacks when a CODiScan
         * device disconnects from the mobile device.
         * @param listener the given [DisconnectListener].
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 2 -> Fail, unable to register listener.
         *  * 6 -> Fail, CODiScan service unbound.
         */
        fun registerDisconnectListener(listener: DisconnectListener): Int {
            disconnectListenerList.add(listener)
            if(clientDisconnectListener == null){
                clientDisconnectListener = ClientDisconnectListener()
            }
            return if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.deviceManager()?.registerDisconnectListener(clientDisconnectListener) ?: INTERNAL_ERROR
        }

        /**
         * Remove a given [DisconnectListener] to stop receiving onDisconnect callbacks.
         * @param listener the given [DisconnectListener].
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 2 -> Fail, unable to register listener.
         *  * 6 -> Fail, CODiScan service unbound.
         */
        fun removeDisconnectListener(listener: DisconnectListener): Int {
            disconnectListenerList.remove(listener)
            if(connectListenerList.size == 0){
                val result = if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.deviceManager()?.removeDisconnectListener(clientDisconnectListener) ?: INTERNAL_ERROR
                clientDisconnectListener = null
                return result
            }
            return SUCCESS
        }

        /**
         * Register a given [PairingCodeListener] to receive onPairingCode callbacks when a new
         * pairing code is sent from the CODiScan service.
         * @param listener the given [PairingCodeListener].
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 2 -> Fail, unable to register listener.
         *  * 6 -> Fail, CODiScan service unbound.
         */
        fun registerPairingCodeListener(listener: PairingCodeListener): Int {
            pairingCodeListenerList.add(listener)
            if(clientPairingCodeListener == null){
                clientPairingCodeListener = ClientPairingCodeListener()
            }
            return if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.deviceManager()?.registerPairingCodeListener(clientPairingCodeListener) ?: INTERNAL_ERROR
        }

        /**
         * Remove a given [PairingCodeListener] to stop receiving onPairingCode callbacks.
         * @param listener the given [PairingCodeListener].
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 3 -> Fail, unable to remove listener.
         *  * 6 -> Fail, CODiScan service unbound.
         */
        fun removePairingCodeListener(listener: PairingCodeListener?): Int {
            pairingCodeListenerList.remove(listener)
            if(pairingCodeListenerList.size == 0){
                val result = if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.deviceManager()?.removePairingCodeListener(clientPairingCodeListener) ?: INTERNAL_ERROR
                clientPairingCodeListener = null
                return result
            }
            return SUCCESS
        }

        /**
         * Register a given [ScanListener] to receive onScan callbacks when a barcode is scanned
         * by the connected CODiScan device.
         * @param listener the given [ScanListener].
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 3 -> Fail, unable to remove listener.
         *  * 6 -> Fail, CODiScan service unbound.
         */
        fun registerScanListener(listener: ScanListener): Int {
            scanListenerList.add(listener)
            if(clientScanListener == null){
                clientScanListener = ClientScanListener()
            }
            return if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.deviceManager()?.registerScanListener(clientScanListener) ?: INTERNAL_ERROR
        }

        /**
         * Remove a given [ScanListener] to stop receiving onScan callbacks.
         * @param listener the given [ScanListener].
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 3 -> Fail, unable to remove listener.
         *  * 6 -> Fail, CODiScan service unbound.
         */
        fun removeScanListener(listener: ScanListener): Int {
            scanListenerList.remove(listener)
            if(scanListenerList.size == 0){
                val result = if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.deviceManager()?.removeScanListener(clientScanListener) ?: INTERNAL_ERROR
                clientScanListener = null
                return result
            }
            return SUCCESS
        }

        /**
         * Register a given [BatteryStatusListener] to receive onBatteryStatus callbacks when battery
         * data is received from the connected CODiScan device. Triggered by calling [triggerBatteryStatus].
         * @param listener the given [BatteryStatusListener].
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 3 -> Fail, unable to remove listener.
         *  * 6 -> Fail, CODiScan service unbound.
         */
        fun registerBatteryStatusListener(listener: BatteryStatusListener): Int {
            batteryStatusListenerList.add(listener)
            if(clientBatteryStatusListener == null){
                clientBatteryStatusListener = ClientBatteryStatusListener()
            }
            return if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.deviceManager()?.registerBatteryStatusListener(clientBatteryStatusListener) ?: INTERNAL_ERROR
        }

        /**
         * Remove a given [BatteryStatusListener] to stop receiving onBatteryStatus callbacks.
         * @param listener the given [BatteryStatusListener].
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 3 -> Fail, unable to remove listener.
         *  * 6 -> Fail, CODiScan service unbound.
         */
        fun removeBatteryStatusListener(listener: BatteryStatusListener): Int {
            batteryStatusListenerList.remove(listener)
            if(batteryStatusListenerList.size == 0){
                val result = if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.deviceManager()?.removeBatteryStatusListener(clientBatteryStatusListener) ?: INTERNAL_ERROR
                clientBatteryStatusListener = null
                return result
            }
            return SUCCESS
        }

        /**
         * Register a given [DeviceDetailsListener] to receive onDeviceDetails callbacks when device
         * data is received from the connected CODiScan device. Triggered by calling [triggerDeviceDetails].
         * @param listener the given [DeviceDetailsListener].
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 3 -> Fail, unable to remove listener.
         *  * 6 -> Fail, CODiScan service unbound.
         */
        fun registerDeviceDetailsListener(listener: DeviceDetailsListener): Int {
            deviceDetailsListenerList.add(listener)
            if(clientDeviceDetailsListener == null){
                clientDeviceDetailsListener = ClientDeviceDetailsListener()
            }
            return if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.deviceManager()?.registerDeviceDetailsListener(clientDeviceDetailsListener) ?: INTERNAL_ERROR
        }

        /**
         * Remove a given [DeviceDetailsListener] to stop receiving onDeviceDetails callbacks.
         * @param listener the given [DeviceDetailsListener].
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 3 -> Fail, unable to remove listener.
         *  * 6 -> Fail, CODiScan service unbound.
         */
        fun removeDeviceDetailsListener(listener: DeviceDetailsListener): Int {
            deviceDetailsListenerList.remove(listener)
            if(deviceDetailsListenerList.size == 0){
                val result = if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.deviceManager()?.removeDeviceDetailsListener(clientDeviceDetailsListener) ?: INTERNAL_ERROR
                clientDeviceDetailsListener = null
                return result
            }
            return SUCCESS
        }
    }

    /** Management class to set/get configuration values of a connected CODiScan device. */
    inner class ConfigurationManager {

        /**
         * Set given integer and/or string properties of the connected CODiScan device.
         * @param ints Map of the given integer property ID's and values.
         * @param strings Map of the given string property ID's and values.
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 1 -> Fail, CODiScan device disconnected.
         *  * 4 -> Fail, no configuration values to set.
         *  * 6 -> Fail, CODiScan service unbound.
         *  * 8 -> Fail, bad configuration values given.
         */
        fun set(ints: HashMap<Int, Int>, strings: HashMap<Int, String>): Int {
            val bundleInts = Bundle()
            val bundleStrings = Bundle()
            for ((key, value) in ints.entries) {
                if(!isIntegerProperty(key)){
                    Log.e(tag, "PropertyID $key does not represent an integer property.")
                    return BAD_PROPERTY
                }
                bundleInts.putInt(key.toString(), value)
            }
            for ((key, value) in strings.entries) {
                if((isStringLabelProperty(key) && value.length > 3) || isStringAppendProperty(key) && value.length > 20){
                    Log.e(tag, "PropertyID $key value of $value has string length of ${value.length}, which is too long")
                    return BAD_PROPERTY
                }
                bundleStrings.putString(key.toString(), value.toHex().padEnd(if(isStringLabelProperty(key)) 6 else 40, '0'))
            }
            return if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.configurationManager()?.set(bundleInts, bundleStrings) ?: INTERNAL_ERROR
        }

        /**
         * Get values for the given property IDs from the connected CODiScan device.
         * @param ids the given Property IDs to retrieve the values of.
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 1 -> Fail, CODiScan device disconnected.
         *  * 4 -> Fail, no configuration values to get.
         *  * 6 -> Fail, CODiScan service unbound.
         *  * 8 -> Fail, bad configuration values given.
         */
        fun get(ids: IntArray): Int {
            if(ids.min() < 1 || ids.max() > 299){
                Log.e(tag, "Passed in IntArray contains an invalid PropertyID.")
                return BAD_PROPERTY
            }
            return if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.configurationManager()?.get(ids) ?: INTERNAL_ERROR
        }

        /**
         * Register a given [GetConfigListener] to receive onGetConfig callbacks when property values
         * are received from the connected CODiScan device. Triggered by [get].
         * @param listener the given [GetConfigListener].
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 2 -> Fail, unable to register listener.
         *  * 6 -> Fail, CODiScan service unbound.
         */
        fun registerGetListener(listener: GetConfigListener): Int {
            getConfigListenerList.add(listener)
            if(clientGetConfigListener == null){
                clientGetConfigListener = ClientGetConfigListener()
            }
            return if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.configurationManager()?.registerGetListener(clientGetConfigListener) ?: INTERNAL_ERROR
        }

        /**
         * Remove a given [GetConfigListener] to stop receiving onGetConfig callbacks.
         * @param listener the given [GetConfigListener].
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 3 -> Fail, unable to remove listener.
         *  * 6 -> Fail, CODiScan service unbound.
         */
        fun removeGetListener(listener: GetConfigListener): Int{
            getConfigListenerList.remove(listener)
            if(getConfigListenerList.size == 0){
                val result = if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.configurationManager()?.removeGetListener(clientGetConfigListener) ?: INTERNAL_ERROR
                clientGetConfigListener = null
                return result
            }
            return SUCCESS
        }

        /**
         * Register a given [SetConfigListener] to receive onSetConfig callbacks when status has been received
         * from applying property values of the connected CODiScan device. Triggered by [set].
         * @param listener the given [SetConfigListener].
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 2 -> Fail, unable to register listener.
         *  * 6 -> Fail, CODiScan service unbound.
         */
        fun registerSetListener(listener: SetConfigListener): Int{
            setConfigListenerList.add(listener)
            if(clientSetConfigListener == null){
                clientSetConfigListener = ClientSetConfigListener()
            }
            return if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.configurationManager()?.registerSetListener(clientSetConfigListener) ?: INTERNAL_ERROR
        }

        /**
         * Remove a given [SetConfigListener] to stop receiving onSetConfig callbacks.
         * @param listener the given [SetConfigListener].
         * @return Status code on success of call:
         *  * 0 -> Success.
         *  * 3 -> Fail, unable to remove listener.
         *  * 6 -> Fail, CODiScan service unbound.
         */
        fun removeSetListener(listener: SetConfigListener): Int{
            setConfigListenerList.remove(listener)
            if(setConfigListenerList.size == 0){
                val result = if(codiscanInterface == null) SDK_NOT_BOUND_ERROR else codiscanInterface?.configurationManager()?.removeSetListener(clientSetConfigListener) ?: INTERNAL_ERROR
                clientSetConfigListener = null
                return result
            }
            return SUCCESS
        }

        /**
         * Checks if a given [PropertyID] is for an integer property.
         * @param id given [PropertyID].
         * @return if the id is for an integer value, true, else false.
         */
        private fun isIntegerProperty(id: Int): Boolean {
            return (id in 1..183) || (id in 186 .. 189) || (id in 276..299)
        }

        /**
         * Checks if a given [PropertyID] is for an string label property.
         * i.e. a string property of length 6.
         * @param id given [PropertyID].
         * @return if the id is for a string label property, true, else false.
         */
        private fun isStringLabelProperty(id: Int): Boolean {
            return (id in 190..275)
        }

        /**
         * Checks if a given [PropertyID] is for an string append property.
         * i.e. a string property of length 40.
         * @param id given [PropertyID].
         * @return if the id is for a string append property, true, else false.
         */
        private fun isStringAppendProperty(id: Int): Boolean {
            return id == 184 || id == 185
        }
    }

    /** Convert a hex string to its ASCII value. Pad with a zero if the string is of odd length. Removes trailing null values. */
    private fun String.decodeHex(): String {
        var output = if(this.length % 2 != 0) "${this}0" else this
        while (output.endsWith("00")){
            output = output.removeSuffix("00")
        }
        return output.chunked(2)
            .map { it.toInt(16).toByte() }
            .toByteArray()
            .toString(Charset.defaultCharset())
    }

    /** Convert a string to its ASCII hex value. */
    private fun String.toHex(): String {
        return map { "${Integer.toHexString(it.code/16)}${Integer.toHexString(it.code%16)}" }.joinToString(separator = "").uppercase()
    }

    /** Client implementation of the CODiScan Service's IBatteryStatusListener AIDL interface. */
    inner class ClientBatteryStatusListener: IBatteryStatusListener.Stub() {
        override fun onBatteryStatus(batteryData: InternalBatteryData?) {
            try {
                runBlocking {
                    batteryStatusListenerList.asFlow().collect { listener ->
                        if(batteryData != null){
                            listener.onBatteryStatus(
                                BatteryData(
                                    batteryData.batteryCharge,
                                    batteryData.batteryProfile,
                                    batteryData.batteryStatus,
                                    batteryData.batteryTemperature,
                                    batteryData.batteryCycleCount,
                                    batteryData.batteryHealth
                                )
                            )
                        }
                    }
                }
            } catch(e: Exception){
                Log.e(tag, "Error from client's onConnect callback.")
                Log.e(tag, "${e.message}")
                Log.e(tag, "${e.printStackTrace()}")
            }
        }
    }

    /** Client implementation of the CODiScan Service's IConnectListener AIDL interface. */
    inner class ClientConnectListener: IConnectListener.Stub() {
        override fun onConnect() {
            try {
                runBlocking {
                    connectListenerList.asFlow().collect { listener ->
                        listener.onConnect()
                    }
                }
            } catch(e: Exception){
                Log.e(tag, "Error from client's onConnect callback.")
                Log.e(tag, "${e.message}")
                Log.e(tag, "${e.printStackTrace()}")
            }
        }
    }

    /** Client implementation of the CODiScan Service's IDeviceDetailsListener AIDL interface. */
    inner class ClientDeviceDetailsListener: IDeviceDetailsListener.Stub() {
        override fun onDeviceDetails(deviceData: InternalDeviceData?) {
            try {
                runBlocking {
                    deviceDetailsListenerList.asFlow().collect { listener ->
                        if(deviceData != null){
                            listener.onDeviceDetails(DeviceData(deviceData.deviceType, deviceData.fwVersion, deviceData.deviceId))
                        }
                    }
                }
            } catch(e: Exception){
                Log.e(tag, "Error from client's onDeviceDetails callback.")
                Log.e(tag, "${e.message}")
                Log.e(tag, "${e.printStackTrace()}")
            }
        }
    }

    /** Client implementation of the CODiScan Service's IDisconnectListener AIDL interface. */
    inner class ClientDisconnectListener: IDisconnectListener.Stub() {
        override fun onDisconnect() {
            try {
                runBlocking {
                    disconnectListenerList.asFlow().collect { listener ->
                        listener.onDisconnect()
                    }
                }
            } catch(e: Exception){
                Log.e(tag, "Error from client's onDisconnect callback.")
                Log.e(tag, "${e.message}")
                Log.e(tag, "${e.printStackTrace()}")
            }
        }
    }

    /** Client implementation of the CODiScan Service's IGetConfigListener AIDL interface. */
    inner class ClientGetConfigListener: IGetConfigListener.Stub() {
        override fun onGetConfig(ints: Bundle?, strings: Bundle?) {
            try {
                val intsHashMap: HashMap<Int, Int> = HashMap()
                val stringsHashMap: HashMap<Int, String> = HashMap()
                ints?.keySet()?.forEach { key ->
                    intsHashMap[key.toInt(radix = 16)] = ints.getInt(key)
                }
                strings?.keySet()?.forEach { key ->
                    stringsHashMap[key.toInt(radix = 16)] = strings.getString(key, "").decodeHex()
                }
                runBlocking {
                    getConfigListenerList.asFlow().collect { listener ->
                        listener.onGetConfig(intsHashMap, stringsHashMap)
                    }
                }
            } catch(e: Exception){
                Log.e(tag, "Error from client's onGetConfig callback.")
                Log.e(tag, "${e.message}")
                Log.e(tag, "${e.printStackTrace()}")
            }
        }
    }

    /** Client implementation of the CODiScan Service's IPairingCodeListener AIDL interface. */
    inner class ClientPairingCodeListener: IPairingCodeListener.Stub() {
        override fun onPairingCode(pairingData: InternalPairingData?) {
            try {
                runBlocking {
                    pairingCodeListenerList.asFlow().collect { listener ->
                        if(pairingData?.pairingCode != null){
                            listener.onPairingCode(PairingData(pairingData.pairingCode, pairingData.bitmap))
                        }
                    }
                }
            } catch(e: Exception){
                Log.e(tag, "Error from client's onPairingCode callback.")
                Log.e(tag, "${e.message}")
                Log.e(tag, "${e.printStackTrace()}")
            }
        }
    }

    /** Client implementation of the CODiScan Service's IScanListener AIDL interface. */
    inner class ClientScanListener: IScanListener.Stub() {
        override fun onScan(scanData: InternalScanData?) {
            try {
                runBlocking {
                    scanListenerList.asFlow().collect { listener ->
                        if(scanData != null){
                            listener.onScan(ScanData(scanData.rawData, scanData.barcodeData, scanData.barcodeID))
                        }
                    }
                }
            } catch(e: Exception){
                Log.e(tag, "Error from client's onScan callback.")
                Log.e(tag, "${e.message}")
                Log.e(tag, "${e.printStackTrace()}")
            }
        }
    }

    /** Client implementation of the CODiScan Service's ISetConfigListener AIDL interface. */
    inner class ClientSetConfigListener: ISetConfigListener.Stub() {
        override fun onSetConfig(status: Int, message: String?) {
            try {
                runBlocking {
                    setConfigListenerList.asFlow().collect { listener ->
                        listener.onSetConfig(status, message ?: "")
                    }
                }
            } catch(e: Exception){
                Log.e(tag, "Error from client's onSetConfig callback.")
                Log.e(tag, "${e.message}")
                Log.e(tag, "${e.printStackTrace()}")
            }
        }
    }
}
