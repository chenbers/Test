package com.inthinc.pro.automation.objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import com.inthinc.pro.automation.interfaces.TextEnum;

/**
 * @author dtanner
 * 
 */
public class AutomationCalendar implements Comparable<Calendar> {
    
    public static enum TimeZones implements TextEnum {
        US_SAMOA("US/Samoa (GMT-11:00)"),
        US_ALEUTIAN("US/Aleutian (GMT-10:00)"),
        US_HAWAII("US/Hawaii (GMT-10:00)"),
        US_ALASKA("US/Alaska (GMT-9:00)"),
        CANADA_PACIFIC("Canada/Pacific (GMT-8:00)"),
        CANADA_YUKON("Canada/Yukon (GMT-8:00)"),
        MEXICO_BAJANORTE("Mexico/BajaNorte (GMT-8:00)"),
        US_PACIFIC("US/Pacific (GMT-8:00)"),
        CANADA_MOUNTAIN("Canada/Mountain (GMT-7:00)"),
        MEXICO_BAJASUR("Mexico/BajaSur (GMT-7:00)"),
        US_ARIZONA("US/Arizona (GMT-7:00)"),
        US_MOUNTAIN("US/Mountain (GMT-7:00)"),
        CANADA_CENTRAL("Canada/Central (GMT-6:00)"),
        CANADA_EAST_SASKATCHEWAN("Canada/East-Saskatchewan (GMT-6:00)"),
        CANADA_SASKATCHEWAN("Canada/Saskatchewan (GMT-6:00)"),
        MEXICO_GENERAL("Mexico/General (GMT-6:00)"),
        US_CENTRAL("US/Central (GMT-6:00)"),
        US_INDIANA_STARKE("US/Indiana-Starke (GMT-6:00)"),
        BRAZIL_ACRE("Brazil/Acre (GMT-5:00)"),
        CANADA_EASTERN("Canada/Eastern (GMT-5:00)"),
        CUBA("Cuba (GMT-5:00)"),
        JAMAICA("Jamaica (GMT-5:00)"),
        US_EAST_INDIANA("US/East-Indiana (GMT-5:00)"),
        US_EASTERN("US/Eastern (GMT-5:00)"),
        US_MICHIGAN("US/Michigan (GMT-5:00)"),
        ANTARCTICA_PALMER("Antarctica/Palmer (GMT-4:00)"),
        BRAZIL_WEST("Brazil/West (GMT-4:00)"),
        CANADA_ATLANTIC("Canada/Atlantic (GMT-4:00)"),
        CANADA_NEWFOUNDLAND("Canada/Newfoundland (GMT-3:30)"),
        ANTARCTICA_ROTHERA("Antarctica/Rothera (GMT-3:00)"),
        BRAZIL_EAST("Brazil/East (GMT-3:00)"),
        BRAZIL_DENORONHA("Brazil/DeNoronha (GMT-2:00)"),
        AFRICA_ABIDJAN("Africa/Abidjan (GMT+0:00)"),
        AFRICA_ACCRA("Africa/Accra (GMT+0:00)"),
        AFRICA_BAMAKO("Africa/Bamako (GMT+0:00)"),
        AFRICA_BANJUL("Africa/Banjul (GMT+0:00)"),
        AFRICA_BISSAU("Africa/Bissau (GMT+0:00)"),
        AFRICA_CASABLANCA("Africa/Casablanca (GMT+0:00)"),
        AFRICA_CONAKRY("Africa/Conakry (GMT+0:00)"),
        AFRICA_DAKAR("Africa/Dakar (GMT+0:00)"),
        AFRICA_EL_AAIUN("Africa/El_Aaiun (GMT+0:00)"),
        AFRICA_FREETOWN("Africa/Freetown (GMT+0:00)"),
        AFRICA_LOME("Africa/Lome (GMT+0:00)"),
        AFRICA_MONROVIA("Africa/Monrovia (GMT+0:00)"),
        AFRICA_NOUAKCHOTT("Africa/Nouakchott (GMT+0:00)"),
        AFRICA_OUAGADOUGOU("Africa/Ouagadougou (GMT+0:00)"),
        AFRICA_SAO_TOME("Africa/Sao_Tome (GMT+0:00)"),
        AFRICA_TIMBUKTU("Africa/Timbuktu (GMT+0:00)"),
        EIRE("Eire (GMT+0:00)"),
        EUROPE_BELFAST("Europe/Belfast (GMT+0:00)"),
        EUROPE_DUBLIN("Europe/Dublin (GMT+0:00)"),
        EUROPE_GUERNSEY("Europe/Guernsey (GMT+0:00)"),
        EUROPE_ISLE_OF_MAN("Europe/Isle_of_Man (GMT+0:00)"),
        EUROPE_JERSEY("Europe/Jersey (GMT+0:00)"),
        EUROPE_LISBON("Europe/Lisbon (GMT+0:00)"),
        EUROPE_LONDON("Europe/London (GMT+0:00)"),
        ICELAND("Iceland (GMT+0:00)"),
        PORTUGAL("Portugal (GMT+0:00)"),
        UTC("UTC (GMT+0:00)"),
        AFRICA_ALGIERS("Africa/Algiers (GMT+1:00)"),
        AFRICA_BANGUI("Africa/Bangui (GMT+1:00)"),
        AFRICA_BRAZZAVILLE("Africa/Brazzaville (GMT+1:00)"),
        AFRICA_CEUTA("Africa/Ceuta (GMT+1:00)"),
        AFRICA_DOUALA("Africa/Douala (GMT+1:00)"),
        AFRICA_KINSHASA("Africa/Kinshasa (GMT+1:00)"),
        AFRICA_LAGOS("Africa/Lagos (GMT+1:00)"),
        AFRICA_LIBREVILLE("Africa/Libreville (GMT+1:00)"),
        AFRICA_LUANDA("Africa/Luanda (GMT+1:00)"),
        AFRICA_MALABO("Africa/Malabo (GMT+1:00)"),
        AFRICA_NDJAMENA("Africa/Ndjamena (GMT+1:00)"),
        AFRICA_NIAMEY("Africa/Niamey (GMT+1:00)"),
        AFRICA_PORTO_NOVO("Africa/Porto-Novo (GMT+1:00)"),
        AFRICA_TUNIS("Africa/Tunis (GMT+1:00)"),
        AFRICA_WINDHOEK("Africa/Windhoek (GMT+1:00)"),
        ARCTIC_LONGYEARBYEN("Arctic/Longyearbyen (GMT+1:00)"),
        EUROPE_AMSTERDAM("Europe/Amsterdam (GMT+1:00)"),
        EUROPE_ANDORRA("Europe/Andorra (GMT+1:00)"),
        EUROPE_BELGRADE("Europe/Belgrade (GMT+1:00)"),
        EUROPE_BERLIN("Europe/Berlin (GMT+1:00)"),
        EUROPE_BRATISLAVA("Europe/Bratislava (GMT+1:00)"),
        EUROPE_BRUSSELS("Europe/Brussels (GMT+1:00)"),
        EUROPE_BUDAPEST("Europe/Budapest (GMT+1:00)"),
        EUROPE_COPENHAGEN("Europe/Copenhagen (GMT+1:00)"),
        EUROPE_GIBRALTAR("Europe/Gibraltar (GMT+1:00)"),
        EUROPE_LJUBLJANA("Europe/Ljubljana (GMT+1:00)"),
        EUROPE_LUXEMBOURG("Europe/Luxembourg (GMT+1:00)"),
        EUROPE_MADRID("Europe/Madrid (GMT+1:00)"),
        EUROPE_MALTA("Europe/Malta (GMT+1:00)"),
        EUROPE_MONACO("Europe/Monaco (GMT+1:00)"),
        EUROPE_OSLO("Europe/Oslo (GMT+1:00)"),
        EUROPE_PARIS("Europe/Paris (GMT+1:00)"),
        EUROPE_PODGORICA("Europe/Podgorica (GMT+1:00)"),
        EUROPE_PRAGUE("Europe/Prague (GMT+1:00)"),
        EUROPE_ROME("Europe/Rome (GMT+1:00)"),
        EUROPE_SAN_MARINO("Europe/San_Marino (GMT+1:00)"),
        EUROPE_SKOPJE("Europe/Skopje (GMT+1:00)"),
        EUROPE_STOCKHOLM("Europe/Stockholm (GMT+1:00)"),
        EUROPE_TIRANE("Europe/Tirane (GMT+1:00)"),
        EUROPE_VADUZ("Europe/Vaduz (GMT+1:00)"),
        EUROPE_VATICAN("Europe/Vatican (GMT+1:00)"),
        EUROPE_VIENNA("Europe/Vienna (GMT+1:00)"),
        EUROPE_WARSAW("Europe/Warsaw (GMT+1:00)"),
        EUROPE_ZAGREB("Europe/Zagreb (GMT+1:00)"),
        EUROPE_ZURICH("Europe/Zurich (GMT+1:00)"),
        POLAND("Poland (GMT+1:00)"),
        AFRICA_BLANTYRE("Africa/Blantyre (GMT+2:00)"),
        AFRICA_BUJUMBURA("Africa/Bujumbura (GMT+2:00)"),
        AFRICA_CAIRO("Africa/Cairo (GMT+2:00)"),
        AFRICA_GABORONE("Africa/Gaborone (GMT+2:00)"),
        AFRICA_HARARE("Africa/Harare (GMT+2:00)"),
        AFRICA_JOHANNESBURG("Africa/Johannesburg (GMT+2:00)"),
        AFRICA_KIGALI("Africa/Kigali (GMT+2:00)"),
        AFRICA_LUBUMBASHI("Africa/Lubumbashi (GMT+2:00)"),
        AFRICA_LUSAKA("Africa/Lusaka (GMT+2:00)"),
        AFRICA_MAPUTO("Africa/Maputo (GMT+2:00)"),
        AFRICA_MASERU("Africa/Maseru (GMT+2:00)"),
        AFRICA_MBABANE("Africa/Mbabane (GMT+2:00)"),
        AFRICA_TRIPOLI("Africa/Tripoli (GMT+2:00)"),
        ASIA_AMMAN("Asia/Amman (GMT+2:00)"),
        ASIA_BEIRUT("Asia/Beirut (GMT+2:00)"),
        ASIA_DAMASCUS("Asia/Damascus (GMT+2:00)"),
        ASIA_GAZA("Asia/Gaza (GMT+2:00)"),
        ASIA_ISTANBUL("Asia/Istanbul (GMT+2:00)"),
        ASIA_JERUSALEM("Asia/Jerusalem (GMT+2:00)"),
        ASIA_NICOSIA("Asia/Nicosia (GMT+2:00)"),
        ASIA_TEL_AVIV("Asia/Tel_Aviv (GMT+2:00)"),
        EGYPT("Egypt (GMT+2:00)"),
        EUROPE_ATHENS("Europe/Athens (GMT+2:00)"),
        EUROPE_BUCHAREST("Europe/Bucharest (GMT+2:00)"),
        EUROPE_CHISINAU("Europe/Chisinau (GMT+2:00)"),
        EUROPE_HELSINKI("Europe/Helsinki (GMT+2:00)"),
        EUROPE_ISTANBUL("Europe/Istanbul (GMT+2:00)"),
        EUROPE_KALININGRAD("Europe/Kaliningrad (GMT+2:00)"),
        EUROPE_KIEV("Europe/Kiev (GMT+2:00)"),
        EUROPE_MARIEHAMN("Europe/Mariehamn (GMT+2:00)"),
        EUROPE_MINSK("Europe/Minsk (GMT+2:00)"),
        EUROPE_NICOSIA("Europe/Nicosia (GMT+2:00)"),
        EUROPE_RIGA("Europe/Riga (GMT+2:00)"),
        EUROPE_SIMFEROPOL("Europe/Simferopol (GMT+2:00)"),
        EUROPE_SOFIA("Europe/Sofia (GMT+2:00)"),
        EUROPE_TALLINN("Europe/Tallinn (GMT+2:00)"),
        EUROPE_TIRASPOL("Europe/Tiraspol (GMT+2:00)"),
        EUROPE_UZHGOROD("Europe/Uzhgorod (GMT+2:00)"),
        EUROPE_VILNIUS("Europe/Vilnius (GMT+2:00)"),
        EUROPE_ZAPOROZHYE("Europe/Zaporozhye (GMT+2:00)"),
        ISRAEL("Israel (GMT+2:00)"),
        LIBYA("Libya (GMT+2:00)"),
        TURKEY("Turkey (GMT+2:00)"),
        AFRICA_ADDIS_ABABA("Africa/Addis_Ababa (GMT+3:00)"),
        AFRICA_ASMARA("Africa/Asmara (GMT+3:00)"),
        AFRICA_ASMERA("Africa/Asmera (GMT+3:00)"),
        AFRICA_DAR_ES_SALAAM("Africa/Dar_es_Salaam (GMT+3:00)"),
        AFRICA_DJIBOUTI("Africa/Djibouti (GMT+3:00)"),
        AFRICA_KAMPALA("Africa/Kampala (GMT+3:00)"),
        AFRICA_KHARTOUM("Africa/Khartoum (GMT+3:00)"),
        AFRICA_MOGADISHU("Africa/Mogadishu (GMT+3:00)"),
        AFRICA_NAIROBI("Africa/Nairobi (GMT+3:00)"),
        ANTARCTICA_SYOWA("Antarctica/Syowa (GMT+3:00)"),
        ASIA_ADEN("Asia/Aden (GMT+3:00)"),
        ASIA_BAGHDAD("Asia/Baghdad (GMT+3:00)"),
        ASIA_BAHRAIN("Asia/Bahrain (GMT+3:00)"),
        ASIA_KUWAIT("Asia/Kuwait (GMT+3:00)"),
        ASIA_QATAR("Asia/Qatar (GMT+3:00)"),
        ASIA_RIYADH("Asia/Riyadh (GMT+3:00)"),
        EUROPE_MOSCOW("Europe/Moscow (GMT+3:00)"),
        EUROPE_VOLGOGRAD("Europe/Volgograd (GMT+3:00)"),
        INDIAN_ANTANANARIVO("Indian/Antananarivo (GMT+3:00)"),
        INDIAN_COMORO("Indian/Comoro (GMT+3:00)"),
        INDIAN_MAYOTTE("Indian/Mayotte (GMT+3:00)"),
        ASIA_TEHRAN("Asia/Tehran (GMT+3:30)"),
        IRAN("Iran (GMT+3:30)"),
        ASIA_BAKU("Asia/Baku (GMT+4:00)"),
        ASIA_DUBAI("Asia/Dubai (GMT+4:00)"),
        ASIA_MUSCAT("Asia/Muscat (GMT+4:00)"),
        ASIA_TBILISI("Asia/Tbilisi (GMT+4:00)"),
        ASIA_YEREVAN("Asia/Yerevan (GMT+4:00)"),
        EUROPE_SAMARA("Europe/Samara (GMT+4:00)"),
        INDIAN_MAHE("Indian/Mahe (GMT+4:00)"),
        INDIAN_MAURITIUS("Indian/Mauritius (GMT+4:00)"),
        INDIAN_REUNION("Indian/Reunion (GMT+4:00)"),
        ASIA_KABUL("Asia/Kabul (GMT+4:30)"),
        ASIA_AQTAU("Asia/Aqtau (GMT+5:00)"),
        ASIA_AQTOBE("Asia/Aqtobe (GMT+5:00)"),
        ASIA_ASHGABAT("Asia/Ashgabat (GMT+5:00)"),
        ASIA_ASHKHABAD("Asia/Ashkhabad (GMT+5:00)"),
        ASIA_DUSHANBE("Asia/Dushanbe (GMT+5:00)"),
        ASIA_KARACHI("Asia/Karachi (GMT+5:00)"),
        ASIA_ORAL("Asia/Oral (GMT+5:00)"),
        ASIA_SAMARKAND("Asia/Samarkand (GMT+5:00)"),
        ASIA_TASHKENT("Asia/Tashkent (GMT+5:00)"),
        ASIA_YEKATERINBURG("Asia/Yekaterinburg (GMT+5:00)"),
        INDIAN_KERGUELEN("Indian/Kerguelen (GMT+5:00)"),
        INDIAN_MALDIVES("Indian/Maldives (GMT+5:00)"),
        ASIA_CALCUTTA("Asia/Calcutta (GMT+5:30)"),
        ASIA_COLOMBO("Asia/Colombo (GMT+5:30)"),
        ASIA_KATMANDU("Asia/Katmandu (GMT+5:45)"),
        ANTARCTICA_MAWSON("Antarctica/Mawson (GMT+6:00)"),
        ANTARCTICA_VOSTOK("Antarctica/Vostok (GMT+6:00)"),
        ASIA_ALMATY("Asia/Almaty (GMT+6:00)"),
        ASIA_BISHKEK("Asia/Bishkek (GMT+6:00)"),
        ASIA_DACCA("Asia/Dacca (GMT+6:00)"),
        ASIA_DHAKA("Asia/Dhaka (GMT+6:00)"),
        ASIA_NOVOSIBIRSK("Asia/Novosibirsk (GMT+6:00)"),
        ASIA_OMSK("Asia/Omsk (GMT+6:00)"),
        ASIA_QYZYLORDA("Asia/Qyzylorda (GMT+6:00)"),
        ASIA_THIMBU("Asia/Thimbu (GMT+6:00)"),
        ASIA_THIMPHU("Asia/Thimphu (GMT+6:00)"),
        INDIAN_CHAGOS("Indian/Chagos (GMT+6:00)"),
        ASIA_RANGOON("Asia/Rangoon (GMT+6:30)"),
        INDIAN_COCOS("Indian/Cocos (GMT+6:30)"),
        ANTARCTICA_DAVIS("Antarctica/Davis (GMT+7:00)"),
        ASIA_BANGKOK("Asia/Bangkok (GMT+7:00)"),
        ASIA_HOVD("Asia/Hovd (GMT+7:00)"),
        ASIA_JAKARTA("Asia/Jakarta (GMT+7:00)"),
        ASIA_KRASNOYARSK("Asia/Krasnoyarsk (GMT+7:00)"),
        ASIA_PHNOM_PENH("Asia/Phnom_Penh (GMT+7:00)"),
        ASIA_PONTIANAK("Asia/Pontianak (GMT+7:00)"),
        ASIA_SAIGON("Asia/Saigon (GMT+7:00)"),
        ASIA_VIENTIANE("Asia/Vientiane (GMT+7:00)"),
        INDIAN_CHRISTMAS("Indian/Christmas (GMT+7:00)"),
        ANTARCTICA_CASEY("Antarctica/Casey (GMT+8:00)"),
        ASIA_BRUNEI("Asia/Brunei (GMT+8:00)"),
        ASIA_CHONGQING("Asia/Chongqing (GMT+8:00)"),
        ASIA_CHUNGKING("Asia/Chungking (GMT+8:00)"),
        ASIA_HARBIN("Asia/Harbin (GMT+8:00)"),
        ASIA_HONG_KONG("Asia/Hong_Kong (GMT+8:00)"),
        ASIA_IRKUTSK("Asia/Irkutsk (GMT+8:00)"),
        ASIA_KASHGAR("Asia/Kashgar (GMT+8:00)"),
        ASIA_KUALA_LUMPUR("Asia/Kuala_Lumpur (GMT+8:00)"),
        ASIA_KUCHING("Asia/Kuching (GMT+8:00)"),
        ASIA_MACAO("Asia/Macao (GMT+8:00)"),
        ASIA_MACAU("Asia/Macau (GMT+8:00)"),
        ASIA_MAKASSAR("Asia/Makassar (GMT+8:00)"),
        ASIA_MANILA("Asia/Manila (GMT+8:00)"),
        ASIA_SHANGHAI("Asia/Shanghai (GMT+8:00)"),
        ASIA_SINGAPORE("Asia/Singapore (GMT+8:00)"),
        ASIA_TAIPEI("Asia/Taipei (GMT+8:00)"),
        ASIA_UJUNG_PANDANG("Asia/Ujung_Pandang (GMT+8:00)"),
        ASIA_ULAANBAATAR("Asia/Ulaanbaatar (GMT+8:00)"),
        ASIA_ULAN_BATOR("Asia/Ulan_Bator (GMT+8:00)"),
        ASIA_URUMQI("Asia/Urumqi (GMT+8:00)"),
        AUSTRALIA_PERTH("Australia/Perth (GMT+8:00)"),
        AUSTRALIA_WEST("Australia/West (GMT+8:00)"),
        HONGKONG("Hongkong (GMT+8:00)"),
        SINGAPORE("Singapore (GMT+8:00)"),
        AUSTRALIA_EUCLA("Australia/Eucla (GMT+8:45)"),
        ASIA_CHOIBALSAN("Asia/Choibalsan (GMT+9:00)"),
        ASIA_DILI("Asia/Dili (GMT+9:00)"),
        ASIA_JAYAPURA("Asia/Jayapura (GMT+9:00)"),
        ASIA_PYONGYANG("Asia/Pyongyang (GMT+9:00)"),
        ASIA_SEOUL("Asia/Seoul (GMT+9:00)"),
        ASIA_TOKYO("Asia/Tokyo (GMT+9:00)"),
        ASIA_YAKUTSK("Asia/Yakutsk (GMT+9:00)"),
        JAPAN("Japan (GMT+9:00)"),
        AUSTRALIA_ADELAIDE("Australia/Adelaide (GMT+9:30)"),
        AUSTRALIA_BROKEN_HILL("Australia/Broken_Hill (GMT+9:30)"),
        AUSTRALIA_DARWIN("Australia/Darwin (GMT+9:30)"),
        AUSTRALIA_NORTH("Australia/North (GMT+9:30)"),
        AUSTRALIA_SOUTH("Australia/South (GMT+9:30)"),
        AUSTRALIA_YANCOWINNA("Australia/Yancowinna (GMT+9:30)"),
        ANTARCTICA_DUMONTDURVILLE("Antarctica/DumontDUrville (GMT+10:00)"),
        ASIA_SAKHALIN("Asia/Sakhalin (GMT+10:00)"),
        ASIA_VLADIVOSTOK("Asia/Vladivostok (GMT+10:00)"),
        AUSTRALIA_ACT("Australia/ACT (GMT+10:00)"),
        AUSTRALIA_BRISBANE("Australia/Brisbane (GMT+10:00)"),
        AUSTRALIA_CANBERRA("Australia/Canberra (GMT+10:00)"),
        AUSTRALIA_CURRIE("Australia/Currie (GMT+10:00)"),
        AUSTRALIA_HOBART("Australia/Hobart (GMT+10:00)"),
        AUSTRALIA_LINDEMAN("Australia/Lindeman (GMT+10:00)"),
        AUSTRALIA_MELBOURNE("Australia/Melbourne (GMT+10:00)"),
        AUSTRALIA_NSW("Australia/NSW (GMT+10:00)"),
        AUSTRALIA_QUEENSLAND("Australia/Queensland (GMT+10:00)"),
        AUSTRALIA_SYDNEY("Australia/Sydney (GMT+10:00)"),
        AUSTRALIA_TASMANIA("Australia/Tasmania (GMT+10:00)"),
        AUSTRALIA_VICTORIA("Australia/Victoria (GMT+10:00)"),
        AUSTRALIA_LHI("Australia/LHI (GMT+10:30)"),
        AUSTRALIA_LORD_HOWE("Australia/Lord_Howe (GMT+10:30)"),
        ASIA_MAGADAN("Asia/Magadan (GMT+11:00)"),
        ANTARCTICA_MCMURDO("Antarctica/McMurdo (GMT+12:00)"),
        ANTARCTICA_SOUTH_POLE("Antarctica/South_Pole (GMT+12:00)"),
        ASIA_ANADYR("Asia/Anadyr (GMT+12:00)"),
        ASIA_KAMCHATKA("Asia/Kamchatka (GMT+12:00)"),
        KWAJALEIN("Kwajalein (GMT+12:00)"),
        
