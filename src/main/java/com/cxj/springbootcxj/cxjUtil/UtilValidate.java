package com.cxj.springbootcxj.cxjUtil;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.validator.EmailValidator;
import org.apache.commons.validator.routines.CalendarValidator;

/**
 * @author : ChuXianJie
 * @date : 2019-12-30 14:10
 */
public class UtilValidate {

    public static final String module = UtilValidate.class.getName();
    public static final String digits = "0123456789";
    public static final String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
    public static final String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String decimalPointDelimiter = ".";
    public static final String phoneNumberDelimiters = "()- ";
    public static final String validUSPhoneChars = "0123456789()- ";
    public static final String validWorldPhoneChars = "0123456789()- +";
    public static final String SSNDelimiters = "- ";
    public static final String validSSNChars = "0123456789- ";
    public static final int digitsInSocialSecurityNumber = 9;
    public static final int digitsInUSPhoneNumber = 10;
    public static final int digitsInUSPhoneAreaCode = 3;
    public static final int digitsInUSPhoneMainNumber = 7;
    public static final String ZipCodeDelimiters = "-";
    public static final String ZipCodeDelimeter = "-";
    public static final String validZipCodeChars = "0123456789-";
    public static final int digitsInZipCode1 = 5;
    public static final int digitsInZipCode2 = 9;
    public static final String creditCardDelimiters = " -";
    public static final int[] daysInMonth = new int[]{31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static final String USStateCodeDelimiter = "|";
    public static final String USStateCodes = "AL|AK|AS|AZ|AR|CA|CO|CT|DE|DC|FM|FL|GA|GU|HI|ID|IL|IN|IA|KS|KY|LA|ME|MH|MD|MA|MI|MN|MS|MO|MT|NE|NV|NH|NJ|NM|NY|NC|ND|MP|OH|OK|OR|PW|PA|PR|RI|SC|SD|TN|TX|UT|VT|VI|VA|WA|WV|WI|WY|AE|AA|AE|AE|AP";
    public static final String ContiguousUSStateCodes = "AL|AZ|AR|CA|CO|CT|DE|DC|FL|GA|ID|IL|IN|IA|KS|KY|LA|ME|MD|MA|MI|MN|MS|MO|MT|NE|NV|NH|NJ|NM|NY|NC|ND|OH|OK|OR|PA|RI|SC|SD|TN|TX|UT|VT|VA|WA|WV|WI|WY";
    private static final boolean defaultEmptyOK = true;
    private static final String hexDigits = "0123456789abcdefABCDEF";
    private static final String whitespace = " \t\n\r";
    private static final String isNotEmptyMsg = "This field cannot be empty, please enter a value.";
    private static final String isStateCodeMsg = "The State Code must be a valid two character U.S. state abbreviation(like CA for California).";
    private static final String isContiguousStateCodeMsg = "The State Code must be a valid two character U.S. state abbreviation for one of the 48 contiguous United States (like CA for California).";
    private static final String isZipCodeMsg = "The ZIP Code must be a 5 or 9 digit U.S. ZIP Code(like 94043).";
    private static final String isUSPhoneMsg = "The US Phone must be a 10 digit U.S. phone number(like 415-555-1212).";
    private static final String isUSPhoneAreaCodeMsg = "The Phone Number Area Code must be 3 digits.";
    private static final String isUSPhoneMainNumberMsg = "The Phone Number must be 7 digits.";
    private static final String isContiguousZipCodeMsg = "Zip Code is not a valid Zip Code for one of the 48 contiguous United States .";
    private static final String isInternationalPhoneNumberMsg = "The World Phone must be a valid international phone number.";
    private static final String isSSNMsg = "The SSN must be a 9 digit U.S. social security number(like 123-45-6789).";
    private static final String isEmailMsg = "The Email must be a valid email address(like john@email.com). Please re-enter it now.";
    private static final String isAnyCardMsg = "The credit card number is not a valid card number.";
    private static final String isCreditCardPrefixMsg = " is not a valid ";
    private static final String isCreditCardSuffixMsg = " credit card number.";
    private static final String isDayMsg = "The Day must be a day number between 1 and 31. ";
    private static final String isMonthMsg = "The Month must be a month number between 1 and 12. ";
    private static final String isYearMsg = "The Year must be a 2 or 4 digit year number. ";
    private static final String isDatePrefixMsg = "The Day, Month, and Year for ";
    private static final String isDateSuffixMsg = " do not form a valid date.  Please reenter them now.";
    private static final String isHourMsg = "The Hour must be a number between 0 and 23.";
    private static final String isMinuteMsg = "The Minute must be a number between 0 and 59.";
    private static final String isSecondMsg = "The Second must be a number between 0 and 59.";
    private static final String isTimeMsg = "The Time must be a valid time formed like: HH:MM or HH:MM:SS.";
    private static final String isDateMsg = "The Date must be a valid date formed like: MM/YY, MM/YYYY, MM/DD/YY, or MM/DD/YYYY.";
    private static final String isDateAfterToday = "The Date must be a valid date after today, and formed like: MM/YY, MM/YYYY, MM/DD/YY, or MM/DD/YYYY.";
    private static final String isIntegerMsg = "The Number must be a valid unsigned whole decimal number.";
    private static final String isSignedIntegerMsg = "The Number must be a valid signed whole decimal number.";
    private static final String isLongMsg = "The Number must be a valid unsigned whole decimal number.";
    private static final String isSignedLongMsg = "The Number must be a valid signed whole decimal number.";
    private static final String isFloatMsg = "The Number must be a valid unsigned decimal number.";
    private static final String isSignedFloatMsg = "The Number must be a valid signed decimal number.";
    private static final String isSignedDoubleMsg = "The Number must be a valid signed decimal number.";

    public UtilValidate() {
    }

    public static boolean areEqual(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        } else {
            return obj.equals(obj2);
        }
    }

