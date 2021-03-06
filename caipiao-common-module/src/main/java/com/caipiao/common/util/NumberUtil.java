package com.caipiao.common.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 数字工具类，如获取随机数等
 * Created by kouyi on 2017-09-22
 */
public class NumberUtil {
    private static Logger logger = LoggerFactory.getLogger(NumberUtil.class);
    public static String MIX_CHARS = "0123456789abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) {
        System.out.println(randomCode4());
    }

    /**
     * 判断是否是数字字符串.
     * @param str
     * @return true : 是; false : 不是.
     */
    public static boolean isNumber(String str){
         if(StringUtils.isBlank(str)){
              return false;
         }
        return str.trim().matches("[0-9]+");
    }

    /**
     * 生成6位随机数字验证码
     * @return
     */
    public static synchronized String randomCode() {
        return (int)((Math.random()*9+1)*100000) + "";
    }

    /**
     * 生成4位随机数字验证码
     * @return
     */
    public static synchronized String randomCode4() {
        return (int)((Math.random()*9+1)*1000) + "";
    }


    /**
     * 随机生成 n 位数字和字符的组合串.
     *
     * @param digit 位数
     * @return 组合串
     */
    public static String generateRandomMix(int digit) {
        String mix = "";
        int L = MIX_CHARS.length();
        for (int i = 0; i < digit; i++) {
            final int random = generateRandomInt(0, L - 1);
            mix += MIX_CHARS.charAt(random);
        }
        return mix;
    }

    /**
     * 随机生成 n 位数字，位数在minDigit 和 maxDigit中之间随机产生
     * 例子： generateRandomNumber(4,6)，那么就可能生成 4432, 45678, 421345。
     *
     * @param minDigit 最小位数
     * @param maxDigit 最大位数
     * @return 生成数字
     */
    public static String generateRamdonNumber(int minDigit, int maxDigit) {
        return randNumber(getCharCount(minDigit, maxDigit));
    }

    /**
     * 生成字符串 位数在minDigit 和 minDigit中之间随机产生
     *
     * @param minDigit
     * @param maxDigit
     * @return 生成字符串
     */
    public static String generateRamdonAlpha(int minDigit, int maxDigit) {
        return randAlpha(getCharCount(minDigit, maxDigit));
    }

    /**
     * 生成随机数字或字母串，由于数字的0，1，2易和字母的o，l,z混淆，使人眼难以识别，因此不生成数字和字母的混合串。
     *
     * @param minDigit
     * @param maxDigit
     * @return 生成随机数字或字母串
     * @see #generateRandomMix(int) 生成数字和字母混合串
     */
    public static String generateRandomString(int minDigit, int maxDigit) {
        if (generateRandomBoolean()) {
            return generateRamdonNumber(minDigit, maxDigit);
        } else {
            return generateRamdonAlpha(minDigit, maxDigit);
        }
    }

    /**
     * 随机生成 n 位数字组合.
     *
     * @param digit 位数
     * @return 随机数字组合.
     */
    public static String randNumber(int digit) {
        StringBuffer randomString = new StringBuffer("");
        for (int i = 0; i < digit; i++) {
            String _char = String.valueOf(generateRandomInt(0, 10));
            randomString.append(_char);
        }

        return randomString.toString();
    }

    /**
     * 随机生成 n 位字母组合.
     *
     * @param digit 位数
     * @return
     */
    public static String randAlpha(int digit) {
        StringBuffer randomString = new StringBuffer("");
        // 生成随机字母串
        for (int i = 0; i < digit; i++) {
            char c = (char) (generateRandomInt(0, 26) + 'A');
            randomString.append(c);
        }
        return randomString.toString();
    }

    /**
     * 随机生成 n 位数字组合.(为了兼容保留此方法,以后建议使用randNumber方法)
     *
     * @param digit 位数
     * @return 随机数字组合.
     */
    @Deprecated
    public static String generateRandomString(int digit) {
        return randNumber(digit);
    }

    /**
     * 生成一个指定范围内的随机整数。
     * <p>随机生成的范围包含指定的“最小值”和“最大值”。</p>
     *
     * @param min 最小范围数
     * @param max 最大范围数
     * @return 整数
     */
    public static Integer generateRandomInteger(int min, int max) {
        Long l = Math.round(Math.random() * (max - min) + min);
        return Integer.valueOf(l.intValue());
    }

    /**
     * 生成一个指定范围内的随机整数。
     * <p>随机生成的范围包含指定的“最小值”和“最大值”。</p>
     *
     * @param min 最小范围数
     * @param max 最大范围数
     * @return 整数
     */
    public static int generateRandomInt(int min, int max) {
        Random r = new Random();
        return min + r.nextInt(max - min);
    }

    /**
     * 获取随机的boolean值
     *
     * @return boolean
     */
    public static boolean generateRandomBoolean() {
        Random r = new Random();
        return r.nextBoolean();
    }

    /**
     * 对给定的字符串进行MD5加密返回一个不可逆的加密字符串.
     *
     * @param source String
     * @return String 32 位的加密字符串
     */
    public static String encyptByMD5(String source) {
        String encyptStr = "";
        if (null == source || "".equals(source)) {
            return encyptStr;
        }
        try {
            MessageDigest mgd = MessageDigest.getInstance("MD5");
            mgd.update(source.getBytes());
            BigInteger bgi = new BigInteger(1, mgd.digest());
            encyptStr = bgi.toString(16).toLowerCase();
        } catch (NoSuchAlgorithmException ex) {
            // ingore this error
        }
        return encyptStr;
    }

    /**
     * 生成长度为32位的UUID，字母全部大写。
     *
     * @return 32位字符串
     */
    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        String uuid32 = uuid.toString().replaceAll("-", "");
        return uuid32.toUpperCase();
    }

    /**
     * 取得一个数组里面最小的数值.
     * <pre>
     * 比如：
     * 传入值 values = {3,4,6,5,9,10,2}
     * 最终返回的值为 2 。
     * </pre>
     *
     * @param values 一个整数的 Set
     * @return Set 里头最小的数值
     */
    public static Integer evalMinValue(Set<Integer> values) {
        List<Integer> tempList = new ArrayList<Integer>(values);
        Collections.sort(tempList);
        return tempList.get(0);
    }

    /**
     * 取得一个数组里面最大的数值.
     * <pre>
     * 比如：
     * 传入值 values = {3,4,6,5,9,10,2}
     * 最终返回的值为 10 。
     * </pre>
     *
     * @param values 一个整数的 Set
     * @return Set 里头最大的数值
     */
    public static Integer evalMaxValue(Set<Integer> values) {
        List<Integer> tempList = new ArrayList<Integer>(values);
        Collections.sort(tempList);
        return tempList.get(tempList.size() - 1);
    }

    public static BigDecimal convertBytesToMeager(long bytes) {
        float f = (float) bytes / 1024 / 1024;
        BigDecimal bd = new BigDecimal(f).setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    private static int getCharCount(int f, int t) {
        int count = 0;
        if (f < 0) {
            f = -f;
        }
        if (t < 0) {
            t = -t;
        }
        if (f >= t) {
            count = f;
        }
        if (f == 0) {
            count = t;
        } else if (t == 0) {
            count = f;
        } else {
            count = generateRandomInt(f, t + 1);
        }
        if (count == 0) {
            count = 6;
        }
        return count;
    }


    /**
     * 将html代码的tag删除掉，只留下text，tag之间用空格代替。
     *
     * @param htmlString html 代码
     * @return 文字
     */
    public static String removeHtmlTag(String htmlString) {
        if(StringUtils.isBlank(htmlString)){
            return "";
        }
        String noHTMLString = htmlString.replaceAll("\\<.*?\\>", " ");

        //附加功能，有必要的时候再打开用
//            noHTMLString = noHTMLString.replaceAll("\r", "<br/>");
//            noHTMLString = noHTMLString.replaceAll("\n", " ");
//            noHTMLString = noHTMLString.replaceAll("\'", "&#39;");
//            noHTMLString = noHTMLString.replaceAll("\"", "&quot;");
        return noHTMLString.trim();
    }

    public static String multiplyToStr(double mulNum , int value){
        return String.valueOf(multiply(mulNum, value));
    }

    public static int multiply(double mulNum , int value){
        return new BigDecimal(String.valueOf(mulNum)).multiply(new BigDecimal(value)).intValue();
    }

}