        ;
        
        private String text;
        private TimeZones(String text){
            this.text = text;
        }
        
        @Override
        public String getText(){
            return text;
        }
        
        public TimeZone getTimeZone(){
            String gmtOffset = text.substring(text.indexOf("(")+1, text.indexOf(")"));
            return TimeZone.getTimeZone(gmtOffset);
        }
        private final static List<String> zoneNames = new ArrayList<String>();
        
        static {
        	for (TimeZones zone: EnumSet.allOf(TimeZones.class)){
        		zoneNames.add(zone.name());
        	}
        }
        
        public static List<String> listZones(){
        	return zoneNames;
        }
    }

    public static enum WebDateFormat implements TextEnum {
        NOTE_DATE_TIME("MMM d, yyyy HH:mm a (z)"),
        HOS_REPORTS("yyyy-MM-dd HH:mm"),
        FUEL_STOPS("MMM d, yyyy HH:mm:ss a (z)"),
        DATE_RANGE_FIELDS("MMM d, yyyy"),
        TIME("HH:mm a (z)"),
        
        DURATION("HH:mm:ss"),
        
        NOTE_PRECISE_TIME("yyyy-MM-dd HH:mm:ss,SSS"),

        LAST_NOTE_CONFIGURATOR("yyyy-MM-dd HH:mm:ss z"),
        