    public static boolean isEmpty(Object o) {
        return ObjectType.isEmpty(o);
    }

    public static boolean isNotEmpty(Object o) {
        return !ObjectType.isEmpty(o);
    }

    public static boolean isEmpty(IsEmpty o) {
        return o == null || o.isEmpty();
    }

    public static boolean isNotEmpty(IsEmpty o) {
        return o != null && !o.isEmpty();
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static <E> boolean isEmpty(Collection<E> c) {
        return c == null || c.isEmpty();
    }

    public static <K, E> boolean isEmpty(Map<K, E> m) {
        return m == null || m.isEmpty();
    }

    public static <E> boolean isEmpty(CharSequence c) {
        return c == null || c.length() == 0;
    }

    public static boolean isNotEmpty(String s) {
        return s != null && s.length() > 0;
    }

    public static <E> boolean isNotEmpty(Collection<E> c) {
        return c != null && !c.isEmpty();
    }

    public static <E> boolean isNotEmpty(CharSequence c) {
        return c != null && c.length() > 0;
    }

    public static boolean isString(Object obj) {
        return obj != null && obj instanceof String;
    }

    public static boolean isWhitespace(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            for (int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                if (" \t\n\r".indexOf(c) == -1) {
                    return false;
                }
            }

            return true;
        }
    }

    public static String stripCharsInBag(String s, String bag) {
        StringBuilder stringBuilder = new StringBuilder("");

        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (bag.indexOf(c) == -1) {
                stringBuilder.append(c);
            }
        }

