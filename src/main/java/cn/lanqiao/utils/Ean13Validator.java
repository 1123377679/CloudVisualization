package cn.lanqiao.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ean13Validator {
    public boolean validateEan13(String barcode) {
        // 正则表达式匹配
        String regex = "^(690|691|692|693|694|695|696|697|698|699)\\d{8}((\\d((\\d|\\p{Upper})|$))|(\\p{Upper}((\\d|\\p{Upper})|$)))$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(barcode);

        // 校验位计算
        char checkDigit = getCheckDigit(barcode.substring(0, 12));

        // 校验
        if (matcher.find()) {
            return matcher.group().charAt(12) == checkDigit;
        } else {
            return false;
        }
    }

    public char getCheckDigit(String barcode) {
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            if (i % 2 == 0) {
                sum += Character.getNumericValue(barcode.charAt(i));
            } else {
                sum += Character.getNumericValue(barcode.charAt(i)) * 3;
            }
        }
        int remainder = sum % 10;
        if (remainder == 0) {
            return '0';
        } else {
            return (char) ((10 - remainder) + '0');
        }
    }
}