        RALLY_DATE_FORMAT("yyyy-MM-dd'T'HH:mm:ssZ"),
        
        ;

        private String format;

        private WebDateFormat(String format) {
            this.format = format;
        }

        public String getText() {
            return format;
        }

        @Override
        public String toString() {
            return format;
        }

    }

    public static final String getCurrentMonth() {
        Calendar today = GregorianCalendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMMM");
        String month = sdf.format(today.getTime());
        return month;
    }

    public static final String[] getFiveDayPeriodLong() {
        String[] timeFrame = new String[5];
        Calendar today = GregorianCalendar.getInstance();
        today.add(Calendar.DATE, -2);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");

        for (int i = 0; i < 5; i++) {
            timeFrame[i] = sdf.format(today.getTime());
            today.add(Calendar.DATE, -1);
        }

        return timeFrame;
    }

    public static final String[] getFiveDayPeriodShort() {
        String[] timeFrame = new String[5];
        Calendar today = GregorianCalendar.getInstance();
        today.add(Calendar.DATE, -2);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE");

        for (int i = 0; i < 5; i++) {
            timeFrame[i] = sdf.format(today.getTime());
            today.add(Calendar.DATE, -1);
        }

        return timeFrame;
    }

    public static final String[] getTimeFrameOptions() {
        String[] timeFrame = new String[11];
        String[] fiveDays = getFiveDayPeriodLong();

        timeFrame[0] = "Today";
        timeFrame[1] = "Yesterday";

        for (int i = 2; i < 7; i++) {
            timeFrame[i] = fiveDays[i - 2];
        }

        timeFrame[7] = "Past Week";
        timeFrame[8] = getCurrentMonth();
        timeFrame[9] = "Past 30 Days";
        timeFrame[10] = "Past Year";

        return timeFrame;
    }

