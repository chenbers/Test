package com.inthinc.pro.automation.deviceEnums;

import java.util.EnumSet;
import java.util.HashMap;

import com.inthinc.pro.automation.interfaces.DeviceTypes;

public enum DeviceAttrs implements DeviceTypes{

    TOP_SPEED(1),
    AVG_SPEED(2),
    SPEED_LIMIT(3),
    AVG_RPM(4),
    RESET_REASON(5),
    MANUAL_RESET_REASON(6),
    FWDCMD_STATUS(7),
    VIOLATION_FLAGS(8),
    ENG_TEMP(9),
    FUEL_LEVEL(10),
    THROTTLE(11),
    GPS_SATS_SNR_COUNT(12),
    GPS_SATS_SNR_MIN(13),
    GPS_SATS_SNR_MAX(14),
    DMM_MONITOR_X30(15),
    DMM_MONITOR_X50(16),
    DMM_MONITOR_X67(17),
    DMM_MONITOR_Y30(18),
    DMM_MONITOR_Y50(19),
    DMM_MONITOR_Y67(20),
    GPS_SATS_SNR_MEDIAN(21),
    GPS_SATS_PDOP_10X(22),
    GPS_SATS_HDOP_10X(23),
    SEVERITY(24),
    DMM_VERSION(25),
    DMM_ORIENTATION(26),
    DMM_DVX(27),
    DMM_YLEVEL(28),
    DMM_ZLEVEL(29),
    SMTLS_ENABLED(30),
    SMTLS_SECURITY_DISABLED(31),
    SMTLS_DEACTIVATE_OVERRIDE(32),
    SMTLS_EMU_DOWNLOADED(33),
    SMTLS_EMU_NAME_VERIFICATION_OVERRIDE(34),
    SMTLS_BUILD_NUM(35),
    SMTLS_PERCENT_SUCCESSFUL_REQUEST_RESPONSE_COMMS(36),
    OBD_SEATBELT_STATUS(37),
    OBD_SEATBELT_AVAIL(38),
    SPEED_IN_EXCEPTION_MAP(39),
    GPS_MOST_LIKELY_LOCKED(40),
    GGA_GPS_QUALITY(41),
    GPS_SPEED(42),
    OBD_RPM_AVAIL(43),
    OBD_SPEED_AVAIL(44),
    OBD_VIN_DIGIT_1(45),
    DMM_CAL_STATUS(46),
    CURRENT_IGN(47),
    NUMBER_OF_GPS_REBOOTS(48),
    PERCENTAGE_OF_TIME_SPEED_FROM_OBD_USED(49),
    PERCENTAGE_OF_TIME_SPEED_FROM_GPS_USED(50),
    STATS(51),
    AGPS_ERROR_CODE(52),
    UNPLUGGED_WHILE_ASLEEP(53),
    LOGOUT_TYPE(54),
    SAT_EPH_CNT(55),
    WITNESS_EVENT_TYPE(56),
    GPINPUT_STATE(57),
    SEATBELT_SRC(58),
    VBCPU_BOOTMODE(59),
    DWNLD_TYPE(60),
    NUM_CONFIG_DUMPS(61),
    NUM_REQSET(62),
    BASE_VER(63),
    DISTANCE(129),
    MAX_RPM(130),
    DELTAV_X(131),
    DELTAV_Y(132),
    DELTAV_Z(133),
    VEHICLE_BATTERY(134),
    BACKUP_BATTERY(135),
    GPS_SATS_SNR_MEAN_100X(136),
    GPS_SATS_SNR_STDDEV_100X(137),
    GPS_LOCK_TIME(138),
    SMTLS_FIRMWARE_REV(139),
    COURSE(140),
    OBD_RPM(141),
    OBD_SPEED(142),
    SMTLS_LOGGED_TX_COUNT(143),
    SMTLS_OP_MODE(144),
    ODOMETER_BY_COORDINATES(145),
    A_AND_D_SPACE(146),
    APPLICATION_SPACE(147),
    BOUNDARY_ID(148),
    MPG(149),
    LOW_POWER_MODE_TIMEOUT(150),
    HARD_VERT_DMM_PEAK_TO_PEAK_LEVEL(161),
    NUMBER_OF_TIMES_A_SPEEDING_VIOLATION_WAS_EITHER_PREVENTED_OR_SQUELCHED_(165),
    PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_(166),
    ZONE_ID(192),
    FIRMWARE_VERSION(193),
    FWDCMD_ID(194),
    FWDCMD_COMMAND(195),
    FWDCMD_ERROR(196),
    SMTLS_TX_ATTEMPTS(197),
    LAT_VERB(198),
    LONG_VERB(199),
    STREET_ID(200),
    SPEED_ID(201),
    EMU_HASH_1(202),
    EMU_HASH_2(203),
    EMU_HASH_3(204),
    EMU_HASH_4(205),
    OBD_X1_TO_X4(206),
    OBD_X5_TO_X8(207),
    OBD_X9_TO_X12(208),
    OBD_X13_TO_X16(209),
    OBD_X17_TO_X20(210),
    OBD_X21_TO_X24(211),
    OBD_X25_TO_X28(212),
    SMTLS_LOGGED_RX_COUNT(213),
    OBD_VIN_DIGIT_2_TO_5(214),
    OBD_VIN_DIGIT_6_TO_9(215),
    OBD_VIN_DIGIT_10_TO_13(216),
    OBD_VIN_DIGIT_14_TO_17(217),
    LOW_IDLE(219),
    HIGH_IDLE(220),
    RFID0(222),
    RFID1(223),
    MPG_DISTANCE(224),
    TRIP_DURATION(225),
    STRING(255),
    BAD_ERROR(226), // Errors that we want the portal to track
    DRIVER_ID(227),

