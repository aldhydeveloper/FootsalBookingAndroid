package com.ns.queenfutsal.Common;

public class Common {
    public static String KEY_LAPANGAN;
    public static String userId;
    public static String Fullname;
    public static String Password;
    public static String Email;
    public static String NoHp;

    public static final int TIME_SLOT_TOTAL = 15;
    public static final Object DISABLE_TAG = "DISABLE" ;

    public static String convertTimeSlotToString(int slot) {
        switch (slot) {
            case 0:
                return "07:00 - 08:00";
            case 1:
                return "08:00 - 09:00";
            case 2:
                return "09:00 - 10:00";
            case 3:
                return "10:00 - 11:00";
            case 4:
                return "11:00 - 12:00";
            case 5:
                return "12:00 - 13:00";
            case 6:
                return "13:00 - 14:00";
            case 7:
                return "14:00 - 15:00";
            case 8:
                return "15:00 - 16:00";
            case 9:
                return "16:00 - 17:00";
            case 10:
                return "17:00 - 18:00";
            case 11:
                return "18:00 - 19:00";
            case 12:
                return "19:00 - 20:00";
            case 13:
                return "20:00 - 21:00";
            case 14:
                return "21:00 - 22:00";
            default:
                return "Closed";
        }
    }
}