    private Calendar date;

    private String format;

    private WebDateFormat rawEnum;

    private SimpleDateFormat formatter;

    public AutomationCalendar(Calendar date, WebDateFormat format) {
        this(format);
        this.date = date;
    }

    public AutomationCalendar(String dateTime, WebDateFormat format) {
        this(format);
        
        if (dateTime.endsWith("Z")){
            dateTime = dateTime.substring(0, dateTime.indexOf(".")) + "-0000";
        }
        setDate(dateTime);
        if (format == WebDateFormat.RALLY_DATE_FORMAT){
            date.setTimeZone(TimeZones.US_MOUNTAIN.getTimeZone());
        }
    }

    public AutomationCalendar(WebDateFormat format) {
        this.format = format.toString();
        rawEnum = format;
        formatter = new SimpleDateFormat(this.format);
        date = GregorianCalendar.getInstance();
    }
    
    public WebDateFormat getFormat(){
        return rawEnum;
    }
    
    public static AutomationCalendar now(){
        return new AutomationCalendar();
    }
    
    public static AutomationCalendar now(WebDateFormat format){
        return new AutomationCalendar(format);
    }
    
    public AutomationCalendar(){
        this(WebDateFormat.NOTE_DATE_TIME);
    }

    public AutomationCalendar(Calendar calendar) {
        this();
        this.date = (Calendar) calendar.clone();
    }

