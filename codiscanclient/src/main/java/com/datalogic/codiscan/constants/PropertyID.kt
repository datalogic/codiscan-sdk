package com.datalogic.codiscan.constants

/** ID values associated with configuration properties of the CODiScan device. */
class PropertyID {
    companion object {
        const val ABC_CODABAR_CONCATENATION_ENABLE = 0x0001
        const val ABC_CODABAR_CONCATENATION_MODE = 0x0002
        const val ABC_CODABAR_DYNAMIC_CONCATENATION_TIMEOUT = 0x0003
        const val ABC_CODABAR_FORCE_CONCATENATION_ENABLE = 0x0004
        const val AZTEC_ENABLE = 0x0005
        const val AZTEC_LENGTH_CONTROL = 0x0006
        const val AZTEC_LENGTH_1 = 0x0007
        const val AZTEC_LENGTH_2 = 0x0008
        const val BC412_ENABLE = 0x0009
        const val BC412_CHECK_CHARACTER_CALCULATION = 0x000A
        const val BC412_LENGTH_CONTROL = 0x000B
        const val BC412_LENGTH_1 = 0x000C
        const val BC412_LENGTH_2 = 0x000D
        const val COMPRESSED_2OF5_ENABLE = 0x000E
        const val COMPRESSED_2OF5_CHECK_CHARACTER_CALCULATION = 0x000F
        const val COMPRESSED_2OF5_CHECK_CHARACTER_TRANSMISSION = 0x0010
        const val COMPRESSED_2OF5_LENGTH_CONTROL = 0x0011
        const val COMPRESSED_2OF5_LENGTH_1 = 0x0012
        const val COMPRESSED_2OF5_LENGTH_2 = 0x0013
        const val CHINA_SENSIBLE_CODE_ENABLE = 0x0014
        const val CHINA_SENSIBLE_LENGTH_CONTROL = 0x0015
        const val CHINA_SENSIBLE_LENGTH_1 = 0x0016
        const val CHINA_SENSIBLE_LENGTH_2 = 0x0017
        const val CODABAR_ENABLE = 0x0018
        const val CODABAR_CHECK_CHARACTER_CALCULATION = 0x0019
        const val CODABAR_CHECK_CHARACTER_TRANSMISSION = 0x001A
        const val CODABAR_START_STOP_CHARACTER_TRANSMISSION = 0x001B
        const val CODABAR_START_STOP_CHARACTER_SET = 0x001C
        const val CODABAR_START_STOP_CHAR_MATCH = 0x001D
        const val CODABAR_QUIET_ZONE = 0x001E
        const val CODABAR_LENGTH_CONTROL = 0x001F
        const val CODABAR_LENGTH_1 = 0x0020
        const val CODABAR_LENGTH_2 = 0x0021
        const val CODE128_ENABLE = 0x0022
        const val EXPAND_CODE128_TO_CODE39 = 0x0023
        const val CODE128_CHECK_CHARACTER_TRANSMISSION = 0x0024
        const val CODE128_TRANSMIT_FUNCTION_CHARS = 0x0025
        const val CODE128_QUIET_ZONE = 0x0026
        const val CODE128_LENGTH_CONTROL = 0x0027
        const val CODE128_LENGTH_1 = 0x0028
        const val CODE128_LENGTH_2 = 0x0029
        const val CODE11_ENABLE = 0x002A
        const val CODE11_CHECK_CHARACTER_CALCULATION = 0x002B
        const val CODE11_CHECK_CHARACTER_TRANSMISSION = 0x002C
        const val CODE11_LENGTH_CONTROL = 0x002D
        const val CODE11_LENGTH_1 = 0x002E
        const val CODE11_LENGTH_2 = 0x002F
        const val CODE32_ENABLE = 0x0030
        const val CODE32_CHECK_CHARACTER_TRANSMISSION = 0x0031
        const val CODE32_START_STOP_CHARACTER_TRANSMISSION = 0x0032
        const val CODE39_ENABLE = 0x0033
        const val CODE39_CHECK_CHARACTER_CALCULATION = 0x0034
        const val CODE39_CHECK_CHARACTER_TRANSMISSION = 0x0035
        const val CODE39_START_STOP_CHARACTER_TRANSMISSION = 0x0036
        const val CODE39_FULL_ASCII = 0x0037
        const val CODE39_QUIET_ZONE = 0x0038
        const val CODE39_LENGTH_CONTROL = 0x0039
        const val CODE39_LENGTH_1 = 0x003A
        const val CODE39_LENGTH_2 = 0x003B
        const val CODE39_DANISH_PPT_ENABLE = 0x003C
        const val CODE39_PZN_ENABLE = 0x003D
        const val CODE39_LAPOSTE_ENABLE = 0x003E
        const val CIP_C39_ENABLE = 0x003F
        const val CODE93_ENABLE = 0x0040
        const val CODE93_CHECK_CHARACTER_CALCULATION = 0x0041
        const val CODE93_CHECK_CHARACTER_TRANSMISSION = 0x0042
        const val CODE93_QUIET_ZONE = 0x0043
        const val CODE93_LENGTH_CONTROL = 0x0044
        const val CODE93_LENGTH_1 = 0x0045
        const val CODE93_LENGTH_2 = 0x0046
        const val DATALOGIC_2OF5_ENABLE = 0x0047
        const val DATALOGIC_2OF5_CHECK_CHARACTER_CALCULATION = 0x0048
        const val DATALOGIC_2OF5_CHECK_CHARACTER_TRANSMISSION = 0x0049
        const val DATALOGIC_2OF5_LENGTH_CONTROL = 0x004A
        const val DATALOGIC_2OF5_LENGTH_1 = 0x004B
        const val DATALOGIC_2OF5_LENGTH_2 = 0x004C
        const val DATAMATRIX_ENABLE = 0x004D
        const val DATAMATRIX_DIMENSIONS_MASK = 0x004E
        const val DATAMATRIX_LENGTH_CONTROL = 0x004F
        const val DATAMATRIX_LENGTH_1 = 0x0050
        const val DATAMATRIX_LENGTH_2 = 0x0051
        const val EAN13_ENABLE = 0x0052
        const val EAN13_CHECK_CHARACTER_TRANSMISSION = 0x0053
        const val EAN13_FLAG1_CHARACTER_TRANSMISSION = 0x0054
        const val EAN13_ISBN_ENABLE = 0x0055
        const val EAN13_ISSN_ENABLE = 0x0056
        const val EAN8_ENABLE = 0x0057
        const val EAN8_CHECK_CHARACTER_TRANSMISSION = 0x0058
        const val EXPAND_EAN8_TO_EAN13 = 0x0059
        const val FOLLETT_2OF5_ENABLE = 0x005A
        const val EAN128_ENABLE = 0x005B
        const val DOTCODE_ENABLE = 0x005C
        const val DOTCODE_HIGH_SOLUTION = 0x005D
        const val DOTCODE_CONST_POSITION = 0x005E
        const val RSS_EXPANDED_ENABLE = 0x005F
        const val RSS_EXPANDED_EAN128_EMULATION = 0x0060
        const val RSS_EXPANDED_LENGTH_CONTROL = 0x0061
        const val RSS_EXPANDED_LENGTH_1 = 0x0062
        const val RSS_EXPANDED_LENGTH_2 = 0x0063
        const val RSS_14_ENABLE = 0x0064
        const val RSS_14_EAN128_EMULATION = 0x0065
        const val RSS_LIMITED_ENABLE = 0x0066
        const val RSS_LIMITED_EAN128_EMULATION = 0x0067
        const val I2OF5_ENABLE = 0x0068
        const val I2OF5_CHECK_CHARACTER_CALCULATION = 0x0069
        const val I2OF5_CHECK_CHARACTER_TRANSMISSION = 0x006A
        const val I2OF5_LENGTH_CONTROL = 0x006B
        const val I2OF5_LENGTH_1 = 0x006C
        const val I2OF5_LENGTH_2 = 0x006D
        const val IATA_ENABLE = 0x006E
        const val IATA_CHECK_CHARACTER_TRANSMISSION = 0x006F
        const val INDUSTRIAL_2OF5_ENABLE = 0x0070
        const val INDUSTRIAL_2OF5_CHECK_CHARACTER_CALCULATION = 0x0071
        const val INDUSTRIAL_2OF5_CHECK_CHARACTER_TRANSMISSION = 0x0072
        const val INDUSTRIAL_2OF5_LENGTH_CONTROL = 0x0073
        const val INDUSTRIAL_2OF5_LENGTH_1 = 0x0074
        const val INDUSTRIAL_2OF5_LENGTH_2 = 0x0075
        const val ISBT128_CONCATENATE_ENABLE = 0x0076
        const val ISBT128_CONCATENATION_MODE = 0x0077
        const val ISBT128_DYNAMIC_CONCATENATION_TIMEOUT = 0x0078
        const val ISBT128_FORCE_CONCATENATION = 0x0079
        const val MATRIX_2OF5_ENABLE = 0x007A
        const val MATRIX_2OF5_CHECK_CHARACTER_CALCULATION = 0x007B
        const val MATRIX_2OF5_CHECK_CHARACTER_TRANSMISSION = 0x007C
        const val MATRIX_2OF5_LENGTH_CONTROL = 0x007D
        const val MATRIX_2OF5_LENGTH_1 = 0x007E
        const val MATRIX_2OF5_LENGTH_2 = 0x007F
        const val MAXICODE_ENABLE = 0x0080
        const val MAXICODE_PRIMARY_MESSAGE_TRANSMISSION = 0x0081
        const val MAXICODE_LENGTH_CONTROL = 0x0082
        const val MAXICODE_LENGTH_1 = 0x0083
        const val MAXICODE_LENGTH_2 = 0x0084
        const val MICRO_PDF_ENABLE = 0x0085
        const val MICROPDF_128_EMULATION = 0x0086
        const val MICROPDF_128_LENGTH_CONTROL = 0x0087
        const val MICROPDF_128_LENGTH_1 = 0x0088
        const val MICROPDF_128_LENGTH_2 = 0x0089
        const val MICROQR_ENABLE = 0x008A
        const val MICROQR_LENGTH_CONTROL = 0x008B
        const val MICROQR_LENGTH_1 = 0x008C
        const val MICROQR_LENGTH_2 = 0x008D
        const val MSI_ENABLE = 0x008E
        const val MSI_CHECK_CHARACTER_CALCULATION = 0x008F
        const val MSI_CHECK_CHARACTER_TRANSMISSION = 0x0090
        const val MSI_LENGTH_CONTROL = 0x0091
        const val MSI_LENGTH_1 = 0x0092
        const val MSI_LENGTH_2 = 0x0093
        const val PDF417_ENABLE = 0x0094
        const val PDF417_LENGTH_CONTROL = 0x0095
        const val PDF417_LENGTH_1 = 0x0096
        const val PDF417_LENGTH_2 = 0x0097
        const val PLESSEY_ENABLE = 0x0098
        const val PLESSEY_CHECK_CHARACTER_CALCULATION = 0x0099
        const val PLESSEY_CHECK_CHARACTER_TRANSMISSION = 0x009A
        const val PLESSEY_LENGTH_CONTROL = 0x009B
        const val PLESSEY_LENGTH_1 = 0x009C
        const val PLESSEY_LENGTH_2 = 0x009D
        const val QRCODE_ENABLE = 0x009E
        const val QRCODE_LENGTH_CONTROL = 0x009F
        const val QRCODE_LENGTH_1 = 0x00A0
        const val QRCODE_LENGTH_2 = 0x00A1
        const val S25_ENABLE = 0x00A2
        const val S25_CHECK_CHARACTER_CALCULATION = 0x00A3
        const val S25_CHECK_CHARACTER_TRANSMISSION = 0x00A4
        const val S25_LENGTH_CONTROL = 0x00A5
        const val S25_LENGTH_1 = 0x00A6
        const val S25_LENGTH_2 = 0x00A7
        const val TRIOPTIC_ENABLE = 0x00A8
        const val UCC_OPTIONAL_COMPOSITE_TIMER = 0x00A9
        const val POSTAL_CODE_SELECTION = 0x00AA
        const val POSTNET_BB_CONTROL = 0x00AB
        const val UPCA_ENABLE = 0x00AC
        const val UPCA_CHECK_CHARACTER_TRANSMISSION = 0x00AD
        const val EXPAND_UPCA_TO_EAN13 = 0x00AE
        const val UPCA_NUMBER_SYSTEM_CHARACTER_TRANSMISSION = 0x00AF
        const val UPCE_ENABLE = 0x00B0
        const val UPCE_CHECK_CHARACTER_TRANSMISSION = 0x00B1
        const val EXPAND_UPCE_TO_EAN13 = 0x00B2
        const val EXPAND_UPCE_TO_UPCA = 0x00B3
        const val UPCE_NUMBER_SYSTEM_CHARACTER_TRANSMISSION = 0x00B4
        const val GTIN_ENABLE = 0x00B5
        const val DO_PRICE_WEIGHT_CHECK = 0x00B6
        const val UPCEAN_QUIET_ZONE = 0x00B7
        const val GLOBAL_PREFIX = 0x00B8
        const val GLOBAL_SUFFIX = 0x00B9
        const val CASE_CONVERSION = 0x00BA
        const val ALL_AIM_ID_ENABLE = 0x00BB
        const val EAN128_AIM_ID_ENABLE = 0x00BC
        const val LABEL_ID_CONTROL = 0x00BD
        const val LABEL_ID_EAN13_P2 = 0x00BE
        const val LABEL_ID_EAN13_P5 = 0x00BF
        const val LABEL_ID_EAN13 = 0x00C0
        const val LABEL_ID_RSS_14 = 0x00C1
        const val LABEL_ID_EAN8_P2 = 0x00C2
        const val LABEL_ID_EAN8_P5 = 0x00C3
        const val LABEL_ID_EAN8 = 0x00C4
        const val LABEL_ID_UPCA_P2 = 0x00C5
        const val LABEL_ID_UPCA_P5 = 0x00C6
        const val LABEL_ID_UPCA = 0x00C7
        const val LABEL_ID_ABC_CODABAR = 0x00C8
        const val LABEL_ID_ANKER_PLESSEY = 0x00C9
        const val LABEL_ID_BC412 = 0x00CA
        const val LABEL_ID_CODE11 = 0x00CB
        const val LABEL_ID_COMPRESSED_2OF5 = 0x00CC
        const val LABEL_ID_CODE39 = 0x00CD
        const val LABEL_ID_NW7_CODABAR = 0x00CE
        const val LABEL_ID_CODE128 = 0x00CF
        const val LABEL_ID_CODE93 = 0x00D0
        const val LABEL_ID_CODABAR = 0x00D1
        const val LABEL_ID_CODE39_CIP = 0x00D2
        const val LABEL_ID_TRIOPTIC = 0x00D3
        const val LABEL_ID_DATALOGIC_2OF5 = 0x00D4
        const val LABEL_ID_CODE39_DANISH_PPT = 0x00D5
        const val LABEL_ID_UPCE_P2 = 0x00D6
        const val LABEL_ID_UPCE_P5 = 0x00D7
        const val LABEL_ID_UPCE = 0x00D8
        const val LABEL_ID_FOLLETT_2OF5 = 0x00D9
        const val LABEL_ID_GTIN2 = 0x00DA
        const val LABEL_ID_GTIN5 = 0x00DB
        const val LABEL_ID_GS1_AZTEC = 0x00DC
        const val LABEL_ID_GTIN = 0x00DD
        const val LABEL_ID_I2OF5_CIP_HR = 0x00DE
        const val LABEL_ID_I2OF5 = 0x00DF
        const val LABEL_ID_ISBT128 = 0x00E0
        const val LABEL_ID_IATA = 0x00E1
        const val LABEL_ID_ISSN = 0x00E2
        const val LABEL_ID_ISBN = 0x00E3
        const val LABEL_ID_RSS_LIMITED = 0x00E4
        const val LABEL_ID_CODE39_LAPOSTE = 0x00E5
        const val LABEL_ID_MATRIX_2OF5 = 0x00E6
        const val LABEL_ID_MSI = 0x00E7
        const val LABEL_ID_PHARMACODE = 0x00E8
        const val LABEL_ID_CODE39_PHARMACODE = 0x00E9
        const val LABEL_ID_PLESSEY = 0x00EA
        const val LABEL_ID_CODE39_PZN = 0x00EB
        const val LABEL_ID_S25 = 0x00EC
        const val LABEL_ID_INDUSTRIAL_2OF5 = 0x00ED
        const val LABEL_ID_EAN128 = 0x00EE
        const val LABEL_ID_RSS_EXPANDED = 0x00EF
        const val LABEL_ID_TELEPEN = 0x00F0
        const val LABEL_ID_EAN13_COMPOSITE = 0x00F1
        const val LABEL_ID_RSS_14_COMPOSITE = 0x00F2
        const val LABEL_ID_EAN8_COMPOSITE = 0x00F3
        const val LABEL_ID_UPCA_COMPOSITE = 0x00F4
        const val LABEL_ID_AZTEC = 0x00F5
        const val LABEL_ID_CHINA_SENSIBLE_CODE = 0x00F6
        const val LABEL_ID_DOTCODE = 0x00F7
        const val LABEL_ID_DATAMATRIX = 0x00F8
        const val LABEL_ID_UPCE_COMPOSITE = 0x00F9
        const val LABEL_ID_GS1_DATAMATRIX = 0x00FA
        const val LABEL_ID_GS1_QR_CODE = 0x00FB
        const val LABEL_ID_RSS_LIMITED_COMPOSITE = 0x00FC
        const val LABEL_ID_MICRO_PDF = 0x00FD
        const val LABEL_ID_MICROQR = 0x00FE
        const val LABEL_ID_MAXICODE = 0x00FF
        const val LABEL_ID_OCR_A = 0x0100
        const val LABEL_ID_OCR_B = 0x0101
        const val LABEL_ID_MICR = 0x0102
        const val LABEL_ID_PDF417 = 0x0103
        const val LABEL_ID_POSTAL_AUSTRALIAN = 0x0104
        const val LABEL_ID_POSTAL_FINNISH = 0x0105
        const val LABEL_ID_POSTAL_PORTUGAL = 0x0106
        const val LABEL_ID_POSTAL_MAIL_MARK = 0x0107
        const val LABEL_ID_POSTAL_JAPANESE = 0x0108
        const val LABEL_ID_POSTAL_KIX = 0x0109
        const val LABEL_ID_POSTAL_IMB = 0x010A
        const val LABEL_ID_POSTNET = 0x010B
        const val LABEL_ID_POSTAL_PLANET = 0x010C
        const val LABEL_ID_POSTAL_ROYAL_MAIL = 0x010D
        const val LABEL_ID_POSTAL_SWEDISH = 0x010E
        const val LABEL_ID_QRCODE = 0x010F
        const val LABEL_ID_TLC39 = 0x0110
        const val LABEL_ID_EAN128_COMPOSITE = 0x0111
        const val LABEL_ID_RSS_EXPANDED_COMPOSITE = 0x0112
        const val LABEL_ID_DIGIMARC = 0x0113
        const val READ_MODE = 0x0114
        const val ELAPSE_TIME = 0x0115
        const val FLASH_ON_TIME = 0x0116
        const val FLASH_OFF_TIME = 0x0117
        const val DOUBLE_READ_TIMEOUT = 0x0118
        const val STAND_MODE_SENSITIVITY = 0x0119
        const val MANUAL_TO_WATCH_TIMEOUT = 0x011A
        const val POWERUP_BEEP_ENABLE = 0x011B
        const val GOOD_READ_BEEP_TYPE = 0x011C
        const val GOOD_READ_BEEP_VOLUME = 0x011D
        const val GOOD_READ_BEEP_FREQUENCY = 0x011E
        const val GOOD_READ_BEEP_LENGTH = 0x011F
        const val GOOD_READ_LED_DURATION = 0x0120
        const val GOOD_READ_WHEN_TO_INDICATE = 0x0121
        const val GREEN_SPOT_DURATION = 0x0122
        const val VIBRATION_GOODREAD_ENABLE = 0x0123
        const val VIBRATION_GOODREAD_LENGTH = 0x0124
        const val AIMING_POINTER_ENABLE = 0x0125
        const val PICK_MODE_ENABLE = 0x0126
        const val MOBILE_PHONE_ENABLE = 0x0127
        const val MOBILE_PHONE_SATURATION_RATE = 0x0128
        const val IMAGE_DECODING_VL_REVERSE = 0x0129
        const val MULTIPLE_LABEL_ENABLE = 0x012A
        const val MULTIPLE_LABEL_TRANSMIT_ORDER = 0x012B
    }
}