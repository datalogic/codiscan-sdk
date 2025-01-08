package com.datalogic.codiscan.constants

/** Return code represents that a function call was successful. */
const val SUCCESS = 0
/** Return code when a function call failed because there is no CODiScan device paired with the mobile device. */
const val DISCONNECT_ERROR = 1
/** Return code when the CodiscanService failed to register a listener. */
const val LISTENER_NOT_ABLE_TO_REGISTERED_ERROR = 2
/** Return code when the CodiscanService failed to de-register a listener. */
const val LISTENER_NOT_ABLE_TO_REMOVE_ERROR = 3
/** Return code when no configuration properties are given to ConfigurationManager's set or get methods. */
const val NO_CONFIG_ADDED_ERROR = 4
/** Return code represents that a function call failed because the CODiScan device is already paired with the mobile device. */
const val ALREADY_CONNECTED = 5
/** Return code when a function is called, but the CodiscanService is not bound to the Codiscan class. */
const val SDK_NOT_BOUND_ERROR = 6
/** Return code that represents an unexpected internal error of the CODiScan SDK has occurred. */
const val INTERNAL_ERROR = 7
/** Return code for when a given configuration property does not have a valid value. */
const val BAD_PROPERTY = 8