    public AutomationCalendar(Long epochTime) {
        this();
        setDate(epochTime);
    }

    public AutomationCalendar addToDay(int amount) {
        date.add(Calendar.DAY_OF_YEAR, amount);
        return this;
    }

    public AutomationCalendar addToHours(int i){
        date.add(Calendar.HOUR_OF_DAY, i);
        return this;
    }

    public AutomationCalendar addToMilliseconds(int amount) {
        date.add(Calendar.MILLISECOND, amount);
        return this;
    }

    public AutomationCalendar addToMinutes(int amount) {
        date.add(Calendar.MINUTE, amount);
        return this;
    }

    public AutomationCalendar addToMonth(int amount) {
        date.add(Calendar.MONTH, amount);
        return this;
    }

    public AutomationCalendar addToSeconds(int amount) {
        date.add(Calendar.SECOND, amount);
        return this;
    }

    public AutomationCalendar addToYear(int amount) {
        date.add(Calendar.YEAR, amount);
        return this;
    }

    public AutomationCalendar changeDayOfMonthTo(int amount) {
        date.set(Calendar.DAY_OF_MONTH, amount);
        return this;
    }

    public AutomationCalendar changeDayOfWeekTo(int amount) {
        date.set(Calendar.DAY_OF_WEEK, amount);
        return this;
    }