    TOTAL_AGPS_BYTES(228),
    MAP_HASH(229),
    TOTAL_BYTES_DUMPSET(230),
    SBS_LINK_ID(231),
    CLOSEST_SBS_LINK_ID(232),

    BASELINE_VERSION(8192),
    BOOTLOADER_REV(8193),
    BRAKE_COLLECTED(8194),
    CAL_VERSION(8195),
    CALIBRATION_STATUS(8196),
    CALLING_FILE(8197),
    CLEAR_DRIVER_FLAG(8198),
    CONFIDENCE_LEVEL(8199),
    DIAGNOSTIC(8200),
    DRIVER_FLAG(8201),
    DRIVER_STATUS(8202),
    DVX(8203),
    DVY(8204),
    DVZ(8205),
    EVENT_CODE(8206),
    EXCEPTION_VERSION(8207),
    FILTERED_SPEED(8208),
    FIRMWARE_REV_PREVIOUS(8209),
    FIRMWARE_VERSION_WAYS(8210),
    FLAG(8211),
    IGNITION_OFF_FULL_EVENTS_ENABLED(8212),
    GPS_CONFIDENCE_PERCENT(8213),
    GPS_STATE(8214),
    HARD_ACCEL_AVERAGE_CHANGE(8215),
    HARD_ACCEL_DELTA_V(8216),
    HARD_ACCEL_LEVEL(8217),
    HARD_VERT_DMM_TO_CRASH_RATIO_THRES(8218),
    IGNITIONON_PERCENTAGE(8219),
    LOCK_PERCENTAGE(8220),
    MAGICA(8221),
    MAPS_MOUNTED_PERCENTAGE(8222),
    MAPS_UNMOUNTED_COUNT(8223),
    MAX_SPEED_LIMIT(8224),
    MCM_RULESET(8225),
    NO_STRING_PERCENTAGE(8226),
    OBD_CONFIDENCE_PERCENT(8227),
    OBD_SPEED_WAYS(8228),
    ODOMETER_COLLECTED(8229),
    ORIENTATION_TRIAX(8230),
    PERCENT_MEMORY_AVAILABLE(8231),
    PRE_INSTALL_FULL_EVENTS_ENABLED(8232),
    REASON_CODE_DOT(8233),
    REASON_CODE_HOS(8234),
    REBOOTS(8235),
    RMS_LEVEL(8236),
    RMS_WINDOW(8237),
    RPM_COLLECTED(8238),
    SBS_DB_UPDATE_RESULT(8239),
    SBS_EX_MAP_UPDATE_RESULT(8240),
    SBS_SPEED_LIMIT(8241),
    SEATBELT_ENGAGED(8242),
    SEATBELT_COLLECTED(8243),
    SEATBELT_IN_PERCENTAGE(8244),
    SEVERE_SPEED_THRESHOLD(8246),
    SLOPE(8247),
    SPEED_COLLECTED(8248),
    SPEED_MODULE_ENABLED(8249),
    SPEED_SOURCE(8250),
    SPEEDING_BUFFER(8251),
    SPEEDING_TYPE(8253),
    STATE(8254),
    STATUS(8255),
    TEMP_COMP_ENABLED(8256),
    TEMP_COMP_SLOPES_IN_BUFFER(8257),
    TRIP_INSPECTION_FLAG(8258),
    TRIP_REPORT_FLAG(8259),
    UP_TO_DATE_STATUS(8260),
    VERSION_BASELINE_1(8261),
    VERSION_BASELINE_2(8262),
    VERSION_BASELINE_3(8263),
    VERSION_BASELINE_4(8264),
    VERSION_EXCEPTION_1(8265),
    VERSION_EXCEPTION_2(8266),
    VERSION_EXCEPTION_3(8267),
    VERSION_EXCEPTION_4(8268),
    WEATHER_SPEED_LIMIT_PERCENT(8269),
    X_ACCEL(8270),
    X_WINDOW(8271),
    X_CAT_DATA(8272),
    X_CAT_EVENT(8273),
    Y_SLOPE(8274),
    Y_WINDOW(8275),
    Z_SLOPE(8276),
    Z_WINDOW(8277),
    ZONE_SPEED_LIMIT(8278),
    HAZMAT_FLAG(8279),
    STRUCT_VERSION(8280),
    SENDER(8281),
    CONNECT_TYPE(8282),
    ACCELERATION(16384),
    ACKNOWLEDGED_COMMAND_ID(16385),
    BELOW_THRESHOLD_FULL_EVENT_COUNT(16386),
    CONFIRMED_NOTE_EVENT_COUNT(16387),
    CPU_PERCENTAGE(16388),
    DATA_LENGTH(16389),
    DIGITAL_INPUT_STATUS(16390),
    DIGITAL_OUTPUT_STATUS(16391),
    DURATION(16392),
    ERROR_CODE(16393),
    ERROR_DETAILS(16394),
    FILTERED_NOTE_EVENT_COUNT(16395),
    FORWARD_COMMAND_ID(16396),
    G_TRIGGER_LEVEL(16397),
    GPS_DISTANCE(16398),
    LAT_LONG_DIST(16399),
    // LOGGED_MESSAGE_COUNT(16400),
    MAX_TIME(16400),
    MESSAGES_LATENT(16401),
    MODE_OF_OPERATIONS(16402),
    MSP_430_REBOOTS(16403),
    OBD_DISTANCE(16404),
    POWER_RECYCLE_ATTEMPTS(16405),
    POWER_UP_COUNT(16406),
    SMTOOLS_HARDWARE_REV(16407),
    SOFTWARE_RESET_COUNT(16408),
    STATUS_DELTAV_X(16409),
    STATUS_DELTAV_Y(16410),
    STATUS_DELTAV_Z(16411),
    STOP_TIME(16412),
    TEMP_AT_CALIBRATION(16413),
    TEMP_COMP_SLOPES_MEASURED(16414),
    TEMP_SENSE_ADC_ATCAL(16415),
    TEXT_LENGTH(16416),
    TOTAL_TEMP_COMP_SLOPE_ESTIMATES(16417),
    TOTAL_TEMP_COMP_SLOPE_UPDATES(16418),
    TRAILER_GALLONS(16419),
//    TRANSMITTED_MESSAGE_COUNT(16420),
    VEHICLE_GALLONS(16420),
    WATCHDOG_COUNT(16421),
    WEI_COUNT(16422),
    WIGGLE_TRIGGER_COUNT_TOTAL(16423),
    ANALOG_SENSOR(16424),
    LOW_IDLE_2(16425),
    HIGH_IDLE_2(16426),
    DATA(24576),                            // string length comes from ATTR_DATA_LENGTH
    TEXT_MESSAGE(24577),                    // string length comes from ATTR_TEXT_LENGTH
    DRIVER_STR(24578, 10, false),           // string 10 fixed length,                      \0 filled
    EMU_NAME_DEVICE(24579, 29, false),      // string 29 fixed length,                      \0 filled
    EMU_NAME_TRANSFORM(24580, 29, false),   // string 29 fixed length,                      \0 filled
    FILE_NAME(24581, 32, true),             // string 32 max, variable length,              \0 terminated
    LOCATION(24582, 32, true),              // string 32 max, variable length,              \0 terminated
    REPORT_ID(24583, 4, true),              // string 4 max, variable length,               \0 terminated
    SERVICE_ID(24584, 20, true),            // string 20 max, variable length,              \0 terminated
    SILICON_ID(24585, 17, false),           // string 17 fixed length,                      \0 filled
    SMTOOLS_FIRMWARE_REV(24586, 36, false), // string 36 fixed length,                      \0 filled
    TRAILER_ID(24587, 20, true),            // string 20 max, variable length,              \0 terminated
    VIN(24588, 18, false),                  // string 18 fixed length,                      \0 filled
    WITNESS_ID(24589, 9, true),             // string 9 max, variable length,               \0 terminated
    OCCUPANT_STR(24590, 10, false),         // string 10 fixed length,                      \0 filled
    EMP_ID(24591, 11, true),                // string 11 max, variable length,              \0 terminated
    VEHICLE_ID_STR(24592, 20, true),          // string 20 max, variable length,              \0 terminated
    IMEI(24593, 15, true),                  // string 15 max, variable length,              \0 terminated
    MCM_ID_STR(24594, 9, true),                  // string 9 max, variable length,               \0 terminated
    