        return stringBuilder.toString();
    }

    public static String stripCharsNotInBag(String s, String bag) {
        StringBuilder stringBuilder = new StringBuilder("");

        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (bag.indexOf(c) != -1) {
                stringBuilder.append(c);
            }
        }

        return stringBuilder.toString();
    }

    public static String stripWhitespace(String s) {
        return stripCharsInBag(s, " \t\n\r");
    }

    public static boolean charInString(char c, String s) {
        return s.indexOf(c) != -1;
    }

    public static String stripInitialWhitespace(String s) {
        int i;
        for (i = 0; i < s.length() && charInString(s.charAt(i), " \t\n\r"); ++i) {
        }

        return s.substring(i);
    }

    public static boolean isLetter(char c) {
        return Character.isLetter(c);
    }

    public static boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    public static boolean isLetterOrDigit(char c) {
        return Character.isLetterOrDigit(c);
    }

    public static boolean isHexDigit(char c) {
        return "0123456789abcdefABCDEF".indexOf(c) >= 0;
    }

    public static boolean isInteger(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            for (int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                if (!isDigit(c)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isSignedInteger(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            try {
                Integer.parseInt(s);
                return true;
            } catch (Exception var2) {
                return false;
            }
        }
    }

    public static boolean isSignedLong(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            try {
                Long.parseLong(s);
                return true;
            } catch (Exception var2) {
                return false;
            }
        }
    }

    public static boolean isPositiveInteger(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            try {
                long temp = Long.parseLong(s);
                return temp > 0L;
            } catch (Exception var3) {
                return false;
            }
        }
    }

    public static boolean isNonnegativeInteger(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            try {
                int temp = Integer.parseInt(s);
                return temp >= 0;
            } catch (Exception var2) {
                return false;
            }
        }
    }

    public static boolean isNegativeInteger(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            try {
                int temp = Integer.parseInt(s);
                return temp < 0;
            } catch (Exception var2) {
                return false;
            }
        }
    }

    public static boolean isNonpositiveInteger(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            try {
                int temp = Integer.parseInt(s);
                return temp <= 0;
            } catch (Exception var2) {
                return false;
            }
        }
    }

    public static boolean isFloat(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            boolean seenDecimalPoint = false;
            if (s.startsWith(".")) {
                return false;
            } else {
                for (int i = 0; i < s.length(); ++i) {
                    char c = s.charAt(i);
                    if (c == ".".charAt(0)) {
                        if (seenDecimalPoint) {
                            return false;
                        }

                        seenDecimalPoint = true;
                    } else if (!isDigit(c)) {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    public static boolean isFloat(String s, boolean allowNegative, boolean allowPositive, int minDecimal,
            int maxDecimal) {
        if (isEmpty(s)) {
            return true;
        } else {
            try {
                float temp = Float.parseFloat(s);
                if (!allowNegative && temp < 0.0F) {
                    return false;
                } else if (!allowPositive && temp > 0.0F) {
                    return false;
                } else {
                    int decimalPoint = s.indexOf(".");
                    if (decimalPoint == -1) {
                        return minDecimal <= 0;
                    } else {
                        int numDecimals = s.length() - decimalPoint - 1;
                        if (minDecimal >= 0 && numDecimals < minDecimal) {
                            return false;
                        } else {
                            return maxDecimal < 0 || numDecimals <= maxDecimal;
                        }
                    }
                }
            } catch (Exception var8) {
                return false;
            }
        }
    }

    public static boolean isDouble(String s, boolean allowNegative, boolean allowPositive, int minDecimal,
            int maxDecimal) {
        if (isEmpty(s)) {
            return true;
        } else {
            try {
                double temp = Double.parseDouble(s);
                if (!allowNegative && temp < 0.0D) {
                    return false;
                } else if (!allowPositive && temp > 0.0D) {
                    return false;
                } else {
                    int decimalPoint = s.indexOf(".");
                    if (decimalPoint == -1) {
                        return minDecimal <= 0;
                    } else {
                        int numDecimals = s.length() - decimalPoint - 1;
                        if (minDecimal >= 0 && numDecimals < minDecimal) {
                            return false;
                        } else {
                            return maxDecimal < 0 || numDecimals <= maxDecimal;
                        }
                    }
                }
            } catch (Exception var9) {
                return false;
            }
        }
    }

    public static boolean isSignedFloat(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            try {
                Float.parseFloat(s);
                return true;
            } catch (Exception var2) {
                return false;
            }
        }
    }

    public static boolean isSignedDouble(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            try {
                Double.parseDouble(s);
                return true;
            } catch (Exception var2) {
                return false;
            }
        }
    }

    public static boolean isAlphabetic(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            for (int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                if (!isLetter(c)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isAlphanumeric(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            for (int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                if (!isLetterOrDigit(c)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isSSN(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            String normalizedSSN = stripCharsInBag(s, "- ");
            if (normalizedSSN == null) {
                return true;
            } else {
                return isInteger(normalizedSSN) && normalizedSSN.length() == 9;
            }
        }
    }

    public static boolean isUSPhoneNumber(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            String normalizedPhone = stripCharsInBag(s, "()- ");
            if (normalizedPhone == null) {
                return true;
            } else {
                return isInteger(normalizedPhone) && normalizedPhone.length() == 10;
            }
        }
    }

    public static boolean isUSPhoneAreaCode(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            String normalizedPhone = stripCharsInBag(s, "()- ");
            if (normalizedPhone == null) {
                return true;
            } else {
                return isInteger(normalizedPhone) && normalizedPhone.length() == 3;
            }
        }
    }

    public static boolean isUSPhoneMainNumber(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            String normalizedPhone = stripCharsInBag(s, "()- ");
            if (normalizedPhone == null) {
                return true;
            } else {
                return isInteger(normalizedPhone) && normalizedPhone.length() == 7;
            }
        }
    }

    public static boolean isInternationalPhoneNumber(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            String normalizedPhone = stripCharsInBag(s, "()- ");
            return isPositiveInteger(normalizedPhone);
        }
    }

    public static boolean isZipCode(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            String normalizedZip = stripCharsInBag(s, "-");
            if (normalizedZip == null) {
                return true;
            } else {
                return isInteger(normalizedZip) && (normalizedZip.length() == 5 || normalizedZip.length() == 9);
            }
        }
    }

    public static boolean isContiguousZipCode(String s) {
        boolean retval = false;
        if (isZipCode(s)) {
            if (isEmpty(s)) {
                retval = true;
            } else {
                String normalizedZip = s.substring(0, 5);
                int iZip = Integer.parseInt(normalizedZip);
                if ((iZip < 96701 || iZip > 96898) && (iZip < 99501 || iZip > 99950)) {
                    retval = true;
                } else {
                    retval = false;
                }
            }
        }

        return retval;
    }

    public static boolean isStateCode(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            return "AL|AK|AS|AZ|AR|CA|CO|CT|DE|DC|FM|FL|GA|GU|HI|ID|IL|IN|IA|KS|KY|LA|ME|MH|MD|MA|MI|MN|MS|MO|MT|NE|NV|NH|NJ|NM|NY|NC|ND|MP|OH|OK|OR|PW|PA|PR|RI|SC|SD|TN|TX|UT|VT|VI|VA|WA|WV|WI|WY|AE|AA|AE|AE|AP"
                    .indexOf(s) != -1 && s.indexOf("|") == -1;
        }
    }

    public static boolean isContiguousStateCode(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            return "AL|AZ|AR|CA|CO|CT|DE|DC|FL|GA|ID|IL|IN|IA|KS|KY|LA|ME|MD|MA|MI|MN|MS|MO|MT|NE|NV|NH|NJ|NM|NY|NC|ND|OH|OK|OR|PA|RI|SC|SD|TN|TX|UT|VT|VA|WA|WV|WI|WY"
                    .indexOf(s) != -1 && s.indexOf("|") == -1;
        }
    }

    public static boolean isEmail(String s) {
        return isEmpty(s) ? true : EmailValidator.getInstance().isValid(s);
    }

    public static boolean isUrl(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            return s.indexOf("://") != -1;
        }
    }

    public static boolean isYear(String s) {
        if (isEmpty(s)) {
            return true;
        } else if (!isNonnegativeInteger(s)) {
            return false;
        } else {
            return s.length() == 2 || s.length() == 4;
        }
    }

    public static boolean isIntegerInRange(String s, int a, int b) {
        if (isEmpty(s)) {
            return true;
        } else if (!isSignedInteger(s)) {
            return false;
        } else {
            int num = Integer.parseInt(s);
            return num >= a && num <= b;
        }
    }

    public static boolean isMonth(String s) {
        return isEmpty(s) ? true : isIntegerInRange(s, 1, 12);
    }

    public static boolean isDay(String s) {
        return isEmpty(s) ? true : isIntegerInRange(s, 1, 31);
    }

    public static int daysInFebruary(int year) {
        return year % 4 != 0 || year % 100 == 0 && year % 400 != 0 ? 28 : 29;
    }

    public static boolean isHour(String s) {
        return isEmpty(s) ? true : isIntegerInRange(s, 0, 23);
    }

    public static boolean isMinute(String s) {
        return isEmpty(s) ? true : isIntegerInRange(s, 0, 59);
    }

    public static boolean isSecond(String s) {
        return isEmpty(s) ? true : isIntegerInRange(s, 0, 59);
    }

    public static boolean isDate(String year, String month, String day) {
        if (isYear(year) && isMonth(month) && isDay(day)) {
            int intYear = Integer.parseInt(year);
            int intMonth = Integer.parseInt(month);
            int intDay = Integer.parseInt(day);
            if (intDay > daysInMonth[intMonth - 1]) {
                return false;
            } else {
                return intMonth != 2 || intDay <= daysInFebruary(intYear);
            }
        } else {
            return false;
        }
    }

    public static boolean isDate(String date) {
        if (isEmpty(date)) {
            return true;
        } else {
            int dateSlash1;
            int dateSlash2;
            String month;
            String day;
            String year;
            if (date.length() == 8) {
                dateSlash1 = 4;
                dateSlash2 = 6;
                year = date.substring(0, dateSlash1);
                month = date.substring(dateSlash1 + 1, dateSlash2);
                day = date.substring(dateSlash2 + 1);
            } else {
                dateSlash1 = date.indexOf("/");
                dateSlash2 = date.lastIndexOf("/");
                if (dateSlash1 > 0 && dateSlash1 != dateSlash2) {
                    month = date.substring(0, dateSlash1);
                    day = date.substring(dateSlash1 + 1, dateSlash2);
                    year = date.substring(dateSlash2 + 1);
                } else {
                    dateSlash1 = date.indexOf("-");
                    dateSlash2 = date.lastIndexOf("-");
                    if (dateSlash1 <= 0 || dateSlash1 == dateSlash2) {
                        return false;
                    }

                    year = date.substring(0, dateSlash1);
                    month = date.substring(dateSlash1 + 1, dateSlash2);
                    day = date.substring(dateSlash2 + 1);
                }
            }

            return isDate(year, month, day);
        }
    }

    public static boolean isDateAfterToday(String date) {
        if (isEmpty(date)) {
            return true;
        } else {
            int dateSlash1 = date.indexOf("/");
            int dateSlash2 = date.lastIndexOf("/");
            if (dateSlash1 <= 0) {
                return false;
            } else {
                Date passed = null;
                String month;
                String day;
                String year;
                if (dateSlash1 == dateSlash2) {
                    month = date.substring(0, dateSlash1);
                    day = "28";
                    year = date.substring(dateSlash1 + 1);
                    if (!isDate(year, month, day)) {
                        return false;
                    }

                    try {
                        int monthInt = Integer.parseInt(month);
                        int yearInt = Integer.parseInt(year);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(yearInt, monthInt - 1, 0, 0, 0, 0);
                        calendar.add(2, 1);
                        passed = new Date(calendar.getTime().getTime());
                    } catch (Exception var10) {
                        passed = null;
                    }
                } else {
                    month = date.substring(0, dateSlash1);
                    day = date.substring(dateSlash1 + 1, dateSlash2);
                    year = date.substring(dateSlash2 + 1);
                    if (!isDate(year, month, day)) {
                        return false;
                    }

                    passed = UtilDateTime.toDate(month, day, year, "0", "0", "0");
                }

                Date now = UtilDateTime.nowDate();
                return passed != null ? passed.after(now) : false;
            }
        }
    }

    public static boolean isDateBeforeToday(String date) {
        if (isEmpty(date)) {
            return true;
        } else {
            int dateSlash1 = date.indexOf("/");
            int dateSlash2 = date.lastIndexOf("/");
            if (dateSlash1 <= 0) {
                return true;
            } else {
                Date passed = null;
                String month;
                String day;
                String year;
                if (dateSlash1 == dateSlash2) {
                    month = date.substring(0, dateSlash1);
                    day = "28";
                    year = date.substring(dateSlash1 + 1);
                    if (!isDate(year, month, day)) {
                        return false;
                    }

                    try {
                        int monthInt = Integer.parseInt(month);
                        int yearInt = Integer.parseInt(year);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(yearInt, monthInt - 1, 0, 0, 0, 0);
                        calendar.add(2, 1);
                        passed = new Date(calendar.getTime().getTime());
                    } catch (Exception var10) {
                        passed = null;
                    }
                } else {
                    month = date.substring(0, dateSlash1);
                    day = date.substring(dateSlash1 + 1, dateSlash2);
                    year = date.substring(dateSlash2 + 1);
                    if (!isDate(year, month, day)) {
                        return false;
                    }

                    passed = UtilDateTime.toDate(month, day, year, "0", "0", "0");
                }

                Date now = UtilDateTime.nowDate();
                return passed != null ? passed.before(now) : false;
            }
        }
    }

    public static boolean isDateBeforeNow(Timestamp date) {
        Timestamp now = UtilDateTime.nowTimestamp();
        return date != null ? date.before(now) : false;
    }

    public static boolean isDateAfterNow(Timestamp date) {
        Timestamp now = UtilDateTime.nowTimestamp();
        return date != null ? date.after(now) : false;
    }

    public static boolean isTime(String hour, String minute, String second) {
        return isHour(hour) && isMinute(minute) && isSecond(second);
    }

    public static boolean isTime(String time) {
        if (isEmpty(time)) {
            return true;
        } else {
            int timeColon1 = time.indexOf(":");
            int timeColon2 = time.lastIndexOf(":");
            if (timeColon1 <= 0) {
                return false;
            } else {
                String hour = time.substring(0, timeColon1);
                String minute;
                String second;
                if (timeColon1 == timeColon2) {
                    minute = time.substring(timeColon1 + 1);
                    second = "0";
                } else {
                    minute = time.substring(timeColon1 + 1, timeColon2);
                    second = time.substring(timeColon2 + 1);
                }

                return isTime(hour, minute, second);
            }
        }
    }

    public static boolean isDateTime(String value, String pattern, Locale locale, TimeZone timeZone) {
        CalendarValidator validator = new CalendarValidator();
        if (timeZone == null) {
            return validator.validate(value, pattern, locale) != null;
        } else {
            return validator.validate(value, pattern, locale, timeZone) != null;
        }
    }

    public static boolean isDateTime(String value, String pattern, Locale locale) {
        return isDateTime(value, pattern, locale, (TimeZone) null);
    }

    public static boolean isTimestamp(String value) {
        return value.length() == 10 ? isDateTime(value, "yyyy-MM-dd", Locale.getDefault())
                : isDateTime(value, "yyyy-MM-dd HH:mm:ss.S", Locale.getDefault());
    }

    public static boolean isValueLinkCard(String stPassed) {
        if (isEmpty(stPassed)) {
            return true;
        } else {
            String st = stripCharsInBag(stPassed, " -");
            return st.length() == 16 && (st.startsWith("7") || st.startsWith("6"));
        }
    }

    public static boolean isOFBGiftCard(String stPassed) {
        if (isEmpty(stPassed)) {
            return true;
        } else {
            String st = stripCharsInBag(stPassed, " -");
            return st.length() == 15 && sumIsMod10(getLuhnSum(st));
        }
    }

    public static boolean isGiftCard(String stPassed) {
        if (isOFBGiftCard(stPassed)) {
            return true;
        } else {
            return isValueLinkCard(stPassed);
        }
    }

    public static int getLuhnSum(String stPassed) {
        stPassed = stPassed.replaceAll("\\D", "");
        int len = stPassed.length();
        int sum = 0;
        int mul = 1;

        for (int i = len - 1; i >= 0; --i) {
            int digit = Character.digit(stPassed.charAt(i), 10);
            digit *= mul == 1 ? mul++ : mul--;
            sum += digit >= 10 ? digit % 10 + 1 : digit;
        }

        return sum;
    }

    public static int getLuhnCheckDigit(String stPassed) {
        int sum = getLuhnSum(stPassed);
        int mod = ((sum / 10 + 1) * 10 - sum) % 10;
        return 10 - mod;
    }

    public static boolean sumIsMod10(int sum) {
        return sum % 10 == 0;
    }

    public static String appendCheckDigit(String stPassed) {
        String checkDigit = Integer.toString(getLuhnCheckDigit(stPassed));
        return stPassed + checkDigit;
    }

    public static boolean isCreditCard(String stPassed) {
        if (isEmpty(stPassed)) {
            return true;
        } else {
            String st = stripCharsInBag(stPassed, " -");
            if (st == null) {
                return true;
            } else if (!isInteger(st)) {
                return false;
            } else {
                return st.length() > 19 ? false : sumIsMod10(getLuhnSum(st));
            }
        }
    }

    public static boolean isVisa(String cc) {
        return (cc.length() == 16 || cc.length() == 13) && cc.substring(0, 1).equals("4") ? isCreditCard(cc) : false;
    }

    public static boolean isMasterCard(String cc) {
        int firstdig = Integer.parseInt(cc.substring(0, 1));
        int seconddig = Integer.parseInt(cc.substring(1, 2));
        return cc.length() == 16 && firstdig == 5 && seconddig >= 1 && seconddig <= 5 ? isCreditCard(cc) : false;
    }

    public static boolean isAmericanExpress(String cc) {
        int firstdig = Integer.parseInt(cc.substring(0, 1));
        int seconddig = Integer.parseInt(cc.substring(1, 2));
        return cc.length() != 15 || firstdig != 3 || seconddig != 4 && seconddig != 7 ? false : isCreditCard(cc);
    }

    public static boolean isDinersClub(String cc) {
        int firstdig = Integer.parseInt(cc.substring(0, 1));
        int seconddig = Integer.parseInt(cc.substring(1, 2));
        return cc.length() != 14 || firstdig != 3 || seconddig != 0 && seconddig != 6 && seconddig != 8 ? false
                : isCreditCard(cc);
    }

    public static boolean isCarteBlanche(String cc) {
        return isDinersClub(cc);
    }

    public static boolean isDiscover(String cc) {
        String first4digs = cc.substring(0, 4);
        return cc.length() == 16 && first4digs.equals("6011") ? isCreditCard(cc) : false;
    }

    public static boolean isEnRoute(String cc) {
        String first4digs = cc.substring(0, 4);
        return cc.length() != 15 || !first4digs.equals("2014") && !first4digs.equals("2149") ? false : isCreditCard(cc);
    }

    public static boolean isJCB(String cc) {
        String first4digs = cc.substring(0, 4);
        return cc.length() != 16 || !first4digs.equals("3088") && !first4digs.equals("3096") && !first4digs
                .equals("3112") && !first4digs.equals("3158") && !first4digs.equals("3337") && !first4digs
                .equals("3528") ? false : isCreditCard(cc);
    }

    public static boolean isswitch(String cc) {
        String first4digs = cc.substring(0, 4);
        String first6digs = cc.substring(0, 6);
        return cc.length() != 16 && cc.length() != 18 && cc.length() != 19 || !first4digs.equals("4903") && !first4digs
                .equals("4905") && !first4digs.equals("4911") && !first4digs.equals("4936") && !first6digs
                .equals("564182") && !first6digs.equals("633110") && !first4digs.equals("6333") && !first4digs
                .equals("6759") ? false : isCreditCard(cc);
    }

    public static boolean isSolo(String cc) {
        String first4digs = cc.substring(0, 4);
        String first2digs = cc.substring(0, 2);
        return cc.length() != 16 && cc.length() != 18 && cc.length() != 19 || !first2digs.equals("63") && !first4digs
                .equals("6767") ? false : isCreditCard(cc);
    }

    public static boolean isVisaElectron(String cc) {
        String first6digs = cc.substring(0, 6);
        String first4digs = cc.substring(0, 4);
        return cc.length() != 16 || !first6digs.equals("417500") && !first4digs.equals("4917") && !first4digs
                .equals("4913") && !first4digs.equals("4508") && !first4digs.equals("4844") && !first4digs
                .equals("4027") ? false : isCreditCard(cc);
    }

    public static boolean isAnyCard(String ccPassed) {
        if (isEmpty(ccPassed)) {
            return true;
        } else {
            String cc = stripCharsInBag(ccPassed, " -");
            if (!isCreditCard(cc)) {
                return false;
            } else {
                return isMasterCard(cc) || isVisa(cc) || isAmericanExpress(cc) || isDinersClub(cc) || isDiscover(cc)
                        || isEnRoute(cc) || isJCB(cc) || isSolo(cc) || isswitch(cc) || isVisaElectron(cc);
            }
        }
    }

    public static boolean isAnyPhoneNumber(String phoneNumber) {
        if (isEmpty(phoneNumber)) {
            return true;
        } else {
            Pattern p = Pattern
                    .compile("^((13\\d{9}$)|(15[0,1,2,3,5,6,7,8,9]\\d{8}$)|(18[0,2,5,6,7,8,9]\\d{8}$)|(147\\d{8})$)");
            Matcher m = p.matcher(phoneNumber);
            return m.matches();
        }
    }

    public static String getCardType(String ccPassed) {
        if (isEmpty(ccPassed)) {
            return "Unknown";
        } else {
            String cc = stripCharsInBag(ccPassed, " -");
            if (!isCreditCard(cc)) {
                return "Unknown";
            } else if (isMasterCard(cc)) {
                return "CCT_MASTERCARD";
            } else if (isVisa(cc)) {
                return "CCT_VISA";
            } else if (isAmericanExpress(cc)) {
                return "CCT_AMERICANEXPRESS";
            } else if (isDinersClub(cc)) {
                return "CCT_DINERSCLUB";
            } else if (isDiscover(cc)) {
                return "CCT_DISCOVER";
            } else if (isEnRoute(cc)) {
                return "CCT_ENROUTE";
            } else if (isJCB(cc)) {
                return "CCT_JCB";
            } else if (isSolo(cc)) {
                return "CCT_SOLO";
            } else if (isswitch(cc)) {
                return "CCT_SWITCH";
            } else {
                return isVisaElectron(cc) ? "CCT_VISAELECTRON" : "Unknown";
            }
        }
    }

    public static boolean isCardMatch(String cardType, String cardNumberPassed) {
        if (isEmpty(cardType)) {
            return true;
        } else if (isEmpty(cardNumberPassed)) {
            return true;
        } else {
            String cardNumber = stripCharsInBag(cardNumberPassed, " -");
            if ("CCT_VISA".equalsIgnoreCase(cardType) && isVisa(cardNumber)) {
                return true;
            } else if ("CCT_MASTERCARD".equalsIgnoreCase(cardType) && isMasterCard(cardNumber)) {
                return true;
            } else if (("CCT_AMERICANEXPRESS".equalsIgnoreCase(cardType) || "CCT_AMEX".equalsIgnoreCase(cardType))
                    && isAmericanExpress(cardNumber)) {
                return true;
            } else if ("CCT_DISCOVER".equalsIgnoreCase(cardType) && isDiscover(cardNumber)) {
                return true;
            } else if ("CCT_JCB".equalsIgnoreCase(cardType) && isJCB(cardNumber)) {
                return true;
            } else if (("CCT_DINERSCLUB".equalsIgnoreCase(cardType) || "CCT_DINERS".equalsIgnoreCase(cardType))
                    && isDinersClub(cardNumber)) {
                return true;
            } else if ("CCT_CARTEBLANCHE".equalsIgnoreCase(cardType) && isCarteBlanche(cardNumber)) {
                return true;
            } else if ("CCT_ENROUTE".equalsIgnoreCase(cardType) && isEnRoute(cardNumber)) {
                return true;
            } else if ("CCT_SOLO".equalsIgnoreCase(cardType) && isSolo(cardNumber)) {
                return true;
            } else if ("CCT_SWITCH".equalsIgnoreCase(cardType) && isswitch(cardNumber)) {
                return true;
            } else {
                return "CCT_VISAELECTRON".equalsIgnoreCase(cardType) && isVisaElectron(cardNumber);
            }
        }
    }

    public static boolean isNotPoBox(String s) {
        if (isEmpty(s)) {
            return true;
        } else {
            String sl = s.toLowerCase();
            if (sl.indexOf("p.o. b") != -1) {
                return false;
            } else if (sl.indexOf("p.o.b") != -1) {
                return false;
            } else if (sl.indexOf("p.o b") != -1) {
                return false;
            } else if (sl.indexOf("p o b") != -1) {
                return false;
            } else if (sl.indexOf("po b") != -1) {
                return false;
            } else if (sl.indexOf("pobox") != -1) {
                return false;
            } else if (sl.indexOf("po#") != -1) {
                return false;
            } else if (sl.indexOf("po #") != -1) {
                return false;
            } else if (sl.indexOf("p.0. b") != -1) {
                return false;
            } else if (sl.indexOf("p.0.b") != -1) {
                return false;
            } else if (sl.indexOf("p.0 b") != -1) {
                return false;
            } else if (sl.indexOf("p 0 b") != -1) {
                return false;
            } else if (sl.indexOf("p0 b") != -1) {
                return false;
            } else if (sl.indexOf("p0box") != -1) {
                return false;
            } else if (sl.indexOf("p0#") != -1) {
                return false;
            } else {
                return sl.indexOf("p0 #") == -1;
            }
        }
    }

    public static boolean isValidUpc(String upc) {
        if (upc != null && upc.length() == 12) {
            char csum = upc.charAt(11);
            char calcSum = calcUpcChecksum(upc);
            return csum == calcSum;
        } else {
            throw new IllegalArgumentException("Invalid UPC length; must be 12 characters");
        }
    }

    public static char calcUpcChecksum(String upc) {
        return calcChecksum(upc, 12);
    }

    public static boolean isValidEan(String ean) {
        if (ean != null && ean.length() == 13) {
            char csum = ean.charAt(12);
            char calcSum = calcChecksum(ean, 12);
            return csum == calcSum;
        } else {
            throw new IllegalArgumentException("Invalid EAN length; must be 13 characters");
        }
    }

    public static char calcChecksum(String value, int length) {
        if (value != null && value.length() == length + 1) {
            value = value.substring(0, length);
        }

        if (value != null && value.length() == length) {
            int oddsum = 0;
            int evensum = 0;

            int check;
            for (check = value.length() - 1; check >= 0; --check) {
                if ((value.length() - check) % 2 == 0) {
                    evensum += Character.digit(value.charAt(check), 10);
                } else {
                    oddsum += Character.digit(value.charAt(check), 10);
                }
            }

            check = 10 - (evensum + 3 * oddsum) % 10;
            if (check >= 10) {
                check = 0;
            }

            return Character.forDigit(check, 10);
        } else {
            throw new IllegalArgumentException(
                    "Illegal size of value; must be either" + length + " or " + (length + 1) + " characters");
        }
    }

    public static String checkValidDatabaseId(String fieldStr) {
        if (fieldStr.indexOf(32) >= 0) {
            return "[space found at position " + (fieldStr.indexOf(32) + 1) + "]";
        } else if (fieldStr.indexOf(34) >= 0) {
            return "[double-quote found at position " + (fieldStr.indexOf(34) + 1) + "]";
        } else if (fieldStr.indexOf(39) >= 0) {
            return "[single-quote found at position " + (fieldStr.indexOf(39) + 1) + "]";
        } else if (fieldStr.indexOf(38) >= 0) {
            return "[ampersand found at position " + (fieldStr.indexOf(38) + 1) + "]";
        } else if (fieldStr.indexOf(63) >= 0) {
            return "[question mark found at position " + (fieldStr.indexOf(63) + 1) + "]";
        } else if (fieldStr.indexOf(60) >= 0) {
            return "[less-than sign found at position " + (fieldStr.indexOf(60) + 1) + "]";
        } else if (fieldStr.indexOf(62) >= 0) {
            return "[greater-than sign found at position " + (fieldStr.indexOf(62) + 1) + "]";
        } else if (fieldStr.indexOf(92) >= 0) {
            return "[back-slash found at position " + (fieldStr.indexOf(92) + 1) + "]";
        } else {
            return fieldStr.indexOf(47) >= 0 ? "[forward-slash found at position " + (fieldStr.indexOf(47) + 1) + "]"
                    : null;
        }
    }

    public static boolean isValidDatabaseId(String fieldStr, StringBuffer errorDetails) {
        boolean isValid = true;
        String checkMessage = checkValidDatabaseId(fieldStr);
        if (checkMessage != null) {
            isValid = false;
            errorDetails.append(checkMessage);
        }

        return isValid;
    }

    public static boolean isValidDatabaseId(String fieldStr, StringBuilder errorDetails) {
        boolean isValid = true;
        String checkMessage = checkValidDatabaseId(fieldStr);
        if (checkMessage != null) {
            isValid = false;
            errorDetails.append(checkMessage);
        }

        return isValid;
    }
}