    public AutomationCalendar changeDayOfYearTo(int amount) {
        date.set(Calendar.DAY_OF_YEAR, amount);
        return this;
    }
    
    public AutomationCalendar changeHourseTo(int i) {
        date.set(Calendar.HOUR_OF_DAY, i);
        return this;
    }

    public AutomationCalendar changeMillisecondsTo(int amount) {
        date.set(Calendar.MILLISECOND, amount);
        return this;
    }

    public AutomationCalendar changeMinutesTo(int amount) {
        date.set(Calendar.MINUTE, amount);
        return this;
    }

    public AutomationCalendar changeMonthTo(int amount) {
        date.set(Calendar.MONTH, amount);
        return this;
    }

    public AutomationCalendar changeSecondsTo(int amount) {
        date.set(Calendar.SECOND, amount);
        return this;
    }

    public AutomationCalendar changeYearTo(int amount) {
        date.set(Calendar.YEAR, amount);
        return this;
    }

    public boolean compareDays(AutomationCalendar compareAgainst) {
        compareAgainst.zeroTimeOfDay();
        AutomationCalendar original = new AutomationCalendar(date, rawEnum);
        original.zeroTimeOfDay();
        return compareAgainst.equals(original);
    }

    public boolean compareDays(Calendar compareAgainst) {
        AutomationCalendar provided = new AutomationCalendar(compareAgainst, rawEnum);
        return compareDays(provided);
    }
    