    SKIP_INT(32000),
    AVERAGE_LOCK_TIME(32768),
    BATTERY_VOLTAGE(32769),
    BRAKE_RECEIVES(32770),
    BRAKE_REQUESTS(32771),
    DATA_CURRENT(32772),
    DATA_EXPECTED(32773),
    DOWNLOAD_WEEKLY_TOTAL_BOOTLOADER(32774),
    DOWNLOAD_WEEKLY_TOTAL_BOUNDARY_DAT(32775),
    DOWNLOAD_WEEKLY_TOTAL_FIRMWARE(32776),
    DOWNLOAD_WEEKLY_TOTAL_MAPS(32777),
    DOWNLOAD_WEEKLY_TOTAL_PLACES_2_DAT(32778),
    DOWNLOAD_WEEKLY_TOTAL_QSI_FIRMWARE(32779),
    DOWNLOAD_WEEKLY_TOTAL_SBS_EX_MAPS_CHECK_BYTES(32780),
    DOWNLOAD_WEEKLY_TOTAL_SBS_EX_MAPS_DOWNLOAD_BYTES(32781),
    DOWNLOAD_WEEKLY_TOTAL_SBS_EX_MAPS_DOWNLOAD_COUNT(32782),
    DOWNLOAD_WEEKLY_TOTAL_SMTOOLS_EMULATION(32783),
    DOWNLOAD_WEEKLY_TOTAL_SMTOOLS_FIRMWARE(32784),
    DOWNLOAD_WEEKLY_TOTAL_TRIAX_II_FIRMWARE(32785),
    DOWNLOAD_WEEKLY_TOTAL_WITNESS_II_FIRMWARE(32786),
    DOWNLOAD_WEEKLY_TOTAL_ZONES(32787),
    EMU_NAME_VERIFIED(32789),
//    ERROR(32790),
    ERROR_CODE_CONF(32790),
    FILE_SIZES(32791),
    FLAGS(32792),
    LOOPS(32794),
    MAP_FILE_SIZE(32795),
    MAP_ID(32796),
    MAX_POSGS(32797),
    MAX_NEGGS(32798),
    MESSAGES_SIZE(32799),
    NOPT_WEEKLY_WRITE_COUNT(32800),
    ODOMETER(32801),
    ODOMETER_GPS_COUNT(32802),
    ODOMETER_ODB_COUNT(32803),
    ODOMETER_RECEIVES(32804),
    ODOMETER_REQUESTS(32805),
    RECEIVED_MESSAGE_COUNT(32806),
    RPM_RECEIVES(32807),
    RPM_REQUESTS(32808),
    SEATBELT_RECEIVES(32809),
    SEATBELT_REQUESTS(32810),
    SMTOOLS_RESET_STATUS(32812),
    SMTOOLSTIMERRUNNING(32813),
    SPEED_RECEIVES(32814),
    SPEED_REQUESTS(32815),
    START_TIME(32816),
    SUBMITTED_TIME(32817),
    TIME(32818),
    TIME_CALIBRATED(32819),
    TIME_LAST_CHECKED(32821),
    TIME_LAST_VISTED(32822),
    TIME_RTC(32823),
    UPLOAD_WEEK_TOTAL_NOTIFICATION_BYTES(32824),
    UPLOAD_WEEK_TOTAL_NOTIFICATION_COUNT(32825),
    UPLOAD_WEEK_TOTAL_WITTNESS_II_TRACE(32826),
    WIRELINE_FLAGS(32827),
    SEVERE_SPEED_SECONDS(32828),
    SPEEDING_GRACE_PERIOD(32829),
    COMPANY_ID(32830),
    EVENT_CODE_INT(32831),
    MAX_LATITUDE(40960),
    MAX_LONGITUDE(40961),
    MIN_LATITUDE(40962),
    MIN_LONGITUDE(40963),
    DELTA_VS(49152, 4),                             //binary   4 bytes
    DENOMINATOR(49153, 6),                          //binary   6 bytes
    IDLE_TIMES(49154, 4),                           //binary   4 bytes
    INTERCEPTS(49155, 6),                           //binary   6 bytes
    LAT_LNG_3(49156, 6),                            //binary   6 bytes
    LAT_LNG_4(49157, 8),                            //binary   8 bytes
    NUMERATOR(49158, 6),                            //binary   6 bytes
    ORIENTATION_ROLL_PITCH_YAW(49159, 6),           //binary   6 bytes roll, pitch, yaw
    SENSOR_AGREEMENT(49160, 4),                     //binary   4 bytes
    SPEED_DATA_HIRES(49161, 100),                   //binary 100 bytes
    TEMP_COMP_DATA(49162, 54),                      //binary  54 bytes
    TEMP_COMP_SAMPLE_MAX(49163, 8),                 //binary   8 bytes
    // TEMP_COMP_SAMPLE_MIN(49163, 8),              //binary   8 bytes
    TEMP_COMP_SLOPE(49164, 6),                      //binary   6 bytes
    WITNESS_II_ARCHIVE(49165, 124),                 //binary 124 bytes
    WITNESS_II_ARCHIVE_HEADER(49166, 17),           //binary  17 bytes
    ACK_DATA(49167, 1024),                          //binary max 1024 bytes, variable length 
    CRASH_TRACE(49168, 16384),                      //binary approximately 16K bytes (not in DB attr) 16384
    ATTR_WKLY_DRIVER_HISTOGRAM_STATS(49169, 1024),  //binary approximately 1k bytes (not in db attr)

