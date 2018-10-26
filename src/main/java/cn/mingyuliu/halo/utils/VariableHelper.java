package cn.mingyuliu.halo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <pre>
 *     变量工具类
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/09/02
 */
@Slf4j
public final class VariableHelper {

    private static SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * 将Object对象解析为String类型的值
     *
     * @param o 被解析的对象
     * @return String类型的值 ,o为null返回null，o为Date类型将o对应的毫秒转换为String类型返回，其他类型调用toString方法
     */
    public static String parseString(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Date) {
            return String.valueOf(((Date) o).getTime());
        }

        return o.toString();
    }

    /**
     * 将Object对象解析为byte类型的值
     *
     * @param o 被解析的对象
     * @return byte类型的值, o为null、false、""返回0,true返回1,数字返回其对应的值
     */
    public static byte parseByte(Object o) {
        if (o == null) {
            return 0;
        }
        if (o instanceof Number) {
            return ((Number) o).byteValue();
        }
        if (o instanceof Boolean) {
            return (byte) (((Boolean) o) ? 1 : 0);
        }

        String s = o.toString();
        if (s.equals(StringUtils.EMPTY)) {
            return 0;
        }

        return Byte.parseByte(s);
    }

    /**
     * 将Object对象解析为short类型的值
     *
     * @param o 被解析的对象
     * @return short类型的值, o为null、false、""返回0,true返回1,数字返回其对应的值
     */
    public static short parseShort(Object o) {
        if (o == null) {
            return 0;
        }

        if (o instanceof Number) {
            return ((Number) o).shortValue();
        }

        if (o instanceof Boolean) {
            return (short) (((Boolean) o) ? 1 : 0);
        }

        String s = o.toString();
        if (s.equals(StringUtils.EMPTY)) {
            return 0;
        }

        return Short.parseShort(s);
    }

    /**
     * 将Object对象解析为int类型的值
     *
     * @param o 被解析的对象
     * @return int类型的值, o为null、false、""返回0,true返回1,数字返回其对应的值
     */
    public static int parseInt(Object o) {
        if (o == null) {
            return 0;
        }

        if (o instanceof Number) {
            return ((Number) o).intValue();
        }

        if (o instanceof Boolean) {
            return (((Boolean) o) ? 1 : 0);
        }

        String s = o.toString();
        if (s.equals(StringUtils.EMPTY)) {
            return 0;
        }

        return Integer.parseInt(s);
    }

    /**
     * 将Object对象解析为long类型的值
     *
     * @param o 被解析的对象
     * @return long类型的值, o为null、false、""返回0L,true返回1L,数字返回其对应的值
     */
    public static long parseLong(Object o) {
        if (o == null) {
            return 0L;
        }
        if (o instanceof Number) {
            return ((Number) o).longValue();
        }
        if (o instanceof Boolean) {
            return (((Boolean) o) ? 1L : 0L);
        }
        if (o instanceof Date) {
            return ((Date) o).getTime();
        }

        String s = o.toString();
        if (s.equals(StringUtils.EMPTY)) {
            return 0L;
        }

        return Long.parseLong(s);
    }

    /**
     * 将Object对象解析为float类型的值
     *
     * @param o 被解析的对象
     * @return float类型的值, o为null、false、""返回0.0F,true返回1.0F,数字返回其对应的值
     */
    public static float parseFloat(Object o) {
        if (o == null) {
            return 0.0F;
        }
        if (o instanceof Number) {
            return ((Number) o).floatValue();
        }
        if (o instanceof Boolean) {
            return (((Boolean) o) ? 1.0F : 0.0F);
        }

        String s = o.toString();
        if (s.equals(StringUtils.EMPTY)) {
            return 0.0F;
        }

        return Float.parseFloat(s);
    }

    /**
     * 将Object对象解析为double类型的值
     *
     * @param o 被解析的对象
     * @return double类型的值, o为null、false、""返回0.0D,true返回1.0D,数字返回其对应的值
     */
    public static double parseDouble(Object o) {
        if (o == null) {
            return 0.0D;
        }
        if (o instanceof Number) {
            return ((Number) o).doubleValue();
        }
        if (o instanceof Boolean) {
            return (((Boolean) o) ? 1.0D : 0.0D);
        }

        String s = o.toString();
        if (s.equals(StringUtils.EMPTY)) {
            return 0.0D;
        }

        return Double.parseDouble(s);
    }

    /**
     * 将Object对象解析为BigDecimal类型的值
     *
     * @param o 被解析的对象
     * @return BigDecimal类型的值, o为null、false、""返回0,true返回1,数字返回其对应的值
     */
    public static BigDecimal parseBigDecimal(Object o) {
        if (o == null) {
            return BigDecimal.valueOf(0L);
        }
        if (o instanceof BigDecimal) {
            return ((BigDecimal) o);
        }
        if (o instanceof Number) {
            return BigDecimal.valueOf(((Number) o).longValue());
        }
        if (o instanceof Boolean) {
            return BigDecimal.valueOf(((Boolean) o) ? 1L : 0L);
        }

        String s = o.toString();
        if (s.equals(StringUtils.EMPTY)) {
            return BigDecimal.valueOf(0L);
        }

        return new BigDecimal(s);
    }

    /**
     * 将String类型的对象解析为boolean类型的值
     *
     * @param s Stirng类型的被解析对象
     * @return true、1、t、T、y、Y返回true，否则false
     */
    public static boolean parseBoolean(String s) {
        if (s == null) {
            return false;
        }
        return (s.equalsIgnoreCase("true")
                || (s.equals("1"))
                || s.equalsIgnoreCase("T")
                || s.equalsIgnoreCase("Y"));
    }

    /**
     * 将Object类型的对象解析为boolean类型的值
     *
     * @param o Object类型的对象
     * @return o为null返回false ，o为String类型的值：true、1、-1、t、T、y、Y返回true，否则false
     */
    public static boolean parseBoolean(Object o) {
        if (o == null) {
            return false;
        }

        if (o instanceof Boolean) {
            return (Boolean) o;
        }

        return parseBoolean(o.toString());
    }

    /**
     * 判断给定的字符串是否为数字
     *
     * @param s 字符串
     * @return 是返回true，不是返回false
     */
    private static boolean isNumeric(String s) {
        int length = s.length();
        for (int i = 0; i < length; ++i) {
            char c = s.charAt(i);
            if ((((c < '0') || (c > '9'))) && (c != '.') && ((
                    (i != 0) || (c != '-')))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 将制定对象转换为日期对象
     *
     * @param o 目标对象
     * @return 日期对象
     */
    public synchronized static Date parseDate(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Date) {
            return ((Date) o);
        }
        if (o instanceof Number) {
            return new Date(((Number) o).longValue());
        }

        String s = String.valueOf(o);
        if (StringUtils.isNotEmpty(s)) {
            if (isNumeric(s)) {
                long time = Long.parseLong(s);
                return new Date(time);
            }

            int len = s.length();
            try {
                if (len < 19) {
                    if (s.indexOf(":") > 0) {
                        return DATETIME_FORMAT.parse(s);
                    }
                    return DATE_FORMAT.parse(s);
                }
            } catch (ParseException e) {
                throw new IllegalArgumentException("can't parse to date with object=" + s);
            }
        }
        throw new IllegalArgumentException("can't parse to date with object=" + o);
    }

    /**
     * 根据数据类型获得变形后的对象方法
     *
     * @param dataType 数据类型
     * @param value    数据对象
     * @return 转换后的对象
     */
    public static Object translate(int dataType, Object value) {
        switch (dataType) {
            case DataType.STRING:
                return parseString(value);
            case DataType.BOOLEAN:
                return parseBoolean(value);
            case DataType.DATE:
            case DataType.TIME:
            case DataType.DATETIME:
                return parseDate(value);
            case DataType.INTEGER:
                return parseInt(value);
            case DataType.DOUBLE:
                return parseDouble(value);
            case DataType.LONG:
                return parseLong(value);
            case DataType.FLOAT:
                return parseFloat(value);
            case DataType.BIGDECIMAL:
                return parseBigDecimal(value);
            case DataType.BYTE:
                return parseByte(value);
            case DataType.SHORT:
                return parseShort(value);
        }
        return value;
    }

}