    public boolean compareDays(String compareAgainst) {
        AutomationCalendar provided = new AutomationCalendar(compareAgainst, rawEnum);
        return compareDays(provided);
    }

    /**
     * Returns the time of the current object minus the given time
     * 
     * @param AutomationCalendar
     * @return
     */
    public int compareTo(AutomationCalendar o) {
        return (int) (epochSecondsInt() - o.epochSecondsInt());
    }

    @Override
    public int compareTo(Calendar o) {
        return date.compareTo(o);
    }

    /**
     * Returns the time of the current object minus the given time
     * 
     * @param AutomationCalendar
     * @return
     */
    public int compareTo(String o) {
        return compareTo(new AutomationCalendar(o, rawEnum));
    }

    public boolean dateInRange(AutomationCalendar start, AutomationCalendar stop) {
        return dateInRange(start.getCalendar(), stop.getCalendar());
    }

    public boolean dateInRange(Calendar start, Calendar stop) {
        if (start.getTimeInMillis() > stop.getTimeInMillis()) {
            throw new IllegalArgumentException("Start: " + formatter.format(start.getTime()) + " is after Stop: " 
                    + formatter.format(stop.getTime()));
        }
        return (start.compareTo(date) <= 0 && stop.compareTo(date) >= 0);
    }