    STATIC,

    ;

    private int code;
    private int size;
    private boolean zeroTerminated;

    private DeviceAttrs() {}

    private DeviceAttrs(int code) {
        this.code = code;
        if (code <= 127) {                          // Attribute Id (1-127 have one byte values)
            size = Byte.SIZE / Byte.SIZE;
        } else if (code >= 128 && 191 >= code) {    // Attribute Id (128->191 have two byte values)
            size = Short.SIZE / Byte.SIZE;
        } else if (code >= 192 && 254 >= code) {    // Attribute id (192->254) have four byte values)
            size = Integer.SIZE / Byte.SIZE;
        } else if (code >= 8192 && 16383 >= code) { // one byte value attributes 8192 [0x2000] to 16383 [0x3FFF]
            size = Byte.SIZE / Byte.SIZE;
        } else if (code >= 16384 && 24575 >= code) {// two byte value Attributes 16384 [0x4000] to 24575 [0x5FFF]    
            size = Short.SIZE / Byte.SIZE;
        } else if (code >= 32768 && 40959 >= code) {// Attribute ids 32768  [0x8000] to  40959 [0x9FFF] have four byte values,
            size = Integer.SIZE / Byte.SIZE;
        } else if (code >= 40960 && 49151 >= code) {// double value Attributes 40960 [0xA000] to 49151 [0xBFFF]
            size = Double.SIZE / Byte.SIZE;
        } 
    }

    private DeviceAttrs(int code, int size) {
        this(code);
        this.size = size;
    }

    private DeviceAttrs(int code, int size, boolean zeroTerminated) {
        this(code, size);
        this.zeroTerminated = zeroTerminated;
    }

    public Integer getCode() {
        return code;
    }

    public Integer getSize() {
        return size;
    }

    public boolean isZeroTerminated() {
        return zeroTerminated;
    }

    private static HashMap<Integer, DeviceAttrs> lookupByCode = new HashMap<Integer, DeviceAttrs>();

    static {
        for (DeviceAttrs p : EnumSet.allOf(DeviceAttrs.class)) {
            lookupByCode.put(p.getCode(), p);
        }
    }

    public static DeviceAttrs valueOf(Integer code) {
        return lookupByCode.get(code);
    }

}