    public boolean dateInRange(String start, String stop) {
        AutomationCalendar begin = new AutomationCalendar(start, rawEnum);
        AutomationCalendar end = new AutomationCalendar(stop, rawEnum);
        return dateInRange(begin, end);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AutomationCalendar){
            return epochSecondsInt() == ((AutomationCalendar) obj).epochSecondsInt();
        } else if (obj instanceof Calendar){
            return date.equals(obj);
        } else if (obj instanceof Date){
            return date.getTime().equals(obj);
        }
        return false;
    }

    public Calendar getCalendar() {
        return date;
    }

    public Date getDate() {
        return date.getTime();
    }

    public Integer epochSecondsInt() {
        return epochSeconds().intValue();
    }
    
    public Long epochSeconds() {
        return epochTime()/1000;
    }
    
    public Long epochTime(){
        return date.getTimeInMillis();
    }

    public AutomationCalendar setDate(String dateTime) {
        try {
            date.setTime(formatter.parse(dateTime));
        } catch (ParseException e) {
            throw new IllegalArgumentException(dateTime + " does not match the pattern " + formatter.toPattern());
        }
        return this;
    }
    
    public AutomationCalendar setDate(AutomationCalendar time){
        date.setTime(time.getDate());
        return this;
    }

    public AutomationCalendar setFormat(WebDateFormat format) {
        this.format = format.toString();
        formatter = new SimpleDateFormat(this.format);
        return this;
        
    }
    
    public AutomationCalendar setTimeZone(TimeZones timeZone){
        date.setTimeZone(timeZone.getTimeZone());
        return this;
    }

    @Override
    public String toString() {
        return formatter.format(date.getTime());
    }

    public String toString(Calendar date) {
        SimpleDateFormat yesterday = new SimpleDateFormat(format);
        return yesterday.format(date);
    }
    
    public String toString(WebDateFormat format){
        return new AutomationCalendar(this.getCalendar(), format).toString();
    }
    
    public AutomationCalendar zeroTimeOfDay(){
        setTimeZone(TimeZones.UTC);
        changeMillisecondsTo(0);
        changeMinutesTo(0);
        changeSecondsTo(0);
        changeHourseTo(0);
        return this;
    }
    
    public int getMonth(){
        return this.date.get(Calendar.MONTH);
    }
    
    public int getWeekOfMonth(){
        return this.date.get(Calendar.WEEK_OF_MONTH);
    }
    
    public int getDayOfWeek(){
        return this.date.get(Calendar.DAY_OF_WEEK);
    }

    public int toInt() {
        Long time = date.getTimeInMillis()/1000;
        return time.intValue();
    }

    public AutomationCalendar copy() {
        return new AutomationCalendar(date, rawEnum);
    }

    public Long getDelta(AutomationCalendar time_last) {
        long delta = epochTime() - time_last.epochTime();
        return delta;
    }

    public AutomationCalendar setDate(long ephochTime) {
        this.date.setTimeInMillis(ephochTime);
        return this;
    }
    
    public AutomationCalendar setDate(int epochTimeWithoutMillis){
        setDate(epochTimeWithoutMillis * 1000l);
        return this;
    }

}
